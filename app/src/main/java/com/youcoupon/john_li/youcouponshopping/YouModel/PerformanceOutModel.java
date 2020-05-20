package com.youcoupon.john_li.youcouponshopping.YouModel;

public class PerformanceOutModel {
    /**
     * status : 0
     * message : 获取本月预估绩效成功！
     * data : {"totalCount":0,"commisonAmount":"0"}
     */

    private int status;
    private String message;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * totalCount : 0
         * commisonAmount : 0
         */

        private long totalCount;
        private String commisonAmount;

        public long getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(long totalCount) {
            this.totalCount = totalCount;
        }

        public String getCommisonAmount() {
            return commisonAmount;
        }

        public void setCommisonAmount(String commisonAmount) {
            this.commisonAmount = commisonAmount;
        }
    }
}
