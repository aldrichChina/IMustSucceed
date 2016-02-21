package net.micode.notes.fragment;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.micode.notes.R;
import net.micode.notes.data.Constant;
import net.micode.notes.data.MyDatabaseHelper;
import net.micode.notes.entities.Detailed;
import net.micode.notes.entities.HouseSaid;
import net.micode.notes.tool.JSONUtil;
import net.micode.notes.tool.Utils;
import net.micode.notes.tool.HttpUtils.HttpService;
import net.micode.notes.ui.activity.MainApplication;
import net.micode.notes.view.XListView;
import net.micode.notes.view.XListView.IXListViewListener;

import org.json.JSONArray;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lidroid.xutils.ViewUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class DbFragment extends BaseFragment implements IXListViewListener {
	private static final String STATE_POSITION = "STATE_POSITION";
	DisplayImageOptions options;
	ViewPager viewPager;
	private TextView desc_image;
	private LinearLayout point_group;
	MyDatabaseHelper dbHelper;
	SQLiteDatabase db;
	private XListView mListView;
	private List<HouseSaid> items = new ArrayList<HouseSaid>();
	private List<HouseSaid> tmpItems = new ArrayList<HouseSaid>();
	private Handler mHandler;
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private TextView tv_empty;
	Gson gson = new Gson();
	HouseSaidAdapter houseAdapter = new HouseSaidAdapter();
	HouseSaid houseSaid;
	private View listHead;
	private ClipboardManager cm;
	List<Detailed> imageUrls;
	protected int lastPosition;
	private List<View> views;
	// 是否开启自动循环
	private boolean isRunning;
	// 当前显示View的位置
	int pagerPosition = 0;
	/**
	 * 自动循环的实现策略：1、定时器timer 2、开启子线程，while true循环 3、使用handler方式发送延时消息，实现循环
	 */
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			// viewPager滑动到下一页
			// viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
			viewPager.setCurrentItem(viewPager.getCurrentItem() == (imageUrls
					.size() - 1) ? 0 : viewPager.getCurrentItem() + 1);
			Utils.Logger(getActivity(), "viewPager.getCurrentItem()="
					+ viewPager.getCurrentItem());
			// 发送一个延时消息,延时2秒钟继续执行handler，达到循环的效果
			if (isRunning) {
				handler.sendEmptyMessageDelayed(0, 10000);
			}
		};
	};

	/** Called when the activity is first created. */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.db_fragment, container, false);
		// 如果之前有保存用户数据
		if (savedInstanceState != null) {
			pagerPosition = savedInstanceState.getInt(STATE_POSITION);
		}
		initView(view);
		initData();
		return view;
	}

	public void initView(View view) {
		mListView = (XListView) view.findViewById(R.id.newmessagelist);
		tv_empty = (TextView) view.findViewById(R.id.tv_empty);
		listHead = LayoutInflater.from(getActivity()).inflate(R.layout.item_main_head, null);
		viewPager = (ViewPager) listHead.findViewById(R.id.pager);
		desc_image = (TextView) listHead.findViewById(R.id.tv_image_desc);
		point_group = (LinearLayout) listHead.findViewById(R.id.point_group);
	}

	public void initData() {

		
		cm = (ClipboardManager) getActivity().getSystemService(
				Context.CLIPBOARD_SERVICE);
		dbHelper = new MyDatabaseHelper(getActivity(), "lemon", 1);
		db = dbHelper.getReadableDatabase();

		
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					imageUrls = JSONUtil.analysisResponse(HttpService
							.OKHttpGet(Constant.HTTPURLMEINV, "num=50"));
					getActivity().runOnUiThread(new Runnable() {

						@Override
						public void run() {
							initSource(imageUrls, true);
						}
					});
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}).start();

		options = new DisplayImageOptions.Builder()
				.showImageForEmptyUri(R.drawable.ic_empty)
				.showImageOnFail(R.drawable.mvlg).resetViewBeforeLoading(true)
				.cacheOnDisc(true).imageScaleType(ImageScaleType.EXACTLY)
				.bitmapConfig(Bitmap.Config.RGB_565)
				.displayer(new FadeInBitmapDisplayer(300)).build();

		// 显示当前位置的view
		viewPager.setCurrentItem(pagerPosition);
		viewPager.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				v.getParent().requestDisallowInterceptTouchEvent(true);
				return false;
			}
		});

		items.clear();
		Cursor cursor = db.rawQuery(
				"select * from said order by inserttime desc", null);
		while (cursor.moveToNext()) {
			houseSaid = new HouseSaid();
			houseSaid.setTaici(cursor.getString(1));
			items.add(houseSaid);
		}

		// ViewUtils.inject(this, view);
		// geneItems();
		getHouseSaid();
		if (items.size() == 0) {
			tv_empty.setVisibility(View.VISIBLE);
		} else {
			tv_empty.setVisibility(View.GONE);
		}
		mListView.addHeaderView(listHead);
		mListView.setPullLoadEnable(true);
		mListView.setAdapter(houseAdapter);
		// mListView.setPullLoadEnable(false);
		// mListView.setPullRefreshEnable(false);
		mListView.setXListViewListener(this);
		mHandler = new Handler();
		mListView.setOnItemClickListener(new OnItemClickListener() {

			private String copyTaici;

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (position > 1) {
					copyTaici = items.get(position - 2).getTaici();
				}
				cm.setPrimaryClip(ClipData.newPlainText(copyTaici, copyTaici));
				Utils.Toast(getActivity(), "箴言复制成功");
			}
		});
	}

	/**
	 * 初始化资源。将资源Array封装成List<ImageView>集合，传递给ViewPager的适配器
	 * 
	 * @param advertiseArray
	 * @param fitXY
	 *            拉伸展开，适应屏幕的xy,否则水平居中
	 */
	private void initSource(final List<Detailed> imageUrls, boolean fitXY) {
		views = new ArrayList<View>();
		for (int i = 0; i < imageUrls.size(); i++) {
			if (fitXY) {
				views.add(View.inflate(getActivity(),
						R.layout.image_advertise_fit, null));
			} else {
				views.add(View.inflate(getActivity(),
						R.layout.image_advertise_center, null));
			}
			// 添加指示点
			ImageView point = new ImageView(getActivity());
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.WRAP_CONTENT,
					LinearLayout.LayoutParams.WRAP_CONTENT);

			params.rightMargin = 20;
			params.gravity = Gravity.CENTER;
			point.setLayoutParams(params);

			point.setBackgroundResource(R.drawable.point_bg);
			if (i == 0) {
				point.setEnabled(true);// 将小点点设置成灰色
			} else {
				point.setEnabled(false);
			}
			point_group.addView(point);

		}
		desc_image.setText(imageUrls.get(0).getDescription());
		// 准备好了views，之后设置ViewPager的适配器
		ImagePagerAdapter adapter = new ImagePagerAdapter(views, imageUrls);
		// 设置适配器，向viewpager容器中添加图片
		viewPager.setAdapter(adapter);
		// 让ViewPager左右滑动的时候无限循环
		// viewPager.setCurrentItem(Integer.MAX_VALUE / 2- (Integer.MAX_VALUE /
		// 2 % views.size()));
		Utils.Logger(getActivity(), "Integer.MAX_VALUE=" + Integer.MAX_VALUE);
		Utils.Logger(getActivity(), "Integer.MAX_VALUE=" + Integer.MIN_VALUE);
		Utils.Logger(getActivity(), "Integer.MAX_VALUE / 2="
				+ Integer.MAX_VALUE / 2);
		Utils.Logger(getActivity(), "Integer.MAX_VALUE / 2 % views.size()="
				+ Integer.MAX_VALUE / 2 % views.size());
		// 开启自动循环
		isRunning = true;
		// 发送延时消息，达到轮播广告的效果
		handler.sendEmptyMessageDelayed(0, 15000);
		// 设置viewpager滑动时候的监听，设置图片的描述和小点点的切换
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			/**
			 * 页面切换后调用
			 */
			@Override
			public void onPageSelected(int position) {
				position = position % imageUrls.size();
				// 设置图片的文字描述信息
				String desc = imageUrls.get(position).getTitle();
				// String desc = result.substring(result.lastIndexOf(":") + 1,
				// result.length());
				desc_image.setText(desc);
				// 改变指示点的状态
				// 当前位置的点设为灰色，true
				point_group.getChildAt(position).setEnabled(true);

				// 上一个位置的点设为透明的 false
				point_group.getChildAt(lastPosition).setEnabled(false);
				lastPosition = position;
			}

			/**
			 * 页面正在滑动的时候的回调
			 */
			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {

			}

			/**
			 * 页面状态发生改变的时候回调
			 */
			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putInt(STATE_POSITION, viewPager.getCurrentItem());
	}

	private void onLoad() {
		mListView.stopRefresh();
		mListView.stopLoadMore();
		mListView.setRefreshTime(df.format(new Date()));
	}

	@Override
	public void onRefresh() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				// items.clear();
				// geneItems();
				getHouseSaid();
				// mAdapter.notifyDataSetChanged();
				if (items.size() == 0) {
					tv_empty.setVisibility(View.VISIBLE);
				} else {
					tv_empty.setVisibility(View.GONE);
				}
				mListView.setAdapter(houseAdapter);
				onLoad();
			}
		}, 2000);
	}

	public void getHouseSaid() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				String responseBody = HttpService.OKHttpGet(Constant.httpUrl,
						Constant.httpArg);
				Utils.Logger(getActivity(), "responseBody=" + responseBody);
				HouseSaid houseSaid = gson.fromJson(responseBody,
						HouseSaid.class);
				if (houseSaid == null)
					return;
				db.execSQL(
						"insert into said values(null,?,?)",
						new String[] { houseSaid.getTaici(),
								Long.toString(new Date().getTime()) });
				Cursor cursor = db.rawQuery(
						"select * from said order by inserttime desc", null);
				while (cursor.moveToNext()) {
					houseSaid = new HouseSaid();
					houseSaid.setTaici(cursor.getString(1));
					tmpItems.add(houseSaid);
				}
			}
		}).start();
		if (tmpItems.size() != 0) {
			items.clear();
		}
		items.addAll(tmpItems);
		tmpItems.clear();
	}

	@Override
	public void onLoadMore() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				// geneItems();
				getHouseSaid();

				houseAdapter.notifyDataSetChanged();
				onLoad();
			}
		}, 2000);
	}

	private class ViewHolder {
		private TextView taici;
	}

	public class HouseSaidAdapter extends BaseAdapter {

		private String proverbs;

		@Override
		public int getCount() {
			return items.size();
		}

		@Override
		public Object getItem(int position) {
			return items.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder myholder;
			if (convertView == null) {
				convertView = LayoutInflater.from(getActivity()).inflate(
						R.layout.adapter_item_proverbs, null);
				myholder = new ViewHolder();
				myholder.taici = (TextView) convertView
						.findViewById(R.id.adapter_item_proverbs_tv);
				convertView.setTag(myholder);
				Log.v("tag", "null getView " + position + " " + convertView);
			} else {
				myholder = (ViewHolder) convertView.getTag();
				Log.v("tag", "getView " + position + " " + convertView);
			}
			proverbs = items.get(position).getTaici();
			myholder.taici.setText(proverbs);

			return convertView;
		}

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (db != null) {
			db.close();
		}
	}

	private class ImagePagerAdapter extends PagerAdapter {
		private List<Detailed> images;
		private LayoutInflater inflater;

		ImagePagerAdapter(List<View> views, List<Detailed> images) {
			this.images = images;
			inflater = LayoutInflater.from(getActivity());
		}

		// 获取要滑动的控件的数量，在这里我们以滑动的广告栏为例，那么这里就应该是展示的广告图片的ImageView数量
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return images.size();
		}

		// 来判断显示的是否是同一张图片，这里我们将两个参数相比较返回即可
		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0.equals(arg1);
		}

		// PagerAdapter只缓存三张要显示的图片，如果滑动的图片超出了缓存的范围，就会调用这个方法，将图片销毁
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			((ViewPager) container).removeView(views.get(position
					% views.size()));
		}

		@Override
		public void finishUpdate(ViewGroup container) {
			// TODO Auto-generated method stub
			super.finishUpdate(container);
		}

		// 当要显示的图片可以进行缓存的时候，会调用这个方法进行显示图片的初始化，我们将要显示的ImageView加入到ViewGroup中，然后作为返回值返回即可
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			((ViewPager) container).addView(views.get(position % views.size()));
			View view = views.get(position % views.size());
			ImageView imageView = (ImageView) view
					.findViewById(R.id.ivAdvertise);
			final ProgressBar spinner = (ProgressBar) view
					.findViewById(R.id.loading);

			MainApplication.imageLoader.displayImage(images.get(position)
					.getPicUrl(), imageView, options,
					new SimpleImageLoadingListener() {
						@Override
						public void onLoadingStarted(String imageUri, View view) {
							spinner.setVisibility(view.VISIBLE);
						}

						@Override
						public void onLoadingFailed(String imageUri, View view,
								FailReason failReason) {
							String message = null;
							switch (failReason.getType()) {// 获取图片失败类型
							case IO_ERROR:// 文件I/O错误
								message = "Input/Output error文件I/O错误";
								break;
							case DECODING_ERROR:// 解码错误
								message = "Image can't be decoded解码错误";
								break;
							case NETWORK_DENIED:// 网络延迟
								message = "Downloads are denied网络延迟";
								break;
							case OUT_OF_MEMORY:// 内存不足
								message = "Out Of Memory error内存不足";
								break;
							case UNKNOWN:// 原因不明
								message = "Unknown error原因不明";
								break;
							}
							Utils.Toast(getActivity(), message);
							spinner.setVisibility(View.GONE);
						}

						@Override
						public void onLoadingComplete(String imageUri,
								View view, Bitmap loadedImage) {
							spinner.setVisibility(View.GONE);// 不显示圆形进度条;
						}
					});
			return view;
		}

		@Override
		public void restoreState(Parcelable state, ClassLoader loader) {
			// TODO Auto-generated method stub
			super.restoreState(state, loader);
		}

		@Override
		public Parcelable saveState() {
			// TODO Auto-generated method stub
			return super.saveState();
		}

		@Override
		public void startUpdate(ViewGroup container) {
			// TODO Auto-generated method stub
			super.startUpdate(container);
		}
	}
}
