package com.guangoon.weibo;

import java.text.SimpleDateFormat;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuth;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.constant.WBConstants;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.AsyncWeiboRunner;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.net.WeiboParameters;
import com.sina.weibo.sdk.utils.LogUtil;
import com.sina.weibo.sdk.utils.UIUtils;

public class WBAuthCodeActivity extends Activity {
	private static final String TAG = "WBAuthCodeActivity";
	private static final String WEB_DEMO_CODE = "43ae7b87892b47e74fc300ef11b9f5c9";
	private static final String OAUTH2_ACCESS_TOKEN_URL = "https://open.weibo.cn/oauth2/access_token";
	private TextView mGetCode;
	private TextView mGetToken;
	private WeiboAuth mWeiboAuth;
	private String mCode;
	private Button mCodeButton;
	private Button mTokenButton;
	private Oauth2AccessToken mAccessToken;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.auth_code_activity);
		mGetCode = (TextView) findViewById(R.id.code);
		mGetToken = (TextView) findViewById(R.id.token);
		mWeiboAuth = new WeiboAuth(this, Constants.APP_KEY,
				Constants.REDIRECT_URL, Constants.SCOPE);
		mCodeButton = (Button) findViewById(R.id.get_code);
		mCodeButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				mWeiboAuth.authorize(new AuthListener(), WeiboAuth.OBTAIN_AUTH_CODE);
			}
		});
		mTokenButton = (Button) findViewById(R.id.get_token);
		mTokenButton.setEnabled(false);
		mTokenButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				 fetchTokenAsync(mCode, WEB_DEMO_CODE);
			}
		});
	}

	/**
	 * 微博认证授权回调类。
	 */
	class AuthListener implements WeiboAuthListener {

		@Override
		public void onComplete(Bundle values) {
			if (null == values) {
				Toast.makeText(WBAuthCodeActivity.this,
						R.string.weibosdk_demo_toast_obtain_code_failed,
						Toast.LENGTH_SHORT).show();
				return;
			}

			String code = values.getString("code");
			if (TextUtils.isEmpty(code)) {
				Toast.makeText(WBAuthCodeActivity.this,
						R.string.weibosdk_demo_toast_obtain_code_failed,
						Toast.LENGTH_SHORT).show();
				return;
			}

			mCode = code;
			mGetCode.setText(code);
			mTokenButton.setEnabled(true);
			mGetToken.setText("");
			Toast.makeText(WBAuthCodeActivity.this,
					R.string.weibosdk_demo_toast_obtain_code_success,
					Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onCancel() {
			Toast.makeText(WBAuthCodeActivity.this,
					R.string.weibosdk_demo_toast_auth_canceled,
					Toast.LENGTH_LONG).show();
		}

		@Override
		public void onWeiboException(WeiboException e) {
			UIUtils.showToast(WBAuthCodeActivity.this,
					"Auth exception : " + e.getMessage(), Toast.LENGTH_LONG);
		}
	}

	/**
	 * 异步获取 Token。
	 * 
	 * @param authCode
	 *            授权 Code，该 Code 是一次性的，只能被获取一次 Token
	 * @param appSecret
	 *            应用程序的 APP_SECRET，请务必妥善保管好自己的 APP_SECRET，
	 *            不要直接暴露在程序中，此处仅作为一个DEMO来演示。
	 */
	public void fetchTokenAsync(String authCode, String appSecret) {

		WeiboParameters requestParams = new WeiboParameters();
		requestParams.put(WBConstants.AUTH_PARAMS_CLIENT_ID, Constants.APP_KEY);
		requestParams.put(WBConstants.AUTH_PARAMS_CLIENT_SECRET, appSecret);
		requestParams.put(WBConstants.AUTH_PARAMS_GRANT_TYPE,
				"authorization_code");
		requestParams.put(WBConstants.AUTH_PARAMS_CODE, authCode);
		requestParams.put(WBConstants.AUTH_PARAMS_REDIRECT_URL,
				Constants.REDIRECT_URL);

		// 异步请求，获取 Token
		AsyncWeiboRunner.requestAsync(OAUTH2_ACCESS_TOKEN_URL, requestParams,
				"POST", new RequestListener() {
					@Override
					public void onComplete(String response) {
						LogUtil.d(TAG, "Response: " + response);

						// 获取 Token 成功
						Oauth2AccessToken token = Oauth2AccessToken
								.parseAccessToken(response);
						if (token != null && token.isSessionValid()) {
							LogUtil.d(TAG, "Success! " + token.toString());

							mAccessToken = token;
							String date = new SimpleDateFormat(
									"yyyy/MM/dd HH:mm:ss")
									.format(new java.util.Date(mAccessToken
											.getExpiresTime()));
							String format = getString(R.string.weibosdk_demo_token_to_string_format_1);
							mGetToken.setText(String.format(format,
									mAccessToken.getToken(), date));
							mCodeButton.setEnabled(false);

							Toast.makeText(
									WBAuthCodeActivity.this,
									R.string.weibosdk_demo_toast_obtain_token_success,
									Toast.LENGTH_SHORT).show();
						} else {
							LogUtil.d(TAG, "Failed to receive access token");
						}
					}

					@Override
					public void onWeiboException(WeiboException e) {
						LogUtil.e(TAG, "onWeiboException： " + e.getMessage());
						Toast.makeText(
								WBAuthCodeActivity.this,
								R.string.weibosdk_demo_toast_obtain_token_failed,
								Toast.LENGTH_SHORT).show();
					}
				});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

}
