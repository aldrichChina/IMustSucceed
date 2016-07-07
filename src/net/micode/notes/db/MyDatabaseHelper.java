package net.micode.notes.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private static final String name = "lemon.db";// 数据库名称
    private static final int version = 1;// 数据库版本
    final String CREATE_TABLE_SQL = "create table said(_id integer primary key UNIQUE ON CONFLICT REPLACE,taici,cat,catcn,show,source,inserttime)";
    String CREATE_TABLE_USER = "CREATE TABLE IF NOT EXISTS user (_id integer primary key autoincrement, enterprisename, lastlogintime,lastloginip)";
    String CREATE_TABLE_NEWSDETAILCONTENT = "create table NewsDetailContent(_id integer primary key autoincrement, channelId,channelName,desc,imageUrl,link,long_desc,pubDate,source,timeStamp,title UNIQUE ON CONFLICT REPLACE)";
    private final String CREATE_TABLE_HOTARTICLE = "CREATE TABLE IF NOT EXISTS HotArticle (_id integer primary key autoincrement, ctime, title UNIQUE ON CONFLICT REPLACE,description,picUrl,url,timestamp)";

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
