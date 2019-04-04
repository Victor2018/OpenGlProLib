package com.victor.opengl.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.text.TextUtils;

import com.victor.opengl.library.app.App;

import java.util.List;
import java.util.Locale;

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: AspectFrameLayout.java
 * Author: Victor
 * Date: 2019/3/14 9:16
 * Description:
 * -----------------------------------------------------------------
 */

public class AppUtil {
    private static int sVersionCode = 0;

    // 应用版本
    public static int getAppVersionCode(Context context) {
        String packageName = context.getPackageName();
        try {
            int versionCode = context.getPackageManager().getPackageInfo(
                    packageName, 0).versionCode;
            return versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException("System fault!!!", e);
        }
    }
    public static String getAppVersionName(Context context) {
        String packageName = context.getPackageName();
        try {
            String versionName = context.getPackageManager().getPackageInfo(
                    packageName, 0).versionName;
            return versionName;
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException("System fault!!!", e);
        }
    }

    /**
     * 获取APP名称
     * @param context
     * @return
     */
    public static String getAppName(Context context) {
        String appName ="";
        try {
            PackageManager packageManager = context.getPackageManager();

            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            appName = packageManager.getApplicationLabel(applicationInfo).toString();
            return appName;
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException("System fault!!!", e);
        }
    }

