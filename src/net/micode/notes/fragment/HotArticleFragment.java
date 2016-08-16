package net.micode.notes.fragment;

import java.util.ArrayList;
import java.util.List;

import net.micode.notes.BaseFragment;
import net.micode.notes.ConstantProvider;
import net.micode.notes.R;
import net.micode.notes.adapter.WxhotAdapter;
import net.micode.notes.adapter.WxhotAdapter.ItemClickListener;
import net.micode.notes.db.DatabaseService;
import net.micode.notes.entity.WxhotArticle;
import net.micode.notes.interfacemanage.InterfaceManager;
import net.micode.notes.interfacemanage.InterfaceManager.OpenX5WebFragment;
import net.micode.notes.util.RecyclerDividerItemDecoration;
import okhttp3.Call;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

public class HotArticleFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, ItemClickListener {

    private SwipeRefreshLayout swipeContainer;
    private RecyclerView recyclerView;
    private WxhotAdapter wxhotAdapter;
    private DatabaseService databaseService;
    private List<WxhotArticle> hotArticleList;
    private InterfaceManager.OpenX5WebFragment openX5FragmentIMPL;

    public HotArticleFragment(OpenX5WebFragment openX5FragmentIMPL) {
        this.openX5FragmentIMPL = openX5FragmentIMPL;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wxhot, container, false);
        findViewById(view);

        return view;
    }

    private void findViewById(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.id_RecyclerView);
        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        initViews();
    }

    @Override
    protected void initViews() {
        databaseService = new DatabaseService(getActivity());

        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        swipeContainer.setOnRefreshListener(this);
        hotArticleList = databaseService.rawQueryHotArticle();
        wxhotAdapter = new WxhotAdapter(getActivity(), hotArticleList, this);
        recyclerView.setAdapter(wxhotAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new RecyclerDividerItemDecoration(getActivity(),
                RecyclerDividerItemDecoration.VERTICAL_LIST));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        obtainWxhotArticle();
        initEvents();
    }

    private void obtainWxhotArticle() {
        OkHttpUtils.get().url(ConstantProvider.BaseURL + ConstantProvider.WXHOTURL)
                .addHeader("apikey", ConstantProvider.APIKEY).addParams("num", "100").build()
                .execute(new StringCallback() {

                    @Override
                    public void onResponse(String response, int id) {
                        Log.d("jia", "WXHOTURL=response==" + response.toString());
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String code = jsonObject.getString("code");
                            String msg = jsonObject.getString("msg");
                            if (code.equals("200") && msg.equals("success")) {
                                parseWxhotArticleJson(jsonObject);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }
                });
    }

    @Override
    protected void initEvents() {

    }

    private void parseWxhotArticleJson(JSONObject jsonObject) throws JSONException {
        List<WxhotArticle> wxHotList = new ArrayList<WxhotArticle>();
        JSONArray articlelist = jsonObject.getJSONArray("newslist");
        for (int i = 0; i < articlelist.length(); i++) {
            JSONObject articleJsonObject = articlelist.getJSONObject(i);
            WxhotArticle wxhotArticle = new WxhotArticle();
            wxhotArticle.setCtime(articleJsonObject.getString("ctime"));
            wxhotArticle.setTitle(articleJsonObject.getString("title"));
            wxhotArticle.setDescription(articleJsonObject.getString("description"));
            wxhotArticle.setPicUrl(articleJsonObject.getString("picUrl"));
            wxhotArticle.setUrl(articleJsonObject.getString("url"));
            wxHotList.add(wxhotArticle);
        }
        databaseService.insertHotArticle(wxHotList);
         hotArticleList.clear();
        hotArticleList.addAll(databaseService.rawQueryHotArticle());
        wxhotAdapter.notifyDataSetChanged();
    }

    /*
     * (非 Javadoc) Description:
     * @see android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener#onRefresh()
     */
    @Override
    public void onRefresh() {
        obtainWxhotArticle();
        swipeContainer.setRefreshing(false);
    }

    /*
     * (非 Javadoc) Description:
     * @see net.micode.notes.adapter.WxhotAdapter.ItemClickListener#onItemClick(android.view.View, int)
     */
    @Override
    public void onItemClick(View view, int postion) {
        WxhotArticle wxhotArticle = hotArticleList.get(postion);
        openX5FragmentIMPL.openX5Fragment(wxhotArticle.getUrl());
    }
}
