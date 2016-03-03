package net.micode.notes.fragment;

import net.micode.notes.R;
import net.micode.notes.activity.DownloadListActivity;
import net.micode.notes.download.DownloadManager;
import net.micode.notes.download.DownloadService;
import net.micode.notes.tool.Utils;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.util.LogUtils;
public class ProfileFragment extends BaseFragment {
	private EditText downloadAddrEdit;
	private Button downloadBtn;
	private Button downloadPageBtn;
	private DownloadManager downloadManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	View v = LayoutInflater.from(getActivity()).inflate(R.layout.profile, container,false);
    	ViewUtils.inject(this, v);
		downloadManager = DownloadService.getDownloadManager(getActivity());
    	downloadAddrEdit=(EditText) v.findViewById(R.id.download_addr_edit);
    	downloadBtn=(Button) v.findViewById(R.id.download_btn);
    	downloadBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String target = "/sdcard/Picture/" + System.currentTimeMillis()
						+ "lemon.jpg";
				try {
					downloadManager.addNewDownload(
							downloadAddrEdit.getText().toString(), 
							downloadAddrEdit.getText().toString(), 
							target,
							true, // 如果目标文件存在，接着未完成的部分继续下载。服务器不支持RANGE时将从新下载。
							true, // 如果从请求返回信息中获取到文件名，下载完成后自动重命名。
							null);
				} catch (DbException e) {
					LogUtils.e(e.getMessage(), e);
				}
			
			}
		});
    	downloadPageBtn=(Button) v.findViewById(R.id.download_page_btn);
    	downloadPageBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Utils.start_Activity(getActivity(),DownloadListActivity.class);
			}
		});
        return v;
    }

}
