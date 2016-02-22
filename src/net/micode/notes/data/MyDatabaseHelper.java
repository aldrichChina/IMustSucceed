package net.micode.notes.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabaseHelper extends SQLiteOpenHelper {
	private static final String name = "lemon.db";// 数据库名称
	private static final int version = 1;// 数据库版本
	final String CREATE_TABLE_SQL = "create table said(_id integer primary key ,taici,cat,catcn,show,source,inserttime)";
	String CREATE_TABLE_USER="CREATE TABLE IF NOT EXISTS user (id integer primary key autoincrement, enterprisename, lastlogintime,lastloginip)";
	public MyDatabaseHelper(Context context) {
		super(context, name, null, version);
	}

	public MyDatabaseHelper(Context context, String name, int version) {
		super(context, name, null, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// 第一次使用数据库时自动建表
		db.execSQL(CREATE_TABLE_SQL);
		db.execSQL(CREATE_TABLE_USER);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		System.out.println("--------onUpdate Called--------" + oldVersion
				+ "--->" + newVersion);
	}
}
