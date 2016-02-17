package net.micode.notes.fragment;

import net.micode.notes.R;
import android.app.Fragment;
import android.app.FragmentTransaction;

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
	public void addFragmentToStack(Fragment fragment, String tag) {
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.replace(R.id.realtabcontent, fragment, tag);
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		ft.addToBackStack(null);
		ft.commit();
	}
}
