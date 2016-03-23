package net.micode.notes.fragment;

import net.micode.notes.BaseFragment;
import net.micode.notes.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SettingsFragment extends BaseFragment {


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.settings, container,false);
		return view;
	}

	@Override
	protected void initViews() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void initEvents() {
		// TODO Auto-generated method stub

	}


}
