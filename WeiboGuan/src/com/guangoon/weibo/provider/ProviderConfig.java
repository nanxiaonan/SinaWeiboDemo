package com.guangoon.weibo.provider;

public class ProviderConfig {
	public static final String DB_NAME = "guangoon_weibo";
	public static final String AUTHORITY = "com.guangoon.weibo.provider";
	public static final String TABLE_WEIBO_INFO_NAME = "gg_weibo_info";
	public static final String TABLE_WEIBO_PIC_NAME = "gg_weibo_pic";
	
	public static  String[] UserProjection = new String[] {
			WeiboColumn.UserAdminColumn.ID, WeiboColumn.UserAdminColumn.IDSTR,
			WeiboColumn.UserAdminColumn.SCREEN_NAME,
			WeiboColumn.UserAdminColumn.NAME,
			WeiboColumn.UserAdminColumn.ALLOW_ALL_ACT_MSG,
			WeiboColumn.UserAdminColumn.ALLOW_ALL_COMMENT,
			WeiboColumn.UserAdminColumn.AVATAR_HD,
			WeiboColumn.UserAdminColumn.AVATAR_LARGE,
			WeiboColumn.UserAdminColumn.BI_FOLLOWERS_COUNT,
			WeiboColumn.UserAdminColumn.CITY,
			WeiboColumn.UserAdminColumn.CREATED_AT,
			WeiboColumn.UserAdminColumn.DESCRIPTION,
			WeiboColumn.UserAdminColumn.DOMAIN,
			WeiboColumn.UserAdminColumn.FAVOURITES_COUNT,
			WeiboColumn.UserAdminColumn.FOLLOW_ME,
			WeiboColumn.UserAdminColumn.FOLLOWERS_COUNT,
			WeiboColumn.UserAdminColumn.FOLLOWING,
			WeiboColumn.UserAdminColumn.FRIENDS_COUNT,
			WeiboColumn.UserAdminColumn.GENDER,
			WeiboColumn.UserAdminColumn.GEO_ENABLED,
			WeiboColumn.UserAdminColumn.LANG,
			WeiboColumn.UserAdminColumn.LOCATION,
			WeiboColumn.UserAdminColumn.ONLINE_STATUS,
			WeiboColumn.UserAdminColumn.PROFILE_IMAGE_URI,
			WeiboColumn.UserAdminColumn.PROFILE_URI,
			WeiboColumn.UserAdminColumn.PROVINCE,
			WeiboColumn.UserAdminColumn.REMARK,
			WeiboColumn.UserAdminColumn.STATUS,
			WeiboColumn.UserAdminColumn.STATUSES_COUNT,
			WeiboColumn.UserAdminColumn.URI,
			WeiboColumn.UserAdminColumn.VERIFIED_REASON,
			WeiboColumn.UserAdminColumn.VERIFIED_TYPE,
			WeiboColumn.UserAdminColumn.VERIFILED,
			WeiboColumn.UserAdminColumn.WEIHAO, };
}
