package net.micode.notes.dialog.dialogfragment;

import android.app.DialogFragment;

public class ConfimDialog extends DialogFragment {/*
	private String url;
	public ConfimDialog(){}
	public ConfimDialog(String url){
		this.url=url;
	}
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the Builder class for convenient dialog construction
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setMessage("开始下载图片? ")
				.setPositiveButton("好",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								Intent intent = new Intent(getActivity(),DownloadListActivity.class);
								getActivity().startActivity(intent);
								download();
							}
						})
				.setNegativeButton("否",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								// User cancelled the dialog
							}
						});
		// Create the AlertDialog object and return it
		return builder.create();
	}
	public void download() {
		String target = "/sdcard/Picture/" + System.currentTimeMillis()+ "lemon.jpg";
		try {
			DownloadService.getDownloadManager(getActivity()).addNewDownload(
					url, 
					url, 
					target,
					true, // 如果目标文件存在，接着未完成的部分继续下载。服务器不支持RANGE时将从新下载。
					true, // 如果从请求返回信息中获取到文件名，下载完成后自动重命名。
					null);
		} catch (DbException e) {
			LogUtils.e(e.getMessage(), e);
		}
	}
*/}
