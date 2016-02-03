package net.micode.notes.fragment;

import net.micode.notes.R;
import net.micode.notes.entities.NewsDetailContent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
public class NewsDetailFragment extends BaseFragment {
	private TextView news_detail_fragment_text;
	private int position;
	NewsDetailContent detailContent;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.news_detail_fragment, container,
				false);
		news_detail_fragment_text = (TextView) view.findViewById(R.id.news_detail_fragment_text);
		Bundle arguments = getArguments();
		detailContent = (NewsDetailContent) arguments.getSerializable("news_detailContent");
		updateArticleView(detailContent);
		return view;
	}

	public void updateArticleView(NewsDetailContent detailContent) {
		news_detail_fragment_text.setText(detailContent.getLong_desc());
	}
}
