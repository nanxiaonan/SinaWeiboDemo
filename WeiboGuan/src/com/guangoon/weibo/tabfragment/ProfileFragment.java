package com.guangoon.weibo.tabfragment;

import java.net.URL;

import com.guangoon.weibo.AccessTokenKeeper;
import com.guangoon.weibo.R;
import com.guangoon.weibo.models.User;
import com.guangoon.weibo.provider.WeiboColumn;
import com.guangoon.weibo.sdk.net.HttpManager;
import com.guangoon.weibo.sdk.net.WeiboParameters;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.openapi.UsersAPI;

import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
	private CursorLoader mUserLoader;
	private static final int USER_CURSOR_LOADER = 1;
	protected static final String API_SERVER = "https://api.weibo.com/2";
	private static final String API_BASE_URL = API_SERVER + "/users";
	private static final String User_Uri = API_BASE_URL + "/show.json";
	private String[] mUserProjection = new String[] {
			WeiboColumn.UserAdminColumn.ID, WeiboColumn.UserAdminColumn.IDSTR,
			WeiboColumn.UserAdminColumn.SCREEN_NAME,
			WeiboColumn.UserAdminColumn.NAME,
			WeiboColumn.UserAdminColumn.ALLOW_ALL_ACT_MSG,
			WeiboColumn.UserAdminColumn.ALLOW_ALL_COMMENT,
			WeiboColumn.UserAdminColumn.AVATAR_HD,
			WeiboColumn.UserAdminColumn.AVATAR_LARGE,
			WeiboColumn.UserAdminColumn.BI_FOLLOWERS_COUNT,
			WeiboColumn.UserAdminColumn.CITY,
			WeiboColumn.UserAdminColumn.CREATED_AT,
			WeiboColumn.UserAdminColumn.DESCRIPTION,
			WeiboColumn.UserAdminColumn.DOMAIN,
			WeiboColumn.UserAdminColumn.FAVOURITES_COUNT,
			WeiboColumn.UserAdminColumn.FOLLOW_ME,
			WeiboColumn.UserAdminColumn.FOLLOWERS_COUNT,
			WeiboColumn.UserAdminColumn.FOLLOWING,
			WeiboColumn.UserAdminColumn.FRIENDS_COUNT,
			WeiboColumn.UserAdminColumn.GENDER,
			WeiboColumn.UserAdminColumn.GEO_ENABLED,
			WeiboColumn.UserAdminColumn.LANG,
			WeiboColumn.UserAdminColumn.LOCATION,
			WeiboColumn.UserAdminColumn.ONLINE_STATUS,
			WeiboColumn.UserAdminColumn.PROFILE_IMAGE_URI,
			WeiboColumn.UserAdminColumn.PROFILE_URI,
			WeiboColumn.UserAdminColumn.PROVINCE,
			WeiboColumn.UserAdminColumn.REMARK,
			WeiboColumn.UserAdminColumn.STATUS,
			WeiboColumn.UserAdminColumn.STATUSES_COUNT,
			WeiboColumn.UserAdminColumn.URI,
			WeiboColumn.UserAdminColumn.VERIFIED_REASON,
			WeiboColumn.UserAdminColumn.VERIFIED_TYPE,
			WeiboColumn.UserAdminColumn.VERIFILED,
			WeiboColumn.UserAdminColumn.WEIHAO, };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 获取当前已保存过的 Token
		mAccessToken = AccessTokenKeeper.readAccessToken(getActivity());
		// 获取用户信息接口
		mUsersAPI = new UsersAPI(mAccessToken);

		mUserLoader = new CursorLoader(getActivity(),
				WeiboColumn.UserAdminColumn.CONTENT_URI, mUserProjection, null,
				null, null);
		getLoaderManager().initLoader(USER_CURSOR_LOADER, null,
				mUserLoaderCallback);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if (mAccessToken != null && mAccessToken.isSessionValid()) {
			long uid = Long.parseLong(mAccessToken.getUid());
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

	private LoaderCallbacks<Cursor> mUserLoaderCallback = new LoaderCallbacks<Cursor>() {

		@Override
		public android.support.v4.content.Loader<Cursor> onCreateLoader(
				int arg0, Bundle arg1) {
			if (arg0 == USER_CURSOR_LOADER) {
				return mUserLoader;
			}
			return null;
		}

		@Override
		public void onLoadFinished(
				android.support.v4.content.Loader<Cursor> loader, Cursor cursor) {
			if (loader.getId() == USER_CURSOR_LOADER) {
				if (cursor != null && cursor.getCount() > 0) {
					cursor.moveToFirst();
					mUserNameText
							.setText(cursor.getString(cursor
									.getColumnIndexOrThrow(WeiboColumn.UserAdminColumn.SCREEN_NAME)));
					mUserDescriptionText
							.setText(cursor.getString(cursor
									.getColumnIndexOrThrow(WeiboColumn.UserAdminColumn.DESCRIPTION)));
				}else
				{
					new Thread(new Runnable() {
						
						@Override
						public void run() {
							WeiboParameters p = new WeiboParameters();
							p.put("uid", Long.parseLong(mAccessToken.getUid()));
							p.put("access_token", mAccessToken.getToken());
							
							String result = HttpManager.openUrl(User_Uri, "GET", p);
							User user = User.parse(result);
							ContentValues values = new ContentValues();
							values.put(WeiboColumn.UserAdminColumn.ALLOW_ALL_ACT_MSG, user.allow_all_act_msg);
							values.put(WeiboColumn.UserAdminColumn.ALLOW_ALL_COMMENT, user.allow_all_comment);
							values.put(WeiboColumn.UserAdminColumn.AVATAR_HD, user.avatar_hd);
							values.put(WeiboColumn.UserAdminColumn.AVATAR_LARGE, user.avatar_large);
							values.put(WeiboColumn.UserAdminColumn.BI_FOLLOWERS_COUNT, user.bi_followers_count);
							values.put(WeiboColumn.UserAdminColumn.CITY, user.city);
							values.put(WeiboColumn.UserAdminColumn.CREATED_AT, user.created_at);
							values.put(WeiboColumn.UserAdminColumn.DESCRIPTION, user.description);
							values.put(WeiboColumn.UserAdminColumn.DOMAIN, user.domain);
							values.put(WeiboColumn.UserAdminColumn.FAVOURITES_COUNT, user.favourites_count);
							values.put(WeiboColumn.UserAdminColumn.FOLLOW_ME, user.follow_me);
							values.put(WeiboColumn.UserAdminColumn.FOLLOWERS_COUNT, user.followers_count);
							values.put(WeiboColumn.UserAdminColumn.FOLLOWING, user.following);
							values.put(WeiboColumn.UserAdminColumn.FRIENDS_COUNT, user.friends_count);
							values.put(WeiboColumn.UserAdminColumn.GENDER, user.gender);
							values.put(WeiboColumn.UserAdminColumn.GEO_ENABLED, user.geo_enabled);
							values.put(WeiboColumn.UserAdminColumn.ID, user.id);
							values.put(WeiboColumn.UserAdminColumn.IDSTR, user.idstr);
							values.put(WeiboColumn.UserAdminColumn.LANG, user.lang);
							values.put(WeiboColumn.UserAdminColumn.LOCATION, user.location);
							values.put(WeiboColumn.UserAdminColumn.NAME, user.name);
							values.put(WeiboColumn.UserAdminColumn.ONLINE_STATUS, user.online_status);
							values.put(WeiboColumn.UserAdminColumn.PROFILE_IMAGE_URI, user.profile_image_url);
							values.put(WeiboColumn.UserAdminColumn.PROVINCE, user.province);
							values.put(WeiboColumn.UserAdminColumn.REMARK, user.remark);
							values.put(WeiboColumn.UserAdminColumn.SCREEN_NAME, user.screen_name);
							values.put(WeiboColumn.UserAdminColumn.STATUS, user.status);
							values.put(WeiboColumn.UserAdminColumn.STATUSES_COUNT, user.statuses_count);
							values.put(WeiboColumn.UserAdminColumn.URI, user.uri);
							values.put(WeiboColumn.UserAdminColumn.VERIFIED_REASON, user.verified_reason);
							values.put(WeiboColumn.UserAdminColumn.VERIFIED_TYPE, user.verified_type);
							values.put(WeiboColumn.UserAdminColumn.VERIFILED, user.verified);	
							getActivity().getContentResolver().insert(WeiboColumn.UserAdminColumn.CONTENT_URI,
									values);
						}
					}).start();
				}
			}
		}

		@Override
		public void onLoaderReset(android.support.v4.content.Loader<Cursor> arg0) {
		}

	};
}
