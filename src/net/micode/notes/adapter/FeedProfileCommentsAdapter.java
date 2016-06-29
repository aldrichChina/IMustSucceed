package net.micode.notes.adapter;

import java.util.List;

import net.micode.notes.MyApplication;
import net.micode.notes.BaseObjectListAdapter;
import net.micode.notes.R;
import net.micode.notes.entity.Entity;
import net.micode.notes.entity.FeedComment;
import net.micode.notes.view.EmoticonsTextView;
import net.micode.notes.view.HandyTextView;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class FeedProfileCommentsAdapter extends BaseObjectListAdapter {

	public FeedProfileCommentsAdapter(MyApplication application,
			Context context, List<? extends Entity> datas) {
		super(application, context, datas);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = mInflater
					.inflate(R.layout.listitem_feedcomment, null);
			holder = new ViewHolder();
			holder.mIvAvatar = (ImageView) convertView
					.findViewById(R.id.feedcomment_item_iv_avatar);
			holder.mEtvName = (EmoticonsTextView) convertView
					.findViewById(R.id.feedcomment_item_etv_name);
			holder.mEtvContent = (EmoticonsTextView) convertView
					.findViewById(R.id.feedcomment_item_etv_content);
			holder.mHtvTime = (HandyTextView) convertView
					.findViewById(R.id.feedcomment_item_htv_time);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		FeedComment comment = (FeedComment) getItem(position);
		holder.mIvAvatar.setImageBitmap(mApplication.getAvatar(comment
				.getAvatar()));
		holder.mEtvName.setText(comment.getName());
		holder.mEtvContent.setText(comment.getContent());
		holder.mHtvTime.setText(comment.getTime());
		return convertView;
	}

	class ViewHolder {
		ImageView mIvAvatar;
		EmoticonsTextView mEtvName;
		EmoticonsTextView mEtvContent;
		HandyTextView mHtvTime;
	}
}
