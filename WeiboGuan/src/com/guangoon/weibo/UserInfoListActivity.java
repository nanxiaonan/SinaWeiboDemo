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
import android.graphics.Bitmap;
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
import com.guangoon.weibo.models.WeiboInfo;
import com.guangoon.weibo.models.UserInfoItem;
import com.guangoon.weibo.sdk.net.HttpManager;
import com.guangoon.weibo.sdk.net.WeiboParameters;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

@SuppressLint("NewApi")
public class UserInfoListActivity extends Activity implements LoaderCallbacks<List<UserInfoItem>>{
	private static final String TAG = "UserInfoListActivity";
	private static final String FFIENDS_URI = "https://api.weibo.com/2/friendships/friends.json";
	private static final String WEIBO_URI = "https://api.weibo.com/2/statuses/show.json";
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

	public  static class FriendsLoader extends
			AsyncTaskLoader<List<UserInfoItem>> {

		public FriendsLoader(Context context) {
			super(context);
		}

		@Override
		public List<UserInfoItem> loadInBackground() {
			WeiboParameters p = new WeiboParameters();
			p.put("uid", Long.parseLong(mAccessToken.getUid()));
			p.put("access_token", mAccessToken.getToken());
			p.put("screen_name", mScreenName);
			p.put("count", mFriendCount);

			String result = HttpManager.openUrl(FFIENDS_URI, "GET", p);
			List<UserInfoItem> usersItemList = new ArrayList<UserInfoItem>();
			try {
				JSONObject usersObject = new JSONObject(result);
				JSONArray usersArray = usersObject.getJSONArray("users");
				for (int i = 0; i < usersArray.length(); i++) {
					UserInfoItem item = new UserInfoItem();
					User user = User.parse(usersArray.getJSONObject(i));
					if(Util.isNum(user.status)){
						WeiboParameters pw = new WeiboParameters();
						//pw.put("uid", Long.parseLong(mAccessToken.getUid()));
						pw.put("access_token", mAccessToken.getToken());		
						pw.put("id", Long.parseLong(user.status));
						String weiboResult = HttpManager.openUrl(WEIBO_URI, "GET", pw);
						Log.i(TAG,weiboResult);
						item.weiboInfo  = WeiboInfo.parse(weiboResult);
					}
					URL url = new URL(user.avatar_large);
					item.bitmap = BitmapFactory.decodeStream(url.openStream());
					item.user = user;
					usersItemList.add(item);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return usersItemList;
		}

		@Override
		protected void onStartLoading() {
			super.onStartLoading();
			forceLoad();
		}
	}

	private class UserInfoAdapter extends BaseAdapter {
		private List<UserInfoItem> mList = new ArrayList<UserInfoItem>();

		public void setList(List<UserInfoItem> list) {
			mList = new ArrayList<UserInfoItem>(list);
			notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			return mList.size();
		}

		@Override
		public UserInfoItem getItem(int position) {
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
			if(getItem(position).user.screen_name != null){
				screenName.setText(getItem(position).user.screen_name);
			}
			if(null != getItem(position).weiboInfo){
				lastWeiboText.setText(getItem(position).weiboInfo.text);
			}
			userImage.setImageBitmap(getItem(position).bitmap);
			return convertView;
		}

	}

	@Override
	public Loader<List<UserInfoItem>> onCreateLoader(int id, Bundle args) {
		return new FriendsLoader(this);
	}

	@Override
	public void onLoadFinished(Loader<List<UserInfoItem>> loader, List<UserInfoItem> data) {
		mAdapter.setList(data);
	}

	@Override
	public void onLoaderReset(Loader<List<UserInfoItem>> loader) {
		
	}
}
