package com.youcoupon.john_li.youcouponshopping.YouActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.io.File;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.gyf.immersionbar.ImmersionBar;
import com.youcoupon.john_li.youcouponshopping.R;
import com.youcoupon.john_li.youcouponshopping.YouModel.UserInfoOutsideModel;
import com.youcoupon.john_li.youcouponshopping.YouUtils.PhotoUtils;
import com.youcoupon.john_li.youcouponshopping.YouUtils.SPUtils;
import com.youcoupon.john_li.youcouponshopping.YouUtils.YouCommonUtils;
import com.youcoupon.john_li.youcouponshopping.YouUtils.YouConfigor;
import com.youcoupon.john_li.youcouponshopping.YouView.YouHeadView;

public class UserInfoActivity extends BaseActivity implements View.OnClickListener {
    private YouHeadView headView;
    private ImageView headImgIv;
    private LinearLayout headImgLL, nameLL,sexLL, birthdayLL, areaLL;
    private TextView nameTv,sexTv, birthdayTv, areaTv, saveTv;
    private ProgressDialog dialog;

    private File dir; //圖片文件夾路徑
    private File fileUri;//照片文件路徑
    private Uri imageUri;//照片文件路徑
    private static final int CODE_GALLERY_REQUEST = 2;   //0xa0
    private static final int CODE_CAMERA_REQUEST = 1;    //0xa1
    private static final int CAMERA_PERMISSIONS_REQUEST_CODE = 0x03;
    private static final int STORAGE_PERMISSIONS_REQUEST_CODE = 0x04;
    private UserInfoOutsideModel.DataBean mUserInfoModel;
    private ImageOptions options = new ImageOptions.Builder().setSize(0, 0).setLoadingDrawableId(R.mipmap.head_iimg).setFailureDrawableId(R.mipmap.head_iimg).build();
    //private OSSClient oss;
    private AsyncTask asyncTask;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:     // 頭像提交至OSS成功
                    mUserInfoModel.setHead_img(fileUri.getName());
                    Toast.makeText(UserInfoActivity.this, "上傳頭像成功！", Toast.LENGTH_LONG).show();
                    break;
                case -1:    // 頭像提交至OSS失敗
                    Toast.makeText(UserInfoActivity.this, "上传头像失败！", Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        initView();
        setListener();
        initData();
    }

    @Override
    public void initView() {
        ImmersionBar.with(this).init();
        headView = findViewById(R.id.user_info_head);
        headImgIv = findViewById(R.id.userinfo_headimg_iv);
        nameTv = findViewById(R.id.userinfo_name_tv);
        sexTv = findViewById(R.id.userinfo_sex_tv);
        birthdayTv = findViewById(R.id.userinfo_birthday_tv);
        areaTv = findViewById(R.id.userinfo_flag_tv);
        saveTv = findViewById(R.id.userinfo_save_btn);
        nameLL = findViewById(R.id.userinfo_name_ll);
        sexLL = findViewById(R.id.userinfo_sex_ll);
        birthdayLL = findViewById(R.id.userinfo_birthday_ll);
        areaLL = findViewById(R.id.userinfo_flag_ll);
        headImgLL = findViewById(R.id.userinfo_headimg_ll);
    }

    @Override
    public void setListener() {
        saveTv.setOnClickListener(this);
        nameLL.setOnClickListener(this);
        sexLL.setOnClickListener(this);
        birthdayLL.setOnClickListener(this);
        areaLL.setOnClickListener(this);
        headImgLL.setOnClickListener(this);
    }

