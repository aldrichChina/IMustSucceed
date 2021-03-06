package net.micode.notes.activity;

import net.micode.notes.BaseActivity;
import net.micode.notes.R;
import net.micode.notes.activity.ResideMenuItem.BaiduMapActivity;
import net.micode.notes.activity.ResideMenuItem.BarCodeActivity;
import net.micode.notes.activity.ResideMenuItem.CameraActivity;
import net.micode.notes.activity.ResideMenuItem.CameraBeginActivity;
import net.micode.notes.activity.ResideMenuItem.PictureActivity;
import net.micode.notes.activity.ResideMenuItem.ProfileActivity;
import net.micode.notes.activity.ResideMenuItem.UtilActivity;
import net.micode.notes.activity.main.NearByActivity;
import net.micode.notes.entity.NewsDetailContent;
import net.micode.notes.entity.ResponseWrapper;
import net.micode.notes.entity.WeatherSubEntity;
import net.micode.notes.fragment.BitmapFragment;
import net.micode.notes.fragment.DbFragment;
import net.micode.notes.fragment.HotArticleFragment;
import net.micode.notes.fragment.NewsFragment;
import net.micode.notes.fragment.NewsFragment.OnHeadlineSelectedListener;
import net.micode.notes.fragment.RecyclerNewsFragment;
import net.micode.notes.fragment.UserFragment;
import net.micode.notes.interfacemanage.InterfaceManager.OpenX5WebFragment;
import net.micode.notes.util.Utils;
import net.micode.notes.view.ResideMenu.ResideMenu;
import net.micode.notes.view.ResideMenu.ResideMenuItem;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.umeng.update.UmengUpdateAgent;
import com.zhy.sample_okhttp.OkHttpActivity;

public class MainActivity extends BaseActivity implements OnClickListener, OnLongClickListener, OpenX5WebFragment {

    /**
     * 用于展示消息的Fragment
     */
    private RecyclerNewsFragment newsFragment;

    private DbFragment dbFragment;

    /**
     * 用于展示动态的Fragment
     */
    private BitmapFragment bitmapFragment;

    /**
     * 用于展示联系人的Fragment
     */
    private HotArticleFragment hotArticleFragment;
    /**
     * 用于展示设置的Fragment
     */
    private UserFragment settingFragment;

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
    private FragmentManager fragmentManager;
    private TextView toptitle;
    // 侧边栏Menu
    private ResideMenu resideMenu;
    private ResideMenuItem itemHome;
    private ResideMenuItem itemProfile;
    private ResideMenuItem itemOkHttp;
    private ResideMenuItem itemBaiduMap;
    private ResideMenuItem itemWeather;
    private ResideMenuItem itemPicture;
    private ResideMenuItem itemCamera;
    private ResideMenuItem itemCustomCamera;
    private ResideMenuItem itemScan;
    private ResideMenuItem itemUtil;
    // 侧边栏Activity
    private ProfileActivity profileActivity;
    private OkHttpActivity okHttpActivity;
    private BaiduMapActivity baiduMapActivity;
    private WeatherActivity weatherActivity;
    // private ProfileActivity settingsActivity;
    private PictureActivity pictureActivity;
    private CameraActivity cameraActivity;
    private CameraBeginActivity cameraBeginActivity;
    private BarCodeActivity barCodeActivity;
    private UtilActivity utilActivity;
    public static ResponseWrapper mResponseWrapper = new ResponseWrapper();// 数据结构的对象

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
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

