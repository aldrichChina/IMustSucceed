package net.micode.notes.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import net.micode.notes.R;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;

public class SystemUtils {

	/**
	 * @author htq_
	 * @param mActivity
	 * bolg:www.csdn.net/htq__
	 */
	public static void shareApp(Activity mActivity)
	{
      String shareAppContent="各位亲爱的小伙伴们，我发现了一款简约好用且颜值爆表的天气APP柠檬天气，分享给大家，记得关注作者的博客http://www.yaojiawei.cc，福利多多哦！";
    
      new File(mActivity.getFilesDir(), "share.png").deleteOnExit();
		FileOutputStream fileOutputStream=null;
		try {
			fileOutputStream = mActivity.openFileOutput(
					"share.png", 1);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
     
		  Bitmap pic=BitmapFactory.decodeResource(mActivity.getResources(),R.drawable.icon_app);
		  pic.compress(CompressFormat.JPEG, 100,fileOutputStream);
		  
      
        Intent intent = new Intent("android.intent.action.SEND");
		intent.setType("image/*");
		intent.putExtra("sms_body", shareAppContent);
		intent.putExtra("android.intent.extra.TEXT",shareAppContent);
		intent.putExtra("android.intent.extra.STREAM",
				Uri.fromFile(new File(mActivity.getFilesDir(), "share.png")));
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		mActivity.startActivity(Intent.createChooser(intent,"好东西要与小伙伴们一起分享"));
	}
}