    @Override
    public void initData() {
        headView.setTitle("个人中心");
        headView.setLeft(this);
        //oss = AliyunOSSUtils.initOSS(this);
        String userInfoJson = (String) SPUtils.get(this, "UserInfo", "");
        if (!userInfoJson.equals("")){
            mUserInfoModel = JSON.parseObject(userInfoJson, UserInfoOutsideModel.DataBean.class);
            nameTv.setText(String.valueOf(mUserInfoModel.getNick_name()));
            switch (mUserInfoModel.getSex()) {
                case 0:
                    sexTv.setText("男");
                    break;
                case 1:
                    sexTv.setText("女");
                    break;
                case 2:
                    sexTv.setText("保密");
                    break;
            }
            if (mUserInfoModel.getBirth_day().equals("")) {
                birthdayTv.setText("0000-00-00");
            } else {
                birthdayTv.setText(mUserInfoModel.getBirth_day());
            }
            birthdayTv.setText(mUserInfoModel.getBirth_day());
            areaTv.setText(mUserInfoModel.getAddress());
            x.image().bind(headImgIv, mUserInfoModel.getHead_img(), options);
        } else {
            mUserInfoModel = new UserInfoOutsideModel.DataBean();
            nameTv.setText("");
            sexTv.setText("保密");
            birthdayTv.setText("0000-00-00");
            areaTv.setText("unknow");
            headImgIv.setImageResource(R.mipmap.ic_launcher_round);
        }

        saveTv.setEnabled(true);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.head_left:
                finish();
                break;
            case R.id.userinfo_name_ll:
                updateUserName();
                break;
            case R.id.userinfo_sex_ll:
                updateSex();
                break;
            case R.id.userinfo_birthday_ll:
                updateBirthday();
                break;
            case R.id.userinfo_flag_ll:
                updateFlag();
                break;
            case R.id.userinfo_headimg_ll:
                updateHeadImg();
                break;
            case R.id.userinfo_save_btn:
                callNetSaveUserInfo();
                break;
        }
    }

    private void callNetSaveUserInfo() {
        dialog = new ProgressDialog(this);
        dialog.setTitle("提示");
        dialog.setMessage("正在修改中......");
        dialog.setCancelable(false);
        dialog.show();
        RequestParams params = new RequestParams(YouConfigor.BASE_URL + YouConfigor.UPDATE_USER_INFO);
        params.setAsJsonContent(true);
        params.addQueryStringParameter("nickname", String.valueOf(mUserInfoModel.getNick_name()));
        params.addQueryStringParameter("headimg", String.valueOf(mUserInfoModel.getHead_img()));
        params.addQueryStringParameter("birthday", String.valueOf(mUserInfoModel.getBirth_day()));
        params.addQueryStringParameter("realName", String.valueOf(mUserInfoModel.getAddress()));
        params.addQueryStringParameter("descx", String.valueOf(mUserInfoModel.getDescx()));
        params.addQueryStringParameter("idCardNo", String.valueOf(mUserInfoModel.getId_card_no()));
        params.addQueryStringParameter("address", String.valueOf(mUserInfoModel.getAddress()));
        params.addHeader("token", String.valueOf(SPUtils.get(this, "UserToken", "")));
        String uri = params.getUri();
        params.setConnectTimeout(30 * 1000);
        x.http().request(HttpMethod.POST, params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                UserInfoOutsideModel model =  JSON.parseObject(result.toString(), UserInfoOutsideModel.class);
                if (model.getStatus() == 0) {
                    if (model.getData().getNick_name() == null) {
                        model.getData().setNick_name("用戶");
                    }
                    if (model.getData().getAddress() == null) {
                        model.getData().setAddress("");
                    }
                    if (model.getData().getReal_name() == null) {
                        model.getData().setReal_name("");
                    }
                    if (model.getData().getAddress() == null) {
                        model.getData().setAddress("");
                    }
                    if (model.getData().getDescx() == null) {
                        model.getData().setDescx("");
                    }
                    if (model.getData().getHead_img() == null) {
                        model.getData().setHead_img("");
                    }
                    if (model.getData().getBirth_day() == null) {
                        model.getData().setBirth_day("");
                    }
                    if (model.getData().getInviteCode() == null) {
                        model.getData().setBirth_day("");
                    }
                    String userInfoJson = JSON.toJSONString(model.getData());
                    SPUtils.put(UserInfoActivity.this, "UserInfo", userInfoJson);
                    EventBus.getDefault().post("LOGIN");
                    Toast.makeText(UserInfoActivity.this, "修改用户信息成功", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Log.d("getUserURI", "獲取用戶信息失敗");
                    Toast.makeText(UserInfoActivity.this, "修改用戶信息失敗", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                if (ex instanceof java.net.SocketTimeoutException) {
                    Toast.makeText(UserInfoActivity.this, "网络连接超时", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(UserInfoActivity.this, "请求异常", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                dialog.dismiss();
            }
        });
    }

    private void updateUserName() {
        final EditText et = new EditText(this);
        new AlertDialog.Builder(this).setTitle("请输入用户名")
                .setIcon(R.mipmap.logo)
                .setView(et)
                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //按下确定键后的事件
                        nameTv.setText(et.getText().toString());
                        mUserInfoModel.setNick_name(et.getText().toString());
                    }
                }).setNegativeButton("否",null).show();
    }

    private void updateSex() {
        AlertDialog.Builder builder = new AlertDialog.Builder(UserInfoActivity.this);
        LayoutInflater inflater = LayoutInflater.from(UserInfoActivity.this);
        View v = inflater.inflate(R.layout.dialog_sex, null);
        RadioGroup rg = (RadioGroup) v.findViewById(R.id.dialog_sex_rg);
        TextView yesTv = (TextView) v.findViewById(R.id.dialog_sex_yes);
        //builer.setView(v);//这里如果使用builer.setView(v)，自定义布局只会覆盖title和button之间的那部分
        final Dialog dialog = builder.create();
        dialog.show();
        dialog.getWindow().setContentView(v);//自定义布局应该在这里添加，要在dialog.show()的后面
        //dialog.getWindow().setGravity(Gravity.CENTER);//可以设置显示的位置
        yesTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.dialog_man_rb:
                        mUserInfoModel.setSex(0);
                        sexTv.setText("男");
                        break;
                    case R.id.dialog_woman_rb:
                        mUserInfoModel.setSex(1);
                        sexTv.setText("女");
                        break;
                    case R.id.dialog_secrecy_rb:
                        mUserInfoModel.setSex(2);
                        sexTv.setText("保密");
                        break;
                    default:
                        mUserInfoModel.setSex(2);
                        sexTv.setText("保密");
                        break;
                }
            }
        });
    }

    private void updateBirthday() {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(UserInfoActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth){
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        String dateStr = sdf.format(calendar.getTime());
                        birthdayTv.setText(dateStr);
                        mUserInfoModel.setBirth_day(dateStr);
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }

    private void updateFlag() {

    }

    private void updateHeadImg() {
        AlertDialog.Builder builder = new AlertDialog.Builder(UserInfoActivity.this);
        LayoutInflater inflater = LayoutInflater.from(UserInfoActivity.this);
        View v = inflater.inflate(R.layout.dialog_photo, null);
        TextView camareTv = (TextView) v.findViewById(R.id.photo_camare);
        TextView albumTv = (TextView) v.findViewById(R.id.photo_album);
        TextView cancelTv = (TextView) v.findViewById(R.id.photo_cancel);
        //builer.setView(v);//这里如果使用builer.setView(v)，自定义布局只会覆盖title和button之间的那部分
        final Dialog dialog = builder.create();
        dialog.show();
        dialog.getWindow().setContentView(v);//自定义布局应该在这里添加，要在dialog.show()的后面
        //dialog.getWindow().setGravity(Gravity.CENTER);//可以设置显示的位置
        camareTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autoObtainCameraPermission();
                dialog.dismiss();
            }
        });
        albumTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autoObtainStoragePermission();
                dialog.dismiss();
            }
        });
        cancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    /**
     * 自动获取相机权限
     */
    private void autoObtainCameraPermission() {
        try {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                    Toast.makeText(UserInfoActivity.this, "您已经拒绝过一次", Toast.LENGTH_SHORT).show();
                }
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, CAMERA_PERMISSIONS_REQUEST_CODE);
            } else {//有权限直接调用系统相机拍照
                SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
                Date date = new Date(System.currentTimeMillis());
                fileUri = new File(dir.getPath() + "/headImg" + format.format(date) + ".jpg");
                imageUri = Uri.fromFile(fileUri);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                    imageUri = FileProvider.getUriForFile(UserInfoActivity.this, "com.youcoupon.john_li.youcouponshopping" + ".fileprovider", fileUri);//通过FileProvider创建一个content类型的Uri
                }

                PhotoUtils.takePicture(this, imageUri, CODE_CAMERA_REQUEST);
            }
        }catch (Exception e) {

        }
    }

    /**
     * 自动获取相冊权限
     */
    private void autoObtainStoragePermission() {
        // 使用意图直接调用手机相册  
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // 打开手机相册,设置请求码  
        startActivityForResult(intent, CODE_GALLERY_REQUEST);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case CAMERA_PERMISSIONS_REQUEST_CODE: {//调用系统相机申请拍照权限回调
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
                    Date date = new Date(System.currentTimeMillis());
                    fileUri = new File(dir.getPath() + "head" + format.format(date) + ".jpg");
                    imageUri = Uri.fromFile(fileUri);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                        imageUri = FileProvider.getUriForFile(UserInfoActivity.this, "com.youcoupon.john_li.youcouponshopping" + ".fileprovider", fileUri);//通过FileProvider创建一个content类型的Uri
                    PhotoUtils.takePicture(this, imageUri, CODE_CAMERA_REQUEST);
                } else {
                    Toast.makeText(UserInfoActivity.this, "请允许打开相机", Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case STORAGE_PERMISSIONS_REQUEST_CODE://调用系统相册申请Sdcard权限回调
                // 使用意图直接调用手机相册  
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                // 打开手机相册,设置请求码  
                startActivityForResult(intent, CODE_GALLERY_REQUEST);
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            Toast.makeText(UserInfoActivity.this, "影相失敗！", Toast.LENGTH_SHORT).show();
            return;
        }
        switch(requestCode) {
            case CODE_CAMERA_REQUEST:
                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                Uri contentUri = Uri.fromFile(fileUri);
                mediaScanIntent.setData(contentUri);
                sendBroadcast(mediaScanIntent);
                mUserInfoModel.setHead_img(fileUri.getPath());
                // 上傳頭像
                //putImg();
                postImg(fileUri.getPath());

                x.image().bind(headImgIv, mUserInfoModel.getHead_img(), options);
                break;
            case CODE_GALLERY_REQUEST:
                Uri uri = data.getData();
                String imagePath = YouCommonUtils.getRealFilePath(this, uri);
                fileUri = new File(imagePath);
                mUserInfoModel.setHead_img(fileUri.getPath());
                // 上傳頭像
                //putImg();
                postImg(fileUri.getPath());
                x.image().bind(headImgIv, mUserInfoModel.getHead_img(), options);
                break;
            default:
                break;
        }
    }

    private void postImg(String filePath) {
        callNetUpdateHeadImg(filePath);
        /*RequestParams params = new RequestParams(YouConfigor.BASE_URL + YouConfigor.UPLOAD_FILE);
        // 添加到请求body体的参数, 只有POST, PUT, PATCH, DELETE请求支持.
        // params.addBodyParameter("wd", "xUtils");
        // 使用multipart表单上传文件
        params.setMultipart(true);
        params.addHeader("token", String.valueOf(SPUtils.get(UserInfoActivity.this, "UserToken", "")));
        File img = new File(filePath);
        params.addBodyParameter("file", img, "multipart/form-data"); // 如果文件没有扩展名, 最好设置contentType参数.
        x.http().post(params, new Callback.CommonCallback<JSONObject>() {
            @Override
            public void onSuccess(JSONObject result) {
                UploadFileOutModel model =  JSON.parseObject(result.toString(), UploadFileOutModel.class);
                if (model.getStatus() == 0) {
                    mUserInfoModel.setHead_img(fileUri.getName());
                    callNetUpdateHeadImg(model.getMsg());
                    //Toast.makeText(UserInfoActivity.this, "上傳頭像成功！", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(UserInfoActivity.this, "上传头像失败！", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                if (ex instanceof java.net.SocketTimeoutException) {
                    Toast.makeText(UserInfoActivity.this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(UserInfoActivity.this, getString(R.string.request_error), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {
            }

        });*/
    }

    private void callNetUpdateHeadImg(String imgUrl) {
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("imgUrl", imgUrl);
        paramsMap.put("token", String.valueOf(SPUtils.get(this, "UserToken", "")));
        RequestParams params = new RequestParams(YouConfigor.BASE_URL + YouConfigor.UPDATE_HEAD_IMG + YouCommonUtils.createLinkStringByGet(paramsMap));
        //params.addHeader("token", String.valueOf(SPUtils.get(this, "UserToken", "")));
        params.setConnectTimeout(30 * 1000);
        x.http().request(HttpMethod.GET, params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                UserInfoOutsideModel model =  JSON.parseObject(result.toString(), UserInfoOutsideModel.class);
                if (model.getStatus() == 0) {
                    if (model.getData().getNick_name() == null) {
                        model.getData().setNick_name("用戶");
                    }
                    if (model.getData().getAddress() == null) {
                        model.getData().setAddress("");
                    }
                    if (model.getData().getReal_name() == null) {
                        model.getData().setReal_name("");
                    }
                    if (model.getData().getAddress() == null) {
                        model.getData().setAddress("");
                    }
                    if (model.getData().getDescx() == null) {
                        model.getData().setDescx("");
                    }
                    if (model.getData().getHead_img() == null) {
                        model.getData().setHead_img("");
                    }
                    if (model.getData().getBirth_day() == null) {
                        model.getData().setBirth_day("");
                    }
                    if (model.getData().getInviteCode() == null) {
                        model.getData().setBirth_day("");
                    }
                    String userInfoJson = JSON.toJSONString(model.getData());
                    SPUtils.put(UserInfoActivity.this, "UserInfo", userInfoJson);
                    EventBus.getDefault().post("LOGIN");
                    Toast.makeText(UserInfoActivity.this, "上传头像成功", Toast.LENGTH_LONG).show();
                } else {
                    Log.d("getUserURI", "獲取用戶信息失敗");
                    Toast.makeText(UserInfoActivity.this, "獲取用戶信息失敗", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                if (ex instanceof java.net.SocketTimeoutException) {
                    Toast.makeText(UserInfoActivity.this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(UserInfoActivity.this, getString(R.string.request_error), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }
}
