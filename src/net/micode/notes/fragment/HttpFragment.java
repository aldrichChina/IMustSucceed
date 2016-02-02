package net.micode.notes.fragment;

import java.util.ArrayList;
import java.util.List;

import net.micode.notes.R;
import net.micode.notes.adapter.TechnologyNewsAdapter;
import net.micode.notes.data.Constant;
import net.micode.notes.entities.TechnologyNews;
import net.micode.notes.tool.Utils;
import net.micode.notes.tool.HttpUtils.HttpService;
import net.micode.notes.view.XListView;
import net.micode.notes.view.XListView.IXListViewListener;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class HttpFragment extends BaseFragment implements IXListViewListener{
	private XListView mListView;
	private List<TechnologyNews>newsList=new ArrayList<TechnologyNews>();
	private TechnologyNewsAdapter adapter;
	private Handler handler=new Handler();
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.http_fragment, container, false);
		
		getTechnologyNews();
		adapter=new TechnologyNewsAdapter(getActivity(),newsList);
		mListView = (XListView) view.findViewById(R.id.xlistView_newslist);
		mListView.setPullLoadEnable(false);
		mListView.setAdapter(adapter);
		mListView.setXListViewListener(this);
		return view;
	}

	@Override
	public void onRefresh() {
		getTechnologyNews();
	}
	@Override
	public void onLoadMore() {
		
	}
	private void getTechnologyNews(){

		new Thread(new Runnable() {
			
			@Override
			public void run() {
				String okHttpGet = HttpService.OKHttpGet(Constant.TechnologyNews, Constant.TechnologyArg);
				try {
					JSONObject jsonObject = new JSONObject(okHttpGet);
					int code = jsonObject.optInt("code");
					Utils.Logger(getActivity(), "code="+code);
					if(code!=200){
//						Utils.Toast(getActivity(), jsonObject.optString("msg"));
						return;
					}
					for(int i=0;i<9;i++){
						TechnologyNews news=new TechnologyNews();
						JSONObject optJSONObject = jsonObject.optJSONObject(String.valueOf(i));
						news.setTime(optJSONObject.optString("time"));
						news.setTitle(optJSONObject.optString("title"));
						news.setDescription(optJSONObject.optString("description"));
						news.setPicUrl(optJSONObject.optString("picUrl"));
						news.setUrl(optJSONObject.optString("url"));
						newsList.add(news);
					}
					handler.post(new Runnable() {
						@Override
						public void run() {
							adapter.notifyDataSetChanged();
						}
					});
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
				
			
		}).start();
	
	}
}
