package net.micode.notes.tool.HttpUtils;

import java.io.IOException;

import net.micode.notes.tool.Utils;
import net.micode.notes.ui.activity.MainApplication;
import okhttp3.Request;
import okhttp3.Response;
import android.util.Log;

public class HttpService {
	private static String responseBody;
	public static String OKHttpGet(String httpUrl, String httpArg) {
		try {
			httpUrl = httpUrl + "?" + httpArg;
			Request request = new Request.Builder().url(httpUrl).addHeader("apikey", "334070f0f84d859e75972ebfdaae49fe").build();
			Response response = MainApplication.client.newCall(request).execute();
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
