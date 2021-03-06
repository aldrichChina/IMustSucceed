package net.micode.notes.asynctask;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import net.micode.notes.ConstantProvider;
import net.micode.notes.db.DatabaseService;
import net.micode.notes.entity.NewsBody;
import net.micode.notes.entity.NewsChannel;
import net.micode.notes.entity.NewsDetailContent;
import net.micode.notes.entity.NewsPageBean;
import net.micode.notes.util.Utils;
import okhttp3.Call;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

public class ContentAsyncTask extends AsyncTask<String, Integer, Boolean> {

    List<NewsDetailContent> newsList = new ArrayList<NewsDetailContent>();
    Gson gson = new Gson();
    private Context context;
    private ContentCallback contentCallback;
    private DatabaseService databaseService;
    private int page;

    public ContentAsyncTask(Context context, int page, ContentCallback contentCallback) {
        this.context = context;
        this.page = page;
        this.contentCallback = contentCallback;
        databaseService = new DatabaseService(context);
    }

    @Override
    protected Boolean doInBackground(String... params) {
        OkHttpUtils.get().url(ConstantProvider.BaseURL + ConstantProvider.MailData)
                .addHeader("apikey", "334070f0f84d859e75972ebfdaae49fe")
                .addParams("page", ""+page)
                .addParams("title", ConstantProvider.newsTitle)
                .addParams("needContent", "1")
                .addParams("needHtml", "1")
                .build().execute(new StringCallback() {

                    @Override
                    public void onResponse(String response, int id) {
                        Log.d("jia", "response==" + response.toString());
                        String newsokHttpGetReturn = response.toString();
                        NewsChannel newsChannel = gson.fromJson(newsokHttpGetReturn, NewsChannel.class);
                        Utils.Logger(context, newsChannel.toString());
                        if (newsChannel.getShowapi_res_code() != 0) {
                            return;
                        }
                        NewsBody showapi_res_body = newsChannel.getShowapi_res_body();
                        NewsPageBean pagebean = showapi_res_body.getPagebean();
                        newsList = pagebean.getContentlist();
                        Utils.Logger(context, pagebean.toString());
                        for (NewsDetailContent detailContent : newsList) {
                            try {
                                databaseService.insertNewsDetailContent(detailContent);
                            } catch (ParseException e) {
                                e.printStackTrace();
                                Log.d("jia","ParseException="+ e);
                            }
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.d("jia", "onError==" + e.toString());
                        e.printStackTrace();
                    }
                });

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
