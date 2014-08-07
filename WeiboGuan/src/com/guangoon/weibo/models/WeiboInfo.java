package com.guangoon.weibo.models;

import org.json.JSONException;
import org.json.JSONObject;

public class WeiboInfo {
	public String created_at;
	public String id;
	public String mid;
	public String idstr;
	public String text;
	public String source;
	public boolean favorited;
	public boolean truncated;
	public String in_reply_to_status_id;
	public String in_reply_to_user_id;
	public String in_reply_to_screen_name;
	public String thumbnail_pic;
	public String bmiddle_pic;
	public String  original_pic;
	public String geo;
	public String user;
	public String retweeted_status;
	public int reposts_count;
	public int comments_counts;
	public int attitudes_count;
	public int mlevel;
	public String visible;
	public String pic_urls;
	public String ad;
	
	public static WeiboInfo parse(String jsonString) {
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(jsonString);
			return parse(jsonObject);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static WeiboInfo parse(JSONObject jsonObject) {
		if (jsonObject == null) {
			return null;
		}
		
		WeiboInfo weiboInfo = new WeiboInfo();
		weiboInfo.created_at = jsonObject.optString("created_at","");
		weiboInfo.id = jsonObject.optString("id","");
		weiboInfo.mid = jsonObject.optString("mid","");
		weiboInfo.idstr = jsonObject.optString("idstr","");
		weiboInfo.text = jsonObject.optString("text","");
		weiboInfo.source = jsonObject.optString("source");
		weiboInfo.favorited = jsonObject.optBoolean("favorited");
		weiboInfo.truncated =  jsonObject.optBoolean("truncated");
		weiboInfo.in_reply_to_status_id = jsonObject.optString("in_reply_to_status_id");
		weiboInfo.in_reply_to_user_id = jsonObject.optString("in_reply_to_user_id");
		weiboInfo.in_reply_to_screen_name = jsonObject.optString("in_reply_to_screen_name");
		weiboInfo.thumbnail_pic = jsonObject.optString("thumbnail_pic");
		weiboInfo.bmiddle_pic = jsonObject.optString("bmiddle_pic");
		weiboInfo.original_pic = jsonObject.optString("orginal_pic");
		weiboInfo.geo = jsonObject.optString("geo");
		weiboInfo.user = jsonObject.optString("user");
		weiboInfo.retweeted_status = jsonObject.optString("retweeted_status");
		weiboInfo.reposts_count = jsonObject.optInt("reposts_count");
		weiboInfo.comments_counts = jsonObject.optInt("comments_counts");
		weiboInfo.attitudes_count = jsonObject.optInt("attitudes_count");
		weiboInfo.mlevel = jsonObject.optInt("mlevel");
		weiboInfo.visible = jsonObject.optString("visible");
		weiboInfo.pic_urls = jsonObject.optString("pic_urls");
		weiboInfo.ad = jsonObject.optString("ad");
		return weiboInfo;
	}
}
