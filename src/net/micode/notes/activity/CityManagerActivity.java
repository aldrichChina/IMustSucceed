package net.micode.notes.activity;

import java.util.ArrayList;
import java.util.List;

import net.micode.notes.R;
import net.micode.notes.R.drawable;
import net.micode.notes.R.id;
import net.micode.notes.R.layout;
import net.micode.notes.adapter.GridCityMAdapter;
import net.micode.notes.entity.CityManagerEntity;
import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;

public class CityManagerActivity extends Activity {

	private GridView mGrid;
	private List<CityManagerEntity> mCityManagerEntity;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gridview_activity);
		initView();
	}
	private void initView()
	{
		mCityManagerEntity = new ArrayList<CityManagerEntity>();
		mGrid = (GridView) findViewById(R.id.gridview);
		mGrid.setNumColumns(3);
		mGrid.setBackgroundResource(R.drawable.bg_homepager_blur);
		mGrid.setAdapter(new GridCityMAdapter(this, mCityManagerEntity));
		
	}
}
