package net.micode.notes.adapter;

import java.util.List;

import com.lidroid.xutils.BitmapUtils;

import net.micode.notes.R;
import net.micode.notes.entities.NewsDetailContent;
import net.micode.notes.entities.NewsImageUrls;
import net.micode.notes.fragment.HttpFragment.UpdateNewsAdapter;
import net.micode.notes.ui.activity.MainApplication;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NewsAdapter extends BaseAdapter implements UpdateNewsAdapter {
	private List<NewsDetailContent> newsList;
	private Context context;

	public NewsAdapter(Context context, List<NewsDetailContent> newsList) {
		super();
		this.newsList = newsList;
		this.context = context;
	}

	@Override
	public int getCount() {
		return newsList.size();
	}

	@Override
	public Object getItem(int position) {
		return newsList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public class ViewHolder {
		private ImageView pic;
		private TextView time;
		private TextView title;
		private TextView description;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.technology_item, null);
			holder = new ViewHolder();
			holder.pic = (ImageView) convertView
					.findViewById(R.id.TechnologyNewsAdapter_pic);
			holder.title = (TextView) convertView
					.findViewById(R.id.TechnologyNewsAdapter_title);
			holder.time = (TextView) convertView
					.findViewById(R.id.TechnologyNewsAdapter_time);
			holder.description = (TextView) convertView
					.findViewById(R.id.TechnologyNewsAdapter_description);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.title.setSelected(true);
		List<NewsImageUrls> imageurls = newsList.get(position).getImageurls();
		if (imageurls!=null) {
			MainApplication.imageLoader.displayImage(newsList.get(position)
					.getImageurls().get(0).getUrl(), holder.pic);
		}
		holder.title.setText(newsList.get(position).getTitle());
		holder.time.setText(newsList.get(position).getPubDate());
		holder.description.setText(newsList.get(position).getDesc());
		return convertView;
	}

	@Override
	public void upadapter(List<NewsDetailContent> newsList) {
		this.newsList = newsList;
		notifyDataSetChanged();
	}

}
