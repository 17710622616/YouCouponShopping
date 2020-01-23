package com.youcoupon.john_li.youcouponshopping.YouModel;

import java.util.List;

/**
 * Created by John_Li on 2019/10/31.
 */

public class UserModel {
    //昵称
    private String nickname;
    //国家
    private String country;
    // 生日
    private String birth_day;
    // 真实姓名
    private String real_name;
    // 个人简介
    private String descx;
    // 身份证号
    private String id_card_no;
    // 地址
    private String address;
    // 积分
    private double integral;
    // 余额
    private double balance;
    // 支付密码
    private String pay_pwd;
    // 邀请码
    private String invite_code;
    // 邀请人
    private String inviter;
    // 邀请状态
    private String invite_status;
    // 性别
    private Integer gender;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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

    public double getIntegral() {
        return integral;
    }

    public void setIntegral(double integral) {
        this.integral = integral;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getPay_pwd() {
        return pay_pwd;
    }

    public void setPay_pwd(String pay_pwd) {
        this.pay_pwd = pay_pwd;
    }

    public String getInvite_code() {
        return invite_code;
    }

    public void setInvite_code(String invite_code) {
        this.invite_code = invite_code;
    }

    public String getInviter() {
        return inviter;
    }

    public void setInviter(String inviter) {
        this.inviter = inviter;
    }

    public String getInvite_status() {
        return invite_status;
    }

    public void setInvite_status(String invite_status) {
        this.invite_status = invite_status;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }
}
