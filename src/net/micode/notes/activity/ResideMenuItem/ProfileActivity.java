package net.micode.notes.activity.ResideMenuItem;

import net.micode.notes.R;
import net.micode.notes.activity.BaseActivity;
import net.micode.notes.activity.DownloadListActivity;
import net.micode.notes.activity.maintabs.MainActivity;
import net.micode.notes.download.DownloadManager;
import net.micode.notes.download.DownloadService;
import net.micode.notes.util.Utils;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.util.LogUtils;

public class ProfileActivity extends BaseActivity {
	private EditText downloadAddrEdit;
	private Button downloadBtn;
	private Button downloadPageBtn;
	private DownloadManager downloadManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.profile);
		super.onCreate(savedInstanceState);
		ViewUtils.inject(this);
		downloadManager = DownloadService.getDownloadManager(this);
		downloadAddrEdit = (EditText) findViewById(R.id.download_addr_edit);
		downloadBtn = (Button) findViewById(R.id.download_btn);
		downloadBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String target = "/sdcard/Picture/" + System.currentTimeMillis()
						+ "lemon.jpg";
				try {
					downloadManager.addNewDownload(downloadAddrEdit.getText()
							.toString(), downloadAddrEdit.getText().toString(),
							target, true, // 如果目标文件存在，接着未完成的部分继续下载。服务器不支持RANGE时将从新下载。
							true, // 如果从请求返回信息中获取到文件名，下载完成后自动重命名。
							null);
				} catch (DbException e) {
					LogUtils.e(e.getMessage(), e);
				}

			}
		});
		downloadPageBtn = (Button) findViewById(R.id.download_page_btn);
		downloadPageBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Utils.start_Activity(ProfileActivity.this,
						DownloadListActivity.class);
			}
		});

	}

	@Override
	protected void initViews() {
		ImageView righttitle = (ImageView) findViewById(R.id.righttitle);
		righttitle.setVisibility(View.INVISIBLE);
		ImageView topback = (ImageView) findViewById(R.id.topback);
		topback.setBackgroundResource(R.drawable.ic_topbar_back_normal);
		topback.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Utils.finish(ProfileActivity.this);
			}
		});
	}

	@Override
	protected void initEvents() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void setListener() {
		// TODO Auto-generated method stub

	}

}
