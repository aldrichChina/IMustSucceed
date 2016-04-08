/******************************************************************
 *    Package:     net.micode.notes.activity.ResideMenuItem
 *
 *    Filename:    CustomCameraActivity.java
 *
 *    Description: TODO(用一句话描述该文件做什么)
 *
 *    @author:     Aldrich_jia
 *
 *    @version:    1.0.0
 *
 *    Create at:   2016年4月6日 下午1:55:29
 *
 *****************************************************************/
package net.micode.notes.activity.ResideMenuItem;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import net.micode.notes.R;
import net.micode.notes.activity.BaseActivity;
import android.app.Activity;
import android.content.Intent;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * @ClassName CustomCameraActivity
 * @Description TODO(这里用一句话描述这个类的作用)
 * @author Aldrich_jia
 * @Date 2016年4月6日 下午1:55:29
 * @version 1.0.0
 */
public class CustomCameraActivity extends BaseActivity implements SurfaceHolder.Callback{

//    private Camera mCamera;
//    private CameraPreview mPreview;
//    private SurfaceHolder surfaceHolder;
//    int mNumberOfCameras;
//    static int mCurrentCamera; // Camera ID currently chosen
//    // The first rear facing camera
//    int mDefaultCameraId;
//    String lastPicPath = ""; // 上一张拍摄图片的文件路径
//    int mCameraCurrentlyLocked; // Camera ID that's actually acquired
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        Window window = getWindow(); // 得到窗口
//        requestWindowFeature(Window.FEATURE_NO_TITLE); // 没有标题
//        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); // 设置全屏
//        setContentView(R.layout.activity_custom_camera);
//        initView();
//        initData();
//    }
//
//    private void initView() {
//
//    }
//
//    private void initData() {
//        // 检查设备是否支持摄像头
//        if (checkCameraHardware(this) == false) {
//            Toast.makeText(this, "该设备不支持摄像头功能", Toast.LENGTH_LONG).show();
//            return;
//        }
//        // Find the total number of cameras available
//        mNumberOfCameras = Camera.getNumberOfCameras();
//
//        // Find the ID of the rear-facing ("default") camera
//        CameraInfo cameraInfo = new CameraInfo();
//        for (int i = 0; i < mNumberOfCameras; i++) {
//            Camera.getCameraInfo(i, cameraInfo);
//            if (cameraInfo.facing == CameraInfo.CAMERA_FACING_BACK) {
//                mCurrentCamera = mDefaultCameraId = i;
//            }
//        }
//        // 创建Camera实例
//        mCamera = getCameraInstance();
//        setCameraParams(mCamera);
//        // 创建Preview view并将其设为activity中的内容
//        mPreview = new CameraPreview(this, mCamera);
//        SurfaceView surfaceView = (SurfaceView) this.findViewById(R.id.camera_preview);
//        surfaceHolder = surfaceView.getHolder();
//        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
//        surfaceHolder.setFixedSize(176, 144); // 设置Surface分辨率
//        surfaceHolder.setKeepScreenOn(true);// 屏幕常亮
//        surfaceHolder.addCallback(mPreview);// 为SurfaceView的句柄添加一个回调函数
//        // 拍照按钮的时间响应
//        Button captureButton = (Button) findViewById(id.button_capture);
//        captureButton.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                takeFocusedPicture();
//            }
//        });
//        captureButton.setOnLongClickListener(new View.OnLongClickListener() {
//
//            @Override
//            public boolean onLongClick(View v) {
//                // Release this camera -> mCameraCurrentlyLocked
//                if (mCamera != null) {
//                    mCamera.stopPreview();
//                    mPreview.setCamera(null);
//                    mCamera.release();
//                    mCamera = null;
//                }
//
//                // Acquire the next camera and request Preview to reconfigure
//                // parameters.
//                mCurrentCamera = (mCameraCurrentlyLocked + 1) % mNumberOfCameras;
//                mCamera = Camera.open(mCurrentCamera);
//                setCameraParams(mCamera);
//                mCameraCurrentlyLocked = mCurrentCamera;
//                mPreview.switchCamera(mCamera);
//
//                // Start the preview
//                mCamera.startPreview();
//                return true;
//            }
//        });
//        // 打开图片按钮的事件响应
//        ImageButton openPicBtn = (ImageButton) findViewById(id.imgBtnOpenPic);
//        openPicBtn.setOnClickListener(new OpenPictureListener());
//        // 读取保存目录中的文件，获取上一次最后拍的照片
//        lastPicPath = getLastCaptureFile();
//        if (lastPicPath != "")
//            updateOpenPicImgBtn(lastPicPath);
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        if (mCamera == null) {
//            mCamera = getCameraInstance();
//            setCameraParams(mCamera);
//            mPreview.setCamera(mCamera);
//            mCamera.startPreview();
//        }
//
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        releaseCamera();
//    }
//
//    // 先对焦后拍照
//    public void takeFocusedPicture() {
//        mCamera.autoFocus(new Camera.AutoFocusCallback() {
//
//            @Override
//            public void onAutoFocus(boolean success, Camera camera) {
//                if (camera != null) {
//                    mCamera.takePicture(null, null, mPicture);
//                }
//            }
//        });
//    }
//
//    // 读取保存目录中最新的图像文件
//    String getLastCaptureFile() {
//        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
//                "SuperCamera");
//        if (mediaStorageDir.exists() == false) {
//            return "";
//        }
//        File[] fs = mediaStorageDir.listFiles();
//        if (fs.length <= 0)
//            return "";
//        Arrays.sort(fs, new CustomCameraActivity.CompratorByLastModified());
//        return fs[fs.length - 1].getPath();
//    }
//
//    // 排序器，按修改时间从新到旧排序
//    static class CompratorByLastModified implements Comparator<File> {
//
//        public int compare(File f1, File f2) {
//            long diff = f1.lastModified() - f2.lastModified();
//            if (diff > 0)
//                return 1;
//            else if (diff == 0)
//                return 0;
//            else
//                return -1;
//        }
//
//        public boolean equals(Object obj) {
//            return true;
//        }
//    }
//
//    // 设置摄像头参数
//    protected void setCameraParams(Camera camera) {
//        camera.setDisplayOrientation(90);
//        Camera.Parameters params = camera.getParameters();
//        params.setRotation(90);
//        camera.setParameters(params);
//    }
//
//    // 检查设备是否提供摄像头
//    private boolean checkCameraHardware(Context context) {
//        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
//            // 摄像头存在
//            return true;
//        } else {
//            // 摄像头不存在
//            return false;
//        }
//    }
//
//    // 安全获取Camera对象实例的方法*/
//    public static Camera getCameraInstance() {
//        Camera c = null;
//        try {
//            c = Camera.open(mCurrentCamera); // 试图获取Camera实例
//        } catch (Exception e) {
//            // 摄像头不可用（正被占用或不存在）
//        }
//        return c; // 不可用则返回null
//    }
//
//    public class OpenPictureListener implements View.OnClickListener {
//
//        @Override
//        public void onClick(View v) {
//            File file = new File(lastPicPath);
//            Log.d("jia", "lastPicPath=" + lastPicPath);
//            Log.d("jia", "file=" + file);
//            Intent intent = new Intent(Intent.ACTION_VIEW);
//            intent.setDataAndType(Uri.fromFile(file), "image/*");
//            Log.d("jia", "Uri.fromFile(file), image=" + Uri.fromFile(file) + "image/*");
//            startActivity(intent);
//        }
//    };
//
//    // 更新打开图像按钮的缩略图
//    public void updateOpenPicImgBtn(String path) {
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inJustDecodeBounds = true; // 表明只获取图像大小
//        Bitmap bm = BitmapFactory.decodeFile(path, options); // 由于inJustDecodeBounds为true，此时bm为null
//        options.inSampleSize = options.outWidth / 64;
//        options.inJustDecodeBounds = false;
//        bm = BitmapFactory.decodeFile(path, options);
//        Matrix matrix = new Matrix();
//        matrix.setRotate(getPreviewDegree(CustomCameraActivity.this));
//        bm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
//        ImageButton openPicBtn = (ImageButton) findViewById(id.imgBtnOpenPic);
//        openPicBtn.setImageBitmap(bm);
//    }
//
//    public static final int MEDIA_TYPE_IMAGE = 1;
//    public static final int MEDIA_TYPE_VIDEO = 2;
//    protected static final String TAG = "MainActivity";
//
//    /** 为保存图片或视频创建文件Uri */
//    private Uri getOutputMediaFileUri(int type) {
//        return Uri.fromFile(getOutputMediaFile(type));
//    }
//
//    /** 为保存图片或视频创建File */
//    private File getOutputMediaFile(int type) {
//        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
//                "SuperCamera");
//        // 如果期望图片在应用程序卸载后还存在、且能被其它应用程序共享，
//        // 则此保存位置最合适
//        // 如果不存在的话，则创建存储目录
//        if (!mediaStorageDir.exists()) {
//            if (!mediaStorageDir.mkdirs()) {
//                Log.d("MyCameraApp", "failed to create directory");
//                return null;
//            }
//        }
//        // 创建媒体文件名
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        File mediaFile;
//        if (type == MEDIA_TYPE_IMAGE) {
//            Log.d("jia", "mediaStorageDir.getPath()" + mediaStorageDir.getPath());
//            mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
//        } else if (type == MEDIA_TYPE_VIDEO) {
//            mediaFile = new File(mediaStorageDir.getPath() + File.separator + "VID_" + timeStamp + ".mp4");
//        } else {
//            return null;
//        }
//        return mediaFile;
//    }
//
//    private PictureCallback mPicture = new PictureCallback() {
//
//        @Override
//        public void onPictureTaken(byte[] data, Camera camera) {
//            File pictureFile = getOutputMediaFile(MEDIA_TYPE_IMAGE);
//            if (pictureFile == null) {
//                Log.d(TAG, "Error creating media file, check storage permissions: ");
//                return;
//            }
//            try {
//                Log.d("jia", "" + 1);
//                FileOutputStream fos = new FileOutputStream(pictureFile);
//                fos.write(data);
//                Log.d("jia", "" + 2);
//                fos.close();
//                camera.startPreview();
//                // 更新上一张图片路径，更新ImgBtnOpenPic缩略图
//                lastPicPath = pictureFile.getAbsolutePath();
//                updateOpenPicImgBtn(lastPicPath);
//            } catch (FileNotFoundException e) {
//                Log.d(TAG, "File not found: " + e.getMessage());
//            } catch (IOException e) {
//                Log.d(TAG, "Error accessing file: " + e.getMessage());
//            }
//
//        }
//    };
//
//    public void returnActivity(View view) {
//        Intent intent = getIntent();
//        lastPicPath = getLastCaptureFile();
//        if (lastPicPath != "") {
//            Bundle bundle = new Bundle();
//            bundle.putString("lastPicPath", lastPicPath);
//            intent.putExtras(bundle);
//            setResult(RESULT_OK, intent);
//            releaseCamera();
//            finish();
//        }
//    }
//
//    private void releaseCamera() {
//        if (mCamera != null) {
//            mCamera.release(); // 为其它应用释放摄像头
//            mCamera = null;
//        }
//    }
//
//    // 基本的摄像头预览类
//    public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
//
//        // private SurfaceHolder mHolder;
//        private Camera mCamera;
//        boolean mSurfaceCreated = false;
//        List<Size> mSupportedPreviewSizes;
//
//        public CameraPreview(Context context, Camera camera) {
//            super(context);
//            mCamera = camera;
//            // 安装一个SurfaceHolder.Callback，
//            // 这样创建和销毁底层surface时能够获得通知。
//            // mHolder = getHolder();
//            // mHolder.addCallback(this);
//            // 已过期的设置，但版本低于3.0的Android还需要
//            // mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
//        }
//
//        public void startPreview() {
//            mCamera.startPreview();
//        }
//
//        public void surfaceCreated(SurfaceHolder holder) {
//            // surface已被创建，现在把预览画面的位置通知摄像头
//            try {
//                mCamera.setPreviewDisplay(holder);
//                mCamera.startPreview();
//            } catch (IOException e) {
//                Log.d(TAG, "Error setting camera preview: " + e.getMessage());
//            }
//        }
//
//        public void surfaceDestroyed(SurfaceHolder holder) {
//            // 空代码。注意在activity中释放摄像头预览对象
//        }
//
//        public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
//            // 如果预览无法更改或旋转，注意此处的事件
//            // 确保在缩放或重排时停止预览
//            if (surfaceHolder.getSurface() == null) {
//                // 预览surface不存在
//                return;
//            }
//            // 更改时停止预览
//            try {
//                mCamera.stopPreview();
//            } catch (Exception e) {
//                // 忽略：试图停止不存在的预览
//            }
//            // 在此进行缩放、旋转和重新组织格式
//            // 以新的设置启动预
//            try {
//                mCamera.setPreviewDisplay(surfaceHolder);
//                mCamera.setDisplayOrientation(90);
//                mCamera.startPreview();
//                mSurfaceCreated = true;
//            } catch (Exception e) {
//                Log.d(TAG, "Error starting camera preview: " + e.getMessage());
//            }
//        }
//
//        public void switchCamera(Camera camera) {
//            setCamera(camera);
//            try {
//                camera.setPreviewDisplay(surfaceHolder);
//            } catch (IOException exception) {
//                Log.e(TAG, "IOException caused by setPreviewDisplay()", exception);
//            }
//        }
//
//        public void setCamera(Camera camera) {
//            try {
//                mCamera = camera;
//
//                if (mCamera != null) {
//                    camera.setPreviewDisplay(surfaceHolder);
//                    mSupportedPreviewSizes = mCamera.getParameters().getSupportedPreviewSizes();
//                    if (mSurfaceCreated)
//                        requestLayout();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }


    private Camera mCamera;
    private SurfaceView mPreview;
    private SurfaceHolder mHolder;
    private Camera.PictureCallback mPictureCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            File tempFile = new File("/sdcard/temp.png");
            String path = tempFile.getPath();
            try {
                FileOutputStream fos = new FileOutputStream(tempFile);
                fos.write(data);
                fos.close();
                Intent intent = getIntent();
                Bundle bundle = new Bundle();
              bundle.putString("lastPicPath", path);
              intent.putExtras(bundle);
              setResult(RESULT_OK, intent);
              releaseCamera();
              CustomCameraActivity.this.finish();
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    };

    /*
     * (非 Javadoc) Description:
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_camera2);
        mPreview = (SurfaceView) findViewById(R.id.preview);
        mHolder = mPreview.getHolder();
        mHolder.addCallback(this);
        mPreview.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View paramView) {
                mCamera.autoFocus(null);
            }
        });
    }

    public void capture(View view) {
        Camera.Parameters parameters = mCamera.getParameters();
        parameters.setPictureFormat(ImageFormat.JPEG);
        // parameters.setPreviewSize(800, 400);
        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
        mCamera.autoFocus(new Camera.AutoFocusCallback() {

            @Override
            public void onAutoFocus(boolean success, Camera camera) {
                // if(success){
                mCamera.takePicture(null, null, mPictureCallback);
                // }
            }
        });
    }

    /*
     * (非 Javadoc) Description:
     * @see android.app.Activity#onResume()
     */
    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        if (mCamera == null) {
            mCamera = getCamera();
            if (mHolder != null) {
                setStartPreview(mCamera, mHolder);
            }
        }
    }

