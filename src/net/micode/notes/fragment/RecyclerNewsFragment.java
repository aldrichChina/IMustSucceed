/******************************************************************
 *    Package:     net.micode.notes.fragment
 *
 *    Filename:    RecyclerNewsFragment.java
 *
 *    @version:    1.0.0
 *
 *    Create at:   2016年8月12日 下午1:49:25
 *
 *    Revision:
 *
 *    2016年8月12日 下午1:49:25
 *        - second revision
 *
 *****************************************************************/
package net.micode.notes.fragment;

import java.util.ArrayList;
import java.util.List;

import net.micode.notes.BaseFragment;
import net.micode.notes.MyApplication;
import net.micode.notes.R;
import net.micode.notes.adapter.PtrrvBaseAdapter;
import net.micode.notes.asynctask.ContentAsyncTask;
import net.micode.notes.asynctask.ContentAsyncTask.ContentCallback;
import net.micode.notes.db.DatabaseService;
import net.micode.notes.entities.NewsDetailContent;
import net.micode.notes.entities.NewsImageUrls;
import net.micode.notes.interfacemanage.InterfaceManager;
import net.micode.notes.interfacemanage.InterfaceManager.RecyClerViewClick;
import net.micode.notes.util.Utils;
import net.micode.notes.view.PullToRefreshRecyclerView.PullToRefreshRecyclerView;
import net.micode.notes.view.PullToRefreshRecyclerView.footer.loadmore.BaseLoadMoreView;
import net.micode.notes.view.widget.DemoLoadMoreView;
import net.micode.notes.view.widget.DividerItemDecoration;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @ClassName RecyclerNewsFragment
 * @Description TODO(这里用一句话描述这个类的作用)
 * @Date 2016年8月12日 下午1:49:25
 * @version 1.0.0
 */
public class RecyclerNewsFragment extends BaseFragment implements RecyClerViewClick{

    private PullToRefreshRecyclerView mRecyclerView;
    private PtrrvAdapter mAdapter;
    private static final int DEFAULT_ITEM_SIZE = 20;
    private static final int ITEM_SIZE_OFFSET = 5;

    private static final int MSG_CODE_REFRESH = 0;
    private static final int MSG_CODE_LOADMORE = 1;

    private static final int TIME = 1000;
    private int pagenum = 1;
    private List<NewsDetailContent> newsList = new ArrayList<NewsDetailContent>();
    private DatabaseService databaseService;
    private InterfaceManager.OpenX5WebFragment openX5FragmentIMPL;
    public RecyclerNewsFragment(InterfaceManager.OpenX5WebFragment openX5FragmentIMPL){
        this.openX5FragmentIMPL=openX5FragmentIMPL;
    }
    /*
     * (非 Javadoc) Description:
     * @see net.micode.notes.BaseFragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup,
     * android.os.Bundle)
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.news_fragment, container, false);
        findViews();
        return view;
    }

    private void findViews() {

        databaseService = new DatabaseService(getActivity());
        getTechnologyNews();
        mRecyclerView = (PullToRefreshRecyclerView) view.findViewById(R.id.id_recycler_newslist);
        mRecyclerView.setSwipeEnable(true);// open swipe
         loadMoreView = new DemoLoadMoreView(getActivity(), mRecyclerView.getRecyclerView());
        // loadMoreView.setLoadmoreString(getString(R.string.demo_loadmore));
        // loadMoreView.setLoadmoreString("当前处在热点"+newsList.size()+"条,目前共有"+rawNewsTotal+"条");
         loadMoreView.setLoadmoreString(newsList.size()+"/"+rawNewsTotal+"条");
        loadMoreView.setLoadMorePadding(100);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setLoadMoreCount(ITEM_SIZE_OFFSET);
        // 下拉刷新
        mRecyclerView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                pagenum = 1;
                getTechnologyNews();

                mHandler.sendEmptyMessageDelayed(MSG_CODE_REFRESH, TIME);

            }
        });
        // 上拉加载
        mRecyclerView.setPagingableListener(new PullToRefreshRecyclerView.PagingableListener() {

            @Override
            public void onLoadMoreItems() {
                pagenum++;
                Utils.Log("pagenum==" + pagenum);
                getNewsFromDb();

                loadMoreView.setLoadmoreString(newsList.size() + "/" + rawNewsTotal + "条");
                mHandler.sendEmptyMessageDelayed(MSG_CODE_LOADMORE, TIME);
            }
        });

        mRecyclerView.getRecyclerView().addItemDecoration(
                new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
//        mRecyclerView.addHeaderView(View.inflate(getActivity(), R.layout.header, null));
        mRecyclerView.setEmptyView(View.inflate(getActivity(), R.layout.empty_view, null));
        // mRecyclerView.removeHeader();
        mRecyclerView.setLoadMoreFooter(loadMoreView);
        mRecyclerView.getLoadMoreFooter().setOnDrawListener(new BaseLoadMoreView.OnDrawListener() {

            @Override
            public boolean onDrawLoadMore(Canvas c, RecyclerView parent) {
                return false;
            }
        });
        mAdapter = new PtrrvAdapter(getActivity());
        mAdapter.setRecyClerViewClick(this);
        // mAdapter.setCount(0);
        mAdapter.setCount(newsList.size());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.onFinishLoading(true, false);
    }

    /**
     * @Description (请求新闻放入数据库)
     */
    private void getTechnologyNews() {
        getNewsFromDb();
        ContentAsyncTask contentAsyncTask = new ContentAsyncTask(getActivity(), pagenum, new ContentCallback() {

            @Override
            public void send(Boolean result) {
                if (result) {
                    getNewsFromDb();

                } else {
                    Utils.Toast(getActivity(), "哎呀，出错了！请再试试");
                }
            }

        });
        contentAsyncTask.execute();

    }

