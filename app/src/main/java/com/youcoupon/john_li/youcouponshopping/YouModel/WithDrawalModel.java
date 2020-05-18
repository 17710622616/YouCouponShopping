package com.youcoupon.john_li.youcouponshopping.YouModel;

public class WithDrawalModel {

    public long userId;      //用户id
    public double money;         //提现金额
    public String createtime;          //创建时间
    public int status;           //0未审核 1已打款 2拒绝
    public int type;             //1 支付宝 2银行 3微信
    public String realName;          //真实姓名
    public String phoneNumber;       //联系方式
    public String bankName;          //银行名称
    public String bankCardNo;        //银行卡号
    public String weixinAccount;         //微信账号
    public String zfbAccount;            //支付宝账号
    public double rate;              //手续费率
    public double realmoney;         //实际打款金额

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankCardNo() {
        return bankCardNo;
    }

    public void setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo;
    }

    public String getWeixinAccount() {
        return weixinAccount;
    }

    public void setWeixinAccount(String weixinAccount) {
        this.weixinAccount = weixinAccount;
    }

    public String getZfbAccount() {
        return zfbAccount;
    }

    public void setZfbAccount(String zfbAccount) {
        this.zfbAccount = zfbAccount;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public double getRealmoney() {
        return realmoney;
    }

    public void setRealmoney(double realmoney) {
        this.realmoney = realmoney;
    }
}
