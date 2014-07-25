package com.guangoon.weibo.sdk.net;

import android.graphics.Bitmap;

import android.text.TextUtils;

import com.sina.weibo.sdk.utils.LogUtil;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Set;

public class WeiboParameters {
	private static final String DEFAULT_CHARSET = "UTF-8";
	private LinkedHashMap<String, Object> mParams = new LinkedHashMap();

	public LinkedHashMap<String, Object> getParams() {
		return this.mParams;
	}

	public void setParams(LinkedHashMap<String, Object> params) {
		this.mParams = params;
	}

	@Deprecated
	public void add(String key, String val) {
		this.mParams.put(key, val);
	}

	@Deprecated
	public void add(String key, int value) {
		this.mParams.put(key, String.valueOf(value));
	}

	@Deprecated
	public void add(String key, long value) {
		this.mParams.put(key, String.valueOf(value));
	}

	@Deprecated
	public void add(String key, Object val) {
		this.mParams.put(key, val.toString());
	}

	public void put(String key, String val) {
		this.mParams.put(key, val);
	}

	public void put(String key, int value) {
		this.mParams.put(key, String.valueOf(value));
	}

	public void put(String key, long value) {
		this.mParams.put(key, String.valueOf(value));
	}

	public void put(String key, Bitmap bitmap) {

		this.mParams.put(key, bitmap);
	}

	public void put(String key, Object val) {
		this.mParams.put(key, val.toString());
	}

	public Object get(String key) {
		return this.mParams.get(key);
	}

	public void remove(String key) {
		if (this.mParams.containsKey(key)) {
			this.mParams.remove(key);
			this.mParams.remove(this.mParams.get(key));
		}
	}

	public Set<String> keySet() {
		return this.mParams.keySet();
	}

	public boolean containsKey(String key) {
		return this.mParams.containsKey(key);
	}

	public boolean containsValue(String value) {
		return this.mParams.containsValue(value);
	}

	public int size() {
		return this.mParams.size();
	}

	public String encodeUrl() {
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		for (String key : this.mParams.keySet()) {
			if (first) {
				first = false;
			} else {
				sb.append("&");
			}
			Object value = this.mParams.get(key);
			if ((value instanceof String)) {
				String param = (String) value;
				if (!TextUtils.isEmpty(param)) {
					try {
						sb.append(URLEncoder.encode(key, "UTF-8") + "="
								+ URLEncoder.encode(param, "UTF-8"));
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}
				LogUtil.i("encodeUrl", sb.toString());
			}
		}
		return sb.toString();
	}

	public boolean hasBinaryData() {
		Set<String> keys = this.mParams.keySet();
		for (String key : keys) {
			Object value = this.mParams.get(key);
			if (((value instanceof ByteArrayOutputStream))
					|| ((value instanceof Bitmap))) {
				return true;
			}
		}
		return false;
	}
}
