package net.micode.notes.fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.micode.notes.BaseFragment;
import net.micode.notes.R;
import net.micode.notes.adapter.NewsAdapter;
import net.micode.notes.asynctask.ContentAsyncTask;
import net.micode.notes.asynctask.ContentAsyncTask.ContentCallback;
import net.micode.notes.db.DatabaseService;
import net.micode.notes.entities.NewsDetailContent;
import net.micode.notes.util.Utils;
import net.micode.notes.view.XListView;
import net.micode.notes.view.XListView.IXListViewListener;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

public class NewsFragment extends BaseFragment implements IXListViewListener {

    private XListView mListView;
    private List<NewsDetailContent> newsList = new ArrayList<NewsDetailContent>();
    private NewsAdapter adapter;
    private Handler handler = new Handler();
    private TextView tv_empty;
    private UpdateNewsAdapter upadapter;
    private DatabaseService databaseService;
    OnHeadlineSelectedListener mCallback;
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private int page=1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.http_fragment, container, false);
        databaseService = new DatabaseService(getActivity());
        mListView = (XListView) view.findViewById(R.id.xlistView_newslist);
        tv_empty = (TextView) view.findViewById(R.id.tv_empty);
        getTechnologyNews();
        adapter = new NewsAdapter(getActivity(), newsList);
        if (newsList.isEmpty()) {
            tv_empty.setVisibility(View.VISIBLE);
        } else {
            tv_empty.setVisibility(View.INVISIBLE);
        }
        mListView.setPullLoadEnable(true);
        mListView.setAdapter(adapter);
        mListView.setXListViewListener(this);
        mListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    mCallback.onArticleSelected(newsList.get(position - 1), "NewsDetailFragment");
                }
            }
        });
        return view;
    }

    @Override
    public void onRefresh() {
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                page=1;
                getTechnologyNews();
            }
        }, 1000);

    }

    @Override
    public void onLoadMore() {
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                page++;
                getTechnologyNews();
            }
        }, 1000);
    }

    /** 停止刷新， */
    private void onLoad() {
        mListView.stopRefresh();
        mListView.stopLoadMore();
        mListView.setRefreshTime(df.format(new Date()));
    }

    private void getTechnologyNews() {
        getNewsFromDb();
        ContentAsyncTask contentAsyncTask = new ContentAsyncTask(getActivity(),page, new ContentCallback() {

            @Override
            public void send(Boolean result) {
                if (result) {
                    getNewsFromDb();
                    onLoad();

                } else {
                    Utils.Toast(getActivity(), "哎呀，出错了！请再试试");
                }
            }

        });
        contentAsyncTask.execute();

    }

    // 从数据库获取新闻列表
    private void getNewsFromDb() {
        newsList = databaseService.rawQueryNewsDetailContent();
        if (adapter instanceof UpdateNewsAdapter) {
            adapter.upadapter(newsList);
        }

        if (newsList.isEmpty()) {
            tv_empty.setVisibility(View.VISIBLE);
        } else {
            tv_empty.setVisibility(View.INVISIBLE);
        }
    }

    /** 停止刷新， */
    public interface UpdateNewsAdapter {

        public void upadapter(List<NewsDetailContent> newsList);
    }

    // 用来存放fragment的Activtiy必须实现这个接口
    public interface OnHeadlineSelectedListener {

        public void onArticleSelected(NewsDetailContent detailContent, String tag);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // 这是为了保证Activity容器实现了用以回调的接口。如果没有，它会抛出一个异常。
        try {
            mCallback = (OnHeadlineSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnHeadlineSelectedListener");
        }
    }

    @Override
    protected void initViews() {
        Utils.Log("HttpFragment--->initViews只喜萨达纳副科级 ");
    }

    @Override
    protected void initEvents() {
        Utils.Log("HttpFragment--->initEvents只喜萨达纳副科级");

    }
}
