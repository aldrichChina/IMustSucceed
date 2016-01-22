package net.micode.notes.ui.activity;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

public class MainApplication extends Application {

	private static final String TAG = "MainApplication";

	private List<Activity> mList = new LinkedList<Activity>();

	private static MainApplication instance;

	public synchronized static MainApplication getInstance() {
		return instance;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
		initImageLoader(getApplicationContext());
	}

	private void initImageLoader(Context applicationContext) {
		File cacheDir = applicationContext.getExternalCacheDir();
		ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(
				getApplicationContext())
				.threadPoolSize(5)
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.memoryCache(new UsingFreqLimitedMemoryCache(4 * 1024 * 1024))
				.memoryCacheSize(4 * 1024 * 1024)
				.discCacheSize(50 * 1024 * 1024)
				.discCacheFileNameGenerator(new HashCodeFileNameGenerator())
				// 将保存的时候的URI名称用MD5 加密
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.discCacheFileCount(100)
				// 缓存的文件数量
				.discCache(new UnlimitedDiscCache(cacheDir))
				// 自定义缓存路径
				.defaultDisplayImageOptions(DisplayImageOptions.createSimple())
				.writeDebugLogs()
				.imageDownloader(
						new BaseImageDownloader(applicationContext, 5 * 1000,
								30 * 1000)).build();// 开始构建
		ImageLoader.getInstance().init(configuration);

	}

	public void addActivity(Activity activity) {
		mList.add(activity);
	}

	public void removeActivity(Activity activity) {
		if (activity != null) {
			mList.remove(activity);
		}
	}

	public void exit() {
		try {
			for (Activity activity : mList) {
				if (activity != null)
					activity.finish();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.d(TAG, "e"+e);
		} finally {
			System.exit(0);
		}
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
		System.gc();
	}

}
