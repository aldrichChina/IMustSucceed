package net.micode.notes.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.micode.notes.BaseFragment;
import net.micode.notes.MyApplication;
import net.micode.notes.R;
import net.micode.notes.activity.ImageActivity;
import net.micode.notes.util.Utils;
import okhttp3.Call;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.squareup.picasso.Picasso;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
public class BitmapFragment extends BaseFragment {
    private ListView imageListView;
    private ImageListAdapter imageListAdapter;
//	public static BitmapUtils bitmapUtils;
    private HashMap<String, Integer> temp = new HashMap<String, Integer>();
    private View view;
    private MyApplication myApplication; 
    private float mProgress;
    private String[] imgSites = {
    		"http://www.bing.com/gallery/",
    		"http://22mm.xiuna.com/mm/qingliang/",
    		"http://cn.bing.com/images/search?q=超高清美女电脑壁纸&qs=IM&form=QBIR&pq=超高清&sc=8-3&sp=5&sk=IM4",
            "http://image.baidu.com/",
            "http://www.22mm.cc/",
            "http://www.moko.cc/",
            "http://eladies.sina.com.cn/photo/",
            "http://www.youzi4.com/"
    };

    

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.bitmap_fragment, container, false);
        
        //bitmapUtils.configMemoryCacheEnabled(false);
        //bitmapUtils.configDiskCacheEnabled(false);

        //bitmapUtils.configDefaultAutoRotation(true);

        //ScaleAnimation animation = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f,
        //        Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        //animation.setDuration(800);

        // AlphaAnimation 在一些android系统上表现不正常, 造成图片列表中加载部分图片后剩余无法加载, 目前原因不明.
        // 可以模仿下面示例里的fadeInDisplay方法实现一个颜色渐变动画。
        //AlphaAnimation animation = new AlphaAnimation(0.0f, 1.0f);
        //animation.setDuration(1000);
        //bitmapUtils.configDefaultImageLoadAnimation(animation);

        // 设置最大宽高, 不设置时更具控件属性自适应.
//        bitmapUtils.configDefaultBitmapMaxSize(BitmapCommonUtils.getScreenSize(getActivity()).scaleDown(3));

        // 滑动时加载图片，快速滑动时不加载图片
        //imageListView.setOnScrollListener(new PauseOnScrollListener(bitmapUtils, false, true));

        
        // 加载url请求返回的图片连接给listview
        // 这里只是简单的示例，并非最佳实践，图片较多时，最好上拉加载更多...
        for (String url : imgSites) {
            loadImgList(url);
        }

        /*for (int i = 0; i < 162; i++) {
            imageListAdapter.addSrc("/sdcard/pic/" + i);
        }
        imageListAdapter.notifyDataSetChanged();//通知listview更新数据*/
