package com.sharedream.demo.harmony.utils;

import com.sharedream.demo.harmony.MyApplication;
import ohos.app.Context;
import ohos.data.DatabaseHelper;
import ohos.data.preferences.Preferences;

public class PreferenceUtils {
  static PreferenceUtils preferenceUtils = null;
  private static String PREFERENCE_FILE_NAME = "fileName";

  public static PreferenceUtils getInstance() {
    if (preferenceUtils == null) {
      preferenceUtils = new PreferenceUtils();
    }

    return preferenceUtils;
  }

  Preferences getApplicationPref(Context context) {
    DatabaseHelper databaseHelper = new DatabaseHelper(context);

    return databaseHelper.getPreferences(PREFERENCE_FILE_NAME);
  }

  public void putString(String key, String value) {
    preferenceUtils.getApplicationPref(MyApplication.mContext).putString(key, value).flushSync();
  }

  public String getString(String key, String defValue) {
    return preferenceUtils.getApplicationPref(MyApplication.mContext).getString(key, defValue);
  }

  public void putInt(String key, int value) {
    preferenceUtils.getApplicationPref(MyApplication.mContext).putInt(key, value).flushSync();
  }

  public int getInt(String key, int defValue) {
    return preferenceUtils.getApplicationPref(MyApplication.mContext).getInt(key, defValue);
  }
}
