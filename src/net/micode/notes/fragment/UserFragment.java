package net.micode.notes.fragment;

import java.util.List;

import net.micode.notes.BaseFragment;
import net.micode.notes.ConstantProvider;
import net.micode.notes.R;
import net.micode.notes.db.DatabaseService;
import net.micode.notes.entities.LoginBean;
import net.micode.notes.entities.UserBean;
import net.micode.notes.interfacemanage.InterfaceManager;
import net.micode.notes.interfacemanage.InterfaceManager.OpenX5WebFragment;
import net.micode.notes.util.ImgUtil;
import net.micode.notes.util.Utils;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
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
    private ImageView ivHeadPortrait;
    private TextView tvUserName;
    private TextView tvToken;
    private TextView tvUserId;
    private TextView tvVip;
    private TextView tvGender;
    private TextView tvCity;
    private TextView tvProvince;
    private String nickname;
    private String access_token;
    private String openid;
    private String vip;
    private String gender;
    private String city;
    private String province;
    private InterfaceManager.OpenX5WebFragment openX5FragmentIMPL;

    public UserFragment(OpenX5WebFragment openX5FragmentIMPL) {
        this.openX5FragmentIMPL = openX5FragmentIMPL;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE |
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
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
        View introduce = view.findViewById(R.id.btn_introduce_aboutus);
        introduce.setOnClickListener(introduceListener);
        ivHeadPortrait = (ImageView) view.findViewById(R.id.iv_user_head_portrait);
        tvUserName = (TextView) view.findViewById(R.id.tv_user_name);
        tvToken = (TextView) view.findViewById(R.id.tv_token);
        tvUserId = (TextView) view.findViewById(R.id.tv_user_id);
        tvVip = (TextView) view.findViewById(R.id.tv_vip);
        tvGender = (TextView) view.findViewById(R.id.tv_gender);
        tvCity = (TextView) view.findViewById(R.id.tv_city);
        tvProvince = (TextView) view.findViewById(R.id.tv_province);
    }

    @Override
    protected void initEvents() {
        Utils.Log("UserFragment---->initEvents");
        Bitmap avator = ImgUtil.getQQAvator();
        ivHeadPortrait.setImageBitmap(avator);
        for (LoginBean loginBean : mLoginList) {
            openid = loginBean.getOpenid();
            access_token = loginBean.getAccess_token();
        }
        for (UserBean userBean : mUserList) {
            nickname = userBean.getNickname();
            vip = userBean.getVip();
            gender = userBean.getGender();
            city = userBean.getCity();
            province = userBean.getProvince();
        }

        fillPageInformation();

    }
    private View.OnClickListener introduceListener=new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            openX5FragmentIMPL.openX5Fragment("http://www.yaojiawei.cc");
        }};
    /**
     * @Description (TODO这里用一句话描述这个方法的作用)
     */
    private void fillPageInformation() {
        tvUserName.setText("hello,　" + nickname);
        tvToken.setText(access_token);
        tvUserId.setText(openid);
        if ("0".equals(vip)) {
            tvVip.setText("不是");
        } else {
            tvVip.setText("是");
        }
        tvGender.setText(gender);
        tvCity.setText(city);
        tvProvince.setText(province);
    }
}
