package net.micode.notes;

import net.micode.notes.fragment.BitmapFragment;
import net.micode.notes.fragment.DbFragment;
import net.micode.notes.fragment.HttpFragment;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ContentView;

@ContentView(R.layout.main)
public class MyActivity extends Activity {
	private FragmentManager fragmentManager;
	private RadioGroup bottomRg;
	private Fragment fragmentArray[] = { new HttpFragment(), new DbFragment(),new BitmapFragment(), };
	private int iconArray[] = { R.drawable.icon_http, R.drawable.icon_database,R.drawable.icon_btimap };
	private String titleArray[] = { "网络", "数据库", "图片" };
	private FragmentTransaction beginTransaction;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LogUtils.customTagPrefix = "xUtilsSample"; // 方便调试时过滤 adb logcat 输出
		LogUtils.allowI = false; // 关闭 LogUtils.i(...) 的 adb log 输出

		ViewUtils.inject(this);
		bottomRg = (RadioGroup) findViewById(R.id.bottomRg);  
        fragmentManager=getFragmentManager();
        beginTransaction = fragmentManager.beginTransaction();
        beginTransaction.replace(R.id.realtabcontent, fragmentArray[0]).commit();
		setupTabView();
	}

	private void setupTabView() {
		bottomRg.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				FragmentManager fm = getFragmentManager();  
    	        // 开启Fragment事务  
    	        FragmentTransaction transaction = fm.beginTransaction();
				switch(checkedId){
				case R.id.rbOne:
					transaction.replace(R.id.realtabcontent, fragmentArray[0]).addToBackStack(null).commit();
					break;
				case R.id.rbTwo:
					transaction.replace(R.id.realtabcontent, fragmentArray[1]).addToBackStack(null).commit();
					break;
				case R.id.rbThree:
					transaction.replace(R.id.realtabcontent, fragmentArray[2]).addToBackStack(null).commit();
					break;
					default:
						break;
				}
			}
		});
	}

}
