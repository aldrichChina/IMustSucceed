package net.micode.notes;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import net.micode.notes.adapter.BgPicGridAdapter;
import net.micode.notes.entity.BgPicEntity;
import net.micode.notes.utils.SharePrefrenceUtil;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class ChangeBackgroundFragment extends Fragment {

	private List<BgPicEntity> mBgPicList;
	private View baseView;
	private GridView mGridView;
	private BgPicGridAdapter mBgPicAdapter;
	private SharePrefrenceUtil shareUtil;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
				super.onCreateView(inflater, container, savedInstanceState);
				baseView=inflater.inflate(R.layout.change_background_fragment, null);
				initBackgroundPic();
				initView();
				return baseView;
	}

	
	private void initView()
	{
		
		mGridView = (GridView) baseView.findViewById(R.id.change_background_grid);
		mBgPicAdapter = new BgPicGridAdapter(getActivity(),mBgPicList);

		mGridView.setOnItemClickListener(gridItemClickListener);
		mGridView.setAdapter(mBgPicAdapter);
		
	}
	private void initBackgroundPic()
	{
		AssetManager am = getActivity().getAssets();
		try {
			String[] drawableList = am.list("bkgs");
			mBgPicList = new ArrayList<BgPicEntity>();
			for (String path : drawableList) {
				BgPicEntity bg = new BgPicEntity();
				InputStream is = am.open("bkgs/" + path);
				Bitmap bitmap = BitmapFactory.decodeStream(is);
				bg.path = path;
				bg.bitmap = bitmap;
				mBgPicList.add(bg);
				is.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	 OnItemClickListener gridItemClickListener=new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			// TODO Auto-generated method stub
			String path = ((BgPicEntity)mBgPicAdapter.getItem(position)).path;
			
			shareUtil=new SharePrefrenceUtil(getActivity());
			shareUtil.saveBgPicPath(path);
			Log.i("weatherIndex",path);
//			Drawable drawable=Drawable.createFromPath(path);
//			mMainLayout.setBackgroundDrawable(drawable);
//			Bitmap bitmap =getBitmapByPath(path);
//			if(bitmap != null) {
//				mMainLayout.setBackgroundDrawable(new BitmapDrawable(getActivity().getResources(), bitmap));
//			}
			Intent intent=new Intent("change_background");
			intent.putExtra("path", path);
			getActivity().sendBroadcast(intent);
			mBgPicAdapter.notifyDataSetChanged();
			
		}
	};
	
	
 
}
