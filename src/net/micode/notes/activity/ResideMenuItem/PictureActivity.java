package net.micode.notes.activity.ResideMenuItem;

import java.io.IOException;
import java.util.List;

import net.micode.notes.R;
import net.micode.notes.activity.BaseActivity;
import net.micode.notes.activity.ImageActivity;
import net.micode.notes.adapter.MeinvAdapter;
import net.micode.notes.data.Constant;
import net.micode.notes.entities.Detailed;
import net.micode.notes.util.JSONUtil;
import net.micode.notes.util.Utils;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

public class PictureActivity extends BaseActivity {

    OkHttpClient client = new OkHttpClient();
    String httpArg = "num=50";
    Handler handler = new Handler();
    private ListView listView;
    Gson gson = new Gson();
    private List<Detailed> detailedList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.picture_fragment);
        super.onCreate(savedInstanceState);

    }

    private void setDate() {
        listView.setAdapter(new MeinvAdapter(this, detailedList));
        setListener();
    }

    @Override
    protected void setListener() {
        listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("url", detailedList.get(position).getPicUrl());
                intent.setClass(PictureActivity.this, ImageActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initViews() {

        ImageView righttitle = (ImageView) findViewById(R.id.righttitle);
        righttitle.setVisibility(View.INVISIBLE);
        ImageView topback = (ImageView) findViewById(R.id.topback);
        topback.setBackgroundResource(R.drawable.ic_topbar_back_normal);
        topback.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Utils.finish(PictureActivity.this);
            }
        });

    }

    @Override
    protected void initEvents() {
        listView = (ListView) findViewById(R.id.listView);
        OkHttpUtils.get().url(Constant.HTTPURLMEINV).addHeader("apikey", "334070f0f84d859e75972ebfdaae49fe")
                .addParams("num", "50").build().execute(new StringCallback() {

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            Log.d("jia", "HTTPURLMEINV=response==" + response);
                            detailedList = JSONUtil.analysisResponse(response.toString());
                            setDate();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.d("jia", "onError==" + e.toString());
                        e.printStackTrace();
                    }
                });
    }
}
