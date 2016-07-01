package net.micode.notes.fragment;

import net.micode.notes.BaseFragment;
import net.micode.notes.ConstantProvider;
import net.micode.notes.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SettingsFragment extends BaseFragment {

    private EditText etTitle;
    private Button newsConfirm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settings, container, false);
        etTitle = (EditText) view.findViewById(R.id.title);
        newsConfirm = (Button) view.findViewById(R.id.news_confirm);
        newsConfirm.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                ConstantProvider.newsTitle = etTitle.getText().toString();
                showCustomToast("改变新闻标头成功"+ConstantProvider.newsTitle);
            }
        });
        return view;
    }

    @Override
    protected void initViews() {
        
    }

    @Override
    protected void initEvents() {

    }
}
