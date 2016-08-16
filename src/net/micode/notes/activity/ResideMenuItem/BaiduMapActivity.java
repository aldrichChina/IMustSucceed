package net.micode.notes.activity.ResideMenuItem;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import net.micode.notes.R;
import net.micode.notes.entity.BaiduMapInfo;
import net.micode.notes.util.OrientationListener;
import net.micode.notes.util.OrientationListener.OnOrientationListener;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapClickListener;
import com.baidu.mapapi.map.BaiduMap.OnMapLongClickListener;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.InfoWindow.OnInfoWindowClickListener;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.navisdk.adapter.BNOuterTTSPlayerCallback;
import com.baidu.navisdk.adapter.BNRoutePlanNode;
import com.baidu.navisdk.adapter.BNRoutePlanNode.CoordinateType;
import com.baidu.navisdk.adapter.BNaviSettingManager;
import com.baidu.navisdk.adapter.BaiduNaviManager;
import com.baidu.navisdk.adapter.BaiduNaviManager.NaviInitListener;
import com.baidu.navisdk.adapter.BaiduNaviManager.RoutePlanListener;

public class BaiduMapActivity extends Activity implements OnClickListener {
	private MapView mMapView;
	private BaiduMap mBaiduMap;

	private Context context;

	// 定位相关
	private LocationClient mLocationClient;
	private MyLocationListener mLocationListener;
	private boolean isFirstIn = true;
	private double mLatitude;
	private double mLongtitude;
	private LatLng mLastLocationData;
	private LatLng mDestLocationData;

	// 自定义定位图标
	private BitmapDescriptor mIconLocation;
	private OrientationListener orientationListener;
	private float mCurrentX;
	private LocationMode mLocationMode;

	// 覆盖物相关
	private BitmapDescriptor mMarker;
	private RelativeLayout mMarkerLy;
	private Button mBtnMockNav;
	private Button mBtnRealNav;
	// 导航相关
	public static List<Activity> activityList = new LinkedList<Activity>();
	private static final String APP_FOLDER_NAME = "BaiduSdkDemoLocation";
	public static final String ROUTE_PLAN_NODE = "routePlanNode";
	private String mSDCardPath = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 在使用SDK各组件之前初始化context信息，传入ApplicationContext
		// 注意该方法要再setContentView方法之前实现
		// getWindow().setFlags(WindowManager.LayoutParams.FLAG_NEEDS_MENU_KEY,WindowManager.LayoutParams.FLAG_NEEDS_MENU_KEY);
		try {
			getWindow().addFlags(WindowManager.LayoutParams.class.getField("FLAG_NEEDS_MENU_KEY").getInt(null));
		} catch (NoSuchFieldException e) {
			// Ignore since this field won't exist in most versions of Android
		} catch (IllegalAccessException e) {
			Log.w("feelyou.info", "Could not access FLAG_NEEDS_MENU_KEY in addLegacyOverflowButton()", e);
		}
		SDKInitializer.initialize(getApplicationContext());
		activityList.add(this);
		setContentView(R.layout.activity_baidu_map);

		this.context = this;

