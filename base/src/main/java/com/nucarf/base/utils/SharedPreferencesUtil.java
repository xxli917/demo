package com.nucarf.base.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPreferencesUtil {

	private static final String SHARED_NAME = "starshow";

	public static SharedPreferences getSharedPreferences(Context context) {
		return (context == null ? BaseAppCache.getContext() : context).getSharedPreferences(SHARED_NAME,
				Context.MODE_PRIVATE);
	}

	public static String getStringValue(Context context, String key) {
		return getSharedPreferences(context).getString(key, null);
	}

	public static String getStringValue(Context context, String key, String defValue) {
		return getSharedPreferences(context).getString(key, defValue);
	}

	public static int getIntValue(Context context, String key) {
		return getSharedPreferences(context).getInt(key, -1);
	}


	public static int getIntValue(Context context, String key, int defValue) {
		return getSharedPreferences(context).getInt(key, defValue);
	}

	protected static float getFloatValue(Context context, String key) {
		return getSharedPreferences(context).getFloat(key, -1);
	}

	public static float getFloatValue(Context context, String key, float defValue) {
		return getSharedPreferences(context).getFloat(key, defValue);
	}

	public static boolean getBooleanValue(Context context, String key) {
		return getSharedPreferences(context).getBoolean(key, false);
	}

	public static boolean getBooleanValue(Context context, String key, boolean defValue) {
		return getSharedPreferences(context).getBoolean(key, defValue);
	}

	public static long getLongValue(Context context, String key) {
		return getSharedPreferences(context).getLong(key, (long) -1);
	}

	public static long getLongValue(Context context, String key, long defValue) {
		return getSharedPreferences(context).getLong(key, defValue);
	}

	public static void setValue(Context context, String key, String value) {
		getSharedPreferences(context).edit().putString(key, value).commit();
	}

	public static void setValue(Context context, String key, int value) {
		getSharedPreferences(context).edit().putInt(key, value).commit();
	}

	public static void setValue(Context context, String key, float value) {
		getSharedPreferences(context).edit().putFloat(key, value).commit();
	}

	public static void setValue(Context context, String key, boolean value) {
		getSharedPreferences(context).edit().putBoolean(key, value).commit();
	}

	public static void setValue(Context context, String key, long value) {
		getSharedPreferences(context).edit().putLong(key, value).commit();
	}

	public static void removeKeyArray(Context context, String... keys) {
		try {
			Editor edit = getSharedPreferences(context).edit();
			for (String key : keys) {
				edit.remove(key);
			}
			edit.commit();
		} catch (Exception e) {
			LogUtils.e("removeKeyArray e[" + e + "]","removeKeyArray e[" + e + "]");
		}
	}

	public static void  clearSharePreferences(Context context){
		Editor edit = getSharedPreferences(context).edit();
		edit.clear();
		edit.commit();
	}

}
