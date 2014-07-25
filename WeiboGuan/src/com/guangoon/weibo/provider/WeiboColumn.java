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
}
