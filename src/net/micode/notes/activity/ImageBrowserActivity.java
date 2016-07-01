package net.micode.notes.activity;

import java.util.ArrayList;
import java.util.List;

import net.micode.notes.R;
import net.micode.notes.adapter.ImageBrowserAdapter;
import net.micode.notes.entities.NearByPeopleProfile;
import net.micode.notes.view.PhotoTextView;
import net.micode.notes.view.ScrollViewPager;
import android.os.Bundle;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class ImageBrowserActivity extends BaseActivity implements
		OnPageChangeListener, OnClickListener {

	private ScrollViewPager mSvpPager;
	private PhotoTextView mPtvPage;
	private ImageView mIvDownload;
	private ImageBrowserAdapter mAdapter;
	private String mType;
	private int mPosition;
	private int mTotal;
	private NearByPeopleProfile mProfile;

	public static final String IMAGE_TYPE = "image_type";
	public static final String TYPE_ALBUM = "image_album";
	public static final String TYPE_PHOTO = "image_photo";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_imagebrowser);
		super.onCreate(savedInstanceState);
		initViews();
		initEvents();
		init();
	}

	@Override
	protected void initViews() {
		mSvpPager = (ScrollViewPager) findViewById(R.id.imagebrowser_svp_pager);
		mPtvPage = (PhotoTextView) findViewById(R.id.imagebrowser_ptv_page);
		mIvDownload = (ImageView) findViewById(R.id.imagebrowser_iv_download);
	}

	@Override
	protected void initEvents() {
		mSvpPager.setOnPageChangeListener(this);
		mIvDownload.setOnClickListener(this);
	}

	private void init() {
		mType = getIntent().getStringExtra(IMAGE_TYPE);
		if (TYPE_ALBUM.equals(mType)) {
			mIvDownload.setVisibility(View.GONE);
			mPosition = getIntent().getIntExtra("position", 0);
			mProfile = getIntent().getParcelableExtra("entity_profile");
			mTotal = mProfile.getPhotos().size();
			if (mPosition > mTotal) {
				mPosition = mTotal - 1;
			}
			if (mTotal > 1) {
				mPosition += 1000 * mTotal;
				mPtvPage.setText((mPosition % mTotal) + 1 + "/" + mTotal);
				mAdapter = new ImageBrowserAdapter(mApplication,
						mProfile.getPhotos(), mType);
				mSvpPager.setAdapter(mAdapter);
				mSvpPager.setCurrentItem(mPosition, false);
			}
		} else if (TYPE_PHOTO.equals(mType)) {
			mIvDownload.setVisibility(View.VISIBLE);
			String path = getIntent().getStringExtra("path");
			List<String> photos = new ArrayList<String>();
			photos.add(path);
			mPtvPage.setText("1/1");
			mAdapter = new ImageBrowserAdapter(mApplication, photos, mType);
			mSvpPager.setAdapter(mAdapter);
		}
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	@Override
	public void onPageSelected(int arg0) {
		mPosition = arg0;
		mPtvPage.setText((mPosition % mTotal) + 1 + "/" + mTotal);
	}

	@Override
	public void onClick(View arg0) {
		showCustomToast("图片已保存到本地");
	}

	@Override
	public void onBackPressed() {
		defaultFinish();
		overridePendingTransition(0, R.anim.zoom_exit);
	}


	@Override
	protected void setListener() {
		// TODO Auto-generated method stub
		
	}

}
