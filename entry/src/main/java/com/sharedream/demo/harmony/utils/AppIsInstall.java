package com.sharedream.demo.harmony.utils;

import ohos.app.Context;

public class AppIsInstall {
  public static final String TENCENT_WEI_XIN_PACKAGE_NAME = "com.tencent.mm";

  public static boolean isInstall(Context context, String packageName) {
    boolean isInstall = false;

    try {
      // 判断是否安装应用
      isInstall = context.getBundleManager().isApplicationEnabled(packageName);
    } catch (IllegalArgumentException exception) {

    }

    return isInstall;
  }
}
