package net.micode.notes.util;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import net.micode.notes.entity.Detailed;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * @description: json转换工具类
 * @author: wht
 * @date: 2015-2-12 上午10:40:37
 * @version: V1.0.0
 */
public class JSONUtil {
	private static Gson gson = null;
	static {
		if (gson == null) {
			gson = new Gson();
		}
	}

	/**
	 * @description: 将json字符串转换成对象
	 * @param json
	 * @return
	 * @author: wht
	 * @date: 2015-2-12 上午10:40:19
	 */
	public static JSONObject testJson(String json) {
		try {
			JSONTokener jsonParser = new JSONTokener(json);
			JSONObject object = (JSONObject) jsonParser.nextValue();
			return object;
		} catch (JSONException ex) {
			Log.i("json", "json转换成对象异常");
		}
		return null;
	}

	/**
	 * @description: json集合对象转换list集合
	 * @param str
	 * @param type
	 * @return
	 * @author: wht
	 * @date: 2015-3-3 上午10:15:36
	 */
	public static <T> List<T> getListFromJSON(String str, Class<T> type) {
		Type listType = new TypeToken<ArrayList<T>>() {
		}.getType();
		List<T> list = new Gson().fromJson(str, listType);
		return list;
	}

	/**
	 * 将json转换成bean对象
	 * 
	 * @param jsonStr
	 * @return
	 */
	public static Object jsonToBean(String jsonStr, Class<?> cl) {
		Object obj = null;
		if (gson != null) {
			obj = gson.fromJson(jsonStr, cl);
		}
		return obj;
	}

	/**
	 * @description: 将集合转成json
	 * @param list
	 * @return
	 * @author: wht
	 * @date: 2015-3-6 下午1:52:02
	 */
	public static String listToJson(List<?> list) {
		String json = gson.toJson(list);
		return json;
	}

	/**
	 * @description: 将bean转成json
	 * @param obj
	 * @return
	 * @author: wht
	 * @date: 2015-3-6 下午1:53:02
	 */
	public static String beanToJson(Object obj) {
		String json = gson.toJson(obj);
		return json;
	}

	public static String getValue(JSONObject result, String strParam) {
		String strValue = "";
		try {
			strValue = result.getString(strParam);

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return strValue;
	}
	/**
	 * @param httpUrl
	 * @param httpArg
	 * @return
	 * @throws IOException
	 */
	public static List<Detailed> analysisResponse(String responseBody)throws IOException {
		List<Detailed> detailedList = new ArrayList<Detailed>();
		try {
			JSONObject jsonObject = new JSONObject(responseBody);
			JSONArray jsonArray = jsonObject.getJSONArray("newslist");
			for(int i=0;i<jsonArray.length();i++){
				JSONObject detailJSONObject = jsonArray.optJSONObject(i);
				Detailed detailed = new Detailed();
				detailed.setTitle(detailJSONObject.optString("title"));
				detailed.setDescription(detailJSONObject.optString("description"));
				detailed.setPicUrl(detailJSONObject.optString("picUrl"));
				detailed.setUrl(detailJSONObject.optString("url"));
				detailedList.add(detailed);
			}
			for (Detailed detailList : detailedList) {
				Utils.Log("detailList--->=" + detailList);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return detailedList;
	}
}
