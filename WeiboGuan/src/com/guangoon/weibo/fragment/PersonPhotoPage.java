package com.guangoon.weibo.fragment;

import java.util.List;

import com.guangoon.weibo.AccessTokenKeeper;
import com.guangoon.weibo.R;
import com.guangoon.weibo.loader.WeiboLoader;
import com.guangoon.weibo.models.OwnerUserInfo;
import com.guangoon.weibo.models.WeiboInfo;
import com.guangoon.weibo.sdk.net.WeiboParameters;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

@SuppressLint("NewApi")
public class PersonPhotoPage extends Fragment implements
		LoaderCallbacks<List<WeiboInfo>> {
	private static final String TAG = "PersonPhotoPage";
	private  Oauth2AccessToken mAccessToken;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(TAG, OwnerUserInfo.getInstance(getActivity()).toString());
		mAccessToken = AccessTokenKeeper.readAccessToken(getActivity());
		getLoaderManager().initLoader(1, null, this);
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_person_weibo_page, null);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onStart() {
		super.onStart();
	}

	@Override
	public Loader<List<WeiboInfo>> onCreateLoader(int id, Bundle args) {
		if (id == 1) {
			WeiboParameters params = new WeiboParameters();
			params.put("access_token", mAccessToken.getToken());
			params.put("feature", 2);
			params.put("trim_user", 1);
			
			return new WeiboLoader(getActivity(), params);
		}
		return null;
	}

	@Override
	public void onLoadFinished(Loader<List<WeiboInfo>> loader,
			List<WeiboInfo> data) {

	}

	@Override
	public void onLoaderReset(Loader<List<WeiboInfo>> loader) {

	}

}