		initView();
		// 初始化定位
		initLocation();
		initMarker();
		mBtnMyLocation = (Button) findViewById(R.id.id_btn_location);
		mBtnMockNav = (Button) findViewById(R.id.id_btn_mocknav);
		mBtnRealNav = (Button) findViewById(R.id.id_btn_realnav);
		mBtnMyLocation.setOnClickListener(this);
		mBtnMockNav.setOnClickListener(this);
		mBtnRealNav.setOnClickListener(this);
		mBaiduMap.setOnMarkerClickListener(new OnMarkerClickListener() {
			@Override
			public boolean onMarkerClick(Marker marker) {
				Bundle extraInfo = marker.getExtraInfo();
				BaiduMapInfo baiduMapInfo = (BaiduMapInfo) extraInfo.getSerializable("info");
				ImageView iv = (ImageView) mMarkerLy.findViewById(R.id.id_info_img);
				TextView distance = (TextView) mMarkerLy.findViewById(R.id.id_info_distance);
				TextView name = (TextView) mMarkerLy.findViewById(R.id.id_info_name);
				TextView zan = (TextView) mMarkerLy.findViewById(R.id.id_info_zan);
				iv.setImageResource(baiduMapInfo.getImgId());
				distance.setText(baiduMapInfo.getDistance());
				name.setText(baiduMapInfo.getName());
				zan.setText(baiduMapInfo.getZan() + "");

				InfoWindow infoWindow;
				TextView tv = new TextView(context);
				tv.setBackgroundResource(R.drawable.location_tips);
				tv.setPadding(30, 20, 30, 50);
				tv.setText(baiduMapInfo.getName());
				tv.setTextColor(Color.parseColor("#ffffff"));

				final LatLng latLng = marker.getPosition();
				Point p = mBaiduMap.getProjection().toScreenLocation(latLng);
				p.y -= 47;
				LatLng ll = mBaiduMap.getProjection().fromScreenLocation(p);

				infoWindow = new InfoWindow(tv, ll, new OnInfoWindowClickListener() {
					@Override
					public void onInfoWindowClick() {
						mBaiduMap.hideInfoWindow();
					}
				});
				mBaiduMap.showInfoWindow(infoWindow);
				mMarkerLy.setVisibility(View.VISIBLE);
				return true;
			}
		});
		mBaiduMap.setOnMapClickListener(new OnMapClickListener() {

			@Override
			public boolean onMapPoiClick(MapPoi arg0) {
				return false;
			}

			@Override
			public void onMapClick(LatLng arg0) {
				mMarkerLy.setVisibility(View.GONE);
				mBaiduMap.hideInfoWindow();
			}
		});
		mBaiduMap.setOnMapLongClickListener(new OnMapLongClickListener() {

			@Override
			public void onMapLongClick(LatLng latLng) {
				Toast.makeText(context, "设置目的地成功", Toast.LENGTH_SHORT).show();
				mDestLocationData = latLng;
				addDestInfoOverlay(latLng);
			}

		});
		// 初始化导航相关
		if (initDirs()) {
			initNavi();
		}
	}

	private boolean initDirs() {
		mSDCardPath = getSdcardDir();
		if (mSDCardPath == null) {
			return false;
		}
		File f = new File(mSDCardPath, APP_FOLDER_NAME);
		if (!f.exists()) {
			try {
				f.mkdir();
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}

	String authinfo = null;

	/**
	 * 内部TTS播报状态回传handler
	 */
	private Handler ttsHandler = new Handler() {
		public void handleMessage(Message msg) {
			int type = msg.what;
			switch (type) {
			case BaiduNaviManager.TTSPlayMsgType.PLAY_START_MSG: {
				showToastMsg("Handler : TTS play start");
				break;
			}
			case BaiduNaviManager.TTSPlayMsgType.PLAY_END_MSG: {
				showToastMsg("Handler : TTS play end");
				break;
			}
			default:
				break;
			}
		}
	};

	/**
	 * 内部TTS播报状态回调接口
	 */
	private BaiduNaviManager.TTSPlayStateListener ttsPlayStateListener = new BaiduNaviManager.TTSPlayStateListener() {

		@Override
		public void playEnd() {
			// showToastMsg("TTSPlayStateListener : TTS play end");
		}

		@Override
		public void playStart() {
			// showToastMsg("TTSPlayStateListener : TTS play start");
		}
	};
    private Button mBtnMyLocation;

	public void showToastMsg(final String msg) {
		BaiduMapActivity.this.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				Toast.makeText(BaiduMapActivity.this, msg, Toast.LENGTH_SHORT).show();
			}
		});
	}

	private void initNavi() {

		BNOuterTTSPlayerCallback ttsCallback = null;

		BaiduNaviManager.getInstance().init(this, mSDCardPath, APP_FOLDER_NAME, new NaviInitListener() {
			@Override
			public void onAuthResult(int status, String msg) {
				if (0 == status) {
					authinfo = "key校验成功!";
				} else {
					authinfo = "key校验失败, " + msg;
				}
				BaiduMapActivity.this.runOnUiThread(new Runnable() {

					@Override
					public void run() {
						Toast.makeText(BaiduMapActivity.this, authinfo, Toast.LENGTH_LONG).show();
					}
				});
			}

			public void initSuccess() {
				Toast.makeText(BaiduMapActivity.this, "百度导航引擎初始化成功", Toast.LENGTH_SHORT).show();
				initSetting();
			}

			public void initStart() {
				Toast.makeText(BaiduMapActivity.this, "百度导航引擎初始化开始", Toast.LENGTH_SHORT).show();
			}

			public void initFailed() {
				Toast.makeText(BaiduMapActivity.this, "百度导航引擎初始化失败", Toast.LENGTH_SHORT).show();
			}

		}, null, ttsHandler, null);

	}

	private String getSdcardDir() {
		if (Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED)) {
			return Environment.getExternalStorageDirectory().toString();
		}
		return null;
	}

	private void addDestInfoOverlay(LatLng latLng) {
		mBaiduMap.clear();
		OverlayOptions options = new MarkerOptions().position(latLng)//
				.icon(BitmapDescriptorFactory.fromResource(R.drawable.myloc))//
				.zIndex(5);
		mBaiduMap.addOverlay(options);
	}

	private void initMarker() {
		mMarker = BitmapDescriptorFactory.fromResource(R.drawable.maker);
		mMarkerLy = (RelativeLayout) findViewById(R.id.id_maker_ly);
	}

	private void initLocation() {

		mLocationMode = LocationMode.NORMAL;
		mLocationClient = new LocationClient(this);
		mLocationListener = new MyLocationListener();
		mLocationClient.registerLocationListener(mLocationListener);

		LocationClientOption option = new LocationClientOption();
		option.setCoorType("bd09ll");
		option.setIsNeedAddress(true);
		option.setOpenGps(true);
		option.setScanSpan(1000);
		mLocationClient.setLocOption(option);
		// 初始化图标
		mIconLocation = BitmapDescriptorFactory.fromResource(R.drawable.navi_map_gps_locked);
		orientationListener = new OrientationListener(context);

		orientationListener.setOnOrientationListener(new OnOrientationListener() {
			@Override
			public void onOrientationChanged(float x) {
				mCurrentX = x;
			}
		});

	}

	private void initView() {
		mMapView = (MapView) findViewById(R.id.id_bmapView);
		mBaiduMap = mMapView.getMap();
		MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(45.0f);
		mBaiduMap.setMapStatus(msu);
	}

	@Override
	protected void onResume() {
		super.onResume();
		// 在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
		mMapView.onResume();
	}

	@Override
	protected void onStart() {
		super.onStart();
		// 开启定位
		mBaiduMap.setMyLocationEnabled(true);
		if (!mLocationClient.isStarted())
			mLocationClient.start();
		// 开启方向传感器
		orientationListener.start();
	}

	@Override
	protected void onPause() {
		super.onPause();
		// 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
		mMapView.onPause();
	}

	@Override
	protected void onStop() {
		super.onStop();

		// 停止定位
		mBaiduMap.setMyLocationEnabled(false);
		mLocationClient.stop();
		// 停止方向传感器
		orientationListener.stop();

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
		mMapView.onDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.baidu_map_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.id_map_common:
			mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
			break;

		case R.id.id_map_site:
			mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
			break;

		case R.id.id_map_traffic:
			if (mBaiduMap.isTrafficEnabled()) {
				mBaiduMap.setTrafficEnabled(false);
				item.setTitle("实时交通(off)");
			} else {
				mBaiduMap.setTrafficEnabled(true);
				item.setTitle("实时交通(on)");
			}
			break;
		case R.id.id_map_location:
			centerToMyLocation();
			break;
		case R.id.id_map_mode_common:
			mLocationMode = LocationMode.NORMAL;
			break;
		case R.id.id_map_mode_following:
			mLocationMode = LocationMode.FOLLOWING;
			break;
		case R.id.id_map_mode_compass:
			mLocationMode = LocationMode.COMPASS;
			break;
		case R.id.id_add_overlay:
			addOverlays(BaiduMapInfo.baiduMapInfos);
			break;
		default:
			break;
		}

		return super.onOptionsItemSelected(item);
	}

	/**
	 * 添加覆盖物
	 * 
	 * @param baiduMapInfos
	 */
	private void addOverlays(List<BaiduMapInfo> baiduMapInfos) {
		mBaiduMap.clear();
		LatLng latLng = null;
		Marker marker = null;
		OverlayOptions options;
		for (BaiduMapInfo baiduMapInfo : baiduMapInfos) {
			// 经纬度
			latLng = new LatLng(baiduMapInfo.getLatitude(), baiduMapInfo.getLongitude());
			// 图标
			options = new MarkerOptions().position(latLng).icon(mMarker).zIndex(5);
			marker = (Marker) mBaiduMap.addOverlay(options);
			Bundle arg0 = new Bundle();
			arg0.putSerializable("info", baiduMapInfo);
			marker.setExtraInfo(arg0);
		}

		MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLng);
		mBaiduMap.setMapStatus(msu);

	}

	/**
	 * 定位到我的位置
	 */
	private void centerToMyLocation() {
		mLastLocationData = new LatLng(mLatitude, mLongtitude);
		MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(mLastLocationData);
		mBaiduMap.animateMapStatus(msu);
	}

	private class MyLocationListener implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {

			MyLocationData data = new MyLocationData.Builder()//
					.direction(mCurrentX)//
					.accuracy(location.getRadius())//
					.latitude(location.getLatitude())//
					.longitude(location.getLongitude())//
					.build();
			mBaiduMap.setMyLocationData(data);
			// 设置自定义图标
			MyLocationConfiguration config = new MyLocationConfiguration(mLocationMode, true, mIconLocation);
			mBaiduMap.setMyLocationConfigeration(config);

			// 更新经纬度
			mLatitude = location.getLatitude();
			mLongtitude = location.getLongitude();

			if (isFirstIn) {
				LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
				MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLng);
				mBaiduMap.animateMapStatus(msu);
				isFirstIn = false;

				Toast.makeText(context, location.getAddrStr(), Toast.LENGTH_SHORT).show();
			}

		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.id_btn_location:
		    centerToMyLocation();
		    break;
		case R.id.id_btn_mocknav:
			openNavigation(false);
			break;
		case R.id.id_btn_realnav:
			openNavigation(true);
			break;
		default:
			break;
		}
	}

	private void openNavigation(boolean isGPSNav) {
		if (mDestLocationData == null) {
			Toast.makeText(context, "长按地图设置目标地点", Toast.LENGTH_SHORT).show();
			return;
		}
		routeplanToNavi(isGPSNav);
	}

	private void routeplanToNavi(boolean mock) {
		CoordinateType coType = CoordinateType.BD09LL;
		BNRoutePlanNode sNode = null;
		BNRoutePlanNode eNode = null;

		// sNode = new BNRoutePlanNode(116.30142, 40.05087, "百度大厦", null,
		// coType);
		// eNode = new BNRoutePlanNode(116.39750, 39.90882, "北京天安门", null,
		// coType);
		centerToMyLocation();
		sNode = new BNRoutePlanNode(mLastLocationData.longitude, mLastLocationData.latitude, "我的位置", null, coType);
		eNode = new BNRoutePlanNode(mDestLocationData.longitude, mDestLocationData.latitude, "目标地点", null, coType);
		if (sNode != null && eNode != null) {
			List<BNRoutePlanNode> list = new ArrayList<BNRoutePlanNode>();
			list.add(sNode);
			list.add(eNode);
			BaiduNaviManager.getInstance().launchNavigator(this, list, 1, mock, new DemoRoutePlanListener(sNode));
		}
	}

	public class DemoRoutePlanListener implements RoutePlanListener {

		private BNRoutePlanNode mBNRoutePlanNode = null;

		public DemoRoutePlanListener(BNRoutePlanNode node) {
			mBNRoutePlanNode = node;
		}

		@Override
		public void onJumpToNavigator() {
			/*
			 * 设置途径点以及resetEndNode会回调该接口
			 */

			for (Activity ac : activityList) {

				if (ac.getClass().getName().endsWith("BNDemoGuideActivity")) {

					return;
				}
			}
			Intent intent = new Intent(BaiduMapActivity.this, BNDemoGuideActivity.class);
			Bundle bundle = new Bundle();
			bundle.putSerializable(ROUTE_PLAN_NODE, (BNRoutePlanNode) mBNRoutePlanNode);
			intent.putExtras(bundle);
			startActivity(intent);

		}

		@Override
		public void onRoutePlanFailed() {
			// TODO Auto-generated method stub
			Toast.makeText(BaiduMapActivity.this, "算路失败", Toast.LENGTH_SHORT).show();
			Log.d("jia", "onRoutePlanFailed");
		}
	}

	private void initSetting() {
		BNaviSettingManager.setDayNightMode(BNaviSettingManager.DayNightMode.DAY_NIGHT_MODE_DAY);
		BNaviSettingManager
				.setShowTotalRoadConditionBar(BNaviSettingManager.PreViewRoadCondition.ROAD_CONDITION_BAR_SHOW_ON);
		BNaviSettingManager.setVoiceMode(BNaviSettingManager.VoiceMode.Veteran);
		BNaviSettingManager.setPowerSaveMode(BNaviSettingManager.PowerSaveMode.DISABLE_MODE);
		BNaviSettingManager.setRealRoadCondition(BNaviSettingManager.RealRoadCondition.NAVI_ITS_ON);
	}
}
