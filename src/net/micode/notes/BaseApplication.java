package net.micode.notes;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

public class BaseApplication extends Application
{
	private static Context mContext;
	private static Thread mMainThread;
	private static long mMainThreadId;
	private static Looper mMainLooper;
	private static Handler mMainHander;
	
	//应用程序的入口
	@Override
	public void onCreate()
	{
		super.onCreate();
		//应用程序的上下文
		mContext=getApplicationContext();
		//主线程
		mMainThread=Thread.currentThread();
		//主线程Id
		//mMainThreadId=mMainThread.getId();
		mMainThreadId=android.os.Process.myTid();
		mMainLooper=getMainLooper();
		//创建主线程的Handler
		mMainHander=new Handler();

		initImageLoader();
		
	}

	public static Context getContext()
	{
		return mContext;
	}

	public static Thread getMainThread()
	{
		return mMainThread;
	}

	public static long getMainThreadId()
	{
		return mMainThreadId;
	}

	public static Looper getMainThreadLooper()
	{
		return mMainLooper;
	}

	public static Handler getMainHander()
	{
		return mMainHander;
	}

	private final static void initImageLoader() {
		ImageLoaderConfiguration config = new  ImageLoaderConfiguration.Builder(mContext)
				.defaultDisplayImageOptions(getDefaultDisplayOption())//显示图片的参数，传入自己配置过得DisplayImageOption对象
				.memoryCache(new LruMemoryCache(50 * 1024 * 1024)) //缓存策略
						.memoryCacheExtraOptions(320, 480) //即保存的每个缓存文件的最大长宽
						.threadPoolSize(8) //线程池内线程的数量，默认是3
						.threadPriority(Thread.NORM_PRIORITY - 2) //当同一个Uri获取不同大小的图片，缓存到内存时，只缓存一个。默认会缓存多个不同的大小的相同图片
						.denyCacheImageMultipleSizesInMemory() //拒绝同一个url缓存多个图片
						.diskCacheSize(50 * 1024 * 1024) //设置磁盘缓存大小 50M
						.diskCacheFileNameGenerator(new Md5FileNameGenerator()) //将保存的时候的URI名称用MD5 加密
						.tasksProcessingOrder(QueueProcessingType.LIFO)//设置图片下载和显示的工作队列排序
						.build();
		ImageLoader.getInstance().init(config);
	}

	private final static DisplayImageOptions getDefaultDisplayOption() {
		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.showImageForEmptyUri(R.drawable.isloading)  // 设置图片Uri为空或是错误的时候显示的图片
				.showImageOnFail(R.drawable.isloading)     //  设置图片加载或解码过程中发生错误显示的图片
				.cacheInMemory(true) // 设置下载的图片是否缓存在内存中
				.cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
				.showImageOnLoading(R.drawable.isloading)
				.build();
		return options;
	}
}
