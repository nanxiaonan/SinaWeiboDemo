package com.guangoon.weibo.provider;


import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

public class WeiboContentProvider extends ContentProvider {
	private static final String TAG = "WeiboContentProvider";
	private WeiboSqliteDatabase mWiWeiboSqliteDatabase;
	private UriMatcher mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
	private static final int RESULT_CODE = 0;

	@Override
	public boolean onCreate() {
		Log.i(TAG, "=============onCreate==================");
		mWiWeiboSqliteDatabase = new WeiboSqliteDatabase(getContext());
		mUriMatcher.addURI(WeiboColumn.AUTHORYITY,
				WeiboSqliteDatabase.TABLE_WEIBO_INFO, RESULT_CODE);
		mUriMatcher.addURI(WeiboColumn.AUTHORYITY,
				WeiboSqliteDatabase.TABLE_WEIBO_USER, RESULT_CODE);
		mUriMatcher.addURI(WeiboColumn.AUTHORYITY,
				WeiboSqliteDatabase.TABLE_WEIBO_USER_ADMIN, RESULT_CODE);
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		if (mUriMatcher.match(uri) != RESULT_CODE)
			return null;
		SqlArguments args = new SqlArguments(uri, selection, selectionArgs);
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(args.table);
		SQLiteDatabase db = mWiWeiboSqliteDatabase.getWritableDatabase();
		Cursor cursor = qb.query(db, projection, args.where, args.args, null,
				null, sortOrder);
		return cursor;
	}

	@Override
	public String getType(Uri uri) {
		return null;
	}
	
	

	@Override
	public int bulkInsert(Uri uri, ContentValues[] values) {
        SqlArguments args = new SqlArguments(uri);
        SQLiteDatabase db = mWiWeiboSqliteDatabase.getWritableDatabase();
        db.beginTransaction();
        try {
            int numValues = values.length;
            for (int i = 0; i < numValues; i++) {
                if (db.insert(args.table, null, values[i]) < 0) {
                    return 0;
                }
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return values.length;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		SqlArguments args = new SqlArguments(uri);
		SQLiteDatabase db = mWiWeiboSqliteDatabase.getWritableDatabase();
		final long rowId = db.insert(args.table, null, values);
		if (rowId < 0) {
			return null;
		}
		Uri result = ContentUris.withAppendedId(uri, rowId);
		getContext().getContentResolver().notifyChange(uri, null);
		return result;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		SqlArguments args = new SqlArguments(uri, selection, selectionArgs);
		SQLiteDatabase db = mWiWeiboSqliteDatabase.getWritableDatabase();
		int count = db.delete(args.table, args.where, args.args);
		if (count > 0) {
			getContext().getContentResolver().notifyChange(uri, null);
		}
		return count;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		SqlArguments args = new SqlArguments(uri, selection, selectionArgs);
		SQLiteDatabase db = mWiWeiboSqliteDatabase.getWritableDatabase();
		int count = db.update(args.table, values, args.where, args.args);
		if (count > 0) {
			getContext().getContentResolver().notifyChange(uri, null);
		}
		return count;
	}

	static class SqlArguments {
		public final String table;
		public final String where;
		public final String[] args;

		SqlArguments(Uri url, String where, String[] args) {
			if (url.getPathSegments().size() == 1) {
				this.table = url.getPathSegments().get(0);
				this.where = where;
				this.args = args;
			} else if (url.getPathSegments().size() != 2) {
				throw new IllegalArgumentException("Invalid URI: " + url);
			} else if (!TextUtils.isEmpty(where)) {
				throw new UnsupportedOperationException(
						"WHERE clause not supported: " + url);
			} else {
				this.table = url.getPathSegments().get(0);
				this.where = "_id=" + ContentUris.parseId(url);
				this.args = null;
			}
		}

		SqlArguments(Uri url) {
			if (url.getPathSegments().size() == 1) {
				table = url.getPathSegments().get(0);
				where = null;
				args = null;
			} else {
				throw new IllegalArgumentException("Invalid URI: " + url);
			}
		}
	}

}