    /*
     * (非 Javadoc) Description:
     * @see android.app.Activity#onPause()
     */
    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        releaseCamera();
    }

    /**
     * @Description (获取Camera对象)
     * @return
     */
    private Camera getCamera() {
        Camera camera;
        try {
            camera = Camera.open();
        } catch (Exception e) {
            camera = null;
            e.printStackTrace();
        }
        return camera;
    }

    /**
     * @Description (开始预览相机内容)
     */
    private void setStartPreview(Camera camera, SurfaceHolder holder) {
        try {
            camera.setPreviewDisplay(holder);
            // 将系统Camera预览角度进行调整
            camera.setDisplayOrientation(90);
            camera.startPreview();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * @Description (释放相机资源)
     */
    private void releaseCamera() {
        if (mCamera != null) {
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }

    /*
     * (非 Javadoc) Description:
     * @see android.view.SurfaceHolder.Callback#surfaceCreated(android.view.SurfaceHolder)
     */
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        setStartPreview(mCamera, mHolder);
    }

    /*
     * (非 Javadoc) Description:
     * @see android.view.SurfaceHolder.Callback#surfaceChanged(android.view.SurfaceHolder, int, int, int)
     */
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        mCamera.stopPreview();
        setStartPreview(mCamera, mHolder);
    }

    /*
     * (非 Javadoc) Description:
     * @see android.view.SurfaceHolder.Callback#surfaceDestroyed(android.view.SurfaceHolder)
     */
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        releaseCamera();
    }

    public static int getPreviewDegree(Activity activity) {
        // 获得手机的方向
        int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
        int degree = 0;
        // 根据手机的方向计算相机预览画面应该选择的角度
        switch (rotation) {
        case Surface.ROTATION_0:
            degree = 90;
            break;
        case Surface.ROTATION_90:
            degree = 0;
            break;
        case Surface.ROTATION_180:
            degree = 270;
            break;
        case Surface.ROTATION_270:
            degree = 180;
            break;
        }
        return degree;
    }

    /*
     * (非 Javadoc) Description:
     * @see net.micode.notes.activity.BaseActivity#initViews()
     */
    @Override
    protected void initViews() {
        // TODO Auto-generated method stub

    }

    /*
     * (非 Javadoc) Description:
     * @see net.micode.notes.activity.BaseActivity#initEvents()
     */
    @Override
    protected void initEvents() {
        // TODO Auto-generated method stub

    }

    /*
     * (非 Javadoc) Description:
     * @see net.micode.notes.activity.BaseActivity#setListener()
     */
    @Override
    protected void setListener() {
        // TODO Auto-generated method stub

    }

}
