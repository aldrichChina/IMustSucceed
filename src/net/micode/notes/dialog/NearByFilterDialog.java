package net.micode.notes.dialog;

import net.micode.notes.R;
import android.app.Dialog;
import android.content.Context;

public class NearByFilterDialog extends Dialog{

	public NearByFilterDialog(Context context) {
		super(context, R.style.Popup_Animation_DownScale);
		setContentView(R.layout.include_dialog_nearby_filter);
	}
}
