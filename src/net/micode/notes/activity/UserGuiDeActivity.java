package net.micode.notes.activity;

import net.micode.notes.R;
import net.micode.notes.adapter.UserGuiDeAdapter;
import net.micode.notes.view.ScrollViewPager;
import android.os.Bundle;
import android.view.View;

public class UserGuiDeActivity extends BaseActivity {
	private ScrollViewPager mSvpPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_userguide);
		super.onCreate(savedInstanceState);
		initViews();
		initEvents();
	}

	@Override
	protected void initViews() {
		mSvpPager = (ScrollViewPager) findViewById(R.id.userguide_svp_pager);
		mSvpPager.setEnableTouchScroll(true);
		mSvpPager.setAdapter(new UserGuiDeAdapter(this));
	}

	@Override
	protected void initEvents() {

	}


	@Override
	protected void setListener() {
		// TODO Auto-generated method stub
		
	}
}
