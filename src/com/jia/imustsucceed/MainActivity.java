package com.jia.imustsucceed;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends Activity {

	private ImageView show;
	Bitmap bitmap;
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 0x123) {
				show.setImageBitmap(bitmap);
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		show = (ImageView) findViewById(R.id.show);
		new Thread() {
			public void run() {
				try {
					URL url = new URL(
							"http://f.hiphotos.baidu.com/image/pic/item/43a7d933c895d14340cb2d2a71f082025aaf077e.jpg");
					InputStream is = url.openStream();
					bitmap = BitmapFactory.decodeStream(is);
					handler.sendEmptyMessage(0x123);
					is.close();
					is = url.openStream();
					OutputStream os = openFileOutput("crazyit.png",
							MODE_PRIVATE);
					byte[] buff = new byte[1024];
					int hasRead = 0;
					while ((hasRead = is.read(buff)) > 0) {
						os.write(buff, 0, hasRead);
					}
					is.close();
					os.close();
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			};
		}.start();
	}

}
