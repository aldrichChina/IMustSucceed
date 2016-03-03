package net.micode.notes.tool.HttpUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.micode.notes.BaseApplication;
import net.micode.notes.data.ConstantUtil;
import net.micode.notes.data.DatabaseService;
import net.micode.notes.data.ErrorCode;
import net.micode.notes.entities.RelationUserEnterprise;
import net.micode.notes.entities.User;
import net.micode.notes.tool.DateTimeUtils;
import net.micode.notes.tool.JSONUtil;
import net.micode.notes.tool.MD5Util;
import net.micode.notes.tool.Utils;
import okhttp3.Request;
import okhttp3.Response;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.lidroid.xutils.http.RequestParams;

public class HttpService {
	private static String responseBody;
	public static String OKHttpGet(String httpUrl, String httpArg) {
		try {
			httpUrl = httpUrl + "?" + httpArg;
			Request request = new Request.Builder().url(httpUrl).addHeader("apikey", "334070f0f84d859e75972ebfdaae49fe").build();
			Response response = BaseApplication.client.newCall(request).execute();
			Utils.Log("response=" + response);
			if (!response.isSuccessful())throw new IOException("Unexpected code " + response);
			responseBody = response.body().string();
			Utils.Log("responseBody=" + responseBody);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return responseBody;
	}
	/**
	 * @description: 登录
	 * @return
	 * @author: wht
	 * @date: 2015-3-5 上午11:04:53
	 */
	public static boolean login(List<String> list,Context context) {
		// http发送请求
		Boolean flag = false;
		JSONObject jsonObject = null;
		try {
			jsonObject = sendPost(list);
			Log.d("tag", "login(List<String> list)--->" + jsonObject);
			if (jsonObject == null) {
				return flag;
			}
			String rtCode = jsonObject.getString("rtCode");
			ConstantUtil.RTCODE = rtCode;
			ConstantUtil.RTMSG = jsonObject.getString("rtMsg");
			if ("0".equals(rtCode)) {
				JSONObject json = jsonObject.getJSONObject("rtData");
				flag = true;
				Log.i("tag", json.toString());
				User user = ParseUser(json);
				// 如果RelationUserEnterprise对象为空，
				RelationUserEnterprise rue = user.getRelationuserenterprise();
				user.setRelationuserenterprise(rue);
//				Log.d("tag","login(List<String> list)--ConstantUtil.contactFlag--->"+ ConstantUtil.loginDB);
//				if (ConstantUtil.loginDB == 0) {
//					Log.d("tag", "本地保存数据ConstantUtil.loginDB--->"+ ConstantUtil.loginDB);
					// 本地保存数据
					if (rue != null) {
//						flag = userDAO.saveOrUpdate(rue);
						Utils.Log(rue.toString());
					}
//					flag = userDAO.saveOrUpdate(user);
					flag = new DatabaseService(context).insertUser(user);
					Utils.Log(user.toString());
//					ConstantUtil.loginDB = 1;
//					Log.d("tag", "本地保存数据ConstantUtil.loginDB--->"+ ConstantUtil.loginDB);
					if (flag == false) {
						ConstantUtil.RTMSG = "user 数据存储错误";
						return flag;
					}
//				}
				// token
				ConstantUtil.infoMap.put(ConstantUtil.TOKEN,
						jsonObject.getString(ConstantUtil.TOKEN));
				// 用户信息
				ConstantUtil.infoMap.put(ConstantUtil.USER, user);
			}
		} catch (JSONException e) {
			ConstantUtil.RTMSG = "登录发生异常，" + e.getMessage();
			e.printStackTrace();
			flag = false;
		}

		return flag;
	}
	/**
	 * @description: post方式http请求
	 * @param list
	 * @return
	 * @author: wht
	 * @date: 2015-2-26 下午3:47:36
	 */
	public static JSONObject sendPost(List<String> list) {
		if (list.size() == 0) {
			ConstantUtil.RTCODE = String.valueOf(ErrorCode.R_PARAMERROR);
			ConstantUtil.RTMSG = "传递参数错误";
			return null;
		}
		String method = list.get(0);
		String url = ConstantUtil.URL + method;
		// 参数保存
		// NameValuePair[] data = new NameValuePair[list.size()];
		Map<String, String> param = new LinkedHashMap<String, String>();
		// 参数加密
		boolean flag = processParameter(list, param);
		String result = HttpUtil.sendPostUCODE(url, param, "UTF-8");
		if (result.equals("") || result == null) {
			return null;
		}
		// json转换对象
		JSONObject object = JSONUtil.testJson(result);
		try {
			Log.i("tag", list.toString());
			Log.i("tag", object.toString());
			if (object.getString("rtCode").equals("23")) {
				List<String> reloglist = new ArrayList<String>();
				String[] relogparams = { "/user/login.html",
						"name=" + ConstantUtil.sessionMap.get("currentname"),
						"pwd=" + ConstantUtil.sessionMap.get("currentpwd"),
						"mobileid=" + ConstantUtil.sessionMap.get("deviceID"),
						"cv=" + ConstantUtil.sessionMap.get("version"),
						"force=0" };
				for (int i = 0; i < relogparams.length; i++) {
					reloglist.add(relogparams[i]);
				}
				if (relogin(reloglist)) {
					Log.i("tag", "relogin success");
					StringBuilder sb = new StringBuilder();
					String head = list.get(0);
					sb.append(head.substring(0, head.indexOf("=") + 1)).append(
							getJSessionId());
					head = new String(sb);
					list.set(0, head);
					list.set(1, "token=" + getToken());
					object = sendPost(list);
				} else {
					Log.e("list", "重新登录失败");
					return null;
				}
			}
			String token = object.getString(ConstantUtil.TOKEN);
			Log.i(ConstantUtil.TOKEN, object.getString(ConstantUtil.TOKEN));
			if (!"null".equals(token)) {
				JSONObject rtData = object.getJSONObject("rtData");
				ConstantUtil.sessionMap.put(ConstantUtil.TOKEN, token);
				if (rtData.has("id")) {
					String userId = rtData.getString("id");
					ConstantUtil.sessionMap.put(ConstantUtil.USERID, userId);
				}
			}
			String jsessionid = object.getString(ConstantUtil.JSESSIONID);
			if (!"null".equals(jsessionid)) {
				ConstantUtil.sessionMap
						.put(ConstantUtil.JSESSIONID, jsessionid);
			}
		} catch (JSONException e) {
			e.printStackTrace();
			ConstantUtil.RTCODE = String.valueOf(ErrorCode.R_EXCEPTION);
			ConstantUtil.RTMSG = "sendPost json数据解析错误" + e.getMessage();
		}
		return object;
	}
	private static User ParseUser(JSONObject json) {
		String strText = "";
		User user = new User();
		try {
			// 用户编号
			user.setId(json.getString("id"));
			// 姓名
			user.setName(json.getString("name"));
			// 性别
			strText = json.getString("sex");
			if ((strText != null) && (strText.length() > 0)
					&& (strText != "null"))
				user.setSex(Short.valueOf(strText));
			// 手机号
			user.setPhone(json.getString("phone"));
			// 年龄
			strText = json.getString("age");
			if ((strText != null) && (strText.length() > 0)
					&& (strText != "null"))
				user.setAge(Short.valueOf(strText));
			// status
			strText = json.getString("status");
			if ((strText != null) && (strText.length() > 0)
					&& (strText != "null"))
				user.setStatus(Short.valueOf(strText));
			// inserttime
			strText = json.getString("inserttime");
			if ((strText != null) && (strText.length() > 0)
					&& (strText != "null"))
				user.setInserttime(DateTimeUtils.stringToDate(strText));
			// 备注
			user.setNotes(json.getString("notes"));

			strText = json.getString("relationUserEnterprise");
			if ((strText == null) || (strText.length() <= 0)
					|| (strText == "null"))
				return user;
			JSONObject jsonRelation = json
					.getJSONObject("relationUserEnterprise");
			if (jsonRelation == null)
				return user;

			RelationUserEnterprise rUserEnterprise = new RelationUserEnterprise();
			rUserEnterprise.setId(jsonRelation.getString("id"));
			rUserEnterprise.setUserid(user.getId());
			rUserEnterprise.setDepartment(jsonRelation.getString("department"));
			rUserEnterprise.setPost(jsonRelation.getString("post"));
			rUserEnterprise.setEnterprisename(jsonRelation.getString("enterprisename"));
			rUserEnterprise.setTaxcode(jsonRelation.getString("taxcode"));
			user.setRelationuserenterprise(rUserEnterprise);

		} catch (JSONException e) {
			ConstantUtil.RTMSG = "解析用户json数据异常";
			e.printStackTrace();
			return null;
		} catch (ParseException pe) {
			ConstantUtil.RTMSG = "解析用户插入时间json数据异常";
			pe.printStackTrace();
			return null;
		}

		return user;
	}
	// NameValuePair[] data
	public static boolean processParameter(List<String> list, Map<String, String> map) {
		boolean flag = true;
		// 需要加密参数保存字符串
		StringBuffer parUrl = new StringBuffer(list.get(0));
		RequestParams params = new RequestParams();
		for (int i = 1; i < list.size(); i++) {
			String str = list.get(i);
			String[] par = str.split("=", 2);
			try {
				// 参数进行编码
				map.put(par[0], URLEncoder.encode(par[1], "utf-8"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// data[i-1]= new NameValuePair(par[0], par[1]);
			parUrl.append(str);
		}
		Log.i("ParUrl", parUrl.toString());
		// 对url进行加密
		String sign = null;
		try {

			String parU = parUrl.toString();
			Log.i("编码前签名", parU);
			// parU = URLEncoder.encode(parUrl.toString(),"UTF-8");
			Log.i("编码后签名", parU);
			Log.i("编码后 urlde", parUrl.toString());
			// sign = DesUtil.encrypt(parU);
			sign = MD5Util.md5(parU);
			Log.i("加密后sign", sign);
			// 替换base64转换换行符
			sign = sign.replaceAll("[\n]", "");
			// data[data.length-1]= new NameValuePair("sign", sign);
			map.put("v", sign);
			Log.i("加密后 urlde", sign);
			Log.i("httpurl", parUrl.toString() + "&v=" + sign);
			Log.i("SIGN 加密", sign);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			flag = false;
			ConstantUtil.RTMSG = "参数加密异常";
			Log.e("httpUrl", "url加密错误");
			e1.printStackTrace();
		}

		return true;
	}
	// session失效时，重新自动登录

	public static boolean relogin(List<String> list) {
		// http发送请求
		Boolean flag = false;
		JSONObject jsonObject = null;
		try {
			if (list.size() == 0) {
				ConstantUtil.RTCODE = String.valueOf(ErrorCode.R_PARAMERROR);
				ConstantUtil.RTMSG = "传递参数错误";
				return false;
			}
			String method = list.get(0);
			String url = ConstantUtil.URL + method;
			// 参数保存
			// NameValuePair[] data = new NameValuePair[list.size()];
			Map<String, String> param = new LinkedHashMap<String, String>();
			// 参数加密
			boolean flag2 = processParameter(list, param);
			String result = HttpUtil.sendPostUCODE(url, param, "UTF-8");
			if (result.equals("") || result == null) {
				return false;
			}
			// json转换对象
			jsonObject = JSONUtil.testJson(result);
			if (jsonObject == null) {
				return flag;
			}
			Log.i("tag", "login(List<String> list)--->" + jsonObject.toString());
			String rtCode = jsonObject.getString("rtCode");
			ConstantUtil.RTCODE = rtCode;
			ConstantUtil.RTMSG = jsonObject.getString("rtMsg");
			if ("0".equals(rtCode)) {
				JSONObject json = jsonObject.getJSONObject("rtData");
				flag = true;
				Log.i("tag", json.toString());
				User user = ParseUser(json);
				// 如果RelationUserEnterprise对象为空，
				RelationUserEnterprise rue = user.getRelationuserenterprise();
				user.setRelationuserenterprise(rue);
				// 本地保存数据
				if (rue != null) {
//					flag = userDAO.saveOrUpdate(rue);
					Utils.Log(rue.toString());
				}
//				flag = userDAO.saveOrUpdate(user);
				Utils.Log(user.toString());
				if (flag == false) {
					ConstantUtil.RTMSG = "user 数据存储错误";
					return flag;
				}
				ConstantUtil.infoMap.put(ConstantUtil.TOKEN,
						jsonObject.getString(ConstantUtil.TOKEN));
				// 用户信息
				ConstantUtil.infoMap.put(ConstantUtil.USER, user);
			}
			String token = jsonObject.getString(ConstantUtil.TOKEN);
			Log.i(ConstantUtil.TOKEN, jsonObject.getString(ConstantUtil.TOKEN));
			if (!"null".equals(token)) {
				JSONObject rtData = jsonObject.getJSONObject("rtData");
				ConstantUtil.sessionMap.put(ConstantUtil.TOKEN, token);
				if (rtData.has("id")) {
					String userId = rtData.getString("id");
					ConstantUtil.sessionMap.put(ConstantUtil.USERID, userId);
				}
			}
			String jsessionid = jsonObject.getString(ConstantUtil.JSESSIONID);
			if (!"null".equals(jsessionid)) {
				ConstantUtil.sessionMap
						.put(ConstantUtil.JSESSIONID, jsessionid);
			}
		} catch (JSONException e) {
			ConstantUtil.RTMSG = "登录发生异常，" + e.getMessage();
			e.printStackTrace();
			flag = false;
		}

		return flag;
	}
	public static String getToken() {
		String token = ConstantUtil.TOKEN;
		if (ConstantUtil.sessionMap.containsKey(token)) {
			return ConstantUtil.sessionMap.get(token);
		}
		return null;
	}

	public static String getJSessionId() {
		String jsessionid = ConstantUtil.JSESSIONID;
		if (ConstantUtil.sessionMap.containsKey(jsessionid)) {
			return ConstantUtil.sessionMap.get(jsessionid);
		}
		return null;
	}
}
