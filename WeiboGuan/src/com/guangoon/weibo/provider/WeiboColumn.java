package com.guangoon.weibo.provider;

import android.provider.BaseColumns;

public class WeiboColumn {
	static final class WeiboInfoColumn implements BaseColumns {
		public static final String CREATE_TIME = "create_time";
		public static final String BLOG_ID = "blog_id";
		public static final String BLOG_MID = "blog_mid";
		public static final String BLOG_ID_STRING = "blog_id_string";
		public static final String BLOG_TEXT = "blog_text";
		public static final String SOURCE = "source";
		public static final String FAVORITED = "favorited";
		public static final String TRUNCATED = "truncated";
		public static final String IN_REPLY_TO_STATUS_ID = "in_reply_to_status_id";
		public static final String IN_REPLY_TO_USER_ID = "in_reply_to_user_id";
		public static final String IN_REPLY_TO_SCREEN_NAME = "in_reply_to_screen_name";
		public static final String LONGITUDE = "longitude";
		public static final String LATITUDE = "latitude";
		public static final String UID = "uid";
		public static final String REPOSTS_COUNT = "reposts_count";
		public static final String COMMENTS_COUNT = "comments_count";
		public static final String ATTITUDES_COUNT = "attitudes_count";
	}
	
	static final class UserColumn implements BaseColumns{
		public static final String ID = "id";
		public static final String IDSTR = "idstr";
		public static final String SCREEN_NAME = "screen_name";
		public static final String NAME = "name";
		public static final String PROVINCE = "province";
		public static final String CITY = "city";
		public static final String LOCATION = "location";
		public static final String DESCRIPTION = "description";
		public static final String URI = "uri";
		public static final String PROFILE_IMAGE_URI = "profile_image_uri";
		public static final String PROFILE_URI = "profile_uri";
		public static final String DOMAIN = "domain";
		public static final String WEIHAO = "weihao";
		public static final String GENDER = "gender";
		public static final String FOLLOWERS_COUNT = "followers_count";
		public static final String FRIENDS_COUNT = "friend_count";
		public static final String STATUSES_COUNT = "statuses_count";
		public static final String FAVOURITES_COUNT = "favourites_count";
		public static final String CREATED_AT = "created_at";
		public static final String FOLLOWING = "following";
		public static final String ALLOW_ALL_ACT_MSG = "allow_all_act_msg";
		public static final String GEO_ENABLED = "geo_enabled";
		public static final String VERIFILED = "verifiled";
		public static final String VERIFIED_TYPE = "verifiled_type";
		public static final String REMARK = "remark";
		public static final String STATUS = "status";
		public static final String ALLOW_ALL_COMMENT = "allow_all_comment";
		public static final String AVATAR_LARGE = "avatar_large";
		public static final String AVATAR_HD = "avatar_hd";
		public static final String VERIFIED_REASON = "verified_reason";
		public static final String FOLLOW_ME = "follwo_me";
		public static final String ONLINE_STATUS = "online_status";
		public static final String BI_FOLLOWERS_COUNT = "bi_followers_count";
		public static final String LANG = "lang";
		public static final String ADMIN_FOLLOW = "admin_follow";
		public static final String ADMIN_FRIEND = "admin_friend";
	}
	
	static final class UserAdminColumn implements BaseColumns{
		public static final String ID = "id";
		public static final String IDSTR = "idstr";
		public static final String SCREEN_NAME = "screen_name";
		public static final String NAME = "name";
		public static final String PROVINCE = "province";
		public static final String CITY = "city";
		public static final String LOCATION = "location";
		public static final String DESCRIPTION = "description";
		public static final String URI = "uri";
		public static final String PROFILE_IMAGE_URI = "profile_image_uri";
		public static final String PROFILE_URI = "profile_uri";
		public static final String DOMAIN = "domain";
		public static final String WEIHAO = "weihao";
		public static final String GENDER = "gender";
		public static final String FOLLOWERS_COUNT = "followers_count";
		public static final String FRIENDS_COUNT = "friend_count";
		public static final String STATUSES_COUNT = "statuses_count";
		public static final String FAVOURITES_COUNT = "favourites_count";
		public static final String CREATED_AT = "created_at";
		public static final String FOLLOWING = "following";
		public static final String ALLOW_ALL_ACT_MSG = "allow_all_act_msg";
		public static final String GEO_ENABLED = "geo_enabled";
		public static final String VERIFILED = "verifiled";
		public static final String VERIFIED_TYPE = "verifiled_type";
		public static final String REMARK = "remark";
		public static final String STATUS = "status";
		public static final String ALLOW_ALL_COMMENT = "allow_all_comment";
		public static final String AVATAR_LARGE = "avatar_large";
		public static final String AVATAR_HD = "avatar_hd";
		public static final String VERIFIED_REASON = "verified_reason";
		public static final String FOLLOW_ME = "follwo_me";
		public static final String ONLINE_STATUS = "online_status";
		public static final String BI_FOLLOWERS_COUNT = "bi_followers_count";
		public static final String LANG = "lang";
	}
}
