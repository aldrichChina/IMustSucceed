package net.micode.notes.activity;

import net.micode.notes.BaseActivity;
import net.micode.notes.R;
import net.micode.notes.dialog.dialogfragment.ConfimDialog;
import net.micode.notes.util.GlideCircleTransform;
import net.micode.notes.util.Utils;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class ImageActivity extends BaseActivity {

    private ImageView bigImage;

    private String imgUrl;

    public void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.show_one_image);
        super.onCreate(savedInstanceState);
        Utils.Log("onCreate(Bundle savedInstanceState)");
        imgUrl = getIntent().getStringExtra("url");
        Glide.with(this).load(imgUrl).diskCacheStrategy(DiskCacheStrategy.ALL).transform(new GlideCircleTransform(context)).into(bigImage);
        bigImage.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                ConfimDialog confimDialog = new ConfimDialog(ImageActivity.this,imgUrl);
                confimDialog.show(getFragmentManager(), "confimDialog");
            }
        });
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initViews() {
        bigImage = (ImageView) findViewById(R.id.big_img);
        Utils.Log("initViews()");
    }

    @Override
    protected void initEvents() {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Utils.finish(this);
        }
        return super.onKeyDown(keyCode, event);
    }

}
