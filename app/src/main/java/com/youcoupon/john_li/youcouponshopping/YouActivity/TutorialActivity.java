package com.youcoupon.john_li.youcouponshopping.YouActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.youcoupon.john_li.youcouponshopping.R;
import com.youcoupon.john_li.youcouponshopping.YouView.YouHeadView;

import org.xutils.image.ImageOptions;
import org.xutils.x;

public class TutorialActivity extends BaseActivity implements View.OnClickListener {
    private YouHeadView headView;
    private ImageView tutorialIv;
    private ImageOptions options = new ImageOptions.Builder().setSize(0, 0).setLoadingDrawableId(R.mipmap.img_loading).setFailureDrawableId(R.mipmap.load_img_fail).build();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        initView();
        setListener();
        initData();
    }

    @Override
    public void initView() {
        headView = findViewById(R.id.tutorial_head);
        tutorialIv = findViewById(R.id.tutorial_iv);
    }

    @Override
    public void setListener() {
    }

    @Override
    public void initData() {
        headView.setTitle("新手教程");
        headView.setLeft(this);

        x.image().bind(tutorialIv, "http://test-pic-666.oss-cn-hongkong.aliyuncs.com/0TaskReward/introduction/tutorial.jpg", options);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.head_left:
                finish();
                break;
        }
    }
}
