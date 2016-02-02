package net.micode.notes.fragment;

import net.micode.notes.tool.Utils;
import android.app.Fragment;

import com.umeng.analytics.MobclickAgent;

public class BaseFragment extends Fragment {
	@Override
	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart(getActivity().getClass().getName());
	}

	@Override
	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd(getActivity().getClass().getName());
	}
}
