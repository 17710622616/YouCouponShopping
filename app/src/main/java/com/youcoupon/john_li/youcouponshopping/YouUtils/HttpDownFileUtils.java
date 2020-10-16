package com.youcoupon.john_li.youcouponshopping.YouUtils;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import com.google.zxing.common.StringUtils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
/**
 * @description:图片，视频下载工具
 */
public class HttpDownFileUtils {
    //public static final String IMAGE_SDCARD_MADER = getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/YouCoupon/";

    /**
     * 下载文件
     */
    public static <T> Callback.Cancelable DownLoadFile(String url, String filepath, Callback.CommonCallback<T> callback) {
        RequestParams params = new RequestParams(url);
        //设置断点续传
        params.setAutoResume(true);
        params.setSaveFilePath(filepath);
        Callback.Cancelable cancelable = x.http().get(params, callback);
        return cancelable;
    }

    /**
     * 获取网络图片
     * @param imageurl 图片网络地址
     * @return Bitmap 返回位图
     */
    public static Bitmap GetImageInputStream(Context context, String imageurl){
        URL url;
        HttpURLConnection connection=null;
        Bitmap bitmap=null;
        try {
            url = new URL(imageurl);
            connection=(HttpURLConnection)url.openConnection();
            connection.setConnectTimeout(6000); //超时设置
            connection.setDoInput(true);
            connection.setUseCaches(false); //设置不使用缓存
            InputStream inputStream=connection.getInputStream();
            bitmap= BitmapFactory.decodeStream(inputStream);
            saveBitmap(context, bitmap, geFileName());
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * 将bitmap存成文件
     *
     * @param context
     * @param bitmap
     * @param imageName
     */
    public static String saveBitmap(Context context, Bitmap bitmap, String imageName) {
        FileOutputStream fos = null;
        OutputStream os = null;
        BufferedInputStream inputStream = null;
        File imageFile = null;
        try {
            //生成路径
//            File filePath = new File(Environment.getExternalStorageDirectory().getAbsolutePath(),APP_FOLDER_PHOTO);
            File filePath = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)+"/"+ context.getPackageName()+"/photo/");
            Log.e("aaa->"," filePath:" + filePath.getPath() + " fileAbsolutePath:" + filePath.getAbsolutePath());
            if (!filePath.exists()) {
                boolean is = filePath.mkdirs();
                Log.e("aaa->","is: " + is);
            }

            //获取文件
            imageFile = new File(filePath, imageName);
            fos = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();

            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.DESCRIPTION, "This is an image");
            values.put(MediaStore.Images.Media.DISPLAY_NAME, imageName);
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/png");
            values.put(MediaStore.Images.Media.TITLE, "Image.png");
            values.put("relative_path", "Pictures/");
            Uri external = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            ContentResolver resolver = context.getContentResolver();
            Uri insertUri = resolver.insert(external, values);

            inputStream = new BufferedInputStream(new FileInputStream(imageFile));
            if (insertUri != null) {
                os = resolver.openOutputStream(insertUri);
            }
            if (os != null) {
                byte[] buffer = new byte[1024 * 4];
                int len;
                while ((len = inputStream.read(buffer)) != -1) {
                    os.write(buffer, 0, len);
                }
                os.flush();
            }

            //通知系统相册刷新
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                    Uri.fromFile(imageFile)));
            return imageFile.getPath();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
                if (os != null) {
                    os.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
                imageFile.delete();// 这里删除源文件不存在 但相册可见
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取声音文件名字
     *
     * @return 假如当前录制声音时间是2016年6月1日11点30分30秒。得到的文件名字就是20160601113030.这样保证文件名的唯一性
     */
    public static String geFileName() {
        long getNowTimeLong = System.currentTimeMillis();
        String result = String.valueOf(getNowTimeLong);
        return result + "Item.png";
    }

    public static void createDirIfNotExist(File file) {
        if (!file.exists()) {
            try {
                file.mkdirs();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
