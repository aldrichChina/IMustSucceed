package net.micode.notes.activity.ResideMenuItem;

import java.io.File;
import java.util.Properties;

import net.micode.notes.BaseActivity;
import net.micode.notes.R;
import net.micode.notes.util.Util;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class UtilActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_reside_menu_util);
        super.onCreate(savedInstanceState);
    }

    /**
     * 拨打电话
     * 
     * @param view
     */
    public void clickCall(View view) {
        Util.call(context, "18515634368");
    }

    /**
     * 跳转至拨号界面
     * 
     * @param view
     */
    public void clickCallDial(View view) {
        Util.callDial(context, "18515634368");
    }

    /**
     * 发送短信
     * 
     * @param view
     */
    public void clickSendSms(View view) {
        Util.sendSms(context, "18515634368", "你真牛逼!");
    }

    /**
     * 唤醒屏幕并解锁
     * 
     * @param view
     */
    public void clickWakeUpAndUnlock(View view) {
        Util.wakeUpAndUnlock(context);
    }

    /**
     * 判断当前App处于前台还是后台状态
     * 
     * @param view
     */
    public void clickIsApplicationBackground(View view) {
        boolean applicationBackground = Util.isApplicationBackground(context);
        ToastMessage("" + applicationBackground);
    }

    /**
     * 判断当前手机是否处于锁屏(睡眠)状态
     * 
     * @param view
     */
    public void clickIsSleeping(View view) {
        boolean sleeping = Util.isSleeping(context);
        ToastMessage("" + sleeping);
    }

    /**
     * 判断当前是否有网络连接
     * 
     * @param view
     */
    public void clickIsOnline(View view) {
        boolean online = Util.isOnline(context);
        ToastMessage("" + online);
    }

    /**
     * 判断当前是否是WIFI连接状态
     * 
     * @param view
     */
    public void clickIsWifiConnected(View view) {
        boolean wifiConnected = Util.isWifiConnected(context);
        ToastMessage("" + wifiConnected);
    }

    /**
     * 安装APK
     * 
     * @param view
     */
    public void clickInstallApk(View view) {
        File file = new File("");
        Util.installApk(context, file);
    }

    /**
     * 判断当前设备是否为手机
     * 
     * @param view
     */
    public void clickIsPhone(View view) {
        boolean phone = Util.isPhone(context);
        ToastMessage("" + phone);
    }

    /**
     * 获取当前设备宽高，单位px
     * 
     * @param view
     */
    public void clickGetDeviceWidthHeight(View view) {
        int deviceWidth = Util.getDeviceWidth(context);
        int deviceHeight = Util.getDeviceHeight(context);

        ToastMessage("deviceWidth==" + deviceWidth + ",deviceHeight==" + deviceHeight);
    }

    /**
     * 获取当前设备的IMEI，需要与上面的isPhone()一起使用
     * 
     * @param view
     */
    public void clickGetDeviceIMEI(View view) {
        String deviceIMEI = Util.getDeviceIMEI(context);
        ToastMessage(deviceIMEI);
    }

    /**
     * 获取当前设备的MAC地址
     * 
     * @param view
     */
    public void clickGetMacAddress(View view) {
        String deviceIMEI = Util.getDeviceIMEI(context);
        ToastMessage(deviceIMEI);
    }

    /**
     * 获取当前程序的版本号
     * 
     * @param view
     */
    public void clickGetAppVersion(View view) {
        String appVersion = Util.getAppVersion(context);
        ToastMessage("" + appVersion);
    }

    /**
     * 收集设备信息，用于信息统计分析
     * 
     * @param view
     */
    public void clickCollectDeviceInfo(View view) {
        Properties collectDeviceInfo = Util.collectDeviceInfo(context);
        ToastMessage("" + collectDeviceInfo);
    }

    /**
     * 收集设备信息，用于信息统计分析,返回字符串
     * 
     * @param view
     */
    public void clickcollectDeviceInfoStr(View view) {
        String collectDeviceInfoStr = Util.collectDeviceInfoStr(context);
        ToastMessage(collectDeviceInfoStr);
    }

    /**
     * 是否有SD卡
     * 
     * @param view
     */
    public void clickHaveSDCard(View view) {
        boolean haveSDCard = Util.haveSDCard();
        ToastMessage("" + haveSDCard);
    }

    /**
     * 动态显示或者是隐藏软键盘
     * 
     * @param view
     */
    public void clickToggleSoftInput(View view) {
        EditText edit = new EditText(context);
        // Util.hideSoftInput(context);
        // Util.hideSoftInput(context, edit);
        // Util.showSoftInput(context, edit);
        Util.toggleSoftInput(context, edit);
    }

    /**
     * 返回主界面
     * 
     * @param view
     */
    public void clickGoHome(View view) {
        Util.goHome(context);
    }

    /**
     * 获取状态栏高度
     * 
     * @param view
     */
    public void clickGetStatusBarHeight(View view) {
        int statusBarHeight = Util.getStatusBarHeight(context);
        ToastMessage("" + statusBarHeight);
    }

    /**
     * 获取状态栏高度＋标题栏(ActionBar)高度
     * 
     * @param view
     */
    public void clickGetTopBarHeight(View view) {
        int topBarHeight = Util.getTopBarHeight(context);
        ToastMessage(topBarHeight + "");
    }

    /**
     * 获取MCC+MNC代码 (SIM卡运营商国家代码和运营商网络代码)
     * 
     * @param view
     */
    public void clickGetNetworkOperator(View view) {
        String networkOperator = Util.getNetworkOperator(context);
        ToastMessage(networkOperator);
    }

    /**
     * 返回移动网络运营商的名字
     * 
     * @param view
     */
    public void clickGetNetworkOperatorName(View view) {
        String networkOperatorName = Util.getNetworkOperatorName(context);
        ToastMessage("" + networkOperatorName);
    }

    /**
     * 返回移动终端类型
     * 
     * @param view
     */
    public void clickGetPhoneType(View view) {
        int phoneType = Util.getPhoneType(context);
        ToastMessage("" + phoneType);
    }

    /**
     * 判断当前手机的网络类型(WIFI还是2,3,4G)
     * 
     * @param view
     */
    public void clickGetNetWorkStatus(View view) {
        int netWorkStatus = Util.getNetWorkStatus(context);
        ToastMessage("" + netWorkStatus);
    }

    /**
     * 格式为小时/分/秒/毫秒
     * 
     * @param view
     */
    public void clickMillisToStringMiddle(View view) {
        String millisToStringMiddle = Util.millisToStringMiddle(24903600, true, true);
        ToastMessage(millisToStringMiddle);
    }

    @Override
    protected void initViews() {
        initTopTitle(this);
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
