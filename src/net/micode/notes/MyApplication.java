package net.micode.notes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import net.micode.notes.entity.NearByGroup;
import net.micode.notes.entity.NearByPeople;
import okhttp3.OkHttpClient;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.https.HttpsUtils;
import com.zhy.http.okhttp.log.LoggerInterceptor;

public class MyApplication extends Application {

    private Bitmap mDefaultAvatar;
    private static final String AVATAR_DIR = "avatar/";
    private static final String PHOTO_ORIGINAL_DIR = "photo/original/";
    private static final String PHOTO_THUMBNAIL_DIR = "photo/thumbnail/";
    private static final String STATUS_PHOTO_DIR = "statusphoto/";
    public Map<String, SoftReference<Bitmap>> mAvatarCache = new HashMap<String, SoftReference<Bitmap>>();
    public Map<String, SoftReference<Bitmap>> mPhotoOriginalCache = new HashMap<String, SoftReference<Bitmap>>();
    public Map<String, SoftReference<Bitmap>> mPhotoThumbnailCache = new HashMap<String, SoftReference<Bitmap>>();
    public Map<String, SoftReference<Bitmap>> mStatusPhotoCache = new HashMap<String, SoftReference<Bitmap>>();
    public List<NearByPeople> mNearByPeoples = new ArrayList<NearByPeople>();
    public List<NearByGroup> mNearByGroups = new ArrayList<NearByGroup>();
    public static List<String> mEmoticons = new ArrayList<String>();
    public static Map<String, Integer> mEmoticonsId = new HashMap<String, Integer>();
    public static List<String> mEmoticons_Zem = new ArrayList<String>();
    public static List<String> mEmoticons_Zemoji = new ArrayList<String>();
    public LocationClient mLocationClient;
    public double mLongitude;
    public double mLatitude;
    private static Context mContext;
    private static Thread mMainThread;
    private static long mMainThreadId;
    private static Looper mMainLooper;
    private static Handler mMainHander;
    public static ImageLoader imageLoader = ImageLoader.getInstance();
    public static OkHttpClient client = new OkHttpClient();
    private static final String TAG = "MainApplication";
    private List<Activity> mList = new LinkedList<Activity>();

    private static class BaseApplicationHolder {

        private static final MyApplication instance = new MyApplication();
    }

    public static MyApplication getInstance() {
        return BaseApplicationHolder.instance;
    }

    private String CER_12306 = "-----BEGIN CERTIFICATE-----\n"
            + "MIICmjCCAgOgAwIBAgIIbyZr5/jKH6QwDQYJKoZIhvcNAQEFBQAwRzELMAkGA1UEBhMCQ04xKTAn\n"
            + "BgNVBAoTIFNpbm9yYWlsIENlcnRpZmljYXRpb24gQXV0aG9yaXR5MQ0wCwYDVQQDEwRTUkNBMB4X\n"
            + "DTA5MDUyNTA2NTYwMFoXDTI5MDUyMDA2NTYwMFowRzELMAkGA1UEBhMCQ04xKTAnBgNVBAoTIFNp\n"
            + "bm9yYWlsIENlcnRpZmljYXRpb24gQXV0aG9yaXR5MQ0wCwYDVQQDEwRTUkNBMIGfMA0GCSqGSIb3\n"
            + "DQEBAQUAA4GNADCBiQKBgQDMpbNeb34p0GvLkZ6t72/OOba4mX2K/eZRWFfnuk8e5jKDH+9BgCb2\n"
            + "9bSotqPqTbxXWPxIOz8EjyUO3bfR5pQ8ovNTOlks2rS5BdMhoi4sUjCKi5ELiqtyww/XgY5iFqv6\n"
            + "D4Pw9QvOUcdRVSbPWo1DwMmH75It6pk/rARIFHEjWwIDAQABo4GOMIGLMB8GA1UdIwQYMBaAFHle\n"
            + "tne34lKDQ+3HUYhMY4UsAENYMAwGA1UdEwQFMAMBAf8wLgYDVR0fBCcwJTAjoCGgH4YdaHR0cDov\n"
            + "LzE5Mi4xNjguOS4xNDkvY3JsMS5jcmwwCwYDVR0PBAQDAgH+MB0GA1UdDgQWBBR5XrZ3t+JSg0Pt\n"
            + "x1GITGOFLABDWDANBgkqhkiG9w0BAQUFAAOBgQDGrAm2U/of1LbOnG2bnnQtgcVaBXiVJF8LKPaV\n"
            + "23XQ96HU8xfgSZMJS6U00WHAI7zp0q208RSUft9wDq9ee///VOhzR6Tebg9QfyPSohkBrhXQenvQ\n"
            + "og555S+C3eJAAVeNCTeMS3N/M5hzBRJAoffn3qoYdAO1Q8bTguOi+2849A==\n" + "-----END CERTIFICATE-----";

