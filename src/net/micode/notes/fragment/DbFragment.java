package net.micode.notes.fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import net.micode.notes.R;
import net.micode.notes.entities.Child;
import net.micode.notes.entities.Parent;
import net.micode.notes.view.XListView;
import net.micode.notes.view.XListView.IXListViewListener;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.table.DbModel;
import com.lidroid.xutils.exception.DbException;

public class DbFragment extends Fragment implements IXListViewListener {
	private XListView mListView;
	private ArrayAdapter<String> mAdapter;
	private List<String> items = new ArrayList<String>();
	private Handler mHandler;
	private int start = 0;
	private static int refreshCnt = 0;
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private TextView tv_empty;

	/** Called when the activity is first created. */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.db_fragment, container, false);
		mListView = (XListView) view.findViewById(R.id.newmessagelist);
		tv_empty = (TextView) view.findViewById(R.id.tv_empty);
		ViewUtils.inject(this, view);
		geneItems();
		if (items.size() == 0) {
			tv_empty.setVisibility(View.VISIBLE);
		} else {
			tv_empty.setVisibility(View.GONE);
		}
		mListView.setPullLoadEnable(true);
		mAdapter = new ArrayAdapter<String>(getActivity(), R.layout.list_item,
				items);
		mListView.setAdapter(mAdapter);
		// mListView.setPullLoadEnable(false);
		// mListView.setPullRefreshEnable(false);
		mListView.setXListViewListener(this);
		mHandler = new Handler();
		return view;
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
				start = ++refreshCnt;
				items.clear();
				geneItems();
				// mAdapter.notifyDataSetChanged();
				if (items.size() == 0) {
					tv_empty.setVisibility(View.VISIBLE);
				} else {
					tv_empty.setVisibility(View.GONE);
				}
				mAdapter = new ArrayAdapter<String>(getActivity(),
						R.layout.list_item, items);
				mListView.setAdapter(mAdapter);
				onLoad();
			}
		}, 2000);
	}

	@Override
	public void onLoadMore() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				geneItems();
				mAdapter.notifyDataSetChanged();
				onLoad();
			}
		}, 2000);
	}

	public void geneItems() {

		String temp = "";

		Parent parent = new Parent();
		parent.name = "测试" + System.currentTimeMillis();
		parent.setAdmin(true);
		parent.setEmail("wyouflf@gmail.com");

		/*
		 * Parent parent2 = new Parent(); parent2.name = "测试2"; parent2.isVIP =
		 * false;
		 */

		try {

			// DbUtils db = DbUtils.create(this.getActivity(), "/sdcard/",
			// "test.db");
			DbUtils db = DbUtils.create(this.getActivity());
			db.configAllowTransaction(true);
			db.configDebug(true);

			Child child = new Child();
			child.name = "child' name";
			// db.saveBindingId(parent);
			// child.parent = new ForeignLazyLoader<Parent>(Child.class,
			// "parentId", parent.getId());
			// child.parent = parent;

			Parent test = db.findFirst(Selector.from(Parent.class)); // 通过parent的属性查找
			// Parent test =
			// db.findFirst(Selector.from(Parent.class).where("id", "in", new
			// int[]{1, 3, 6}));
			// Parent test =
			// db.findFirst(Selector.from(Parent.class).where("id", "between",
			// new String[]{"1", "5"}));
			if (test != null) {
				child.parent = test;
				temp += "first parent:" + test + "\n";
				items.add(temp);
			} else {
				child.parent = parent;
			}

			parent.setTime(new Date());
			parent.setDate(new java.sql.Date(new Date().getTime()));

			db.saveBindingId(child);// 保存对象关联数据库生成的id

			List<Child> children = db.findAll(Selector.from(Child.class));// .where(WhereBuilder.b("name",
																			// "=",
																			// "child' name")));
			temp += "children size:" + children.size() + "\n";
			items.add(temp);
			if (children.size() > 0) {
				temp += "last children:" + children.get(children.size() - 1)
						+ "\n";
				items.add(temp);
			}

			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DATE, -1);
			calendar.add(Calendar.HOUR, 3);

			List<Parent> list = db.findAll(Selector.from(Parent.class)
					.where("id", "<", 54).and("time", ">", calendar.getTime())
					.orderBy("id").limit(10));
			temp += "find parent size:" + list.size() + "\n";
			items.add(temp);
			if (list.size() > 0) {
				temp += "last parent:" + list.get(list.size() - 1) + "\n";
				items.add(temp);
			}

			// parent.name = "hahaha123";
			// db.update(parent);

			Parent entity = db.findById(Parent.class, child.parent.getId());
			temp += "find by id:" + entity.toString() + "\n";
			items.add(temp);

			List<DbModel> dbModels = db.findDbModelAll(Selector
					.from(Parent.class).groupBy("name")
					.select("name", "count(name) as count"));
			temp += "group by result:" + dbModels.get(0).getDataMap() + "\n";
			items.add(temp);

		} catch (DbException e) {
			temp += "error :" + e.getMessage() + "\n";
			items.add(temp);
		}
	}
}
