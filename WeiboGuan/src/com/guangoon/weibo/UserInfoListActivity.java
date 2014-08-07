package com.guangoon.weibo;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Loader;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.guangoon.weibo.models.User;
import com.guangoon.weibo.sdk.net.HttpManager;
import com.guangoon.weibo.sdk.net.WeiboParameters;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

@SuppressLint("NewApi")
public class UserInfoListActivity extends Activity implements
		LoaderCallbacks<List<User>> {
	private static final String TAG = "UserInfoListActivity";
	private static final String FFIENDS_URI = "https://api.weibo.com/2/friendships/friends.json";
	private static final int LOADER_FRIENDS_ID = 1;
	private static int mFriendCount;
	private static String mScreenName;
	private static Oauth2AccessToken mAccessToken;
	private UserInfoAdapter mAdapter;
	private ListView mListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_info_list_layout);
		mAccessToken = AccessTokenKeeper.readAccessToken(this);
		mListView = (ListView) findViewById(R.id.list);
		mAdapter = new UserInfoAdapter();
		mListView.setAdapter(mAdapter);
		if (getIntent() != null) {
			mFriendCount = getIntent().getIntExtra("friendCount", 0);
			mScreenName = getIntent().getStringExtra("screenName");
		}
		Log.i(TAG, "mFriendCount == " + mFriendCount);
		Log.i(TAG, "mScreenName ==" + mScreenName);
		if (mScreenName != null && mFriendCount > 0) {
			getLoaderManager().initLoader(LOADER_FRIENDS_ID, null, this);
		}

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	private static class FriendsLoader extends AsyncTaskLoader<List<User>> {

		public FriendsLoader(Context context) {
			super(context);
		}

		@Override
		public List<User> loadInBackground() {
			WeiboParameters p = new WeiboParameters();
			p.put("uid", Long.parseLong(mAccessToken.getUid()));
			p.put("access_token", mAccessToken.getToken());
			p.put("screen_name", mScreenName);
			p.put("count", mFriendCount);

			String result = HttpManager.openUrl(FFIENDS_URI, "GET", p);
			List<User> usersList = new ArrayList<User>();
			try {
				JSONObject usersObject = new JSONObject(result);
				JSONArray usersArray = usersObject.getJSONArray("users");
				Log.i(TAG, "user count=====" + usersArray.length());
				Log.i(TAG,
						"next_cursor====" + usersObject.getInt("next_cursor"));
				Log.i(TAG,
						"previous_cursor===="
								+ usersObject.getInt("previous_cursor"));
				for (int i = 0; i < usersArray.length(); i++) {
					Log.i(TAG,"=================hhh==== "+ usersArray.getJSONObject(i));
					User user = User.parse(usersArray.getJSONObject(i));
					 Log.i(TAG,"==================" + usersArray.length()
					 +"==========================================");
					Log.i(TAG,user.toString());
					 Log.i(TAG,"============================================================");
					usersList.add(user);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return usersList;
		}

		@Override
		protected void onStartLoading() {
			super.onStartLoading();
			forceLoad();
		}
	}

	@Override
	public Loader<List<User>> onCreateLoader(int id, Bundle args) {
		return new FriendsLoader(this);
	}

	@Override
	public void onLoadFinished(Loader<List<User>> loader, List<User> data) {
		mAdapter.setList(data);
	}

	@Override
	public void onLoaderReset(Loader<List<User>> loader) {

	}

	private class UserInfoAdapter extends BaseAdapter {
		private List<User> mList = new ArrayList<User>();

		public void setList(List<User> list) {
			mList = list;
			notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			return mList.size();
		}

		@Override
		public User getItem(int position) {
			return mList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = View.inflate(UserInfoListActivity.this,
						R.layout.user_info_item, null);
			}
			TextView screenName = (TextView) convertView
					.findViewById(R.id.screen_name);
			TextView lastWeiboText = (TextView) convertView
					.findViewById(R.id.user_last_weibo_text);
			ImageView userImage = (ImageView) convertView
					.findViewById(R.id.user_image);
			screenName.setText(getItem(position).screen_name);
			lastWeiboText.setText(getItem(position).screen_name);
			/*
			URL url;
			try {
				url = new URL(getItem(position).avatar_large);
				userImage.setImageBitmap(BitmapFactory.decodeStream(url
						.openStream()));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			*/
			return convertView;
		}

	}

}
