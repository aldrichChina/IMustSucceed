package net.micode.notes;

import net.micode.notes.fragment.BitmapFragment;
import net.micode.notes.fragment.DbFragment;
import net.micode.notes.fragment.HttpFragment;
import net.micode.notes.ui.NotesListActivity;
import net.micode.notes.ui.activity.BaseActivity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.util.LogUtils;

public class MyActivity extends BaseActivity implements OnLongClickListener{
	private FragmentManager fragmentManager;
	private RadioGroup bottomRg;
	private Fragment fragmentArray[] = { new HttpFragment(), new DbFragment(),new BitmapFragment()};
	private FragmentTransaction beginTransaction;
	private TextView toptitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.main);
		super.onCreate(savedInstanceState);
		LogUtils.customTagPrefix = "xUtilsSample"; // 方便调试时过滤 adb logcat 输出
		LogUtils.allowI = false; // 关闭 LogUtils.i(...) 的 adb log 输出
		ViewUtils.inject(this);
		fragmentManager = getFragmentManager();
		beginTransaction = fragmentManager.beginTransaction();
		beginTransaction.add(R.id.realtabcontent, fragmentArray[0],"HttpFragment").commit();
		setupTabView();
	}

	private void setupTabView() {
		bottomRg.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.rbOne:
					showHideFragment(0, 1, 2, "DbFragment");
					break;
				case R.id.rbTwo:
					showHideFragment(1, 0, 2, "DbFragment");
					break;
				case R.id.rbThree:
					showHideFragment(2, 0, 1, "BitmapFragment");
					break;
				default:
					break;
				}
			}
		});
	}

	@Override
	protected void initView() {
		toptitle = (TextView) findViewById(R.id.toptitle);
		bottomRg = (RadioGroup) findViewById(R.id.bottomRg);
	}

	@Override
	protected void initData() {

	}

	@Override
	protected void setListener() {
		toptitle.setOnLongClickListener(this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * 显示隐藏Fragment
	 * 
	 * @param x
	 *            要显示Fragment的数组索引
	 * @param y
	 *            要隐藏Fragment的数组索引
	 * @param z
	 *            要隐藏Fragment的数组索引
	 * @param tag
	 *            要显示Fragment的tag
	 */
	public void showHideFragment(int x, int y, int z, String tag) {
		FragmentManager fm = getFragmentManager();
		// 开启Fragment事务
		FragmentTransaction transaction = fm.beginTransaction();
		transaction.setCustomAnimations(
				R.animator.fragment_slide_left_enter,
				R.animator.fragment_slide_left_exit,
				R.animator.fragment_slide_right_enter,
				R.animator.fragment_slide_right_exit);
		if (fragmentArray[x] == null) {
			fragmentArray[x] = new HttpFragment();
		}
		if (fragmentArray[x].isAdded()) {
			transaction.show(fragmentArray[x]).hide(fragmentArray[y]).hide(fragmentArray[z]).addToBackStack(null).commit();
		} else {
			transaction.add(R.id.realtabcontent, fragmentArray[x], tag).hide(fragmentArray[y]).hide(fragmentArray[z]).addToBackStack(null).commit();
		}

	}


	@Override
	public boolean onLongClick(View v) {

		switch(v.getId()){
		case R.id.toptitle:
			Intent intent = new Intent(this, NotesListActivity.class);
			this.startActivity(intent);
		}
	
		return false;
	}

	
}
