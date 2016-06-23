package net.micode.notes.activity.ResideMenuItem;

import java.util.ArrayList;

import net.micode.notes.R;
import net.micode.notes.activity.BaseActivity;
import net.micode.notes.util.Utils;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

public class CalendarActivity extends BaseActivity {

	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.calendar);
		super.onCreate(savedInstanceState);

		listView = (ListView) findViewById(R.id.listView);
		initView();

	}

	private void initView() {
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, getCalendarData());
		listView.setAdapter(arrayAdapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view,
					int i, long l) {
				Toast.makeText(CalendarActivity.this, "Clicked item!",
						Toast.LENGTH_LONG).show();
			}
		});
	}

	private ArrayList<String> getCalendarData() {
		ArrayList<String> calendarList = new ArrayList<String>();
		calendarList.add("New Year's Day");
		calendarList.add("St. Valentine's Day");
		calendarList.add("Easter Day");
		calendarList.add("April Fool's Day");
		calendarList.add("Mother's Day");
		calendarList.add("Memorial Day");
		calendarList.add("National Flag Day");
		calendarList.add("Father's Day");
		calendarList.add("Independence Day");
		calendarList.add("Labor Day");
		calendarList.add("Columbus Day");
		calendarList.add("Halloween");
		calendarList.add("All Soul's Day");
		calendarList.add("Veterans Day");
		calendarList.add("Thanksgiving Day");
		calendarList.add("Election Day");
		calendarList.add("Forefather's Day");
		calendarList.add("Christmas Day");
		return calendarList;
	}

	@Override
	protected void initViews() {

		ImageView righttitle = (ImageView) findViewById(R.id.righttitle);
		righttitle.setVisibility(View.INVISIBLE);
		ImageView topback = (ImageView) findViewById(R.id.topback);
		topback.setBackgroundResource(R.drawable.ic_topbar_back_normal);
		topback.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Utils.finish(CalendarActivity.this);
			}
		});
	
	}

	@Override
	protected void initEvents() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void setListener() {
		// TODO Auto-generated method stub

	}
}
