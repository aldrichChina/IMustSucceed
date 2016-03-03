package net.micode.notes.fragment;

import java.io.IOException;
import java.util.List;

import net.micode.notes.R;
import net.micode.notes.activity.ImageActivity;
import net.micode.notes.adapter.MeinvAdapter;
import net.micode.notes.data.Constant;
import net.micode.notes.entities.Detailed;
import net.micode.notes.tool.JSONUtil;
import net.micode.notes.tool.HttpUtils.HttpService;
import okhttp3.OkHttpClient;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.google.gson.Gson;

public class PictureFragment extends BaseFragment {
	OkHttpClient client = new OkHttpClient();
	String httpArg = "num=50";
	Handler handler = new Handler();
	private ListView listView;
	Gson gson = new Gson();
	private List<Detailed> detailedList;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = LayoutInflater.from(getActivity()).inflate(
				R.layout.picture_fragment, container, false);
		listView = (ListView) view.findViewById(R.id.listView);
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					detailedList = JSONUtil.analysisResponse(HttpService.OKHttpGet(Constant.HTTPURLMEINV,httpArg));
					handler.post(new Runnable() {
						@Override
						public void run() {
							setDate();
						}
					});
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
		return view;
	}

	
	private void setDate() {
		listView.setAdapter(new MeinvAdapter(getActivity(), detailedList));
		setListener();
	}
	private void setListener() {
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent();
				intent.putExtra("url", detailedList.get(position).getPicUrl());
				intent.setClass(getActivity(), ImageActivity.class);
				startActivity(intent);
			}
		});
	}
}
