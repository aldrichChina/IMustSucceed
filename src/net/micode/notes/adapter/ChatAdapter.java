package net.micode.notes.adapter;

import java.util.List;

import net.micode.notes.MyApplication;
import net.micode.notes.BaseObjectListAdapter;
import net.micode.notes.activity.message.MessageItem;
import net.micode.notes.entity.Entity;
import net.micode.notes.entity.Message;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

public class ChatAdapter extends BaseObjectListAdapter {

	public ChatAdapter(MyApplication application, Context context,
			List<? extends Entity> datas) {
		super(application, context, datas);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Message msg = (Message) getItem(position);
		MessageItem messageItem = MessageItem.getInstance(msg, mContext);
		messageItem.fillContent();
		View view = messageItem.getRootView();
		return view;
	}
}
