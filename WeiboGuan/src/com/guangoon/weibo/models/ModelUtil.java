package com.guangoon.weibo.models;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class ModelUtil {
	private static final String TAG = "ModelUtil";
	public static List<String> getPicUriList(WeiboInfo weiboInfo) {
		List<String> picUriList = new ArrayList<String>();
		try {
			JSONArray thumbnailUriArray = new JSONArray(weiboInfo.pic_urls);
			Log.i(TAG,"weiboInfo.pic_urls==" + weiboInfo.pic_urls);
			for (int i = 0; i < thumbnailUriArray.length(); i++) {
				JSONObject thumbnailUrl = new JSONObject(thumbnailUriArray.getString(i));
				picUriList.add(thumbnailUrl.optString("thumbnail_pic"));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return picUriList;
	}
}
