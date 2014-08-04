package com.guangoon.weibo.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class WeiboSqliteDatabase extends SQLiteOpenHelper {
	private static final int DATABASE_VERSION = 1;
	static final String TABLE_WEIBO_INFO = "weibo_info";
	static final String TABLE_WEIBO_USER = "weibo_user";
	static final String TABLE_WEIBO_USER_ADMIN = "weibo_user_admin";

	public WeiboSqliteDatabase(Context context) {
		super(context, ProviderConfig.DB_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "CREATE TABLE " + TABLE_WEIBO_INFO + "("
				+ "_id INTEGER PRIMARY KEY,"
				+ WeiboColumn.WeiboInfoColumn.CREATE_TIME + " LONG,"
				+ WeiboColumn.WeiboInfoColumn.BLOG_ID + " LONG,"
				+ WeiboColumn.WeiboInfoColumn.BLOG_MID + " LONG,"
				+ WeiboColumn.WeiboInfoColumn.BLOG_ID_STRING + " TEXT,"
				+ WeiboColumn.WeiboInfoColumn.BLOG_TEXT + " TEXT,"
				+ WeiboColumn.WeiboInfoColumn.SOURCE + " TEXT,"
				+ WeiboColumn.WeiboInfoColumn.FAVORITED + " INTEGER,"
				+ WeiboColumn.WeiboInfoColumn.TRUNCATED + " INTEGER,"
				+ WeiboColumn.WeiboInfoColumn.IN_REPLY_TO_STATUS_ID + " TEXT,"
				+ WeiboColumn.WeiboInfoColumn.IN_REPLY_TO_USER_ID + " TEXT,"
				+ WeiboColumn.WeiboInfoColumn.IN_REPLY_TO_SCREEN_NAME
				+ " TEXT," + WeiboColumn.WeiboInfoColumn.LONGITUDE + " DOUBLE,"
				+ WeiboColumn.WeiboInfoColumn.LATITUDE + " DOUBLE,"
				+ WeiboColumn.WeiboInfoColumn.UID + " LONG,"
				+ WeiboColumn.WeiboInfoColumn.REPOSTS_COUNT + " INTEGER,"
				+ WeiboColumn.WeiboInfoColumn.COMMENTS_COUNT + " INTEGER,"
				+ WeiboColumn.WeiboInfoColumn.ATTITUDES_COUNT + " INTEGER)";
		db.execSQL(sql);
		sql = "CREATE TABLE " + TABLE_WEIBO_USER + "("
				+ "_id INTEGER PRIMARY KEY,"
				+ WeiboColumn.UserColumn.ID + " TEXT,"
				+ WeiboColumn.UserColumn.IDSTR + " TEXT,"
				+ WeiboColumn.UserColumn.SCREEN_NAME + " TEXT,"
				+ WeiboColumn.UserColumn.NAME  + " TEXT,"
				+ WeiboColumn.UserColumn.PROVINCE + " INTEGER,"
				+ WeiboColumn.UserColumn.CITY + " INTEGER,"
				+ WeiboColumn.UserColumn.LOCATION + " TEXT,"
				+ WeiboColumn.UserColumn.DESCRIPTION + " TEXT,"
				+ WeiboColumn.UserColumn.URI + " TEXT,"
				+ WeiboColumn.UserColumn.PROFILE_IMAGE_URI + " TEXT,"
				+ WeiboColumn.UserColumn.PROFILE_URI + " TEXT,"
				+ WeiboColumn.UserColumn.DOMAIN + " TEXT,"
				+ WeiboColumn.UserColumn.WEIHAO + " TEXT,"
				+ WeiboColumn.UserColumn.GENDER + " TEXT,"
				+ WeiboColumn.UserColumn.FOLLOWERS_COUNT + " INTEGER,"
				+ WeiboColumn.UserColumn.FRIENDS_COUNT + " INTEGER,"
				+ WeiboColumn.UserColumn.STATUSES_COUNT + " INTEGER,"
				+ WeiboColumn.UserColumn.FAVOURITES_COUNT + " INTEGER,"
				+ WeiboColumn.UserColumn.CREATED_AT + " TEXT,"
				+ WeiboColumn.UserColumn.FOLLOWING + " INTEGER,"
				+ WeiboColumn.UserColumn.ALLOW_ALL_ACT_MSG + " INTEGER,"
				+ WeiboColumn.UserColumn.GEO_ENABLED + " INTEGER,"
				+ WeiboColumn.UserColumn.VERIFILED + " INTEGER,"
				+ WeiboColumn.UserColumn.VERIFIED_TYPE + " INTEGER,"
				+ WeiboColumn.UserColumn.REMARK + " TEXT," 
				+ WeiboColumn.UserColumn.STATUS + " TEXT,"
				+ WeiboColumn.UserColumn.ALLOW_ALL_COMMENT + " INTEGER,"
				+ WeiboColumn.UserColumn.AVATAR_LARGE + " TEXT,"
				+ WeiboColumn.UserColumn.AVATAR_HD + " TEXT,"
				+ WeiboColumn.UserColumn.VERIFIED_REASON + " TEXT,"
				+ WeiboColumn.UserColumn.FOLLOW_ME + " INTEGER,"
				+ WeiboColumn.UserColumn.ONLINE_STATUS + " INTEGER,"
				+ WeiboColumn.UserColumn.BI_FOLLOWERS_COUNT + " INTEGER,"
				+ WeiboColumn.UserColumn.LANG + "TEXT,"
				+ WeiboColumn.UserColumn.ADMIN_FOLLOW + " INTEGER,"
				+ WeiboColumn.UserColumn.ADMIN_FRIEND + " INTEGER)";
		db.execSQL(sql);
		sql = "CREATE TABLE " + TABLE_WEIBO_USER_ADMIN + "("
				+ "_id INTEGER PRIMARY KEY,"
				+ WeiboColumn.UserAdminColumn.ID + " TEXT,"
				+ WeiboColumn.UserAdminColumn.IDSTR + " TEXT,"
				+ WeiboColumn.UserAdminColumn.SCREEN_NAME + " TEXT,"
				+ WeiboColumn.UserAdminColumn.NAME  + " TEXT,"
				+ WeiboColumn.UserAdminColumn.PROVINCE + " INTEGER,"
				+ WeiboColumn.UserAdminColumn.CITY + " INTEGER,"
				+ WeiboColumn.UserAdminColumn.LOCATION + " TEXT,"
				+ WeiboColumn.UserAdminColumn.DESCRIPTION + " TEXT,"
				+ WeiboColumn.UserAdminColumn.URI + " TEXT,"
				+ WeiboColumn.UserAdminColumn.PROFILE_IMAGE_URI + " TEXT,"
				+ WeiboColumn.UserAdminColumn.PROFILE_URI + " TEXT,"
				+ WeiboColumn.UserAdminColumn.DOMAIN + " TEXT,"
				+ WeiboColumn.UserAdminColumn.WEIHAO + " TEXT,"
				+ WeiboColumn.UserAdminColumn.GENDER + " TEXT,"
				+ WeiboColumn.UserAdminColumn.FOLLOWERS_COUNT + " INTEGER,"
				+ WeiboColumn.UserAdminColumn.FRIENDS_COUNT + " INTEGER,"
				+ WeiboColumn.UserAdminColumn.STATUSES_COUNT + " INTEGER,"
				+ WeiboColumn.UserAdminColumn.FAVOURITES_COUNT + " INTEGER,"
				+ WeiboColumn.UserAdminColumn.CREATED_AT + " TEXT,"
				+ WeiboColumn.UserAdminColumn.FOLLOWING + " INTEGER,"
				+ WeiboColumn.UserAdminColumn.ALLOW_ALL_ACT_MSG + " INTEGER,"
				+ WeiboColumn.UserAdminColumn.GEO_ENABLED + " INTEGER,"
				+ WeiboColumn.UserAdminColumn.VERIFILED + " INTEGER,"
				+ WeiboColumn.UserAdminColumn.VERIFIED_TYPE + " INTEGER,"
				+ WeiboColumn.UserAdminColumn.REMARK + " TEXT," 
				+ WeiboColumn.UserAdminColumn.STATUS + " TEXT,"
				+ WeiboColumn.UserAdminColumn.ALLOW_ALL_COMMENT + " INTEGER,"
				+ WeiboColumn.UserAdminColumn.AVATAR_LARGE + " TEXT,"
				+ WeiboColumn.UserAdminColumn.AVATAR_HD + " TEXT,"
				+ WeiboColumn.UserAdminColumn.VERIFIED_REASON + " TEXT,"
				+ WeiboColumn.UserAdminColumn.FOLLOW_ME + " INTEGER,"
				+ WeiboColumn.UserAdminColumn.ONLINE_STATUS + " INTEGER,"
				+ WeiboColumn.UserAdminColumn.BI_FOLLOWERS_COUNT + " INTEGER,"
				+ WeiboColumn.UserAdminColumn.LANG + " TEXT)";
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
