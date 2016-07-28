/******************************************************************
 *    Package:     net.micode.notes.activity.ResideMenuItem
 *
 *    Filename:    CameraBeginActivity.java
 *
 *    Description: TODO(用一句话描述该文件做什么)
 *
 *    @author:     Aldrich_jia
 *
 *    @version:    1.0.0
 *
 *    Create at:   2016年4月6日 下午2:06:35
 *
 *****************************************************************/
package net.micode.notes.activity.ResideMenuItem;

import net.micode.notes.BaseActivity;
import net.micode.notes.R;
import net.micode.notes.util.Utils;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

/**
 * @ClassName CameraBeginActivity
 * @Description TODO(这里用一句话描述这个类的作用)
 * @author Aldrich_jia
 * @Date 2016年4月6日 下午2:06:35
 * @version 1.0.0
 */
public class CameraBeginActivity extends BaseActivity {

    private ImageView img;
    private Bitmap photo;

    /*
     * (非 Javadoc) Description:
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_camera_begin);
        super.onCreate(savedInstanceState);

    }

    private void setImage(String path) {
        destoryImage();
        Matrix matrix = new Matrix();
        matrix.setRotate(CameraActivity.getPreviewDegree(CameraBeginActivity.this));
        BitmapFactory.Options options = new BitmapFactory.Options();
        // options.inJustDecodeBounds = true; // 表明只获取图像大小
        // photo = BitmapFactory.decodeFile(path, options);// 由于inJustDecodeBounds为true，此时bm为null
        // options.inSampleSize = options.outWidth / 64;
        options.inSampleSize = 2;
        // options.inJustDecodeBounds = false;
        photo = BitmapFactory.decodeFile(path, options);
        photo = Bitmap.createBitmap(photo, 0, 0, photo.getWidth(), photo.getHeight(), matrix, true);
        img.setImageBitmap(photo);
    }

    /*
     * (非 Javadoc) Description:
     * @see android.app.Activity#onActivityResult(int, int, android.content.Intent)
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            String path = data.getExtras().getString("lastPicPath");
            if (path != null && !path.isEmpty()) {
                setImage(path);
            }
        }
    }

    private void destoryImage() {
        if (photo != null) {
            photo.recycle();
            photo = null;
        }
    }

    /*
     * (非 Javadoc) Description:
     * @see net.micode.notes.activity.BaseActivity#initViews()
     */
    @Override
    protected void initViews() {
        img = (ImageView) findViewById(R.id.img);
        findViewById(R.id.btn).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(CameraBeginActivity.this, CustomCameraActivity.class), 1);
            }
        });
    }

    /*
     * (非 Javadoc) Description:
     * @see net.micode.notes.activity.BaseActivity#initEvents()
     */
    @Override
    protected void initEvents() {
        ImageView righttitle = (ImageView) findViewById(R.id.righttitle);
        righttitle.setVisibility(View.INVISIBLE);
        ImageView topback = (ImageView) findViewById(R.id.topback);
        topback.setBackgroundResource(R.drawable.ic_topbar_back_normal);
        topback.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Utils.finish(CameraBeginActivity.this);
            }
        });
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
