package net.micode.notes.ui.activity;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import okhttp3.OkHttpClient;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

public class MainApplication extends Application {
	public static ImageLoader imageLoader = ImageLoader.getInstance();
	public static OkHttpClient client = new OkHttpClient();
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
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				applicationContext)
				.memoryCacheExtraOptions(480, 800)
				// default = device screen dimensions 内存缓存文件的最大长宽
				.threadPoolSize(3)
				// default 线程池内加载的数量
				.threadPriority(Thread.NORM_PRIORITY - 2)
				// default 设置当前线程的优先级
				.tasksProcessingOrder(QueueProcessingType.FIFO)
				// default
				.denyCacheImageMultipleSizesInMemory()
				.memoryCache(new LruMemoryCache(2 * 1024 * 1024))
				// 可以通过自己的内存缓存实现
				.memoryCacheSize(2 * 1024 * 1024)
				// 内存缓存的最大值
				.memoryCacheSizePercentage(13)
				// default
				.discCache(new UnlimitedDiscCache(cacheDir))
				// default 可以自定义缓存路径
				.discCacheSize(50 * 1024 * 1024)
				// 50 Mb sd卡(本地)缓存的最大值
				.discCacheFileCount(100)
				// 可以缓存的文件数量
				// default为使用HASHCODE对UIL进行加密命名， 还可以用MD5(new
				// Md5FileNameGenerator())加密
				.discCacheFileNameGenerator(new HashCodeFileNameGenerator())
				.imageDownloader(new BaseImageDownloader(applicationContext)) // default
				.defaultDisplayImageOptions(DisplayImageOptions.createSimple()) // default
				.writeDebugLogs() // 打印debug log
				.build(); // 开始构建
		imageLoader.init(config);

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
			Log.d(TAG, "e" + e);
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
