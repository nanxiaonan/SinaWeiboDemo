package com.guangoon.weibo.models;

import com.guangoon.weibo.provider.ProviderConfig;
import com.guangoon.weibo.provider.WeiboColumn;

import android.content.Context;
import android.database.Cursor;

public class OwnerUserInfo extends User {
	private static OwnerUserInfo mInstance = null;
	private OwnerUserInfo(Context context)
	{
		updateUserInfo(context);
	}
	
	public static synchronized OwnerUserInfo getInstance(Context context)
	{
		if(mInstance == null){
				mInstance = new OwnerUserInfo(context);
		}
		return mInstance;
	}
	
	public void updateUserInfo(Context context){
		Cursor cursor = context.getContentResolver().query(WeiboColumn.UserAdminColumn.CONTENT_URI,ProviderConfig.UserProjection 
				,null , null,null);
		if(cursor != null && cursor.getCount() > 0 ){
				id = cursor.getString(cursor.getColumnIndexOrThrow(WeiboColumn.UserAdminColumn.ID));
				screen_name = cursor.getString(cursor.getColumnIndexOrThrow(WeiboColumn.UserAdminColumn.SCREEN_NAME));
				name = cursor.getString(cursor.getColumnIndexOrThrow(WeiboColumn.UserAdminColumn.NAME));
				province = cursor.getInt(cursor.getColumnIndexOrThrow(WeiboColumn.UserAdminColumn.PROVINCE));
				city = cursor.getInt(cursor.getColumnIndexOrThrow(WeiboColumn.UserAdminColumn.CITY));
				location = cursor.getString(cursor.getColumnIndexOrThrow(WeiboColumn.UserAdminColumn.LOCATION));
				location = cursor.getString(cursor.getColumnIndexOrThrow(WeiboColumn.UserAdminColumn.LOCATION));
				description  = cursor.getString(cursor.getColumnIndexOrThrow(WeiboColumn.UserAdminColumn.DESCRIPTION));
				profile_image_url =   cursor.getString(cursor.getColumnIndexOrThrow(WeiboColumn.UserAdminColumn.PROFILE_IMAGE_URI));
				followers_count = cursor.getInt(cursor.getColumnIndexOrThrow(WeiboColumn.UserAdminColumn.FAVOURITES_COUNT));
				friends_count =  cursor.getInt(cursor.getColumnIndexOrThrow(WeiboColumn.UserAdminColumn.FRIENDS_COUNT));
				statuses_count =  cursor.getInt(cursor.getColumnIndexOrThrow(WeiboColumn.UserAdminColumn.STATUSES_COUNT));
				favourites_count = cursor.getInt(cursor.getColumnIndexOrThrow(WeiboColumn.UserAdminColumn.FAVOURITES_COUNT));
				created_at =  cursor.getString(cursor.getColumnIndexOrThrow(WeiboColumn.UserAdminColumn.CREATED_AT));
				verified =  cursor.getInt(cursor.getColumnIndexOrThrow(WeiboColumn.UserAdminColumn.FAVOURITES_COUNT)) == 1;
				status = cursor.getString(cursor.getColumnIndexOrThrow(WeiboColumn.UserAdminColumn.STATUS));
				allow_all_comment = cursor.getInt(cursor.getColumnIndexOrThrow(WeiboColumn.UserAdminColumn.ALLOW_ALL_COMMENT)) == 1;
				 avatar_large = cursor.getString(cursor.getColumnIndexOrThrow(WeiboColumn.UserAdminColumn.AVATAR_LARGE));
				 avatar_hd = cursor.getString(cursor.getColumnIndexOrThrow(WeiboColumn.UserAdminColumn.AVATAR_HD)); 
		}
	}
	
}
