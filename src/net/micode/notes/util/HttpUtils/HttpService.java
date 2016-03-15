package net.micode.notes.util.HttpUtils;

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
import net.micode.notes.util.DateTimeUtils;
import net.micode.notes.util.JSONUtil;
import net.micode.notes.util.MD5Util;
import net.micode.notes.util.Utils;
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
	
}
