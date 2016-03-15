package net.micode.notes.activity.maintabs;

import net.micode.notes.R;
import net.micode.notes.activity.BaseActivity;
import net.micode.notes.dialog.CustomExitDialog;
import net.micode.notes.entities.NewsDetailContent;
import net.micode.notes.fragment.BitmapFragment;
import net.micode.notes.fragment.CalendarFragment;
import net.micode.notes.fragment.ContactsFragment;
import net.micode.notes.fragment.DbFragment;
import net.micode.notes.fragment.HttpFragment;
import net.micode.notes.fragment.HttpFragment.OnHeadlineSelectedListener;
import net.micode.notes.fragment.NewsDetailFragment;
import net.micode.notes.fragment.PictureFragment;
import net.micode.notes.fragment.ProfileFragment;
import net.micode.notes.fragment.SettingsFragment;
import net.micode.notes.view.ResideMenu.ResideMenu;
import net.micode.notes.view.ResideMenu.ResideMenuItem;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends BaseActivity implements OnClickListener,
		OnHeadlineSelectedListener, OnLongClickListener {

	/**
	 * 用于展示消息的Fragment
	 */
	private HttpFragment httpFragment;

	private DbFragment dbFragment;

	/**
	 * 用于展示动态的Fragment
	 */
	private BitmapFragment bitmapFragment;

	/**
	 * 用于展示联系人的Fragment
	 */
	private ContactsFragment contactsFragment;
	/**
	 * 用于展示设置的Fragment
	 */
	private SettingsFragment settingFragment;

	/**
	 * 消息界面布局
	 */
	private View messageLayout;
	private View nearbyLayout;
	/**
	 * 联系人界面布局
	 */
	private View contactsLayout;

	/**
	 * 动态界面布局
	 */
	private View newsLayout;

	/**
	 * 设置界面布局
	 */
	private View settingLayout;

	/**
	 * 在Tab布局上显示消息图标的控件
	 */
	private ImageView messageImage;
	private ImageView nearbyImage;
	/**
	 * 在Tab布局上显示联系人图标的控件
	 */
	private ImageView contactsImage;

	/**
	 * 在Tab布局上显示动态图标的控件
	 */
	private ImageView newsImage;

	/**
	 * 在Tab布局上显示设置图标的控件
	 */
	private ImageView settingImage;

	/**
	 * 在Tab布局上显示消息标题的控件
	 */
	private TextView messageText;
	private TextView nearbyText;
	/**
	 * 在Tab布局上显示联系人标题的控件
	 */
	private TextView contactsText;

	/**
	 * 在Tab布局上显示动态标题的控件
	 */
	private TextView newsText;

	/**
	 * 在Tab布局上显示设置标题的控件
	 */
	private TextView settingText;

	/**
	 * 用于对Fragment进行管理
	 */
	private NewsDetailFragment newFragment;
	private FragmentManager fragmentManager;
	private TextView toptitle;
	private ResideMenu resideMenu;
	private ResideMenuItem itemHome;
	private ResideMenuItem itemProfile;
	private ResideMenuItem itemCalendar;
	private ResideMenuItem itemSettings;
	private ResideMenuItem itemPicture;
	private long exitTime = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_maintabs);
		super.onCreate(savedInstanceState);
		// 初始化布局元素
		fragmentManager = getFragmentManager();
		// 第一次启动时选中第0个tab
		setTabSelection(0);
	}

	/**
	 * 在这里获取到每个需要用到的控件的实例，并给它们设置好必要的点击事件。
	 */
	@Override
	protected void initViews() {
		newsLayout = findViewById(R.id.news_layout);
		messageLayout = findViewById(R.id.message_layout);
		contactsLayout = findViewById(R.id.contacts_layout);
		nearbyLayout = findViewById(R.id.nearby_layout);
		settingLayout = findViewById(R.id.setting_layout);
		newsImage = (ImageView) findViewById(R.id.news_image);
		messageImage = (ImageView) findViewById(R.id.message_image);
		contactsImage = (ImageView) findViewById(R.id.contacts_image);
		nearbyImage = (ImageView) findViewById(R.id.nearby_image);
		settingImage = (ImageView) findViewById(R.id.setting_image);
		newsText = (TextView) findViewById(R.id.news_text);
		messageText = (TextView) findViewById(R.id.message_text);
		contactsText = (TextView) findViewById(R.id.contacts_text);
		nearbyText = (TextView) findViewById(R.id.nearby_text);
		settingText = (TextView) findViewById(R.id.setting_text);
		toptitle = (TextView) findViewById(R.id.toptitle);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.news_layout:
			// 当点击了动态tab时，选中第3个tab
			setTabSelection(0);
			break;
		case R.id.message_layout:
			// 当点击了消息tab时，选中第1个tab
			setTabSelection(1);
			break;
		case R.id.nearby_layout:
			// 当点击了消息tab时，选中第1个tab
			setTabSelection(2);
			break;
		case R.id.contacts_layout:
			// 当点击了联系人tab时，选中第2个tab
			setTabSelection(3);
			break;

		case R.id.setting_layout:
			// 当点击了设置tab时，选中第4个tab
			setTabSelection(4);
			break;
		default:
			break;
		}

		if (v == itemHome) {
			changeFragment(new HttpFragment());
		} else if (v == itemProfile) {
			changeFragment(new ProfileFragment());
		} else if (v == itemCalendar) {
			changeFragment(new CalendarFragment());
		} else if (v == itemSettings) {
			changeFragment(new SettingsFragment());
		} else if (v == itemPicture) {
			changeFragment(new PictureFragment());
		}

		resideMenu.closeMenu();

	}

	/**
	 * 根据传入的index参数来设置选中的tab页。
	 * 
	 * @param index
	 *            每个tab页对应的下标。0表示消息，1表示联系人，2表示动态，3表示设置。
	 */
	private void setTabSelection(int index) {
		// 每次选中之前先清楚掉上次的选中状态
		clearSelection();
		// 开启一个Fragment事务
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		// 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
		hideFragments(transaction);
		switch (index) {
		case 0:
			// 当点击了动态tab时，改变控件的图片和文字颜色
			newsImage.setImageResource(R.drawable.ic_nav_3_active);
			newsText.setTextColor(Color.WHITE);
			if (httpFragment == null) {
				// 如果NewsFragment为空，则创建一个并添加到界面上
				httpFragment = new HttpFragment();
				transaction.add(R.id.content, httpFragment);
			} else {
				// 如果NewsFragment不为空，则直接将它显示出来
				transaction.show(httpFragment);
			}
			break;
		case 1:
			// 当点击了消息tab时，改变控件的图片和文字颜色
			messageImage.setImageResource(R.drawable.ic_nav_2_active);
			messageText.setTextColor(Color.WHITE);
			if (dbFragment == null) {
				// 如果MessageFragment为空，则创建一个并添加到界面上
				dbFragment = new DbFragment();
				transaction.add(R.id.content, dbFragment);
			} else {
				// 如果MessageFragment不为空，则直接将它显示出来
				transaction.show(dbFragment);
			}
			break;
		case 2:
			// 当点击了消息tab时，改变控件的图片和文字颜色
			nearbyImage.setImageResource(R.drawable.ic_nav_1_active);
			nearbyText.setTextColor(Color.WHITE);
			if (bitmapFragment == null) {
				// 如果MessageFragment为空，则创建一个并添加到界面上
				bitmapFragment = new BitmapFragment();
				transaction.add(R.id.content, bitmapFragment);
			} else {
				// 如果MessageFragment不为空，则直接将它显示出来
				transaction.show(bitmapFragment);
			}
			break;
		case 3:
			// 当点击了联系人tab时，改变控件的图片和文字颜色
			contactsImage.setImageResource(R.drawable.ic_nav_4_active);
			contactsText.setTextColor(Color.WHITE);
			if (contactsFragment == null) {
				// 如果ContactsFragment为空，则创建一个并添加到界面上
				contactsFragment = new ContactsFragment();
				transaction.add(R.id.content, contactsFragment);
			} else {
				// 如果ContactsFragment不为空，则直接将它显示出来
				transaction.show(contactsFragment);
			}
			break;

		case 4:
		default:
			// 当点击了设置tab时，改变控件的图片和文字颜色
			settingImage.setImageResource(R.drawable.ic_nav_5_active);
			settingText.setTextColor(Color.WHITE);
			if (settingFragment == null) {
				// 如果SettingFragment为空，则创建一个并添加到界面上
				settingFragment = new SettingsFragment();
				transaction.add(R.id.content, settingFragment);
			} else {
				// 如果SettingFragment不为空，则直接将它显示出来
				transaction.show(settingFragment);
			}
			break;
		}
		transaction.commit();
	}

	/**
	 * 清除掉所有的选中状态。
	 */
	private void clearSelection() {
		newsImage.setImageResource(R.drawable.ic_nav_3_normal);
		newsText.setTextColor(Color.parseColor("#82858b"));
		messageImage.setImageResource(R.drawable.ic_nav_2_normal);
		messageText.setTextColor(Color.parseColor("#82858b"));
		nearbyImage.setImageResource(R.drawable.ic_nav_1_normal);
		nearbyText.setTextColor(Color.parseColor("#82858b"));
		contactsImage.setImageResource(R.drawable.ic_nav_4_normal);
		contactsText.setTextColor(Color.parseColor("#82858b"));
		settingImage.setImageResource(R.drawable.ic_nav_5_normal);
		settingText.setTextColor(Color.parseColor("#82858b"));
	}

	/**
	 * 将所有的Fragment都置为隐藏状态。
	 * 
	 * @param transaction
	 *            用于对Fragment执行操作的事务
	 */
	private void hideFragments(FragmentTransaction transaction) {
		if (httpFragment != null) {
			transaction.hide(httpFragment);
		}
		if (dbFragment != null) {
			transaction.hide(dbFragment);
		}
		if (bitmapFragment != null) {
			transaction.hide(bitmapFragment);
		}
		if (contactsFragment != null) {
			transaction.hide(contactsFragment);
		}
		if (settingFragment != null) {
			transaction.hide(settingFragment);
		}
	}

	@Override
	protected void initEvents() {
		setUpMenu();
	}

	@Override
	protected void setListener() {
		messageLayout.setOnClickListener(this);
		contactsLayout.setOnClickListener(this);
		nearbyLayout.setOnClickListener(this);
		newsLayout.setOnClickListener(this);
		settingLayout.setOnClickListener(this);
		toptitle.setOnLongClickListener(this);
	}

	@Override
	public void onArticleSelected(NewsDetailContent detailContent, String tag) {
		// 用户选中HeadlinesFragment中的头标题后
		// 做一些必要的业务操作

		articleFrag = (NewsDetailFragment) getFragmentManager()
				.findFragmentByTag(tag);

		if (articleFrag != null) {
			// 如果 article frag 不为空，那么我们在同时显示两个fragmnet的布局中...

			// 调用ArticleFragment中的方法去更新它的内容
			articleFrag.updateArticleView(detailContent);
		} else {
			// 否则，我们就是在仅包含一个fragment的布局中并需要交换fragment...

			newFragment = new NewsDetailFragment();
			Bundle args = new Bundle();
			args.putSerializable("news_detailContent", detailContent);
			newFragment.setArguments(args);

			FragmentTransaction transaction = getFragmentManager()
					.beginTransaction();

			// 用这个fragment替换任何在fragment_container中的东西
			// 并添加事务到back stack中以便用户可以回退到之前的状态
			transaction.replace(R.id.content, newFragment, tag);
			transaction.addToBackStack(null);

			// 提交事务
			transaction.commit();
		}
	}

	@Override
	public boolean onLongClick(View v) {

		switch (v.getId()) {
		case R.id.toptitle:
			Intent intent = new Intent(this, NearByActivity.class);
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
		itemPicture = new ResideMenuItem(this,
				R.drawable.abc_ic_menu_selectall_mtrl_alpha, "Picture");
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

	private ResideMenu.OnMenuListener menuListener = new ResideMenu.OnMenuListener() {
		@Override
		public void openMenu() {
			// Utils.ToastMessage(context, "Menu is opened!");
		}

		@Override
		public void closeMenu() {
			// Utils.ToastMessage(context, "Menu is closed!");
		}
	};

	private NewsDetailFragment articleFrag;

	private void changeFragment(Fragment targetFragment) {
		resideMenu.clearIgnoredViewList();
		getFragmentManager().beginTransaction()
				.replace(R.id.content, targetFragment, "fragment")
				.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
				.commit();
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (getFragmentManager().getBackStackEntryCount() <= 0) {
			super.onKeyDown(keyCode, event);
		} else {
			getFragmentManager().popBackStack();
		}
		return true;
	}
}
