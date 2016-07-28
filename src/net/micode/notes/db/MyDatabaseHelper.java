package net.micode.notes.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @ClassName MyDatabaseHelper
 * @Description TODO(这里用一句话描述这个类的作用)
 * @Date 2016年7月28日 下午2:59:59
 * @version 1.0.0
 */
public class MyDatabaseHelper extends SQLiteOpenHelper {

    private static final String name = "lemon.db";// 数据库名称
    private static final int version = 1;// 数据库版本
    private final String CREATE_TABLE_SQL = "create table said(_id integer primary key UNIQUE ON CONFLICT REPLACE,taici,cat,catcn,show,source,inserttime)";
    private final String CREATE_TABLE_NEWSDETAILCONTENT = "create table NewsDetailContent(_id integer primary key autoincrement, channelId,channelName,desc,imageUrl,link,long_desc,pubDate,source,timeStamp,title UNIQUE ON CONFLICT REPLACE)";
    private final String CREATE_TABLE_HOTARTICLE = "CREATE TABLE IF NOT EXISTS HotArticle (_id integer primary key autoincrement, ctime, title UNIQUE ON CONFLICT REPLACE,description,picUrl,url,timestamp)";
    private final String CREATE_TABLE_LOGIN="CREATE TABLE IF NOT EXISTS Login (_openid  primary key UNIQUE ON CONFLICT REPLACE, ret, pay_token,pf,query_authority_cost,authority_cost,expires_in,pfkey,msg,access_token,login_cost)";
    private final String CREATE_TABLE_USER = "CREATE TABLE IF NOT EXISTS User (_nickname  primary key UNIQUE ON CONFLICT REPLACE, is_yellow_year_vip, ret,figureurl_qq_1,figureurl_qq_2,yellow_vip_level,is_lost,msg,city,figureurl_1,vip,level,figureurl_2,province,is_yellow_vip,gender,figureurl,openid)";
    /**
     * @Description TODO(这里用一句话描述这个方法的作用)
     * @param context
     */
    public MyDatabaseHelper(Context context) {
        super(context, name, null, version);
    }

    /**
     * @Description TODO(这里用一句话描述这个方法的作用)
     * @param context
     * @param name
     * @param version
     */
    public MyDatabaseHelper(Context context, String name, int version) {
        super(context, name, null, version);
    }

    /*
     * (非 Javadoc) Description:
     * @see android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite.SQLiteDatabase)
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // 第一次使用数据库时自动建表
        db.execSQL(CREATE_TABLE_SQL);
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_NEWSDETAILCONTENT);
        db.execSQL(CREATE_TABLE_HOTARTICLE);
        db.execSQL(CREATE_TABLE_LOGIN);
    }

    /*
     * (非 Javadoc) Description:
     * @see android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite.SQLiteDatabase, int, int)
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        System.out.println("--------onUpdate Called--------" + oldVersion + "--->" + newVersion);
    }
}
