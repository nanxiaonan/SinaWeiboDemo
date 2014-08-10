package com.guangoon.weibo;

import android.app.Application;
import android.database.ContentObserver;
import android.util.Log;

import com.guangoon.weibo.models.OwnerUserInfo;
import com.guangoon.weibo.provider.WeiboColumn;

public class WeiboApplication extends Application {
	private static final String TAG = "WeiboApplication";

	@Override
	public void onCreate() {
		super.onCreate();
		Log.i(TAG,"=============onCreate======================");
		getContentResolver().registerContentObserver(WeiboColumn.UserAdminColumn.CONTENT_URI, true, mUserContentObserver);
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
		Log.i(TAG,"=============onTerminate======================");
		getContentResolver().unregisterContentObserver(mUserContentObserver);
	}
	

		
		private ContentObserver mUserContentObserver = new ContentObserver(null) {

			@Override
			public void onChange(boolean selfChange) {
				super.onChange(selfChange);
				Log.i(TAG,"=============onChange======================");
				OwnerUserInfo.getInstance(WeiboApplication.this).updateUserInfo(WeiboApplication.this);
			}
			
		};
		
		
	
	
}
