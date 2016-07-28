package net.micode.notes;

import java.util.HashMap;
import java.util.Map;

import net.micode.notes.util.DeviceInfoUtil;
import net.micode.notes.util.Utils;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;

public class ConstantProvider {

    // 登录数据库判断
    // public static int loginDB = 2;
    public static final String ENCODING = "UTF-8";
    // TABLE_NAME: 数据库名称
    public static final String DATABASE_NAME = "mutation.db";
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_PASSWORD = "Mutation.2016!@#";
    // "Mutation.2016!@#";
    // public static final String URL = "http://192.168.32.220:7070";
    public static final String URL = "http://172.26.2.109";
    // jsessionid "http://invoice.aisino.com";
    public static final String JSESSIONID = "jsessionid";
    // token
    public static final String TOKEN = "token";
    // 用户id
    public static final String USERID = "userid";
    // 用户
    public static final String USER = "user";
    // 管理sessionID map
    public static Map<String, String> sessionMap = new HashMap<String, String>();
    // 存放内存信息
    public static Map<String, Object> infoMap = new HashMap<String, Object>();
    public static int pageSize = 20;
    // dao类中操作信息标签常量
    public static final String INSEART = "INSEART";
    public static final String UPDATE = "UPDATE";
    public static final String SELECT = "SELECT";
    public static final String DELETE = "DELETE";
    public static String newsTitle = "";
    // 信息编码
    public static String RTCODE = "0";
    // 操作信息
    public static String RTMSG = "";
    // 管理设备信息
    public static Map<String, String> equipinfoMap = new HashMap<String, String>();

    // 设备信息
    public static void initEquipInfo(Context context) {
        try {
            PackageManager pm = context.getApplicationContext().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getApplicationContext().getPackageName(), 0);
            equipinfoMap.put("brand", Build.BRAND);
            equipinfoMap.put("model", Build.MODEL);
            equipinfoMap.put("sdk", Build.VERSION.RELEASE);
            equipinfoMap.put("version", pi.versionName);
            equipinfoMap.put("versionCode", "" + pi.versionCode);
            equipinfoMap.put("deviceID", DeviceInfoUtil.getid(context));
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    // session和token、userid的设置获取
    public static String getSession() {
        if (sessionMap.containsKey(JSESSIONID)) {
            Utils.Log("getSession==" + sessionMap.get(JSESSIONID));
            return sessionMap.get(JSESSIONID);
        }
        return "";
    }

    public static void setSession(String str) {
        Utils.Log("setSession==" + str);
        sessionMap.put(JSESSIONID, str);
    }

    public static String getToken() {
        if (sessionMap.containsKey(TOKEN)) {
            Utils.Log("getToken==" + sessionMap.get(TOKEN));
            return sessionMap.get(TOKEN);
        }
        return "";
    }

    public static void setToken(String str) {
        Utils.Log("setToken==" + str);
        sessionMap.put(TOKEN, str);
    }

    public static String getUserId() {
        if (sessionMap.containsKey(USERID)) {
            Utils.Log("getUserId==" + sessionMap.get(USERID));
            return sessionMap.get(USERID);
        }
        return "";
    }

    public static void setUserId(String str) {
        Utils.Log("setUserId==" + str);
        sessionMap.put(USERID, str);
    }

    public static final String APIKEY = "334070f0f84d859e75972ebfdaae49fe";
    public static final String BaseURL = "http://apis.baidu.com/";
    public static final String httpUrl = "http://apis.baidu.com/acman/zhaiyanapi/tcrand";
    public static final String httpArg = "fangfa=json";
    public static final String MailData = "showapi_open_bus/channel_news/search_news";
    public static final String HTTPURLMEINV = "http://apis.baidu.com/txapi/mvtp/meinv";
    public static final String WXHOTURL = "txapi/weixin/wxhot";
}
