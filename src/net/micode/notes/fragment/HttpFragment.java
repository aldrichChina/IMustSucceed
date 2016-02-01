package net.micode.notes.fragment;

import java.io.File;
import java.util.logging.Logger;

import net.micode.notes.DownloadListActivity;
import net.micode.notes.R;
import net.micode.notes.download.DownloadManager;
import net.micode.notes.download.DownloadService;
import net.micode.notes.tool.Utils;
import net.micode.notes.ui.NotesListActivity;

import org.apache.http.impl.cookie.BasicClientCookie;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.ResponseStream;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.util.PreferencesCookieStore;
import com.lidroid.xutils.view.ResType;
import com.lidroid.xutils.view.annotation.ResInject;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class HttpFragment extends Fragment {

	// private HttpHandler handler;

	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.http_fragment, container, false);
		
		return view;
	}

	
	
	@ViewInject(R.id.button1)
	private Button button1;
	@ViewInject(R.id.result_txt)
	private TextView resultText;
	@ResInject(id = R.string.download_label, type = ResType.String)
	private String label;


	// ///////////////////////////////////// other
	// ////////////////////////////////////////////////////////////////

	// @OnClick(R.id.download_btn)
	public void testUpload(View view) {

		// 设置请求参数的编码
		// RequestParams params = new RequestParams("GBK");
		RequestParams params = new RequestParams(); // 默认编码UTF-8

		// params.addQueryStringParameter("qmsg", "你好");
		// params.addBodyParameter("msg", "测试");

		// 添加文件
		params.addBodyParameter("file", new File("/sdcard/test.zip"));
		// params.addBodyParameter("testfile", new File("/sdcard/test2.zip"));
		// // 继续添加文件

		// 用于非multipart表单的单文件上传
		// params.setBodyEntity(new FileUploadEntity(new
		// File("/sdcard/test.zip"), "binary/octet-stream"));

		// 用于非multipart表单的流上传
		// params.setBodyEntity(new InputStreamUploadEntity(stream ,length));

		HttpUtils http = new HttpUtils();

		// 设置返回文本的编码， 默认编码UTF-8
		// http.configResponseTextCharset("GBK");

		// 自动管理 cookie
//		http.configCookieStore(preferencesCookieStore);

		http.send(HttpRequest.HttpMethod.POST,
				"http://192.168.1.5:8080/UploadServlet", params,
				new RequestCallBack<String>() {

					@Override
					public void onStart() {
						resultText.setText("conn...");
					}

					@Override
					public void onLoading(long total, long current,
							boolean isUploading) {
						if (isUploading) {
							resultText.setText("upload: " + current + "/"
									+ total);
						} else {
							resultText.setText("reply: " + current + "/"
									+ total);
						}
					}

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						resultText.setText("reply: " + responseInfo.result);
					}

					@Override
					public void onFailure(HttpException error, String msg) {
						resultText.setText(msg);
					}
				});
	}

	// @OnClick(R.id.download_btn)
	public void testGet(View view) {

		// RequestParams params = new RequestParams();
		// params.addHeader("name", "value");
		// params.addQueryStringParameter("name", "value");

		HttpUtils http = new HttpUtils();
		http.configCurrentHttpCacheExpiry(1000 * 10);
		http.send(HttpRequest.HttpMethod.GET, "http://www.baidu.com",
		// params,
				new RequestCallBack<String>() {

					@Override
					public void onStart() {
						resultText.setText("conn...");
					}

					@Override
					public void onLoading(long total, long current,
							boolean isUploading) {
						resultText.setText(current + "/" + total);
					}

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						resultText.setText("response:" + responseInfo.result);
					}

					@Override
					public void onFailure(HttpException error, String msg) {
						resultText.setText(msg);
					}
				});
	}

	// @OnClick(R.id.download_btn)
	public void testPost(View view) {
		RequestParams params = new RequestParams();
		params.addQueryStringParameter("method", "mkdir");
		params.addQueryStringParameter("access_token",
				"3.1042851f652496c9362b1cd77d4f849b.2592000.1377530363.3590808424-248414");
		params.addBodyParameter("path", "/apps/测试应用/test文件夹");

		HttpUtils http = new HttpUtils();
		http.send(HttpRequest.HttpMethod.POST,
				"https://pcs.baidu.com/rest/2.0/pcs/file", params,
				new RequestCallBack<String>() {

					@Override
					public void onStart() {
						resultText.setText("conn...");
					}

					@Override
					public void onLoading(long total, long current,
							boolean isUploading) {
						resultText.setText(current + "/" + total);
					}

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						resultText.setText("upload response:"
								+ responseInfo.result);
					}

					@Override
					public void onFailure(HttpException error, String msg) {
						resultText.setText(msg);
					}
				});
	}

	// 同步请求 必须在异步块儿中执行
	private String testGetSync() {
		RequestParams params = new RequestParams();
		params.addQueryStringParameter("wd", "lidroid");

		HttpUtils http = new HttpUtils();
		http.configCurrentHttpCacheExpiry(1000 * 10);
		try {
			ResponseStream responseStream = http.sendSync(
					HttpRequest.HttpMethod.GET, "http://www.baidu.com/s",
					params);
			// int statusCode = responseStream.getStatusCode();
			// Header[] headers =
			// responseStream.getBaseResponse().getAllHeaders();
			return responseStream.readString();
		} catch (Exception e) {
			LogUtils.e(e.getMessage(), e);
		}
		return null;
	}
}
