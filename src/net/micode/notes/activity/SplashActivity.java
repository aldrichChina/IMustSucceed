package net.micode.notes.activity;

import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;

public class SplashActivity extends BaseActivity implements OnClickListener {

    private final int SPLASH_DISPLAY_LENGHT = 2000; // 延迟两秒

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

    }

    @Override
    protected void initViews() {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                start_Activity(SplashActivity.this, LoginActivity.class);
                finish(SplashActivity.this);
            }
        }, SPLASH_DISPLAY_LENGHT);
    }

    @Override
    protected void setListener() {
        // TODO Auto-generated method stub

    }

    @Override
    protected void initEvents() {
        // TODO Auto-generated method stub

    }

}
