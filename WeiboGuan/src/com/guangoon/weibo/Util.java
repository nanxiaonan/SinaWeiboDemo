package com.guangoon.weibo;

import java.math.BigDecimal;

public class Util {
	public static boolean isNum(String string){
			try {
				new BigDecimal(string);
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
	}
}
