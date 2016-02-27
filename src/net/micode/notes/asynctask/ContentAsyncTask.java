package net.micode.notes.asynctask;

import java.util.ArrayList;
import java.util.List;

import net.micode.notes.data.Constant;
import net.micode.notes.data.DatabaseService;
import net.micode.notes.entities.NewsBody;
import net.micode.notes.entities.NewsChannel;
import net.micode.notes.entities.NewsDetailContent;
import net.micode.notes.entities.NewsPageBean;
import net.micode.notes.tool.Utils;
import net.micode.notes.tool.HttpUtils.HttpService;
import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;

public class ContentAsyncTask extends AsyncTask<String, Integer, Boolean> {
	List<NewsDetailContent> newsList = new ArrayList<NewsDetailContent>();
	Gson gson = new Gson();
	private Context context;
	private ContentCallback contentCallback;
	private DatabaseService databaseService;
	public ContentAsyncTask(Context context, ContentCallback contentCallback) {
		this.context = context;
		this.contentCallback = contentCallback;
		databaseService=new DatabaseService(context);
	}

	@Override
	protected Boolean doInBackground(String... params) {
		String newsokHttpGetReturn = HttpService.OKHttpGet(Constant.HTTPURL+ Constant.MailData, null);
		NewsChannel newsChannel = gson.fromJson(newsokHttpGetReturn,NewsChannel.class);
		Utils.Logger(context, newsChannel.toString());
		if (newsChannel.getShowapi_res_code() != 0) {
			return false;
		}
		NewsBody showapi_res_body = newsChannel.getShowapi_res_body();
		NewsPageBean pagebean = showapi_res_body.getPagebean();
		newsList = pagebean.getContentlist();
		Utils.Logger(context, pagebean.toString());
		for(NewsDetailContent detailContent:newsList){
			databaseService.insertNewsDetailContent(detailContent);
		}
		return true;
	}

	@Override
	protected void onPostExecute(Boolean result) {
		contentCallback.send(result);
		super.onPostExecute(result);
	}

	public interface ContentCallback {
		void send(Boolean result);
	}
}
