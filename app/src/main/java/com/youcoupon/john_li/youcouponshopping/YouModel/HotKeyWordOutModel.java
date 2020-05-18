package com.youcoupon.john_li.youcouponshopping.YouModel;

import java.util.List;

public class HotKeyWordOutModel {
    /**
     * status : 0
     * message : 获取关键词列表成功！
     * data : [{"seq":1,"keyWord":"女装"},{"seq":2,"keyWord":"低脂零食"},{"seq":3,"keyWord":"口罩"},{"seq":4,"keyWord":"口红"},{"seq":5,"keyWord":"面膜"},{"seq":6,"keyWord":"螺蛳粉"},{"seq":7,"keyWord":"耳机"},{"seq":8,"keyWord":"电动牙刷"},{"seq":9,"keyWord":"洗发水"},{"seq":10,"keyWord":"唇釉"}]
     */

    private int status;
    private String message;
    private List<DataBean> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * seq : 1
         * keyWord : 女装
         */

        private int seq;
        private String keyWord;

        public int getSeq() {
            return seq;
        }

        public void setSeq(int seq) {
            this.seq = seq;
        }

        public String getKeyWord() {
            return keyWord;
        }

        public void setKeyWord(String keyWord) {
            this.keyWord = keyWord;
        }
    }
}
