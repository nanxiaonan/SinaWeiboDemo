package com.guangoon.weibo.loader;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import com.guangoon.weibo.models.WeiboInfo;
import com.guangoon.weibo.sdk.net.HttpManager;
import com.guangoon.weibo.sdk.net.WeiboParameters;

@SuppressLint("NewApi")
public class WeiboLoader extends AsyncTaskLoader<List<WeiboInfo>> {
	private static final String TAG ="WeiboLoader";
	private WeiboParameters mWiboParameters;
	private List<WeiboInfo> mWeiboInfoList;
	public WeiboLoader(Context context,WeiboParameters params) {
		super(context);
		mWiboParameters = params;
	}

	@Override
	public List<WeiboInfo> loadInBackground() {
		Log.i(TAG,"=====loadInBackground===");
		String result =  HttpManager.openUrl("https://api.weibo.com/2/statuses/user_timeline.json", "GET", mWiboParameters);
		List<WeiboInfo> weiboInfoList = new ArrayList<WeiboInfo>();
		try {
			JSONObject statusObject = new JSONObject(result);
			JSONArray statusArray = statusObject.getJSONArray("statuses");
			for (int i = 0; i < statusArray.length(); i++) {
				WeiboInfo weiboInfo = WeiboInfo.parse(statusArray.getJSONObject(i));
				weiboInfoList.add(weiboInfo);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return weiboInfoList;
	}

	@Override
	protected void onStartLoading() {
		super.onStartLoading();
		Log.i(TAG,"=====onStartLoading===");
		Log.i(TAG,"isReset()===" + isReset());
		Log.i(TAG,"isStarted()===" + isStarted());
		if(takeContentChanged() || mWeiboInfoList == null)
		{
			forceLoad();
		}
		
	}

	@Override
	public void deliverResult(List<WeiboInfo> data) {
		super.deliverResult(data);
		mWeiboInfoList = data;
		if(isStarted()){
			super.deliverResult(data);
		}
	}
	
	
	
	

}
