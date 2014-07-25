package com.guangoon.weibo.tabfragment;

import org.apache.http.HttpHost;

import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.guangoon.weibo.AccessTokenKeeper;
import com.guangoon.weibo.R;
import com.guangoon.weibo.sdk.net.HttpManager;
import com.guangoon.weibo.sdk.net.WeiboParameters;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

public class HomeFragment extends Fragment {
	private static final String TAG = "HomeFragment";
	protected static final String API_SERVER = "https://api.weibo.com/2";
	private static final String API_BASE_URL = API_SERVER + "/users";
	private static final String User_Uri = API_BASE_URL + "/show.json";
	protected Oauth2AccessToken mAccessToken;
	private TextView mApnText;
	private TextView mUserInfoText;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mAccessToken = AccessTokenKeeper.readAccessToken(getActivity());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_home, null);
		mApnText = (TextView) view.findViewById(R.id.apn_text);
		mUserInfoText = (TextView) view.findViewById(R.id.user_info_text);
		view.findViewById(R.id.get_APN).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// HttpHost host = getAPN();
						// Log.i(TAG,"hostNanme ===" + host.getHostName());
						// mApnText.setText(host.getHostName());
					}
				});

		view.findViewById(R.id.get_user_info).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						new GetTestAsyncTask().execute();
					}
				});
		return view;
	}

	public HttpHost getAPN() {
		HttpHost proxy = null;
		Uri uri = Uri.parse("content://telephony/carriers/preferapn");
		Cursor mCursor = null;
		if (getActivity() != null) {
			mCursor = getActivity().getContentResolver().query(uri, null, null,
					null, null);
		}
		if ((mCursor != null) && (mCursor.moveToFirst())) {
			String proxyStr = mCursor
					.getString(mCursor.getColumnIndex("proxy"));
			if ((proxyStr != null) && (proxyStr.trim().length() > 0)) {
				proxy = new HttpHost(proxyStr, 80);
			}
			mCursor.close();
		}
		return proxy;
	}

	private class GetTestAsyncTask extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... params) {
			WeiboParameters p = new WeiboParameters();
			p.put("uid", Long.parseLong(mAccessToken.getUid()));
			p.put("access_token", mAccessToken.getToken());
			
			return HttpManager.openUrl(User_Uri, "GET", p);
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			Log.i(TAG,result);
			mUserInfoText.setText(result);
		}
		
		

	}

}
