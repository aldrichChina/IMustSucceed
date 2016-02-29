/**   
 * @title: ConstantUtil.java 
 * @package: com.founder.apabi.medical.bussiness.util 
 * @description: TODO
 * @author wht  
 * @date 2015-2-10 下午1:29:00 
 * @version 1.0.0 
 */
package net.micode.notes.data;

import java.util.HashMap;
import java.util.Map;

/**
 * @description: 常量管理类
 * @author: wht
 * @date: 2015-2-10 下午1:29:00
 * @version: V1.0.0
 */
public class ConstantUtil {
	// 通讯录标记
	public static int contactFlag = 2;
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
}
