package net.micode.notes.ui.activity;

import net.micode.notes.MyActivity;
import android.os.Handler;
import android.view.View;

public class SplashActivity extends BaseActivity{
	private final int SPLASH_DISPLAY_LENGHT = 5000; // 延迟两秒
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void initView() {
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				start_Activity(SplashActivity.this, MyActivity.class);
				finish(SplashActivity.this);
			}
		}, SPLASH_DISPLAY_LENGHT);
	}

	@Override
	protected void initData() {
		
	}

	@Override
	protected void setListener() {
		// TODO Auto-generated method stub
		
	}

}