super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    private void loadImgList(String url) {
        OkHttpUtils.get().url(url).id(100).build().execute(new StringCallback() {
            
            @Override
            public void onResponse(String response, int id) {

                imageListAdapter.addSrc(getImgSrcList(response));
                imageListAdapter.notifyDataSetChanged();//通知listview更新数据
                            
            }
            
            @Override
            public void onError(Call call, Exception e, int id) {
                e.printStackTrace();
                Log.d("jia", "e=="+e);
            }
            /* (非 Javadoc)
             * Description:
             * @see com.zhy.http.okhttp.callback.Callback#inProgress(float, long, int)
             */
            @Override
            public void inProgress(float progress, long total, int id) {
                Utils.Log("progress=="+progress);
                mProgress=progress;
            }
        });
//        new HttpUtils().send(HttpRequest.HttpMethod.GET, url,
//                new RequestCallBack<String>() {
//                    @Override
//                    public void onSuccess(ResponseInfo<String> responseInfo) {
//                        imageListAdapter.addSrc(getImgSrcList(responseInfo.result));
//                        imageListAdapter.notifyDataSetChanged();//通知listview更新数据
//                    }
//
//                    @Override
//                    public void onFailure(HttpException error, String msg) {
//                    }
//                });
    }

    

   

    private class ImageListAdapter extends BaseAdapter {

        private Context mContext;
        private final LayoutInflater mInflater;
        private ArrayList<String> imgSrcList;
       
        public ImageListAdapter(Context context) {
            super();
            this.mContext = context;
            mInflater = LayoutInflater.from(context);
            imgSrcList = new ArrayList<String>();
        }

        public void addSrc(List<String> imgSrcList) {
            this.imgSrcList.addAll(imgSrcList);
        }

        public void addSrc(String imgUrl) {
            this.imgSrcList.add(imgUrl);
        }

        @Override
        public int getCount() {
            return imgSrcList.size();
        }

        @Override
        public Object getItem(int position) {
            return imgSrcList.get(position);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int position, View view, ViewGroup parent) {
            ImageItemHolder holder = null;
            if (view == null) {
                holder = new ImageItemHolder();
                view = mInflater.inflate(R.layout.bitmap_item, null);
                holder. imgItem = (ImageView) view.findViewById(R.id.img_item);
                holder. imgPb = (ProgressBar) view.findViewById(R.id.img_pb);
                view.setTag(holder);
            } else {
                holder = (ImageItemHolder) view.getTag();
            }
            holder.imgPb.setProgress(0);
//            bitmapUtils.display(holder.imgItem, imgSrcList.get(position), new CustomBitmapLoadCallBack(holder));
            
            myApplication.mPicasso.load(imgSrcList.get(position)).into(holder.imgItem);
            holder.imgPb.setProgress((int) (mProgress));
            holder.imgPb.setProgress(100);
            //bitmapUtils.display((ImageView) view, imgSrcList.get(position), displayConfig);
            //bitmapUtils.display((ImageView) view, imgSrcList.get(position));
            return view;
        }
    }

    private class ImageItemHolder {
        private ImageView imgItem;

        private ProgressBar imgPb;
    }

//    public class CustomBitmapLoadCallBack extends DefaultBitmapLoadCallBack<ImageView> {
//        private final ImageItemHolder holder;
//
//        public CustomBitmapLoadCallBack(ImageItemHolder holder) {
//            this.holder = holder;
//        }
//
//        @Override
//        public void onLoading(ImageView container, String uri, BitmapDisplayConfig config, long total, long current) {
//            this.holder.imgPb.setProgress((int) (current * 100 / total));
//        }
//
//        @Override
//        public void onLoadCompleted(ImageView container, String uri, Bitmap bitmap, BitmapDisplayConfig config, BitmapLoadFrom from) {
//            //super.onLoadCompleted(container, uri, bitmap, config, from);
//            fadeInDisplay(container, bitmap);
//            this.holder.imgPb.setProgress(100);
//        }
//    }

    private static final ColorDrawable TRANSPARENT_DRAWABLE = new ColorDrawable(android.R.color.transparent);
   

   

    private void fadeInDisplay(ImageView imageView, Bitmap bitmap) {
        final TransitionDrawable transitionDrawable =
                new TransitionDrawable(new Drawable[]{
                        TRANSPARENT_DRAWABLE,
                        new BitmapDrawable(imageView.getResources(), bitmap)
                });
        imageView.setImageDrawable(transitionDrawable);
        transitionDrawable.startTransition(500);
    }

    /**
     * 得到网页中图片的地址
     */
    public static List<String> getImgSrcList(String htmlStr) {
        List<String> pics = new ArrayList<String>();

        String regEx_img = "<img.*?src=\"http://(.*?).jpg\""; // 图片链接地址
        Pattern p_image = Pattern.compile(regEx_img, Pattern.CASE_INSENSITIVE);
        Matcher m_image = p_image.matcher(htmlStr);
        while (m_image.find()) {
            String src = m_image.group(1);
            if (src.length() < 100) {
                pics.add("http://" + src + ".jpg");
            }
        }
        return pics;
    }

	@Override
	protected void initViews() {
	    
	    imageListView = (ListView) view.findViewById(R.id.img_list);
	    imageListAdapter = new ImageListAdapter(getActivity());
        imageListView.setAdapter(imageListAdapter);
	    imageListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ImageActivity.class);
                intent.putExtra("url", imageListAdapter.getItem(position).toString());
                getActivity().startActivity(intent);
                            
            }
        });
	}

	@Override
	protected void initEvents() {
	    myApplication=MyApplication.getInstance();       
	}


}