    // 应用程序的入口
    @Override
    public void onCreate() {
        super.onCreate();

        ClearableCookieJar cookieJar1 = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(
                getApplicationContext()));

        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);

        // CookieJarImpl cookieJar1 = new CookieJarImpl(new MemoryCookieStore());
        OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS).addInterceptor(new LoggerInterceptor("TAG"))
                .cookieJar(cookieJar1).hostnameVerifier(new HostnameVerifier() {

                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                }).sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager).build();
        OkHttpUtils.initClient(okHttpClient);

        // 应用程序的上下文
        mContext = getApplicationContext();
        // 主线程
        mMainThread = Thread.currentThread();
        // 主线程Id
        // mMainThreadId=mMainThread.getId();
        mMainThreadId = android.os.Process.myTid();
        mMainLooper = getMainLooper();
        // 创建主线程的Handler
        mMainHander = new Handler();

        initImageLoader(getApplicationContext());
        initImageLoader();

        mDefaultAvatar = BitmapFactory.decodeResource(getResources(), R.drawable.ic_common_def_header);
        for (int i = 1; i < 64; i++) {
            String emoticonsName = "[zem" + i + "]";
            int emoticonsId = getResources().getIdentifier("zem" + i, "drawable", getPackageName());
            mEmoticons.add(emoticonsName);
            mEmoticons_Zem.add(emoticonsName);
            mEmoticonsId.put(emoticonsName, emoticonsId);
        }
        for (int i = 1; i < 59; i++) {
            String emoticonsName = "[zemoji" + i + "]";
            int emoticonsId = getResources().getIdentifier("zemoji_e" + i, "drawable", getPackageName());
            mEmoticons.add(emoticonsName);
            mEmoticons_Zemoji.add(emoticonsName);
            mEmoticonsId.put(emoticonsName, emoticonsId);
        }

        // 获取当前用户位置
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.setAK("60b43d1a9513d904b6aa2948b27b4a20");
        mLocationClient.registerLocationListener(new BDLocationListener() {

            @Override
            public void onReceivePoi(BDLocation arg0) {

            }

            @Override
            public void onReceiveLocation(BDLocation arg0) {
                mLongitude = arg0.getLongitude();
                mLatitude = arg0.getLatitude();
                Log.i("地理位置", "经度:" + mLongitude + ",纬度:" + mLatitude);
                mLocationClient.stop();
            }
        });
        mLocationClient.start();
        mLocationClient.requestOfflineLocation();
        System.out.println("开始获取");
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Log.e("BaseApplication", "onLowMemory");
        System.gc();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Log.e("BaseApplication", "onTerminate");
    }

    public Bitmap getAvatar(String imageName) {
        if (mAvatarCache.containsKey(imageName)) {
            Reference<Bitmap> reference = mAvatarCache.get(imageName);
            if (reference.get() == null || reference.get().isRecycled()) {
                mAvatarCache.remove(imageName);
            } else {
                return reference.get();
            }
        }
        InputStream is = null;
        Bitmap bitmap = null;
        try {
            is = getAssets().open(AVATAR_DIR + imageName);
            bitmap = BitmapFactory.decodeStream(is);
            if (bitmap == null) {
                throw new FileNotFoundException(imageName + "is not find");
            }
            mAvatarCache.put(imageName, new SoftReference<Bitmap>(bitmap));
            return bitmap;
        } catch (Exception e) {
            return mDefaultAvatar;
        } finally {
            try {
                if (is != null) {
                    is.close();
                    is = null;
                }
            } catch (IOException e) {

            }
        }
    }

    public Bitmap getPhotoOriginal(String imageName) {
        if (mPhotoOriginalCache.containsKey(imageName)) {
            Reference<Bitmap> reference = mPhotoOriginalCache.get(imageName);
            if (reference.get() == null || reference.get().isRecycled()) {
                mPhotoOriginalCache.remove(imageName);
            } else {
                return reference.get();
            }
        }
        InputStream is = null;
        Bitmap bitmap = null;
        try {
            is = getAssets().open(PHOTO_ORIGINAL_DIR + imageName);
            bitmap = BitmapFactory.decodeStream(is);
            if (bitmap == null) {
                throw new FileNotFoundException(imageName + "is not find");
            }
            mPhotoOriginalCache.put(imageName, new SoftReference<Bitmap>(bitmap));
            return bitmap;
        } catch (Exception e) {
            return null;
        } finally {
            try {
                if (is != null) {
                    is.close();
                    is = null;
                }
            } catch (IOException e) {

            }
        }
    }

    public Bitmap getPhotoThumbnail(String imageName) {
        if (mPhotoThumbnailCache.containsKey(imageName)) {
            Reference<Bitmap> reference = mPhotoThumbnailCache.get(imageName);
            if (reference.get() == null || reference.get().isRecycled()) {
                mPhotoThumbnailCache.remove(imageName);
            } else {
                return reference.get();
            }
        }
        InputStream is = null;
        Bitmap bitmap = null;
        try {
            is = getAssets().open(PHOTO_THUMBNAIL_DIR + imageName);
            bitmap = BitmapFactory.decodeStream(is);
            if (bitmap == null) {
                throw new FileNotFoundException(imageName + "is not find");
            }
            mPhotoThumbnailCache.put(imageName, new SoftReference<Bitmap>(bitmap));
            return bitmap;
        } catch (Exception e) {
            return null;
        } finally {
            try {
                if (is != null) {
                    is.close();
                    is = null;
                }
            } catch (IOException e) {

            }
        }
    }

    public Bitmap getStatusPhoto(String imageName) {
        if (mStatusPhotoCache.containsKey(imageName)) {
            Reference<Bitmap> reference = mStatusPhotoCache.get(imageName);
            if (reference.get() == null || reference.get().isRecycled()) {
                mStatusPhotoCache.remove(imageName);
            } else {
                return reference.get();
            }
        }
        InputStream is = null;
        Bitmap bitmap = null;
        try {
            is = getAssets().open(STATUS_PHOTO_DIR + imageName);
            bitmap = BitmapFactory.decodeStream(is);
            if (bitmap == null) {
                throw new FileNotFoundException(imageName + "is not find");
            }
            mStatusPhotoCache.put(imageName, new SoftReference<Bitmap>(bitmap));
            return bitmap;
        } catch (Exception e) {
            return null;
        } finally {
            try {
                if (is != null) {
                    is.close();
                    is = null;
                }
            } catch (IOException e) {

            }
        }
    }

    public static Context getContext() {
        return mContext;
    }

    public static Thread getMainThread() {
        return mMainThread;
    }

    public static long getMainThreadId() {
        return mMainThreadId;
    }

    public static Looper getMainThreadLooper() {
        return mMainLooper;
    }

    public static Handler getMainHander() {
        return mMainHander;
    }

    private final static void initImageLoader() {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(mContext)
                .defaultDisplayImageOptions(getDefaultDisplayOption())// 显示图片的参数，传入自己配置过得DisplayImageOption对象
                .memoryCache(new LruMemoryCache(50 * 1024 * 1024)) // 缓存策略
                .memoryCacheExtraOptions(320, 480) // 即保存的每个缓存文件的最大长宽
                .threadPoolSize(8) // 线程池内线程的数量，默认是3
                .threadPriority(Thread.NORM_PRIORITY - 2) // 当同一个Uri获取不同大小的图片，缓存到内存时，只缓存一个。默认会缓存多个不同的大小的相同图片
                .denyCacheImageMultipleSizesInMemory() // 拒绝同一个url缓存多个图片
                .diskCacheSize(50 * 1024 * 1024) // 设置磁盘缓存大小 50M
                .diskCacheFileNameGenerator(new Md5FileNameGenerator()) // 将保存的时候的URI名称用MD5
                                                                        // 加密
                .tasksProcessingOrder(QueueProcessingType.LIFO)// 设置图片下载和显示的工作队列排序
                .build();
        ImageLoader.getInstance().init(config);
    }

    private final static DisplayImageOptions getDefaultDisplayOption() {
        DisplayImageOptions options = new DisplayImageOptions.Builder().showImageForEmptyUri(R.drawable.isloading) // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.isloading) // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
                .cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
                .showImageOnLoading(R.drawable.isloading).build();
        return options;
    }

    private void initImageLoader(Context applicationContext) {
        File cacheDir = applicationContext.getExternalCacheDir();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(applicationContext)
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
                .diskCache(new UnlimitedDiskCache(cacheDir))
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

}
