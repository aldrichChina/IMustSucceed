package net.micode.notes.data;

public class ErrorCode {
	
	public static final int R_INITIALIZE = -1;	// 初始值
	public static final int R_OK = 0;	// 成功
	public static final int R_FAIL = 1;	// 失败
	public static final int R_EXCEPTION = 2;	// 异常错误
	public static final int R_PARAMERROR = 3;	// 参数错误
	public static final int R_PASSWORDERROR = 4;	// 密码错误
	public static final int R_NOEMAILACTIVED = 5;	// email地址未激活
	public static final int R_NOSERVICEID = 6;	// 获取用户对应服务单位失败
	public static final int R_VERIFYFAIL = 7;	// 校验失败
	public static final int R_TOKENFAILED = 8;
	public static final int R_PARTOK = 9;	// 部分成功
	public static final int R_NOLOGIN = 10;	// 未登录
	public static final int R_USERDELETE = 11;	// 用户已被注销
	public static final int R_TAXCODENOMATCH = 12;	// 企业名称和税号不匹配
	public static final int R_NOENTERPRISE = 13;	// 没有该企业
	public static final int R_EXISTS = 14;	// 已存在
	public static final int R_NOEXISTS = 15;	// 不存在
	public static final int R_ENTERPRISEDELETE = 16;	// 企业已被停用
	public static final int R_SENDSMSERROR = 17;	// 发送短信失败
	public static final int R_NOLOGININFO = 18;	// 未找到登录用户信息
	public static final int R_RESPONSEISNULL = 19;	// 请求为空
}
