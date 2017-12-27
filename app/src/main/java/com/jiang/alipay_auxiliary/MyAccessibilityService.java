package com.jiang.alipay_auxiliary;

import android.accessibilityservice.AccessibilityService;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import com.jiang.alipay_auxiliary.utils.AccessibilityOperator;
import com.jiang.alipay_auxiliary.utils.LogUtil;

import java.util.List;

/**
 * @author: jiangyao
 * @date: 2017/12/19
 * @Email: www.fangmu@qq.com
 * @Phone: 186 6120 1018
 * TODO:
 */

public class MyAccessibilityService extends AccessibilityService {
    private static final String TAG = "MyAccessibilityService";

    /**
     * 查询前台应用的包名
     *
     * @param context
     * @return
     */
    public static String getTopAppPackageName(Context context) {

        String packageName = "";
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        try {
            List<ActivityManager.RunningAppProcessInfo> processes = activityManager.getRunningAppProcesses();
            if (processes.size() == 0) {
                return packageName;
            }
            for (ActivityManager.RunningAppProcessInfo process : processes) {

                if (process.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    return process.processName;
                }
            }
        } catch (Exception ignored) {
            LogUtil.e(TAG, "没查到");
        }
        return packageName;
    }

    /**
     * 系统会在成功连接上你的服务的时候调用这个方法，
     * 在这个方法里你可以做一下初始化工作，
     * 例如设备的声音震动管理，
     * 也可以调用setServiceInfo()进行配置工作。
     */
    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        LogUtil.e(TAG, "服务已开启");

        //如果前台是支付宝
        if (getTopAppPackageName(MyApplication.context).equals("com.eg.android.AlipayGphone")) {

        } else {
            startActivity(getPackageManager().getLaunchIntentForPackage("com.eg.android.AlipayGphone"));
        }

    }


    /**
     * 通过这个函数可以接收系统发送来的AccessibilityEvent，
     * 接收来的AccessibilityEvent是经过过滤的，
     * 过滤是在配置工作时设置的。
     *
     * @param event
     */
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {

        AccessibilityOperator.getInstance().updateEvent(this, event);

        String classname = event.getClassName().toString();

        switch (event.getEventType()) {
            case AccessibilityEvent.TYPE_VIEW_CLICKED:
                LogUtil.e(TAG, "点击了: " + classname);
                break;
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
                LogUtil.e(TAG, "到: " + classname);

                //进入支付宝主页面
                if ("com.eg.android.AlipayGphone.AlipayLogin".equals(classname)) {
                    Click("收钱");
                }

                //进入支付宝收钱页面
                if ("com.alipay.mobile.payee.ui.PayeeQRActivity".equals(classname)) {
                    Click("设置金额");
                }

                //进入支付宝收钱页面
                if ("com.alipay.mobile.payee.ui.PayeeQRSetMoneyActivity".equals(classname)) {
                    Input("com.alipay.mobile.ui:id/content","100");
                }

                //进入支付宝收钱页面
                if ("com.alipay.mobile.payee.ui.PayeeQRSetMoneyActivity".equals(classname)) {
                    Click("确定");
                }

//                com.alipay.mobile.payee:id/payee_QRCodeImageView  二维码

                //进入支付宝收钱页面
                if ("com.alipay.mobile.payee.ui.PayeeQRActivity".equals(classname)) {
                    Click("设置金额");
                }


                break;

            default:
                break;

        }
    }

    /**
     * 点击
     *
     * @param click
     */
    public void Click(String click) {

        //第一次尝试点击
        boolean b1 = AccessibilityOperator.getInstance().clickByText(click);
        LogUtil.e(TAG, b1 ? "第一次成功" : "第一次失败");
        if (b1) {
            return;
        }

        //尝试第二次点击
        boolean b2 = AccessibilityOperator.getInstance().clickByText(click);
        LogUtil.e(TAG, b2 ? "第二次成功" : "第二次失败");
        if (b2) {
            return;
        }

        //尝试第三次点击
        boolean b3 = AccessibilityOperator.getInstance().clickByText(click);
        LogUtil.e(TAG, b3 ? "第三次成功" : "第三次失败");

    }

    /**
     * 赋值
     * @param click
     * @param text
     */
    public void Input(String click,String text) {

        //第一次尝试点击
        boolean b1 = AccessibilityOperator.getInstance().InputById(click,text);
        LogUtil.e(TAG, b1 ? "第一次成功" : "第一次失败");
        if (b1) {
            return;
        }

        //尝试第二次点击
        boolean b2 = AccessibilityOperator.getInstance().InputById(click,text);
        LogUtil.e(TAG, b2 ? "第二次成功" : "第二次失败");
        if (b2) {
            return;
        }

        //尝试第三次点击
        boolean b3 = AccessibilityOperator.getInstance().InputById(click,text);
        LogUtil.e(TAG, b3 ? "第三次成功" : "第三次失败");

    }

    /**
     * 这个在系统想要中断AccessibilityService返给的响应时会调用。
     * 在整个生命周期里会被调用多次。
     */
    @Override
    public void onInterrupt() {
        Log.e(TAG, "onInterrupt: ");
    }

    /**
     * 在系统将要关闭这个AccessibilityService会被调用。
     * 在这个方法中进行一些释放资源的工作。
     *
     * @param intent
     * @return
     */
    @Override
    public boolean onUnbind(Intent intent) {
        LogUtil.e(TAG, "onUnbind: ");
        return super.onUnbind(intent);
    }

}
