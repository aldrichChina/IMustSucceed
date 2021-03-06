package net.micode.notes.activity.ResideMenuItem;

import net.micode.notes.BaseActivity;
import net.micode.notes.R;
import net.micode.notes.util.Utils;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.WriterException;
import com.zbar.lib.CaptureActivity;
import com.zbar.lib.EncodingHandler;

public class BarCodeActivity extends BaseActivity {

    /** Called when the activity is first created. */
    private TextView resultTextView;
    private EditText qrStrEditText;
    private ImageView qrImgImageView;
    private Button scanBarCodeButton;
    private Button generateQRCodeButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_barcode);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 处理扫描结果（在界面上显示）
        if (resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            String scanResult = bundle.getString("result");
            resultTextView.setText(scanResult);
        }
    }

    /*
     * (非 Javadoc) Description:
     * @see net.micode.notes.activity.BaseActivity#initViews()
     */
    @Override
    protected void initViews() {
        resultTextView = (TextView) this.findViewById(R.id.tv_scan_result);
        qrStrEditText = (EditText) this.findViewById(R.id.et_qr_string);
        qrImgImageView = (ImageView) this.findViewById(R.id.iv_qr_image);
        scanBarCodeButton = (Button) this.findViewById(R.id.btn_scan_barcode);
        generateQRCodeButton = (Button) this.findViewById(R.id.btn_add_qrcode);
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
                Utils.finish(BarCodeActivity.this);
            }
        });
        scanBarCodeButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // 打开扫描界面扫描条形码或二维码
                Intent openCameraIntent = new Intent(BarCodeActivity.this, CaptureActivity.class);
                startActivityForResult(openCameraIntent, 0);
            }
        });

        generateQRCodeButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    String contentString = qrStrEditText.getText().toString();
                    if (!contentString.equals("")) {
                        // 根据字符串生成二维码图片并显示在界面上，第二个参数为图片的大小（350*350）
                        Bitmap qrCodeBitmap = EncodingHandler.createQRCode(contentString, 350);
                        qrImgImageView.setImageBitmap(qrCodeBitmap);
                    } else {
                        Toast.makeText(BarCodeActivity.this, "Text can not be empty", Toast.LENGTH_SHORT).show();
                    }

                } catch (WriterException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
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
