package com.guangoon.weibo.models;

import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;
import android.util.Log;

public class Geo {
	private static final String TAG = "Geo";
	// 经度坐标
	public String longitude;
	// 维度坐标
	public String latitude;
	// 所在城市的城市代码
	public String city;
	// 所在省份的省份代码
	public String province;
	// 所在的实际地址，可以为空
	public String address;
	// 地址的汉语拼音，不是所有情况都会返回该字段
	public String pinyin;
	// 更多信息，不是所有情况都会返回该字段
	public String more;

	public static Geo parse(String geo) {
		Log.i(TAG,"geo====" + geo);
		if (null == geo || TextUtils.isEmpty(geo)) {
			return null;
		}
		try {
			JSONObject jsonObject = new JSONObject(geo);
			return parse(jsonObject);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Geo parse(JSONObject jsonObject) {
		if (jsonObject == null) {
			return null;
		}
		Geo geo = new Geo();
		geo.longitude = jsonObject.optString("longitude");
		geo.latitude = jsonObject.optString("latitude");
		geo.city = jsonObject.optString("city");
		geo.province = jsonObject.optString("province");
		geo.address = jsonObject.optString("address");
		geo.pinyin = jsonObject.optString("pinyin");
		geo.more = jsonObject.optString("more");
		return geo;
	}

}
