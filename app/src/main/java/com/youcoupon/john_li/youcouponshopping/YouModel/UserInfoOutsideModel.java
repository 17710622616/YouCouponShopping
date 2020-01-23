package com.youcoupon.john_li.youcouponshopping.YouModel;

public class UserInfoOutsideModel {
    /**
     * status : 0
     * message : 获取用户信息成功！
     * data : {"mobile":"65999632","nick_name":"澳券宝宝9996","head_img":"https://pic.feizl.com/upload/allimg/190809/gxtxl14tghm2se4.jpg","birth_day":"","real_name":"","descx":"","sex":0,"id_card_no":"","address":"","balance":1237.25,"fxLevel":"","pid":"","childId":"","relationId":0}
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
         * mobile : 65999632
         * nick_name : 澳券宝宝9996
         * head_img : https://pic.feizl.com/upload/allimg/190809/gxtxl14tghm2se4.jpg
         * birth_day :
         * real_name :
         * descx :
         * sex : 0
         * id_card_no :
         * address :
         * balance : 1237.25
         * fxLevel :
         * pid :
         * childId :
         * relationId : 0
         */

        private String mobile;
        private String nick_name;
        private String head_img;
        private String birth_day;
        private String real_name;
        private String descx;
        private int sex;
        private String id_card_no;
        private String address;
        private double balance;
        private String fxLevel;
        private String pid;
        private String childId;
        private long relationId;
        private String inviteCode;
        private String inviteStatus;

        public String getInviteCode() {
            return inviteCode;
        }

        public void setInviteCode(String inviteCode) {
            this.inviteCode = inviteCode;
        }

        public String getInviteStatus() {
            return inviteStatus;
        }

        public void setInviteStatus(String inviteStatus) {
            this.inviteStatus = inviteStatus;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getNick_name() {
            return nick_name;
        }

        public void setNick_name(String nick_name) {
            this.nick_name = nick_name;
        }

        public String getHead_img() {
            return head_img;
        }

        public void setHead_img(String head_img) {
            this.head_img = head_img;
        }

        public String getBirth_day() {
            return birth_day;
        }

        public void setBirth_day(String birth_day) {
            this.birth_day = birth_day;
        }

        public String getReal_name() {
            return real_name;
        }

        public void setReal_name(String real_name) {
            this.real_name = real_name;
        }

        public String getDescx() {
            return descx;
        }

        public void setDescx(String descx) {
            this.descx = descx;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public String getId_card_no() {
            return id_card_no;
        }

        public void setId_card_no(String id_card_no) {
            this.id_card_no = id_card_no;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public double getBalance() {
            return balance;
        }

        public void setBalance(double balance) {
            this.balance = balance;
        }

        public String getFxLevel() {
            return fxLevel;
        }

        public void setFxLevel(String fxLevel) {
            this.fxLevel = fxLevel;
        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public String getChildId() {
            return childId;
        }

        public void setChildId(String childId) {
            this.childId = childId;
        }

        public long getRelationId() {
            return relationId;
        }

        public void setRelationId(long relationId) {
            this.relationId = relationId;
        }
    }
}