        // new CameraActivity();
        if (v == itemHome) {
            changeActivity(MainActivity.this);
        } else if (v == itemProfile) {
            changeActivity(profileActivity);
        } else if (v == itemOkHttp) {
            changeActivity(okHttpActivity);
        } else if (v == itemBaiduMap) {
            changeActivity(baiduMapActivity);
        } else if (v == itemWeather) {
            changeActivity(weatherActivity);
        } else if (v == itemPicture) {
            changeActivity(pictureActivity);
        } else if (v == itemCamera) {
            changeActivity(cameraActivity);
        } else if (v == itemCustomCamera) {
            changeActivity(cameraBeginActivity);
        } else if (v == itemScan) {
            changeActivity(barCodeActivity);
        }else if(v==itemUtil){
            changeActivity(utilActivity);
        }
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
            if (newsFragment == null) {
                // 如果NewsFragment为空，则创建一个并添加到界面上
                newsFragment = new RecyclerNewsFragment(MainActivity.this);
                transaction.add(R.id.content, newsFragment);
            } else {
                // 如果NewsFragment不为空，则直接将它显示出来
                transaction.show(newsFragment);
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
            if (hotArticleFragment == null) {
                // 如果ContactsFragment为空，则创建一个并添加到界面上
                hotArticleFragment = new HotArticleFragment(MainActivity.this);
                transaction.add(R.id.content, hotArticleFragment);
            } else {
                // 如果ContactsFragment不为空，则直接将它显示出来
                transaction.show(hotArticleFragment);
            }
            break;

        case 4:
        default:
            // 当点击了设置tab时，改变控件的图片和文字颜色
            settingImage.setImageResource(R.drawable.ic_nav_5_active);
            settingText.setTextColor(Color.WHITE);
            if (settingFragment == null) {
                // 如果SettingFragment为空，则创建一个并添加到界面上
                settingFragment = new UserFragment(MainActivity.this);
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
        if (newsFragment != null) {
            transaction.hide(newsFragment);
        }
        if (dbFragment != null) {
            transaction.hide(dbFragment);
        }
        if (bitmapFragment != null) {
            transaction.hide(bitmapFragment);
        }
        if (hotArticleFragment != null) {
            transaction.hide(hotArticleFragment);
        }
        if (settingFragment != null) {
            transaction.hide(settingFragment);
        }
    }

    @Override
    protected void initEvents() {
        WeatherSubEntity weatherSubEntity = getWeatherEntity().getWeather_data().get(0);
        String weatherDate = weatherSubEntity.getDate();
        if (weatherDate.length() > 14) {
            String subs = weatherDate.substring(14, weatherDate.length() - 1);
            toptitle.setText(weatherSubEntity.getWeather() + subs);
        } else {
            toptitle.setText(weatherSubEntity.getWeather());
        }

        profileActivity = new ProfileActivity();
        okHttpActivity = new OkHttpActivity();
        baiduMapActivity = new BaiduMapActivity();
        // settingsActivity = new ProfileActivity();
        weatherActivity = new WeatherActivity();
        pictureActivity = new PictureActivity();
        cameraActivity = new CameraActivity();
        cameraBeginActivity = new CameraBeginActivity();
        barCodeActivity = new BarCodeActivity();
        utilActivity=new UtilActivity();
        setUpMenu();
        UmengUpdateAgent.update(this);

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
        // resideMenu.setMenuListener(menuListener);
        // valid scale factor is between 0.0f and 1.0f. leftmenu'width is
        // 150dip.
        resideMenu.setScaleValue(0.8f);

        // create menu items;
        itemHome = new ResideMenuItem(this, R.drawable.icon_home, "Home");
        itemProfile = new ResideMenuItem(this, R.drawable.icon_profile, "Profile");
        itemOkHttp = new ResideMenuItem(this, R.drawable.icon_profile, "OkHttpUtil");
        itemBaiduMap = new ResideMenuItem(this, R.drawable.icon_calendar, "地图导航");
        itemWeather = new ResideMenuItem(this, R.drawable.icon_settings, "天气");
        itemPicture = new ResideMenuItem(this, R.drawable.abc_ic_menu_selectall_mtrl_alpha, "Picture");
        itemCamera = new ResideMenuItem(this, R.drawable.nact_chose_camera, "系统相机");
        itemCustomCamera = new ResideMenuItem(this, R.drawable.nact_chose_camera, "自定义相机");
        itemScan = new ResideMenuItem(this, R.drawable.share_btn_logo_pyq, "二维码");
        itemUtil=new ResideMenuItem(this, R.drawable.share_btn_logo_pyq, "小工具");
        itemHome.setOnClickListener(this);
        itemProfile.setOnClickListener(this);
        itemOkHttp.setOnClickListener(this);
        itemBaiduMap.setOnClickListener(this);
        itemWeather.setOnClickListener(this);
        itemPicture.setOnClickListener(this);
        itemCamera.setOnClickListener(this);
        itemCustomCamera.setOnClickListener(this);
        itemScan.setOnClickListener(this);
        itemUtil.setOnClickListener(this);
        resideMenu.addMenuItem(itemHome, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemProfile, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemOkHttp, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemBaiduMap, ResideMenu.DIRECTION_RIGHT);
        resideMenu.addMenuItem(itemWeather, ResideMenu.DIRECTION_RIGHT);
        resideMenu.addMenuItem(itemPicture, ResideMenu.DIRECTION_RIGHT);
        resideMenu.addMenuItem(itemCamera, ResideMenu.DIRECTION_RIGHT);
        resideMenu.addMenuItem(itemCustomCamera, ResideMenu.DIRECTION_RIGHT);
        resideMenu.addMenuItem(itemScan, ResideMenu.DIRECTION_RIGHT);
        resideMenu.addMenuItem(itemUtil, ResideMenu.DIRECTION_RIGHT);
        // You can disable a direction by setting ->
        // resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);

        findViewById(R.id.topback).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
            }
        });
        findViewById(R.id.righttitle).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                resideMenu.openMenu(ResideMenu.DIRECTION_RIGHT);
            }
        });
    }

    private void changeActivity(Activity targetActivity) {
        resideMenu.clearIgnoredViewList();
        Utils.start_Activity(MainActivity.this, targetActivity.getClass());
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (getFragmentManager().getBackStackEntryCount() <= 0) {
            super.onKeyDown(keyCode, event);
        } else {
            getFragmentManager().popBackStack();
        }
        return true;
    }

    /*
     * (非 Javadoc) Description:
     * @see net.micode.notes.interfacemanage.InterfaceManager.OpenX5WebFragment#openX5Fragment(java.lang.String)
     */
    @Override
    public void openX5Fragment(String articalUrl) {
        Bundle bundle = new Bundle();
        bundle.putString("articalUrl", articalUrl);
        startActivity(X5WebViewActivity.class, bundle);

        // x5WebViewFragment = new X5WebViewFragment(articalUrl);
        // FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
        // hideFragments(beginTransaction);
        // beginTransaction.replace(R.id.content, x5WebViewFragment, "x5WebViewFragment");
        // beginTransaction.addToBackStack(null);
        // beginTransaction.commit();
    }

}
