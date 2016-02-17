package net.micode.notes.adapter;

import java.util.List;
import java.util.zip.Inflater;

import net.micode.notes.R;
import net.micode.notes.entities.Detailed;
import net.micode.notes.ui.activity.MainApplication;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MeinvAdapter extends BaseAdapter{
	private List<Detailed> detailedList;
	private Context context;
	private DisplayImageOptions options;
	public MeinvAdapter(Context context,List<Detailed> detailedList){
		this.context=context;
		this.detailedList=detailedList;
		options=new DisplayImageOptions.Builder()
									.showImageOnLoading(R.drawable.ic_stub)//设置图片在下载期间显示的图片
									.showImageForEmptyUri(R.drawable.ic_empty)//设置URL为空或错误的时候显示的图片
									.showImageOnFail(R.drawable.ic_error)//设置图片加载/解码过程中显示错误的图片
									.cacheInMemory()//设置下载的图片是否缓存到内存中
									.cacheOnDisc()//设置下载的图片是否缓存到SD卡中
									.displayer(new RoundedBitmapDisplayer(5))//设置图片显示方式    设置圆角图片
									.build();
	}
	static class ViewHodler{
		ImageView imageView;
		TextView textView;
	}
	@Override
	public int getCount() {
		return detailedList.size();
	}

	@Override
	public Object getItem(int position) {
		return detailedList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view=convertView;
		ViewHodler hodler;
		if(convertView==null){
			hodler = new ViewHodler();
			view = LayoutInflater.from(context).inflate(R.layout.meinv_adapter_item, null);
			hodler.imageView = (ImageView) view.findViewById(R.id.image);
			hodler.textView=(TextView) view.findViewById(R.id.description);
			view.setTag(hodler);
		}else{
			hodler  = (ViewHodler) view.getTag();
		}
		hodler.textView.setText(detailedList.get(position).getDescription());
		MainApplication.imageLoader.displayImage(detailedList.get(position).getPicUrl(), hodler.imageView);
		MainApplication.imageLoader.displayImage(detailedList.get(position).getPicUrl(), hodler.imageView,options);
		return view;
	}

}
