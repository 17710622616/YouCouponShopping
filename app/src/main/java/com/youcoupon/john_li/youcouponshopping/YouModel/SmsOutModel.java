package com.youcoupon.john_li.youcouponshopping.YouModel;

public class SmsOutModel {
    private int status;
    private String message;
    private SmsModel data;

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

    public SmsModel getData() {
        return data;
    }

    public void setData(SmsModel data) {
        this.data = data;
    }

    public class SmsModel {
        private String code;
        private String overTime;
        private String tel;

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getOverTime() {
            return overTime;
        }

        public void setOverTime(String overTime) {
            this.overTime = overTime;
        }
    }
}
