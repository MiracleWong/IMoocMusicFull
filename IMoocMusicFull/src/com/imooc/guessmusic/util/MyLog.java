package com.imooc.guessmusic.util;

import android.util.Log;

public class MyLog {
	// Log的开关
	public static final boolean DEBUG = true;
	// D级别
	public static void d(String tag, String message) {
		if (DEBUG) {
			Log.d(tag, message);
		}
	}
	// W级别
	public static void w(String tag, String message) {
		if (DEBUG) {
			Log.w(tag, message);
		}
	}
	// E级别
	
	public static void e(String tag, String message) {
		if (DEBUG) {
			Log.e(tag, message);
		}
	}
	// I级别
	public static void i(String tag, String message) {
		if (DEBUG) {
			Log.i(tag, message);
		}
	}
}
