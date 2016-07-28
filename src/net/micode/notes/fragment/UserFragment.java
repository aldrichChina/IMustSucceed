package net.micode.notes.fragment;

import java.util.List;

import net.micode.notes.BaseFragment;
import net.micode.notes.ConstantProvider;
import net.micode.notes.R;
import net.micode.notes.db.DatabaseService;
import net.micode.notes.entities.LoginBean;
import net.micode.notes.entities.UserBean;
import net.micode.notes.util.ImgUtil;
import net.micode.notes.util.Utils;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class UserFragment extends BaseFragment {

    private EditText etTitle;
    private Button newsConfirm;
    private DatabaseService databaseService;
    private View view;
    private List<LoginBean> mLoginList;
    private List<UserBean> mUserList;
    private String nickname;
    private TextView tvUserName;
    private ImageView ivHeadPortrait;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_user_center, container, false);
        super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    @Override
    protected void initViews() {
        findViewById();
        newsConfirm.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                ConstantProvider.newsTitle = etTitle.getText().toString();
                showCustomToast("改变新闻标头成功" + ConstantProvider.newsTitle);
            }
        });

        Utils.Log("UserFragment---->initViews");
        databaseService = new DatabaseService(getActivity());
        mLoginList = databaseService.rawQueryLogin();
        Utils.Log("UserFragment---->rawQueryLogin" + mLoginList.toString());
        mUserList = databaseService.rawQueryUser();
        Utils.Log("UserFragment---->rawQueryUser" + mUserList.toString());
    }

    private void findViewById() {
        etTitle = (EditText) view.findViewById(R.id.title);
        newsConfirm = (Button) view.findViewById(R.id.news_confirm);
        tvUserName = (TextView) view.findViewById(R.id.tv_user_name);
        ivHeadPortrait = (ImageView) view.findViewById(R.id.iv_user_head_portrait);
    }

    @Override
    protected void initEvents() {
        Utils.Log("UserFragment---->initEvents");
        Bitmap avator=ImgUtil.getQQAvator();
        ivHeadPortrait.setImageBitmap(avator);
        for(LoginBean loginBean:mLoginList){
            String openid = loginBean.getOpenid();
        }
        for(UserBean userBean:mUserList){
            nickname = userBean.getNickname();
        }
        tvUserName.setText(nickname);
    }
}
