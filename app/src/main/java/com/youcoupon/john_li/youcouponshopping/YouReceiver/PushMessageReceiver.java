package com.youcoupon.john_li.youcouponshopping.YouReceiver;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.ali.auth.third.core.util.SystemUtils;
import com.alibaba.fastjson.JSON;
import com.youcoupon.john_li.youcouponshopping.LoginActivity;
import com.youcoupon.john_li.youcouponshopping.MainActivity;
import com.youcoupon.john_li.youcouponshopping.MerchandiseDetialActivity;
import com.youcoupon.john_li.youcouponshopping.SplashActivity;
import com.youcoupon.john_li.youcouponshopping.YouActivity.WebH5Activity;
import com.youcoupon.john_li.youcouponshopping.YouModel.JpushContentModel;
import com.youcoupon.john_li.youcouponshopping.YouModel.JpushExtandOutModel;
import com.youcoupon.john_li.youcouponshopping.YouUtils.TagAliasOperatorHelper;
import com.youcoupon.john_li.youcouponshopping.YouUtils.YouCommonUtils;
import com.youcoupon.john_li.youcouponshopping.YouUtils.YouConfigor;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cn.jpush.android.api.CmdMessage;
import cn.jpush.android.api.CustomMessage;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.JPushMessage;
import cn.jpush.android.api.NotificationMessage;
import cn.jpush.android.service.JPushMessageReceiver;

public class PushMessageReceiver extends JPushMessageReceiver{
    private static final String TAG = "PushMessageReceiver";
    @Override
    public void onMessage(Context context, CustomMessage customMessage) {
        Log.e(TAG,"[onMessage] "+customMessage);
        processCustomMessage(context,customMessage);
    }

    @Override
    public void onNotifyMessageOpened(Context context, NotificationMessage message) {
        Log.e(TAG,"[onNotifyMessageOpened] "+message);
        String extandJson = message.notificationExtras;
        if (extandJson != null) {
            JpushContentModel contentModel = JSON.parseObject(extandJson, JpushContentModel.class);
            JpushExtandOutModel activityModel = JSON.parseObject(contentModel.getContent(), JpushExtandOutModel.class);
            if (activityModel.isIsMaterials()) {
                // 打开APP商品
                openMerchandise(context, JSON.toJSONString(activityModel.getMaterialModel().getMaterialModel()));
            } else {
                // 打开H5
                openActivityH5(context, activityModel.getTitle(), activityModel.getPromotionDes(), activityModel.getLink());
            }
        }



        /*try{
            if (isRunningInForeground(context)) {
                //打开自定义的Activity
                *//*Bundle bundle = new Bundle();
                bundle.putString(JPushInterface.EXTRA_NOTIFICATION_TITLE,message.notificationTitle);
                bundle.putString(JPushInterface.EXTRA_ALERT,message.notificationContent);
                i.putExtras(bundle);*//*
                //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Intent i = new Intent(context, SplashActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
                context.startActivity(i);
            }
        }catch (Throwable throwable){

        }*/
    }

    /**
     * 打开商品详情
     * @param context
     * @param merchandiseJson
     */
    private void openMerchandise(Context context, String merchandiseJson) {
        //判断app进程是否存活
        if(YouCommonUtils.isAppAlive(context, "com.youcoupon.john_li.youcouponshopping")) {
            //如果存活的话，就直接启动DetailActivity，但要考虑一种情况，就是app的进程虽然仍然在
            //但Task栈已经空了，比如用户点击Back键退出应用，但进程还没有被系统回收，如果直接启动
            //DetailActivity,再按Back键就不会返回MainActivity了。所以在启动
            //DetailActivity前，要先启动MainActivity。
            Log.i("NotificationReceiver", "the app process is alive");
            Intent mainIntent = new Intent(context, MainActivity.class);
            //将MainAtivity的launchMode设置成SingleTask, 或者在下面flag中加上Intent.FLAG_CLEAR_TOP,
            //如果Task栈中有MainActivity的实例，就会把它移到栈顶，把在它之上的Activity都清理出栈，
            //如果Task栈不存在MainActivity实例，则在栈顶创建
            mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            Intent detailIntent = new Intent(context, MerchandiseDetialActivity.class);
            detailIntent.putExtra("MerchandiseModel", merchandiseJson);

            Intent[] intents = {mainIntent, detailIntent};
            context.startActivities(intents);
        }else {
            //如果app进程已经被杀死，先重新启动app，将DetailActivity的启动参数传入Intent中，参数经过
            //SplashActivity传入MainActivity，此时app的初始化已经完成，在MainActivity中就可以根据传入             //参数跳转到DetailActivity中去了
            Log.i("NotificationReceiver", "the app process is dead");
            Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage("com.youcoupon.john_li.youcouponshopping");
            launchIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
            Bundle args = new Bundle();
            args.putString("isH5", "0");
            args.putString("MerchandiseModel", merchandiseJson);
            launchIntent.putExtra(YouConfigor.EXTRA_BUNDLE, args);
            context.startActivity(launchIntent);
        }
    }

