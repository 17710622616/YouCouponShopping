package com.youcoupon.john_li.youcouponshopping.YouModel;

import java.util.List;

public class WithDrawalDetialModel {
    /**
     * status : 0
     * message : 获取钱包记录成功!
     * data : {"walletRecordList":[{"amount":10,"createTime":"2020-05-18 18:09:43","flag":1,"inOutFrom":1,"des":"用户提现","userId":7}],"totalCount":5}
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
         * walletRecordList : [{"amount":10,"createTime":"2020-05-18 18:09:43","flag":1,"inOutFrom":1,"des":"用户提现","userId":7}]
         * totalCount : 5
         */

        private long totalCount;
        private List<WalletRecordListBean> walletRecordList;

        public long getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(long totalCount) {
            this.totalCount = totalCount;
        }

        public List<WalletRecordListBean> getWalletRecordList() {
            return walletRecordList;
        }

        public void setWalletRecordList(List<WalletRecordListBean> walletRecordList) {
            this.walletRecordList = walletRecordList;
        }

        public static class WalletRecordListBean {
            /**
             * amount : 10
             * createTime : 2020-05-18 18:09:43
             * flag : 1
             * inOutFrom : 1
             * des : 用户提现
             * userId : 7
             */
            private long id;
            private double amount;
            private String createTime;
            private int flag;
            private int inOutFrom;
            private String des;
            private int userId;

            public long getId() {
                return id;
            }

            public void setId(long id) {
                this.id = id;
            }

            public double getAmount() {
                return amount;
            }

            public void setAmount(double amount) {
                this.amount = amount;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public int getFlag() {
                return flag;
            }

            public void setFlag(int flag) {
                this.flag = flag;
            }

            public int getInOutFrom() {
                return inOutFrom;
            }

            public void setInOutFrom(int inOutFrom) {
                this.inOutFrom = inOutFrom;
            }

            public String getDes() {
                return des;
            }

            public void setDes(String des) {
                this.des = des;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }
        }
    }
}
