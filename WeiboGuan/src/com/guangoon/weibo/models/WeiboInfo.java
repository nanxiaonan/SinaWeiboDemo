package com.guangoon.weibo.models;

import org.json.JSONException;
import org.json.JSONObject;

public class WeiboInfo {
	// 微博创建时间
	public String created_at;
	// 微博ID
	public String id;
	// 微博MID
	public String mid;
	// 字符串型的微博ID
	public String idstr;
	// 微博信息内容
	public String text;
	// 微博来源
	public String source;
	// 是否已收藏，true：是，false：否
	public boolean favorited;
	// 是否被截断，true：是，false：否
	public boolean truncated;
	// 暂未支持）回复ID
	public String in_reply_to_status_id;
	// （暂未支持）回复人UID
	public String in_reply_to_user_id;
	// （暂未支持）回复人昵称
	public String in_reply_to_screen_name;
	// 缩略图片地址，没有时不返回此字段
	public String thumbnail_pic;
	// 中等尺寸图片地址，没有时不返回此字段
	public String bmiddle_pic;
	// 原始图片地址，没有时不返回此字段
	public String original_pic;
	// 地理信息字段 详细
	public String geo;
	// 微博作者的用户信息字段 详细
	public String user;
	// 被转发的原微博信息字段，当该微博为转发微博时返回 详细
	public String retweeted_status;
	// 转发数
	public int reposts_count;
	// 评论数
	public int comments_counts;
	// 表态数
	public int attitudes_count;
	// 暂未支持
	public int mlevel;
	// 微博的可见性及指定可见分组信息。该object中type取值，0：普通微博，1：私密微博，3：指定分组微博，
	// 4：密友微博；list_id为分组的组号
	public String visible;
	// 微博配图地址。多图时返回多图链接。无配图返回“[]”
	public String pic_urls;
	// 微博流内的推广微博ID
	public String ad;
	
	public User userInfo;
	public Geo geoInfo;

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
		weiboInfo.created_at = jsonObject.optString("created_at", "");
		weiboInfo.id = jsonObject.optString("id", "");
		weiboInfo.mid = jsonObject.optString("mid", "");
		weiboInfo.idstr = jsonObject.optString("idstr", "");
		weiboInfo.text = jsonObject.optString("text", "");
		weiboInfo.source = jsonObject.optString("source");
		weiboInfo.favorited = jsonObject.optBoolean("favorited");
		weiboInfo.truncated = jsonObject.optBoolean("truncated");
		weiboInfo.in_reply_to_status_id = jsonObject
				.optString("in_reply_to_status_id");
		weiboInfo.in_reply_to_user_id = jsonObject
				.optString("in_reply_to_user_id");
		weiboInfo.in_reply_to_screen_name = jsonObject
				.optString("in_reply_to_screen_name");
		weiboInfo.thumbnail_pic = jsonObject.optString("thumbnail_pic");
		weiboInfo.bmiddle_pic = jsonObject.optString("bmiddle_pic");
		weiboInfo.original_pic = jsonObject.optString("orginal_pic");
		weiboInfo.geo = jsonObject.optString("geo");
		//weiboInfo.geoInfo = Geo.parse(weiboInfo.geo);
		weiboInfo.user = jsonObject.optString("user");
		weiboInfo.userInfo = User.parse(weiboInfo.user);
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
