package net.micode.notes.activity.ResideMenuItem;

import java.io.File;

import net.micode.notes.BaseActivity;
import net.micode.notes.R;
import net.micode.notes.activity.DownloadListActivity;
import net.micode.notes.util.Utils;
import okhttp3.Call;
import okhttp3.Request;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;

public class ProfileActivity extends BaseActivity {

    private EditText downloadAddrEdit;
    private Button downloadBtn;
    private Button downloadPageBtn;
    private ProgressBar mProgressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.profile);
        super.onCreate(savedInstanceState);
        downloadAddrEdit = (EditText) findViewById(R.id.download_addr_edit);
        downloadBtn = (Button) findViewById(R.id.download_btn);
        downloadBtn.setOnClickListener(downloadFile);
        downloadPageBtn = (Button) findViewById(R.id.download_page_btn);
        downloadPageBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Utils.start_Activity(ProfileActivity.this, DownloadListActivity.class);
            }
        });

    }
    private  View.OnClickListener downloadFile=new View.OnClickListener() {
        
        @Override
        public void onClick(View v) {

            String url = downloadAddrEdit.getText().toString();
            String target =  System.currentTimeMillis() + "lemon.png";
            OkHttpUtils//
                    .get()//
                    .url(url)//
                    .build()//
                    .execute(
                            new FileCallBack(Environment.getExternalStorageDirectory().getAbsolutePath(), target)//
                            {

                                @Override
                                public void onBefore(Request request, int id) {
                                }

                                @Override
                                public void inProgress(float progress, long total, int id) {
                                    mProgressBar.setProgress((int) (100 * progress));
                                    Log.e("jia", "inProgress :" + (int) (100 * progress));
                                }

                                @Override
                                public void onError(Call call, Exception e, int id) {
                                    Log.e("jia", "onError :" + e.getMessage());
                                }

                                @Override
                                public void onResponse(File file, int id) {
                                    Log.e("jia", "onResponse :" + file.getAbsolutePath());
                                }
                            });
        
                    
        }
    };
    @Override
    protected void initViews() {
        ImageView righttitle = (ImageView) findViewById(R.id.righttitle);
        righttitle.setVisibility(View.INVISIBLE);
        ImageView topback = (ImageView) findViewById(R.id.topback);
        mProgressBar = (ProgressBar) findViewById(R.id.id_progress);
        topback.setBackgroundResource(R.drawable.ic_topbar_back_normal);
        topback.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Utils.finish(ProfileActivity.this);
            }
        });
    }

    @Override
    protected void initEvents() {
        // TODO Auto-generated method stub

    }

    @Override
    protected void setListener() {
        // TODO Auto-generated method stub

    }

}
