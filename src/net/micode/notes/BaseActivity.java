package net.micode.notes;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.micode.notes.dialog.CustomExitDialog;
import net.micode.notes.dialog.FlippingLoadingDialog;
import net.micode.notes.entity.ResponseWrapper;
import net.micode.notes.entity.WeatherEntity;
import net.micode.notes.util.MD5Util;
import net.micode.notes.util.NetWorkUtils;
import net.micode.notes.util.Utils;
import net.micode.notes.view.HandyTextView;

import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.umeng.analytics.MobclickAgent;

public abstract class BaseActivity extends FragmentActivity {

    protected MyApplication mApplication;
    protected NetWorkUtils mNetWorkUtils;
    protected FlippingLoadingDialog mLoadingDialog;

    /**
     * 屏幕的宽度、高度、密度
     */
    protected int mScreenWidth;
    protected int mScreenHeight;
    protected float mDensity;

    protected List<AsyncTask<Void, Void, Boolean>> mAsyncTasks = new ArrayList<AsyncTask<Void, Void, Boolean>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
            // 透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        mApplication = (MyApplication) getApplication();
        mNetWorkUtils = new NetWorkUtils(this);
        mLoadingDialog = new FlippingLoadingDialog(this, "请求提交中");

        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        mScreenWidth = metric.widthPixels;
        mScreenHeight = metric.heightPixels;
        mDensity = metric.density;
        if (savedInstanceState != null) {
            Log.d("tag", "savedInstanceState--->" + savedInstanceState);
            Intent i = getBaseContext().getPackageManager()
                    .getLaunchIntentForPackage(getBaseContext().getPackageName());
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            this.finish();
            return;
        }
        MyApplication.getInstance().addActivity(this);
        context = this;
        initViews();
        initEvents();
        setListener();

    }

    @Override
    protected void onDestroy() {
        clearAsyncTask();

        // cancelToast();
        MyApplication.getInstance().removeActivity(this);

        super.onDestroy();

    }

    protected void putAsyncTask(AsyncTask<Void, Void, Boolean> asyncTask) {
        mAsyncTasks.add(asyncTask.execute());
    }

    protected void clearAsyncTask() {
        Iterator<AsyncTask<Void, Void, Boolean>> iterator = mAsyncTasks.iterator();
        while (iterator.hasNext()) {
            AsyncTask<Void, Void, Boolean> asyncTask = iterator.next();
            if (asyncTask != null && !asyncTask.isCancelled()) {
                asyncTask.cancel(true);
            }
        }
        mAsyncTasks.clear();
    }

    protected void showLoadingDialog(String text) {
        if (text != null) {
            mLoadingDialog.setText(text);
        }
        mLoadingDialog.show();
    }

    protected void dismissLoadingDialog() {
        if (mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
    }

    /** 短暂显示Toast提示(来自res) **/
    protected void showShortToast(int resId) {
        Toast.makeText(this, getString(resId), Toast.LENGTH_SHORT).show();
    }

    /** 短暂显示Toast提示(来自String) **/
    protected void showShortToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    /** 长时间显示Toast提示(来自res) **/
    protected void showLongToast(int resId) {
        Toast.makeText(this, getString(resId), Toast.LENGTH_LONG).show();
    }

    /** 长时间显示Toast提示(来自String) **/
    protected void showLongToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }

    /**
     * toast提示
     * 
     * @param text
     *            -提示内容
     */

    public void ToastMessage(String text) {
        if (mToast == null) {
            mToast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(text);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

    /** 显示自定义Toast提示(来自res) **/
    protected void showCustomToast(int resId) {
        View toastRoot = LayoutInflater.from(BaseActivity.this).inflate(R.layout.common_toast, null);
        ((HandyTextView) toastRoot.findViewById(R.id.toast_text)).setText(getString(resId));
        Toast toast = new Toast(BaseActivity.this);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(toastRoot);
        toast.show();
    }

    /** 显示自定义Toast提示(来自String) **/
    protected void showCustomToast(String text) {
        View toastRoot = LayoutInflater.from(BaseActivity.this).inflate(R.layout.common_toast, null);
        ((HandyTextView) toastRoot.findViewById(R.id.toast_text)).setText(text);
        Toast toast = new Toast(BaseActivity.this);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(toastRoot);
        toast.show();
    }

    protected void showToast(String text) {
        View toastRoot = LayoutInflater.from(BaseActivity.this).inflate(R.layout.common_toast, null);
        ((HandyTextView) toastRoot.findViewById(R.id.toast_text)).setText(text);

        if (mToast == null) {
            mToast = new Toast(BaseActivity.this);
            mToast.setGravity(Gravity.CENTER, 0, 0);
            mToast.setDuration(Toast.LENGTH_SHORT);
            mToast.setView(toastRoot);
        } else {
            mToast.setView(toastRoot);
            mToast.setText(text);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

    /** Debug输出Log日志 **/
    protected void showLogDebug(String tag, String msg) {
        Log.d(tag, msg);
    }

    /** Error输出Log日志 **/
    protected void showLogError(String tag, String msg) {
        Log.e(tag, msg);
    }

    /** 通过Class跳转界面 **/
    protected void startActivity(Class<?> cls) {
        startActivity(cls, null);
    }

    /** 含有Bundle通过Class跳转界面 **/
    protected void startActivity(Class<?> cls, Bundle bundle) {
        MyApplication.getInstance().addActivity(this);
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /** 通过Action跳转界面 **/
    protected void startActivity(String action) {
        startActivity(action, null);
    }

    /** 含有Bundle通过Action跳转界面 **/
    protected void startActivity(String action, Bundle bundle) {
        Intent intent = new Intent();
        intent.setAction(action);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /** 含有标题和内容的对话框 **/
    protected AlertDialog showAlertDialog(String title, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).setTitle(title).setMessage(message).show();
        return alertDialog;
    }

    /** 含有标题、内容、两个按钮的对话框 **/
    protected AlertDialog showAlertDialog(String title, String message, String positiveText,
            DialogInterface.OnClickListener onPositiveClickListener, String negativeText,
            DialogInterface.OnClickListener onNegativeClickListener) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).setTitle(title).setMessage(message)
                .setPositiveButton(positiveText, onPositiveClickListener)
                .setNegativeButton(negativeText, onNegativeClickListener).show();
        return alertDialog;
    }

    /** 含有标题、内容、图标、两个按钮的对话框 **/
    protected AlertDialog showAlertDialog(String title, String message, int icon, String positiveText,
            DialogInterface.OnClickListener onPositiveClickListener, String negativeText,
            DialogInterface.OnClickListener onNegativeClickListener) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).setTitle(title).setMessage(message).setIcon(icon)
                .setPositiveButton(positiveText, onPositiveClickListener)
                .setNegativeButton(negativeText, onNegativeClickListener).show();
        return alertDialog;
    }

    /** 默认退出 **/
    protected void defaultFinish() {
        super.finish();
    }

    protected Activity context;
    private Toast mToast;
    private long mExitTime = 0;

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        /*
         * if (keyCode == KeyEvent.KEYCODE_BACK) { if ((System.currentTimeMillis() - mExitTime) > 2000) {
         * Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show(); mExitTime = System.currentTimeMillis(); } else {
         * Utils.finish(this); } return true; }
         */
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) { // 按下的如果是BACK，同时没有重复
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                // Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            } else {
                CustomExitDialog exitDialog = new CustomExitDialog(this, R.style.customExitDailogStyle,
                        R.layout.custom_exit_dialog);
                exitDialog.show();
            }
        }
        return true;
    }

    /** 初始化视图 **/
    protected abstract void initViews();

    /** 初始化事件 **/
    protected abstract void initEvents();

    /**
     * 设置监听
     */
    protected abstract void setListener();

    /**
     * 打开 Activity
     * 
     * @param activity
     * @param cls
     * @param name
     */
    public void start_Activity(Activity activity, Class<?> cls, BasicNameValuePair... name) {
        Utils.start_Activity(activity, cls, name);
    }

    /**
     * 关闭 Activity
     * 
     * @param activity
     */
    public void finish(Activity activity) {
        MyApplication.getInstance().removeActivity(activity);
        Utils.finish(activity);
    }

    /**
     * 判断是否有网络连接
     */
    public boolean isNetworkAvailable(Context context) {
        return Utils.isNetworkAvailable(context);
    }

    /**
     * 从sdCard显示图片
     * 
     * @param imageView
     *            -组件, bootpath-路径,imgName-图片名称
     * @author Junhui
     */
    public void showImage(ImageView imageView, String bootpath, String imgName) throws IOException {
        Log.d(getLocalClassName(), "showImage -- 被执行 --- imgName == " + imgName);
        if (isHavedSDcard()) {
            try {
                String path = bootpath + "/" + imgName;
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 3;
                Bitmap bmLocal = BitmapFactory.decodeFile(path, options);
                imageView.setImageBitmap(bmLocal);
            } catch (NullPointerException e) {
                e.printStackTrace();
                imageView.setBackgroundColor(Color.RED);
            }
        } else {
            imageView.setBackgroundColor(Color.WHITE);
        }
    }

    /**
     * 判断是否有SDcard
     * 
     * @return true-有,false-无
     * @author Junhui
     */
    public boolean isHavedSDcard() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
            return true;
        else
            return false;
    }

    /**
     * 判断文件夹是否存在
     * 
     * @param path
     *            -路径,fileName-文件名
     * @return true-存在,false-不存在
     * @author Junhui
     */
    public boolean isHasFile(String path, String fileName) {
        File file = new File(path + "/" + fileName);
        if (!file.exists())
            return false;
        return true;
    }

    /**
     * 删除文件
     * 
     * @param path
     *            -路径,name-文件名
     * @author Junhui
     */
    public void deleteFile(String path, String name) {
        File file = new File(path + "/" + name);
        if (file.exists()) // 不存在返回 false
            if (file.isFile()) // 为文件时调用删除文件方法
                deleteFile(path + name);

    }

    /**
     * 获取SDcard根目录
     */
    public String getRootPath() {
        return Environment.getExternalStorageDirectory().toString();
    }

    /**
     * 检测网络状态
     *
     * @param context
     *            -容器
     * @return true-可用,false-不可用
     */
    public static boolean isNetWorkAvailable(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = cm.getActiveNetworkInfo();
            return (info != null && info.isConnected());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 动态创建线性布局
     * 
     * @param iOrientation
     *            -对齐方式-垂直/水平
     * @author Junhui
     */
    public LinearLayout createLayout(int iOrientation) {
        LinearLayout layout = new LinearLayout(this);
        layout.setLayoutParams(new LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT,
                android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
        layout.setOrientation(iOrientation);
        return layout;
    }

    /**
     * 设置网络对话框
     * 
     * @author Junhui
     */
    public void showNetUnAvailableDialog() {
        AlertDialog.Builder builder = new Builder(this);
        builder.setTitle("提示!");
        builder.setMessage("无可用网络，是否前去设置?");
        builder.setPositiveButton("设置", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent("/");
                ComponentName cm = new ComponentName("com.android.settings", "com.android.settings.WirelessSettings");
                intent.setComponent(cm);
                intent.setAction("android.intent.action.VIEW");
                startActivityForResult(intent, 0);
            }
        });
        builder.setNegativeButton("取消", null);
        builder.show();
    }

    /**
     * 往shared 写入数据
     * 
     * @param key
     *            -键,Object-写入的对象
     * @author Junhui
     */
    public void writeObjectToShared(String key, Object object) {
        SharedPreferences sp = this.getSharedPreferences("Content.SPNAME", Context.MODE_PRIVATE);
        Editor editor = sp.edit();
        if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        }

        editor.commit();
    }

    /**
     * 从shared里获取 String 数据
     * 
     * @param key
     *            -键, defaultValues-默认返回值
     * @return value
     * @author Junhui
     */
    public String getStringFromShared(String key, String defaultValue) {
        SharedPreferences sp = this.getSharedPreferences("Content.SPNAME", Context.MODE_PRIVATE);
        return sp.getString(key, defaultValue);
    }

    /**
     * 从shared里获取 boolean 数据
     * 
     * @param key
     *            -键, defaultValues-默认返回值
     * @return value
     * @author Junhui
     */
    public Boolean getBooleanFromShared(String key, boolean defaultValue) {
        SharedPreferences sp = this.getSharedPreferences("Content.SPNAME", Context.MODE_PRIVATE);
        return sp.getBoolean(key, defaultValue);
    }

    /**
     * 从shared里获取 int 数据
     * 
     * @param key
     *            -键, defaultValues-默认返回值
     * @return value
     * @author Junhui
     */
    public Integer getIntFromShared(String key, int defaultValue) {
        SharedPreferences sp = this.getSharedPreferences("Content.SPNAME", Context.MODE_PRIVATE);
        return sp.getInt(key, defaultValue);
    }

    /**
     * 提示卸载原有APP
     */
    protected void showInstallDialog(String title, String message, final String pkg) {
        AlertDialog.Builder builder = new Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                startUninstall(pkg);
            }
        });
        builder.show();
    }

    /**
     * 判断有无安装某软件
     * 
     * @param pkgName
     *            -包名
     */
    protected boolean isApkInstalled(final String pkgName) {
        try {
            getPackageManager().getPackageInfo(pkgName, 0);
            return true;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 卸载包
     * 
     * @param pkg
     *            -包名称
     */
    protected void startUninstall(String pkg) {
        Uri packageURI = Uri.parse("package:" + pkg);
        Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
        startActivity(uninstallIntent);
    }

    /**
     * 将APP拖入通知栏
     */
    public void showNotification(int icon, String appName, String message) {

        Intent intent = new Intent();
        ComponentName componentName = new ComponentName("com.example.notificationintent",
                "com.example.notificationintent.MainActivity");
        intent.setComponent(componentName);
        intent.setAction("Android.intent.action.MAIN");
        intent.addCategory("Android.intent.category.LAUNCHER");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, 0);
        Notification notif = new Notification(icon, appName + "正在运行", System.currentTimeMillis());
        notif.flags = Notification.FLAG_NO_CLEAR;
        notif.setLatestEventInfo(this, appName, message, contentIntent);

        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.notify(R.string.app_name, notif);
    }

    /**
     * 关闭图标通知
     */
    public void doExit() {
        // this.finish();
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.cancel(R.string.app_name);
    }

    /**
     * 判断sdcard上剩余空间
     * 
     * @param sizeMb
     *            -MB
     * @return true-有剩余，false-剩余不足
     */
    public boolean isAvaiableSpace(int sizeMb) {
        boolean ishasSpace = false;
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
            String sdcard = Environment.getExternalStorageDirectory().getPath();
            StatFs statFs = new StatFs(sdcard);
            long blockSize = statFs.getBlockSize();
            long blocks = statFs.getAvailableBlocks();
            long availableSpare = (blocks * blockSize) / (1024 * 1024);
            Log.d("剩余空间", "availableSpare = " + availableSpare);
            if (availableSpare > sizeMb) {
                ishasSpace = true;
            }
        }
        return ishasSpace;
    }

    /**
     * @description: 获得字符MD5摘要
     * @param strObj
     * @return
     * @author: wht
     * @date: 2015-3-11 下午2:20:12
     */
    public String GetMD5Code(String strObj) {
        return MD5Util.md5(strObj);
    }
    /**
     * @Description (获得天气信息)
     * @return
     */
    public WeatherEntity getWeatherEntity(){
        return new GsonBuilder().create().fromJson(ConstantProvider.weather_data, ResponseWrapper.class).getResults().get(0);
    }
    /**
     * @Description ()
     * @param titleString
     */
    public void initTopTitle(final Activity activity) {
        ImageView righttitle = (ImageView) findViewById(R.id.righttitle);
        righttitle.setVisibility(View.INVISIBLE);
        ImageView topback = (ImageView) findViewById(R.id.topback);

        topback.setBackgroundResource(R.drawable.ic_topbar_back_normal);
        topback.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Utils.finish(activity);
            }
        });
    }
}
