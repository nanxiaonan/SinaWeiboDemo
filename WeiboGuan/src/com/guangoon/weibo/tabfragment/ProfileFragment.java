package com.guangoon.weibo.tabfragment;

import java.net.URL;

import com.guangoon.weibo.AccessTokenKeeper;
import com.guangoon.weibo.R;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.openapi.UsersAPI;
import com.sina.weibo.sdk.openapi.models.ErrorInfo;
import com.sina.weibo.sdk.openapi.models.User;
import com.sina.weibo.sdk.utils.LogUtil;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileFragment extends Fragment {
	private static final String TAG = "ProfileFragment";
	private View mUserInfoView;
	private TextView mUserNameText;
	private TextView mUserDescriptionText;
	private ImageView mUserPhoto;
	/** 当前 Token 信息 */
	private Oauth2AccessToken mAccessToken;
	/** 用户信息接口 */
	private UsersAPI mUsersAPI;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 获取当前已保存过的 Token
		mAccessToken = AccessTokenKeeper.readAccessToken(getActivity());
		// 获取用户信息接口
		mUsersAPI = new UsersAPI(mAccessToken);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if (mAccessToken != null && mAccessToken.isSessionValid()) {
			long uid = Long.parseLong(mAccessToken.getUid());
			mUsersAPI.show(uid, mListener);
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_profile, null);
		mUserInfoView = rootView.findViewById(R.id.user_info_view);
		mUserInfoView.setOnClickListener(mClickListener);
		mUserNameText = (TextView) rootView.findViewById(R.id.user_name);
		mUserDescriptionText = (TextView) rootView
				.findViewById(R.id.user_intro);
		mUserPhoto = (ImageView) rootView.findViewById(R.id.head_portrait);
		return rootView;
	}

	private View.OnClickListener mClickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.user_info_view:
				break;

			default:
				break;
			}

		}
	};

	private RequestListener mListener = new RequestListener() {
		@Override
		public void onComplete(String response) {
			if (!TextUtils.isEmpty(response)) {
				LogUtil.i(TAG, response);
				// 调用 User#parse 将JSON串解析成User对象
				User user = User.parse(response);
				if (user != null) {
					Toast.makeText(ProfileFragment.this.getActivity(),
							"获取User信息成功，用户昵称：" + user.screen_name,
							Toast.LENGTH_LONG).show();
					mUserNameText.setText(user.name);
					mUserDescriptionText.setText(ProfileFragment.this
							.getActivity().getString(
									R.string.user_description_head)
							+ user.description);
					new ImageDecodeAsyncTask().execute(user.profile_image_url);
				} else {
					Toast.makeText(ProfileFragment.this.getActivity(),
							response, Toast.LENGTH_LONG).show();
				}
			}
		}

		@Override
		public void onWeiboException(WeiboException e) {
			LogUtil.e(TAG, e.getMessage());
			ErrorInfo info = ErrorInfo.parse(e.getMessage());
			Toast.makeText(ProfileFragment.this.getActivity(), info.toString(),
					Toast.LENGTH_LONG).show();
		}
	};

	private class ImageDecodeAsyncTask extends
			AsyncTask<String, Integer, Bitmap> {

		@Override
		protected Bitmap doInBackground(String... params) {

			Bitmap bitmap = null;
			try {
				URL url = new URL(params[0]);
				bitmap = BitmapFactory.decodeStream(url.openStream());
			} catch (Exception e) {
				e.printStackTrace();
			}
			return bitmap;
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			mUserPhoto.setImageBitmap(result);
		}

	}

}
