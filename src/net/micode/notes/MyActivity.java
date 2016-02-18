package net.micode.notes;

import net.micode.notes.entities.NewsDetailContent;
import net.micode.notes.fragment.BitmapFragment;
import net.micode.notes.fragment.CalendarFragment;
import net.micode.notes.fragment.DbFragment;
import net.micode.notes.fragment.HttpFragment;
import net.micode.notes.fragment.HttpFragment.OnHeadlineSelectedListener;
import net.micode.notes.fragment.NewsDetailFragment;
import net.micode.notes.fragment.PictureFragment;
import net.micode.notes.fragment.ProfileFragment;
import net.micode.notes.fragment.SettingsFragment;
import net.micode.notes.ui.NotesListActivity;
import net.micode.notes.ui.activity.BaseActivity;
import net.micode.notes.view.ResideMenu.ResideMenu;
import net.micode.notes.view.ResideMenu.ResideMenuItem;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.util.LogUtils;

public class MyActivity extends BaseActivity implements OnLongClickListener ,OnHeadlineSelectedListener{
	private FragmentManager fragmentManager;
	private RadioGroup bottomRg;
	private Fragment fragmentArray[] = { new HttpFragment(), new DbFragment(),
			new BitmapFragment() };
	private FragmentTransaction beginTransaction;
	private TextView toptitle;
	private ResideMenu resideMenu;
	private MyActivity mContext;
	private ResideMenuItem itemHome;
	private ResideMenuItem itemProfile;
	private ResideMenuItem itemCalendar;
	private ResideMenuItem itemSettings;
	private ResideMenuItem itemPicture;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.main);
		super.onCreate(savedInstanceState);
		LogUtils.customTagPrefix = "xUtilsSample"; // 方便调试时过滤 adb logcat 输出
		LogUtils.allowI = false; // 关闭 LogUtils.i(...) 的 adb log 输出
		ViewUtils.inject(this);
		mContext = this;
		setUpMenu();
		if (savedInstanceState == null)
			changeFragment(new HttpFragment());
		fragmentManager = getFragmentManager();
		beginTransaction = fragmentManager.beginTransaction();
		beginTransaction.add(R.id.realtabcontent, fragmentArray[0],
				"HttpFragment").commit();
		setupTabView();
	}

	private void setupTabView() {
		bottomRg.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.rbOne:
					showHideFragment(0, 1, 2, "HttpFragment");
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
	public void onClick(View view) {

		if (view == itemHome) {
			changeFragment(new HttpFragment());
		} else if (view == itemProfile) {
			changeFragment(new ProfileFragment());
		} else if (view == itemCalendar) {
			changeFragment(new CalendarFragment());
		} else if (view == itemSettings) {
			changeFragment(new SettingsFragment());
		}else if(view == itemPicture){
			changeFragment(new PictureFragment());
		}

		resideMenu.closeMenu();
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
		transaction.setCustomAnimations(R.animator.fragment_slide_left_enter,
				R.animator.fragment_slide_left_exit,
				R.animator.fragment_slide_right_enter,
				R.animator.fragment_slide_right_exit);
		if (fragmentArray[x] == null) {
			fragmentArray[x] = new HttpFragment();
		}
		if (fragmentArray[x].isAdded()) {
			transaction.show(fm.findFragmentByTag(tag)).hide(fragmentArray[y])
					.hide(fragmentArray[z]).hide(fm.findFragmentByTag("fragment")).addToBackStack(null).commit();
		} else {
			transaction.add(R.id.realtabcontent, fragmentArray[x], tag)
					.hide(fragmentArray[y]).hide(fragmentArray[z]).hide(fm.findFragmentByTag("fragment"))
					.addToBackStack(null).commit();
		}

	}

	@Override
	public boolean onLongClick(View v) {

		switch (v.getId()) {
		case R.id.toptitle:
			Intent intent = new Intent(this, NotesListActivity.class);
			this.startActivity(intent);
		}

		return false;
	}

	private void setUpMenu() {

		// attach to current activity;
		resideMenu = new ResideMenu(this);
		resideMenu.setUse3D(true);
		resideMenu.setBackground(R.drawable.menu_background);
		resideMenu.attachToActivity(this);
		resideMenu.setMenuListener(menuListener);
		// valid scale factor is between 0.0f and 1.0f. leftmenu'width is
		// 150dip.
		resideMenu.setScaleValue(0.6f);

		// create menu items;
		itemHome = new ResideMenuItem(this, R.drawable.icon_home, "Home");
		itemProfile = new ResideMenuItem(this, R.drawable.icon_profile,
				"Profile");
		itemCalendar = new ResideMenuItem(this, R.drawable.icon_calendar,
				"Calendar");
		itemSettings = new ResideMenuItem(this, R.drawable.icon_settings,
				"Settings");
		itemPicture=new ResideMenuItem(this,R.drawable.abc_ic_menu_selectall_mtrl_alpha,"Picture");
		itemHome.setOnClickListener(this);
		itemProfile.setOnClickListener(this);
		itemCalendar.setOnClickListener(this);
		itemSettings.setOnClickListener(this);
		itemPicture.setOnClickListener(this);
		resideMenu.addMenuItem(itemHome, ResideMenu.DIRECTION_LEFT);
		resideMenu.addMenuItem(itemProfile, ResideMenu.DIRECTION_LEFT);
		resideMenu.addMenuItem(itemCalendar, ResideMenu.DIRECTION_RIGHT);
		resideMenu.addMenuItem(itemSettings, ResideMenu.DIRECTION_RIGHT);
		resideMenu.addMenuItem(itemPicture, ResideMenu.DIRECTION_RIGHT);
		// You can disable a direction by setting ->
		// resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);

		findViewById(R.id.topback).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
					}
				});
		findViewById(R.id.righttitle).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						resideMenu.openMenu(ResideMenu.DIRECTION_RIGHT);
					}
				});
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		return resideMenu.dispatchTouchEvent(ev);
	}

	private ResideMenu.OnMenuListener menuListener = new ResideMenu.OnMenuListener() {
		@Override
		public void openMenu() {
//			Utils.ToastMessage(context, "Menu is opened!");
		}

		@Override
		public void closeMenu() {
//			Utils.ToastMessage(context, "Menu is closed!");
		}
	};

	private void changeFragment(Fragment targetFragment) {
		resideMenu.clearIgnoredViewList();
		getFragmentManager().beginTransaction()
				.replace(R.id.realtabcontent, targetFragment, "fragment")
				.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
				.commit();
	}

	// What good method is to access resideMenu？
	public ResideMenu getResideMenu() {
		return resideMenu;
	}
	

	@Override
	public void onArticleSelected(NewsDetailContent detailContent,String tag) {
		  // 用户选中HeadlinesFragment中的头标题后
        // 做一些必要的业务操作

		NewsDetailFragment articleFrag = (NewsDetailFragment)getFragmentManager().findFragmentByTag(tag);

        if (articleFrag != null) {
            // 如果 article frag 不为空，那么我们在同时显示两个fragmnet的布局中...

            // 调用ArticleFragment中的方法去更新它的内容
            articleFrag.updateArticleView(detailContent);
        } else {
            // 否则，我们就是在仅包含一个fragment的布局中并需要交换fragment...

            // 创建fragment并给他一个跟选中的文章有关的参数
        	NewsDetailFragment newFragment = new NewsDetailFragment();
            Bundle args = new Bundle();
            args.putSerializable("news_detailContent", detailContent);
            newFragment.setArguments(args);
        
            FragmentTransaction transaction = getFragmentManager().beginTransaction();

            // 用这个fragment替换任何在fragment_container中的东西
            // 并添加事务到back stack中以便用户可以回退到之前的状态
            transaction.replace(R.id.realtabcontent, newFragment,tag);
            transaction.addToBackStack(null);

            // 提交事务
            transaction.commit();
	}}
	}
