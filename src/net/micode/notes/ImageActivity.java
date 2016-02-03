package net.micode.notes;

import net.micode.notes.tool.dialogfragment.ConfimDialog;
import net.micode.notes.ui.activity.BaseActivity;
import net.micode.notes.ui.activity.MainApplication;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class ImageActivity extends BaseActivity {

	@ViewInject(R.id.big_img)
	private ImageView bigImage;

	private BitmapUtils bitmapUtils;

	private BitmapDisplayConfig bigPicDisplayConfig;

	private String imgUrl;

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.image);
		ViewUtils.inject(this);

		imgUrl = getIntent().getStringExtra("url");

//		bitmapUtils = BitmapFragment.bitmapUtils;
//		if (bitmapUtils == null) {
//			bitmapUtils = BitmapHelp.getBitmapUtils(this
//					.getApplicationContext());
//		}
//
//		bigPicDisplayConfig = new BitmapDisplayConfig();
//		// bigPicDisplayConfig.setShowOriginal(true); // 显示原始图片,不压缩, 尽量不要使用,
//		// 图片太大时容易OOM。
//		bigPicDisplayConfig.setBitmapConfig(Bitmap.Config.RGB_565);
//		bigPicDisplayConfig.setBitmapMaxSize(BitmapCommonUtils
//				.getScreenSize(this));
//
//		BitmapLoadCallBack<ImageView> callback = new DefaultBitmapLoadCallBack<ImageView>() {
//			@Override
//			public void onLoadStarted(ImageView container, String uri,
//					BitmapDisplayConfig config) {
//				super.onLoadStarted(container, uri, config);
//				Toast.makeText(getApplicationContext(), uri, 300).show();
//			}
//
//			@Override
//			public void onLoadCompleted(ImageView container, String uri,
//					Bitmap bitmap, BitmapDisplayConfig config,
//					BitmapLoadFrom from) {
//				super.onLoadCompleted(container, uri, bitmap, config, from);
//				Toast.makeText(getApplicationContext(),
//						bitmap.getWidth() + "*" + bitmap.getHeight(), 300)
//						.show();
//			}
//		};
//
//		bitmapUtils.display(bigImage, imgUrl, bigPicDisplayConfig, callback);
		// 读取assets中的图片
		// bitmapUtils.display(bigImage, "assets/img/wallpaper.jpg",
		// bigPicDisplayConfig, callback);
		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.ic_stub) // 设置图片下载期间显示的图片
				.showImageForEmptyUri(R.drawable.ic_empty) // 设置图片Uri为空或是错误的时候显示的图片
				.showImageOnFail(R.drawable.ic_error) // 设置图片加载或解码过程中发生错误显示的图片
				.bitmapConfig(Bitmap.Config.ARGB_8888)
				.cacheInMemory(true) // 设置下载的图片是否缓存在内存中
				.cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
				.displayer(new RoundedBitmapDisplayer(5)) // 设置成圆角图片
				.build(); // 构建完成
		MainApplication.imageLoader.displayImage(imgUrl, bigImage, options);
		bigImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ConfimDialog confimDialog = new ConfimDialog(imgUrl);
				confimDialog.show(getFragmentManager(), "confimDialog");
			}
		});
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void setListener() {
		// TODO Auto-generated method stub
		
	}

}