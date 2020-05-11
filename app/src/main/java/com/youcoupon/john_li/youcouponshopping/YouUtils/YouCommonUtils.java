package com.youcoupon.john_li.youcouponshopping.YouUtils;

import android.content.Context;
import android.provider.ContactsContract;
import android.util.DisplayMetrics;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by John_Li on 2/4/2019.
 */

public class YouCommonUtils {

    public static String createLinkStringByGet(Map<String, String> params) {
        String prestr = "";
        try {
            List<String> keys = new ArrayList<String>(params.keySet());
            Collections.sort(keys);
            for (int i = 0; i < keys.size(); i++) {
                String key = keys.get(i);
                String value = params.get(key);
                value = URLEncoder.encode(value, "UTF-8");

                if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
                    prestr = prestr + key + "=" + value;
                } else {
                    prestr = prestr + key + "=" + value + "&";
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return prestr;
    }


    /**
     * 获取屏幕密度
     * @param context
     * @return
     */
    public static int getDeviceWitdh(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        dm = context.getResources().getDisplayMetrics();

        return dm.widthPixels;      // 屏幕宽（像素，如：480px）
    }

    /**
     * 获取屏幕宽度(px)
     */
    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 獲取今日時間
     * @return
     */
    public static String getTimeToday() {
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(now);
    }

    /**
     * 獲取今日時間
     * @return
     */
    public static String getTimeNow() {
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(now);
    }

    /**
     * 比较两个时间大小
     */
    public static boolean compareTwoTimes(String overTimeString) {
        boolean compareResult = true;
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        try {
            Date timeNow = new Date();
            Date overTime = format.parse(overTimeString);
            int compareTo = timeNow.compareTo(overTime);
            if (compareTo != 1) {
                compareResult = false;
            }
        } catch (Exception e) {
            compareResult = true;
        }

        return compareResult;
    }
}
