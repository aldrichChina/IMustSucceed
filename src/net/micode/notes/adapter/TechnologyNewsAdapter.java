package net.micode.notes.adapter;

import java.util.List;

import net.micode.notes.R;
import net.micode.notes.entities.TechnologyNews;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TechnologyNewsAdapter extends BaseAdapter {
	private List<TechnologyNews> newsList;
	private Context context;
	public TechnologyNewsAdapter(Context context,List<TechnologyNews> newsList) {
		super();
		this.newsList = newsList;
		this.context=context;
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
	public class ViewHolder{
		private TextView time;
		private TextView title;
		private TextView description;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView==null){
			convertView = LayoutInflater.from(context).inflate(R.layout.technology_item,null);
			holder=new ViewHolder();
			holder.title=(TextView) convertView.findViewById(R.id.TechnologyNewsAdapter_title);
			holder.time=(TextView) convertView.findViewById(R.id.TechnologyNewsAdapter_time);
			holder.description = (TextView) convertView.findViewById(R.id.TechnologyNewsAdapter_description);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		holder.title.setText(newsList.get(position).getTitle());
		holder.time.setText(newsList.get(position).getTime());
		holder.description.setText(newsList.get(position).getDescription());
		return convertView;
	}

}
