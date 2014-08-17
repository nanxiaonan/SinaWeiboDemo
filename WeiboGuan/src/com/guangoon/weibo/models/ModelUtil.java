package com.guangoon.weibo.models;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;
import android.util.Log;

public class ModelUtil {
	private static final String TAG = "ModelUtil";
	public static final String THUMBNAIL = "thumbnail";
	public static final String BMIDDLE = "bmiddle";
	public static final String ORIGINAL = "original";
	
	public static List<String> getPicUriList(WeiboInfo weiboInfo) {
		List<String> picUriList = new ArrayList<String>();
		Log.i(TAG,"weiboInfo.original_pic==" + weiboInfo.original_pic);
		try {
			JSONArray thumbnailUriArray = new JSONArray(weiboInfo.pic_urls);
			for (int i = 0; i < thumbnailUriArray.length(); i++) {
				JSONObject thumbnailUrl = new JSONObject(thumbnailUriArray.getString(i));
				Log.i(TAG,"thumbnail_pic==" + thumbnailUrl.optString("thumbnail_pic"));
				String[] tmpStrings = thumbnailUrl.optString("thumbnail_pic").split("/");
				for(String string:tmpStrings)
				{
					Log.i(TAG,"tmpString==" + string);
				}
				picUriList.add(thumbnailUrl.optString("thumbnail_pic"));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return picUriList;
	}
	
	public static List<String> getBmiddle_pic(WeiboInfo weiboInfo){
		List<String> bmiddleUriList = new ArrayList<String>();
		try {
			JSONArray thumbnailUriArray = new JSONArray(weiboInfo.pic_urls);
			for (int i = 0; i < thumbnailUriArray.length(); i++) {
				JSONObject thumbnailUrl = new JSONObject(thumbnailUriArray.getString(i));
				String thumbnailURL = thumbnailUrl.optString("thumbnail_pic");
				Log.i(TAG,"thumbnail_pic==" + thumbnailURL);
				if((!TextUtils.isEmpty(thumbnailURL))) {
					String[] URIItems = thumbnailURL.split("/");
					if(URIItems.length == 5 && URIItems[3].equals(THUMBNAIL)){
						StringBuffer sb = new StringBuffer();
						sb.append("http://ww1.sinaimg.cn/bmiddle/");
						sb.append(URIItems[4]);
						bmiddleUriList.add(sb.toString());
					}else {
						continue;
					}
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return bmiddleUriList;
	}
	
	public static List<String> getOriginal_pic(WeiboInfo weiboInfo){
		List<String> originalUriList = new ArrayList<String>();
		Log.i(TAG,"weiboInfo.original_pic==" + weiboInfo.original_pic);
		try {
			JSONArray thumbnailUriArray = new JSONArray(weiboInfo.pic_urls);
			for (int i = 0; i < thumbnailUriArray.length(); i++) {
				JSONObject thumbnailUrl = new JSONObject(thumbnailUriArray.getString(i));
				String thumbnailURL = thumbnailUrl.optString("thumbnail_pic");
				Log.i(TAG,"thumbnail_pic==" + thumbnailURL);
				if((!TextUtils.isEmpty(thumbnailURL))) {
					String[] URIItems = thumbnailURL.split("/");
					if(URIItems.length == 5 && URIItems[3].equals(THUMBNAIL)){
						StringBuffer sb = new StringBuffer();
						sb.append("http://ww1.sinaimg.cn/original/");
						sb.append(URIItems[4]);
						originalUriList.add(sb.toString());
					}else {
						continue;
					}
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return originalUriList;
	}
}
