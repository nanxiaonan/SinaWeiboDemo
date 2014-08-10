package com.guangoon.weibo.models;

import org.json.JSONException;
import org.json.JSONObject;

public class User {
	// 用户UID
	public String id;
	// 字符串型的用户UID
	public String idstr;
	// 用户昵称
	public String screen_name;
	// 友好显示名称
	public String name;
	// 用户所在省级ID
	public int province;
	// 用户所在城市ID
	public int city;
	// 用户所在地
	public String location;
	// 用户个人描述
	public String description;
	// 用户博客地址
	public String uri;
	// 用户头像地址（中图），50×50像素
	public String profile_image_url;
	// 用户的微博统一URL地址
	public String profile_url;
	// 用户的个性化域名
	public String domain;
	// 用户的微号
	public String weihao;
	// gender
	public String gender;
	// 粉丝数
	public int followers_count;
	// 关注数
	public int friends_count;
	// 微博数
	public int statuses_count;
	// 收藏数
	public int favourites_count;
	// 用户创建（注册）时间
	public String created_at;
	// 暂未支持
	public boolean following;
	// 是否允许所有人给我发私信，true：是，false：否
	public boolean allow_all_act_msg;
	// 是否允许标识用户的地理位置，true：是，false：否
	public boolean geo_enabled;
	// 是否是微博认证用户，即加V用户，true：是，false：否
	public boolean verified;
	// 暂未支持
	public int verified_type;
	// 用户备注信息，只有在查询用户关系时才返回此字段
	public String remark;
	// 用户的最近一条微博信息字段
	public String status;
	// 是否允许所有人对我的微博进行评论，true：是，false：否
	public boolean allow_all_comment;
	// 用户头像地址（大图），180×180像素
	public String avatar_large;
	// 用户头像地址（高清），高清头像原图
	public String avatar_hd;
	// 认证原因
	public String verified_reason;
	// 该用户是否关注当前登录用户，true：是，false：否
	public boolean follow_me;
	// 用户的在线状态，0：不在线、1：在线
	public int online_status;
	// 用户的互粉数
	public int bi_followers_count;
	// 用户当前的语言版本，zh-cn：简体中文，zh-tw：繁体中文，en：英语
	public String lang;

	public static User parse(String jsonString) {
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(jsonString);
			return parse(jsonObject);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static User parse(JSONObject jsonObject) {
		if (jsonObject == null) {
			return null;
		}

		User user = new User();
		user.id = jsonObject.optString("id", "");
		user.idstr = jsonObject.optString("idstr", "");
		user.screen_name = jsonObject.optString("screen_name", "");
		user.name = jsonObject.optString("name", "");
		user.province = jsonObject.optInt("province");
		user.city = jsonObject.optInt("city");
		user.location = jsonObject.optString("location", "");
		user.description = jsonObject.optString("description", "");
		user.uri = jsonObject.optString("uri", "");
		user.profile_image_url = jsonObject.optString("profile_image_url", "");
		user.profile_url = jsonObject.optString("profile_url", "");
		user.domain = jsonObject.optString("domain", "");
		user.weihao = jsonObject.optString("weihao", "");
		user.gender = jsonObject.optString("gender", "");
		user.followers_count = jsonObject.optInt("followers_count");
		user.friends_count = jsonObject.optInt("friends_count");
		user.statuses_count = jsonObject.optInt("statuses_count");
		user.favourites_count = jsonObject.optInt("favourites_count");
		user.created_at = jsonObject.optString("created_at");
		user.following = jsonObject.optBoolean("following", false);
		user.allow_all_act_msg = jsonObject.optBoolean("allow_all_act_msg",
				false);
		user.geo_enabled = jsonObject.optBoolean("geo_enabled", false);
		user.verified = jsonObject.optBoolean("verified", false);
		user.verified_type = jsonObject.optInt("verified_type");
		user.remark = jsonObject.optString("remark", "");
		user.status = jsonObject.optString("status_id", "");
		user.allow_all_comment = jsonObject.optBoolean("allow_all_comment",
				false);
		user.avatar_large = jsonObject.optString("avatar_large", "");
		user.avatar_hd = jsonObject.optString("avatar_hd", "");
		user.verified_reason = jsonObject.optString("verified_reason", "");
		user.follow_me = jsonObject.optBoolean("follow_me", false);
		user.online_status = jsonObject.optInt("online_status");
		user.bi_followers_count = jsonObject.optInt("bi_followers_count");
		user.lang = jsonObject.optString("lang", "");
		return user;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", idstr=" + idstr + ", screen_name="
				+ screen_name + ", name=" + name + ", province=" + province
				+ ", city=" + city + ", location=" + location
				+ ", description=" + description + ", uri=" + uri
				+ ", profile_image_url=" + profile_image_url + ", profile_url="
				+ profile_url + ", domain=" + domain + ", weihao=" + weihao
				+ ", gender=" + gender + ", followers_count=" + followers_count
				+ ", friends_count=" + friends_count + ", statuses_count="
				+ statuses_count + ", favourites_count=" + favourites_count
				+ ", created_at=" + created_at + ", following=" + following
				+ ", allow_all_act_msg=" + allow_all_act_msg + ", geo_enabled="
				+ geo_enabled + ", verified=" + verified + ", verified_type="
				+ verified_type + ", remark=" + remark + ", status=" + status
				+ ", allow_all_comment=" + allow_all_comment
				+ ", avatar_large=" + avatar_large + ", avatar_hd=" + avatar_hd
				+ ", verified_reason=" + verified_reason + ", follow_me="
				+ follow_me + ", online_status=" + online_status
				+ ", bi_followers_count=" + bi_followers_count + ", lang="
				+ lang + "]";
	}
	
	
}
