package com.youcoupon.john_li.youcouponshopping.YouUtils;

import org.xutils.common.Callback;

public class FileDownloadCallBack<ResultType> implements Callback.CommonCallback<ResultType> {

    @Override
    public void onSuccess(ResultType result) {
        //根据需求进行请求成功的逻辑处理
    }

    @Override
    public void onError(Throwable ex, boolean isOnCallback) {
        //根据需求进行请求网络失败的逻辑处理
    }

    @Override
    public void onCancelled(CancelledException cex) {

    }

    @Override
    public void onFinished() {

    }
}