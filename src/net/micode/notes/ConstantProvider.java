package net.micode.notes;

import java.util.HashMap;
import java.util.Map;

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

    // 信息编码
    public static String RTCODE = "0";
    // 操作信息
    public static String RTMSG = "";

    public static final String HTTPURL = "http://apis.baidu.com/";
    public static final String httpUrl = "http://apis.baidu.com/acman/zhaiyanapi/tcrand";
    public static final String httpArg = "fangfa=json";
    public static final String MailData = "showapi_open_bus/channel_news/search_news";
    public static final String HTTPURLMEINV = "http://apis.baidu.com/txapi/mvtp/meinv";

}