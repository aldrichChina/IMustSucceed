package net.micode.notes.util.HttpUtils;

import java.io.IOException;

import net.micode.notes.BaseApplication;
import net.micode.notes.util.Utils;
import okhttp3.Request;
import okhttp3.Response;

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
