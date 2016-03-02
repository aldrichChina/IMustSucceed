package net.micode.notes.jni;
public class JniManager {
	static {
		// 加载so文件
		System.loadLibrary("immomoUrl");
	}
	private static JniManager mJniManager;

	/**
	 * 获取单例对象
	 * 
	 * @return
	 */
	public static JniManager getInstance() {
		if (mJniManager == null) {
			mJniManager = new JniManager();
		}
		return mJniManager;
	}

	// 获取用户帮助URL地址
	public native String getHelpUrl();

	// 获取用户协议URL地址
	public native String getProtocolUrl();

	// 获取用户协议对话框URL地址
	public native String getAgreementDialogUrl();

	// 获取群组等级URL地址
	public native String getGroupLevelUrl();
}
