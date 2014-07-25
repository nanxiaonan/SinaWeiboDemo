package com.guangoon.weibo.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class WeiboSqliteDatabase extends SQLiteOpenHelper {
	private static final int DATABASE_VERSION = 1;
	static final String TABLE_WEIBO_INFO = "weibo_info";

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
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