    // 从数据库获取新闻列表
    private void getNewsFromDb() {
        newsList = databaseService.rawQueryNewsDetailContent(pagenum, ITEM_SIZE_OFFSET);
        rawNewsTotal = databaseService.rawQueryNewsDetailContent();

    }

    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == MSG_CODE_REFRESH) {
                mAdapter.setCount(newsList.size());
                mAdapter.notifyDataSetChanged();
                mRecyclerView.setOnRefreshComplete();
                mRecyclerView.onFinishLoading(true, false);
            } else if (msg.what == MSG_CODE_LOADMORE) {
                if (mAdapter.getItemCount() == rawNewsTotal) {
                    // over
                    Toast.makeText(getActivity(), R.string.nomoredata, Toast.LENGTH_SHORT).show();
                    mRecyclerView.onFinishLoading(false, false);
                } else {

                    mAdapter.setCount(newsList.size());
                    mAdapter.notifyDataSetChanged();
                    mRecyclerView.onFinishLoading(true, false);
                }
            }
        }
    };
    private View view;
    private int rawNewsTotal;
    private DemoLoadMoreView loadMoreView;

    private class PtrrvAdapter extends PtrrvBaseAdapter<PtrrvAdapter.ViewHolder> {
       private InterfaceManager. RecyClerViewClick mRecyClerViewClick;
        public PtrrvAdapter(Context context) {
            super(context);
        }
        private void setRecyClerViewClick(InterfaceManager. RecyClerViewClick mRecyClerViewClick){
            this.mRecyClerViewClick=mRecyClerViewClick;
        }
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = mInflater.inflate(R.layout.technology_item, null);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.title.setSelected(true);
            final NewsDetailContent newsDetailContent = newsList.get(position);
            List<NewsImageUrls> imageurls = newsList.get(position).getImageurls();
            if (imageurls != null) {
                MyApplication.imageLoader.displayImage(newsList.get(position).getImageurls().get(0).getUrl(),
                        holder.pic);
            }
            holder.title.setText(newsList.get(position).getTitle());
            holder.time.setText(newsList.get(position).getPubDate());
            holder.description.setText(newsList.get(position).getDesc());
            holder.technologyItem.setOnClickListener(new OnClickListener() {
                
                @Override
                public void onClick(View v) {
                    mRecyClerViewClick.mOnclikImpl(newsDetailContent); 
                }
            });
           
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            private View technologyItem;
            private ImageView pic;
            private TextView time;
            private TextView title;
            private TextView description;

            public ViewHolder(View itemView) {
                super(itemView);
                technologyItem=itemView.findViewById(R.id.id_technology_item);
                pic = (ImageView) itemView.findViewById(R.id.TechnologyNewsAdapter_pic);
                title = (TextView) itemView.findViewById(R.id.TechnologyNewsAdapter_title);
                time = (TextView) itemView.findViewById(R.id.TechnologyNewsAdapter_time);
                description = (TextView) itemView.findViewById(R.id.TechnologyNewsAdapter_description);

            }
        }

    }

    /*
     * (非 Javadoc) Description:
     * @see net.micode.notes.BaseFragment#initViews()
     */
    @Override
    protected void initViews() {
        // TODO Auto-generated method stub

    }

    /*
     * (非 Javadoc) Description:
     * @see net.micode.notes.BaseFragment#initEvents()
     */
    @Override
    protected void initEvents() {
        // TODO Auto-generated method stub

    }

    /* (非 Javadoc)
     * Description:
     * @see net.micode.notes.interfacemanage.InterfaceManager.RecyClerViewClick#mOnclikImpl(java.lang.Object)
     */
    @Override
    public void mOnclikImpl(Object obj) {
        NewsDetailContent mNewsDetailContent=  (NewsDetailContent) obj;
        openX5FragmentIMPL.openX5Fragment(mNewsDetailContent.getLink());
    }

}
