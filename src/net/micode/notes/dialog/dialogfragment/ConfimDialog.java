package net.micode.notes.dialog.dialogfragment;

import java.io.File;

import okhttp3.Call;
import okhttp3.Request;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;

public class ConfimDialog extends DialogFragment {

    private String url;
    private Context context;
    public ConfimDialog() {
    }

    public ConfimDialog(Context context,String url) {
        this.url = url;
        this.context=context;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("开始下载图片? ").setPositiveButton("好", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {
                download();
            }
        }).setNegativeButton("否", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });
        // Create the AlertDialog object and return it
        return builder.create();
    }

    public void download() {
        String target = "/sdcard/Picture/" + System.currentTimeMillis() + "lemon.jpg";

        OkHttpUtils//
                .get()//
                .url(url)//
                .build()//
                .execute(
                        new FileCallBack(Environment.getExternalStorageDirectory().getAbsolutePath(), System
                                .currentTimeMillis() + "lemon.jpg")//
                        {

                            @Override
                            public void onBefore(Request request, int id) {
                            }

                            @Override
                            public void inProgress(float progress, long total, int id) {
                                // mProgressBar.setProgress((int) (100 * progress));
                                Log.e("jia", "inProgress :" + (int) (100 * progress));
                            }

                            @Override
                            public void onError(Call call, Exception e, int id) {
                                Log.e("jia", "onError :" + e.getMessage());
                            }

                            @Override
                            public void onResponse(File file, int id) {
                                Log.e("jia", "onResponse :" + file.getAbsolutePath());
                                Toast.makeText(context, "宝贝下载成功啦...快去" + file.getAbsolutePath() + "看看吧!",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });

    }
}