    /**
     * 打开活动H5
     * @param context
     * @param title
     * @param promotionDes
     * @param link
     */
    private void openActivityH5(Context context, String title, String promotionDes, String link) {
        //判断app进程是否存活
        if(YouCommonUtils.isAppAlive(context, "com.youcoupon.john_li.youcouponshopping")) {
            //如果存活的话，就直接启动DetailActivity，但要考虑一种情况，就是app的进程虽然仍然在
            //但Task栈已经空了，比如用户点击Back键退出应用，但进程还没有被系统回收，如果直接启动
            //DetailActivity,再按Back键就不会返回MainActivity了。所以在启动
            //DetailActivity前，要先启动MainActivity。
            Log.i("NotificationReceiver", "the app process is alive");
            Intent mainIntent = new Intent(context, MainActivity.class);
            //将MainAtivity的launchMode设置成SingleTask, 或者在下面flag中加上Intent.FLAG_CLEAR_TOP,
            //如果Task栈中有MainActivity的实例，就会把它移到栈顶，把在它之上的Activity都清理出栈，
            //如果Task栈不存在MainActivity实例，则在栈顶创建
            mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            Intent activityIntent = new Intent(context, WebH5Activity.class);
            activityIntent.putExtra("title", title);
            activityIntent.putExtra("webUrl", link);

            Intent[] intents = {mainIntent, activityIntent};
            context.startActivities(intents);
        }else {
            //如果app进程已经被杀死，先重新启动app，将DetailActivity的启动参数传入Intent中，参数经过
            //SplashActivity传入MainActivity，此时app的初始化已经完成，在MainActivity中就可以根据传入             //参数跳转到DetailActivity中去了
            Log.i("NotificationReceiver", "the app process is dead");
            Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage("com.youcoupon.john_li.youcouponshopping");
            launchIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
            Bundle args = new Bundle();
            args.putString("isH5", "1");
            args.putString("title", title);
            args.putString("webUrl", link);
            launchIntent.putExtra(YouConfigor.EXTRA_BUNDLE, args);
            context.startActivity(launchIntent);
        }
    }

    @Override
    public void onMultiActionClicked(Context context, Intent intent) {
        Log.e(TAG, "[onMultiActionClicked] 用户点击了通知栏按钮");
        String nActionExtra = intent.getExtras().getString(JPushInterface.EXTRA_NOTIFICATION_ACTION_EXTRA);
        //Toast.makeText(context, "用户点击了通知栏按钮", Toast.LENGTH_SHORT).show();

        //开发者根据不同 Action 携带的 extra 字段来分配不同的动作。
        if(nActionExtra==null){
            Log.d(TAG,"ACTION_NOTIFICATION_CLICK_ACTION nActionExtra is null");
            return;
        }
        if (nActionExtra.equals("my_extra1")) {
            Log.e(TAG, "[onMultiActionClicked] 用户点击通知栏按钮一");
        } else if (nActionExtra.equals("my_extra2")) {
            Log.e(TAG, "[onMultiActionClicked] 用户点击通知栏按钮二");
        } else if (nActionExtra.equals("my_extra3")) {
            Log.e(TAG, "[onMultiActionClicked] 用户点击通知栏按钮三");
        } else {
            Log.e(TAG, "[onMultiActionClicked] 用户点击通知栏按钮未定义");
        }
    }

