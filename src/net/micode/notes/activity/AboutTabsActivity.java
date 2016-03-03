package net.micode.notes.activity ;
import net.micode.notes.R;
import net.micode.notes.view.HandyTextView;
import net.micode.notes.view.HeaderLayout;
import net.micode.notes.view.HeaderLayout.HeaderStyle;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;

@SuppressWarnings("deprecation")
public class AboutTabsActivity extends TabActivity {

	public static HeaderLayout mHeaderLayout;
	private TabHost mTabHost;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_abouttabs);
		super.onCreate(savedInstanceState);
		initViews();
		initTabs();
	}

	protected void initViews() {
		mTabHost = getTabHost();
		mHeaderLayout = (HeaderLayout) findViewById(R.id.abouttabs_header);
		mHeaderLayout.init(HeaderStyle.DEFAULT_TITLE);
	}

	protected void initTabs() {
		LayoutInflater inflater = LayoutInflater.from(AboutTabsActivity.this);
		View aboutView = inflater.inflate(
				R.layout.common_tabbar_item_lightblue, null);
		((HandyTextView) aboutView.findViewById(R.id.tabbar_item_htv_label))
				.setText("关于陌陌");
		TabHost.TabSpec aboutTabSpec = mTabHost.newTabSpec(
				AboutActivity.class.getName()).setIndicator(aboutView);
		aboutTabSpec.setContent(new Intent(AboutTabsActivity.this,
				AboutActivity.class));
		mTabHost.addTab(aboutTabSpec);

		View helpView = inflater.inflate(R.layout.common_tabbar_item_lightblue,
				null);
		((HandyTextView) helpView.findViewById(R.id.tabbar_item_htv_label))
				.setText("用户帮助");
		helpView.findViewById(R.id.tabbar_item_ligthblue_driver_left)
				.setVisibility(View.VISIBLE);
		TabHost.TabSpec helpTabSpec = mTabHost.newTabSpec(
				HelpActivity.class.getName()).setIndicator(helpView);
		helpTabSpec.setContent(new Intent(AboutTabsActivity.this,
				HelpActivity.class));
		mTabHost.addTab(helpTabSpec);

		View protocolView = inflater.inflate(
				R.layout.common_tabbar_item_lightblue, null);
		((HandyTextView) protocolView.findViewById(R.id.tabbar_item_htv_label))
				.setText("用户协议");
		protocolView.findViewById(R.id.tabbar_item_ligthblue_driver_left)
				.setVisibility(View.VISIBLE);
		TabHost.TabSpec protocolTabSpec = mTabHost.newTabSpec(
				ProtocolActivity.class.getName()).setIndicator(protocolView);
		protocolTabSpec.setContent(new Intent(AboutTabsActivity.this,
				ProtocolActivity.class));
		mTabHost.addTab(protocolTabSpec);
	}
}
