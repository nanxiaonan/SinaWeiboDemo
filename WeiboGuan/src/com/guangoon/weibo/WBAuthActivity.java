package com.guangoon.weibo;

import java.text.SimpleDateFormat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuth;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;

public class WBAuthActivity extends Activity {
	private static final String TAG = "WBAuthActivity";
	private SsoHandler mSsoHandler;
	private WeiboAuth mWeiboAuth;
	private Oauth2AccessToken mAccessToken;
	private TextView mTokenText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_auth);
		mTokenText = (TextView) findViewById(R.id.token_text_view);
		mWeiboAuth = new WeiboAuth(this, Constants.APP_KEY,
				Constants.REDIRECT_URL, Constants.SCOPE);
		findViewById(R.id.obtain_token_via_sso).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						mSsoHandler = new SsoHandler(WBAuthActivity.this,
								mWeiboAuth);
						mSsoHandler.authorize(new AuthListener());
					}
				});
		findViewById(R.id.obtain_token_via_signature).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						mWeiboAuth.anthorize(new AuthListener());
					}
				});
		
		findViewById(R.id.obtain_token_via_code).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(WBAuthActivity.this,WBAuthCodeActivity.class));
				
			}
		});

		// 从 SharedPreferences 中读取上次已保存好 AccessToken 等信息，
		// 第一次启动本应用，AccessToken 不可用
		mAccessToken = AccessTokenKeeper.readAccessToken(this);
		if (mAccessToken.isSessionValid()) {
			updateTokenView(true);
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	class AuthListener implements WeiboAuthListener {

		@Override
		public void onCancel() {
			Log.i(TAG, "onCancel");
			Toast.makeText(WBAuthActivity.this,
					R.string.weibosdk_demo_toast_auth_canceled,
					Toast.LENGTH_LONG).show();
		}

		@Override
		public void onComplete(Bundle bundle) {
			Log.i(TAG, "onComplete");
			// 从 Bundle 中解析 Token
			mAccessToken = Oauth2AccessToken.parseAccessToken(bundle);
			if (mAccessToken.isSessionValid()) {
				// 显示 Token
				 updateTokenView(false);

				// 保存 Token 到 SharedPreferences
				AccessTokenKeeper.writeAccessToken(WBAuthActivity.this,
						mAccessToken);
				Toast.makeText(WBAuthActivity.this,
						R.string.weibosdk_demo_toast_auth_success,
						Toast.LENGTH_SHORT).show();
			} else {
				// 以下几种情况，您会收到 Code：
				// 1. 当您未在平台上注册的应用程序的包名与签名时；
				// 2. 当您注册的应用程序包名与签名不正确时；
				// 3. 当您在平台上注册的包名和签名与您当前测试的应用的包名和签名不匹配时。
				String code = bundle.getString("code");
				String message = getString(R.string.weibosdk_demo_toast_auth_failed);
				if (!TextUtils.isEmpty(code)) {
					message = message + "\nObtained the code: " + code;
				}
				Toast.makeText(WBAuthActivity.this, message, Toast.LENGTH_LONG)
						.show();
			}
		}

		@Override
		public void onWeiboException(WeiboException weiboException) {
			Log.i(TAG, "onWeiboException");
			Toast.makeText(WBAuthActivity.this,
					"Auth exception : " + weiboException.getMessage(),
					Toast.LENGTH_LONG).show();
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		// SSO 授权回调
		// 重要：发起 SSO 登陆的 Activity 必须重写 onActivityResult
		if (mSsoHandler != null) {
			mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
		}
	}

	/**
	 * 显示当前 Token 信息。
	 * 
	 * @param hasExisted
	 *            配置文件中是否已存在 token 信息并且合法
	 */
	private void updateTokenView(boolean hasExisted) {
		
		String date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
				.format(new java.util.Date(mAccessToken.getExpiresTime()));
		String format = getString(R.string.weibosdk_demo_token_to_string_format_1);
		Log.i(TAG,"date==" + date);
		Log.i(TAG,"format == " + format);
		mTokenText
				.setText(String.format(format, mAccessToken.getToken(), date));
		
		String message = String.format(format, mAccessToken.getToken(), date);
		Log.i(TAG,"message == " + message);
		if (hasExisted) {
			message = getString(R.string.weibosdk_demo_token_has_existed)
					+ "\n" + message;
		}
		mTokenText.setText(message);
	}

}