    @Override
    public void onNotifyMessageArrived(Context context, NotificationMessage message) {
        Log.e(TAG,"[onNotifyMessageArrived] "+message);
    }

    @Override
    public void onNotifyMessageDismiss(Context context, NotificationMessage message) {
        Log.e(TAG,"[onNotifyMessageDismiss] "+message);
    }

    @Override
    public void onRegister(Context context, String registrationId) {
        Log.e(TAG,"[onRegister] "+registrationId);
    }

    @Override
    public void onConnected(Context context, boolean isConnected) {
        Log.e(TAG,"[onConnected] "+isConnected);
    }

    @Override
    public void onCommandResult(Context context, CmdMessage cmdMessage) {
        Log.e(TAG,"[onCommandResult] "+cmdMessage);
    }

    @Override
    public void onTagOperatorResult(Context context,JPushMessage jPushMessage) {
        TagAliasOperatorHelper.getInstance().onTagOperatorResult(context,jPushMessage);
        super.onTagOperatorResult(context, jPushMessage);
    }
    @Override
    public void onCheckTagOperatorResult(Context context,JPushMessage jPushMessage){
        TagAliasOperatorHelper.getInstance().onCheckTagOperatorResult(context,jPushMessage);
        super.onCheckTagOperatorResult(context, jPushMessage);
    }
    @Override
    public void onAliasOperatorResult(Context context, JPushMessage jPushMessage) {
        TagAliasOperatorHelper.getInstance().onAliasOperatorResult(context,jPushMessage);
        super.onAliasOperatorResult(context, jPushMessage);
    }

    @Override
    public void onMobileNumberOperatorResult(Context context, JPushMessage jPushMessage) {
        TagAliasOperatorHelper.getInstance().onMobileNumberOperatorResult(context,jPushMessage);
        super.onMobileNumberOperatorResult(context, jPushMessage);
    }

    //send msg to MainActivity
    private void processCustomMessage(Context context, CustomMessage customMessage) {
        /*if (MainActivity.isForeground) {
            String message = customMessage.message;
            String extras = customMessage.extra;
            Intent msgIntent = new Intent(MainActivity.MESSAGE_RECEIVED_ACTION);
            msgIntent.putExtra(MainActivity.KEY_MESSAGE, message);
            if (!ExampleUtil.isEmpty(extras)) {
                try {
                    JSONObject extraJson = new JSONObject(extras);
                    if (extraJson.length() > 0) {
                        msgIntent.putExtra(MainActivity.KEY_EXTRAS, extras);
                    }
                } catch (JSONException e) {

                }

            }
            LocalBroadcastManager.getInstance(context).sendBroadcast(msgIntent);
        }*/
    }

    @Override
    public void onNotificationSettingsCheck(Context context, boolean isOn, int source) {
        super.onNotificationSettingsCheck(context, isOn, source);
        Log.e(TAG,"[onNotificationSettingsCheck] isOn:"+isOn+",source:"+source);
    }
    /**
     * 判断程序是否打开
     * @return
     */
    public static boolean isRunningInForeground(Context context) {
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(context.getPackageName())) {
                /*
                BACKGROUND=400 EMPTY=500 FOREGROUND=100
                GONE=1000 PERCEPTIBLE=130 SERVICE=300 ISIBLE=200
                 */
                Log.i(context.getPackageName(), "此appimportace ="
                        + appProcess.importance
                        + ",context.getClass().getName()="
                        + context.getClass().getName());
                if (appProcess.importance != ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    Log.i(context.getPackageName(), "处于后台"
                            + appProcess.processName);
                    return true;
                } else {
                    Log.i(context.getPackageName(), "处于前台"
                            + appProcess.processName);
                    return false;
                }
            }
        }

        // 未打开APP
        return true;
    }
}
