/******************************************************************
 *    Package:     net.micode.notes.activity.ResideMenuItem
 *
 *    Filename:    CameraActivity.java
 *
 *    Description: TODO(用一句话描述该文件做什么)
 *
 *    @author:     Aldrich_jia
 *
 *    @version:    1.0.0
 *
 *    Create at:   2016年3月31日 下午3:36:12
 *
 *****************************************************************/
package net.micode.notes.activity.ResideMenuItem;

import java.io.File;
import java.io.IOException;

import net.micode.notes.R;
import net.micode.notes.activity.BaseActivity;
import net.micode.notes.util.Utils;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Surface;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

/**
 * @ClassName CameraActivity
 * @Description TODO(这里用一句话描述这个类的作用)
 * @author Aldrich_jia
 * @Date 2016年3月31日 下午3:36:12
 * @version 1.0.0
 */
public class CameraActivity extends BaseActivity {
    private ImageView ivImage;
    private Button btCamera;
    private String saveDir;
    private Bitmap photo;
    private String state;
    private File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_camera);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {
        ivImage = (ImageView) findViewById(R.id.iv_image);
        btCamera = (Button) findViewById(R.id.bt_camera);
    }

    @Override
    protected void initEvents() {
        saveDir = Environment.getExternalStorageDirectory().getPath() + "/SucceedCamera";
        state = Environment.getExternalStorageState();
        ImageView righttitle = (ImageView) findViewById(R.id.righttitle);
        righttitle.setVisibility(View.INVISIBLE);
        ImageView topback = (ImageView) findViewById(R.id.topback);
        topback.setBackgroundResource(R.drawable.ic_topbar_back_normal);
        topback.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Utils.finish(CameraActivity.this);
            }
        });
    }

    @Override
    protected void setListener() {
        btCamera.setOnClickListener(cameraClick);
    }

    private View.OnClickListener cameraClick = new OnClickListener() {

        @Override
        public void onClick(View v) {
            destoryImage();
            if (state.equals(Environment.MEDIA_MOUNTED)) {
                long timeMillis = System.currentTimeMillis();
                file = new File(saveDir, timeMillis + ".jpg");
                if (!file.exists()) {
                    try {
                        file.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Utils.Toast(CameraActivity.this, "照片创建失败!");
                        return;
                    }
                }
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                startActivityForResult(intent, 1);
            } else {
                ToastMessage("sdcard无效或没有插入!");
            }
        }
    };

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            if (file != null && file.exists()) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 2;
                photo = BitmapFactory.decodeFile(file.getPath(), options);
                Matrix matrix = new Matrix();
                matrix.setRotate(CameraActivity.getPreviewDegree(this));
                photo = Bitmap.createBitmap(photo, 0, 0, photo.getWidth(), photo.getHeight(), matrix, true);
                ivImage.setImageBitmap(photo);
            }
        }
    };

    // 提供一个静态方法，用于根据手机方向获得相机预览画面旋转的角度
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

    private void destoryImage() {
        if (photo != null) {
            photo.recycle();
            photo = null;
        }
    }

    /*
     * (非 Javadoc) Description:
     * @see net.micode.notes.activity.BaseActivity#onDestroy()
     */
    @Override
    protected void onDestroy() {
        destoryImage();
        super.onDestroy();
    }

    public void getSDPath() {
        File sdDir = null;
        File sdDir1 = null;
        File sdDir2 = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);// 判断SD卡是否存在
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();// 获取跟目录
            sdDir1 = Environment.getDataDirectory();// 获取 Android 数据目录
            sdDir2 = Environment.getRootDirectory();// 获取 Android 的根目录
        }
        Log.d("jia", "getExternalStorageDirectory(): " + sdDir.toString());
        Log.d("jia", "getDataDirectory(): " + sdDir1.toString());
        Log.d("jia", "getRootDirectory(): " + sdDir2.toString());
    }

    // 判断一个路径下的文件（文件夹）是否存在
    public static void isExist(String path) {
        File file = new File(path);
        // 判断文件夹是否存在,如果不存在则创建文件夹
        if (!file.exists()) {
            file.mkdir();
        }
        String absolutePath = file.getPath();
        Log.d("jia", "absolutePath=" + absolutePath);
    }

    // 创建外部存储目录即 SDCard
    public void creatSDFile() {
        File sd = Environment.getExternalStorageDirectory();
        String path = sd.getPath() + "/notes";
        File file = new File(path);
        if (!file.exists()) {
            file.mkdir();
        }
        String absolutePath = file.getAbsolutePath();
        Log.d("jia", "creatSDFilePath=" + absolutePath);
    }
}