    /**
     * 启动到应用商店app详情界面
     *
     * @param appPkg    目标App的包名
     * @param marketPkg 应用商店包名 ,如果为""则由系统弹出应用商店列表供用户选择,否则调转到目标市场的应用详情界面，某些应用商店可能会失败
     */
    public static void launchAppDetail(Activity activity, String appPkg, String marketPkg) {
        try {
            if (TextUtils.isEmpty(appPkg)) return;

            Uri uri = Uri.parse("market://details?id=" + appPkg);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            if (!TextUtils.isEmpty(marketPkg)) {
                intent.setPackage(marketPkg);
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断某一个Activity是否存在任务栈里面
     * @return
     */
    public static boolean isActivityInTask(Class<?> cls){
        Intent intent = new Intent(App.get(), cls);
        ComponentName cmpName = intent.resolveActivity(App.get().getPackageManager());
        boolean flag = false;
        if (cmpName != null) { // 说明系统中存在这个activity
            ActivityManager am = (ActivityManager) App.get().getSystemService(App.get().ACTIVITY_SERVICE);
            List<ActivityManager.RunningTaskInfo> taskInfoList = am.getRunningTasks(10);
            for (ActivityManager.RunningTaskInfo taskInfo : taskInfoList) {
                if (taskInfo.baseActivity.equals(cmpName)) { // 说明它已经启动了
                    flag = true;
                    break;  //跳出循环，优化效率
                }
            }
        }
        return flag;
    }

    /**
     * 安全的启动APP
     *
     * @param ctx
     * @param intent
     */
    public static boolean launchApp(Context ctx, Intent intent) {
        if (ctx == null)
            throw new NullPointerException("ctx is null");
        try {
            ctx.startActivity(intent);
            return true;
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Launch From Notifycation
     *
     * @param context
     * @param intent
     */
    public static void launchAppWithPending(Context context, Intent intent) {
        if (context == null || intent == null) {
            return;
        }
        Context ctx = context.getApplicationContext();
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(ctx, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        try {
            pendingIntent.send(ctx, 0, intent);
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }
    }

    /**
     * 带有动画切换效果的启动Activity
     *
     * @param ctx
     * @param intent
     * @return
     */
    public static boolean launchAppWithAnim(Context ctx, Intent intent) {
        return launchAppWithAnim(ctx, intent, android.R.anim.fade_in, android.R.anim.fade_out);
    }

    /**
     * 带有动画切换效果的启动Activity
     *
     * @param ctx
     * @param intent
     * @param enterAnim
     * @param exitAnim
     * @return
     */
    public static boolean launchAppWithAnim(Context ctx, Intent intent, int enterAnim, int exitAnim) {
        if (ctx == null)
            throw new NullPointerException("ctx is null");
        try {
            ctx.startActivity(intent);
            if (ctx instanceof Activity) {
                ((Activity) ctx).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
            return true;
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean startActivityForResult(Activity ctx, Intent intent, int code) {
        if (ctx == null)
            throw new NullPointerException("ctx is null");
        try {
            ctx.startActivityForResult(intent, code);
            return true;
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Fragment和Activity均可唤起
     *
     * @param ctx
     * @param intent
     * @param requestCode
     * @return
     */
    public static boolean launchAppForResult(Activity ctx, Intent intent, int requestCode) {
        if (ctx == null)
            throw new NullPointerException("ctx is null");
        try {
            ctx.startActivityForResult(intent, requestCode);
            return true;
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 启动APP
     *
     * @param ctx   上下文
     * @param clazz 类名
     */
    public static void launchApp(Context ctx, Class<?> clazz) {
        if (clazz == null)
            throw new NullPointerException("the parameter is null");
        Intent intent = new Intent(ctx, clazz);
        launchApp(ctx, intent);
    }

    /**
     * 启动APP
     *
     * @param ctx         上下文
     * @param packageName 包名
     * @param className   类名
     */
    public static void launchApp(Context ctx, String packageName, String className) {
        if (packageName == null || className == null)
            throw new NullPointerException("the parameter is null");
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setComponent(new ComponentName(packageName, className));
        launchApp(ctx, intent);
    }

    /**
     * 结束当前页面
     *
     * @param ctx
     */
    public static void finish(Context ctx) {
        if (ctx == null)
            throw new NullPointerException("ctx is null");
        if (ctx instanceof Activity) {
            ((Activity) ctx).finish();
        }
    }

    /**
     * 卸载APP
     *
     * @param ctx        上下文参数
     * @param packageUri 包URI
     */
    public static void uninstallApp(Context ctx, Uri packageUri) {
        if (ctx == null || packageUri == null)
            throw new NullPointerException("the parameter is null");
        Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageUri);
        launchApp(ctx, uninstallIntent);
    }

    /**
     * 卸载APP
     *
     * @param ctx     上下文
     * @param pkgName 包名
     */
    public static void uninstallApp(Context ctx, String pkgName) {
        if (pkgName == null)
            throw new NullPointerException("the parameter is null");
        Uri packageUri = Uri.parse("package:" + pkgName);
        uninstallApp(ctx, packageUri);
    }

    /**
     * 启动系统设置页面
     *
     * @param ctx 上下文
     */
    public static void launchSettings(Context ctx) {
        Intent intent = new Intent(Settings.ACTION_SETTINGS);
        launchApp(ctx, intent);
    }

    /**
     * 启动系统浏览器页面
     *
     * @param ctx 上下文
     */
    public static void launchBrowser(Context ctx, String url) {
        Uri browserUri = Uri.parse(url);
        if (null != browserUri) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, browserUri);
            browserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            launchApp(ctx, browserIntent);
        }
    }

    /**
     * 获取包信息
     *
     * @return 包信息
     */
    public static PackageInfo getPackageInfo(Context ctx) {
        final PackageManager pm = ctx.getPackageManager();
        try {
            return pm.getPackageInfo(ctx.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 获取包信息
     *
     * @return 包信息
     */
    public static PackageManager getPackageManager(Context ctx) {
        return ctx.getPackageManager();
    }

    /**
     * 获取指定APP上下文
     *
     * @param context     上下文
     * @param packageName 包名
     * @return
     */
    public static Context getAppContext(Context context, String packageName) {
        if (context == null || packageName == null)
            throw new NullPointerException("the parameter is null");
        Context ctx = null;
        try {
            ctx = context.createPackageContext(packageName, Context.CONTEXT_IGNORE_SECURITY);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return ctx;
    }

    /**
     * 判断是否为系统APP
     *
     * @return
     */
    public static boolean isSystemApp(Context ctx, String packageName) {
        PackageManager packageManager = ctx.getPackageManager();
        boolean isSystemApp = false;
        try {
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(packageName, 0);
            if (applicationInfo != null) {
                isSystemApp = ((applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0)
                        || ((applicationInfo.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return isSystemApp;
    }

    /**
     * 获取版本Code
     *
     * @param packageName
     * @return
     */
    public static int getVersionCode(String packageName) {
        final PackageManager packageManager = App.get().getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(packageName, 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 获取VersonName
     *
     * @param packageName
     * @return
     */
    public static String getVersionName(String packageName) {
        final PackageManager packageManager = App.get().getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(packageName, 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取包名
     *
     * @return
     */
    public static String getPackageName() {
        return  App.get().getPackageName();
    }

    /**
     * com.tencent.mobileqq
     *
     * @param packageName
     * @return
     */
    public static boolean isAppExist(String packageName) {
        PackageManager packageManager =  App.get().getPackageManager();
        List<ApplicationInfo> applicationInfos = packageManager.getInstalledApplications(0);
        for (ApplicationInfo info : applicationInfos) {
            if (TextUtils.equals(info.packageName, packageName)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 判断是否在主线程里面
     *
     * @return
     */
    public static boolean inMainProcess() {
        String packageName =  App.get().getPackageName();
        String processName = getProcessName();
        return packageName.equals(processName);
    }

    public static final String getProcessName() {
        String processName = null;
        ActivityManager am = ((ActivityManager)  App.get().getSystemService(Context.ACTIVITY_SERVICE));
        if (am == null)
            return null;
        while (true) {
            for (ActivityManager.RunningAppProcessInfo info : am.getRunningAppProcesses()) {
                if (info.pid == android.os.Process.myPid()) {
                    processName = info.processName;
                    break;
                }
            }
            if (!TextUtils.isEmpty(processName)) {
                return processName;
            }
            try {
                Thread.sleep(100L);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }


    /**
     * 移动到顶部
     *
     * @param activity
     */
    public static void moveToFront(Activity activity) {
        //移动到顶部
        int taskID = activity.getTaskId();
        ActivityManager manager = (ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE);
        manager.moveTaskToFront(taskID, ActivityManager.MOVE_TASK_WITH_HOME);
    }



    /**
     * 判断某个界面是否在前台
     *
     * @param activity
     * @return 是否在前台显示
     */
    public static boolean isForeground(Activity activity) {
        if (activity == null) return false;
        ActivityManager am = (ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(1);
        if (list != null && list.size() > 0) {
            ComponentName cpn = list.get(0).topActivity;
            if (activity.getClass().getName().equals(cpn.getClassName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取系统语言
     * @return
     */
    public static String getSysLanguage () {
        return Locale.getDefault().getLanguage();
    }

    /**
     * 获取SDK版本
     */
    public static int getSDKVersion() {
        int version = 0;
        try {
            version = Integer.valueOf(android.os.Build.VERSION.SDK);
        } catch (NumberFormatException e) {
        }
        return version;
    }

    public static Activity scanForActivity(Context context) {
        if (context == null) return null;
        if (context instanceof Activity) {
            return (Activity) context;
        } else if (context instanceof ContextWrapper) {
            return scanForActivity(((ContextWrapper) context).getBaseContext());
        }
        return null;
    }
}
