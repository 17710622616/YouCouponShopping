package com.youcoupon.john_li.youcouponshopping.YouModel;

import java.util.List;

public class UserOrderOutModel {
    /**
     * status : 0
     * message : 获取自己的订单列表成功！2360417743
     * data : {"orderList":[{"id":0,"tb_paid_time":"2020-01-17 18:27:02","tk_paid_time":"2020-01-17 18:27:12","pay_price":"","pub_share_fee":"0.00","trade_id":"821039008321125986","tk_order_role":2,"tk_earning_time":"","adzone_id":109946850496,"pub_share_rate":"0.00","unid":0,"refund_tag":0,"subsidy_rate":"0.00","tk_total_rate":"30.00","item_category_name":"节庆用品/礼品","seller_nick":"飞可家园幸福商场","pub_id":132021823,"alimama_rate":"10.00","subsidy_type":"--","item_img":"//img.alicdn.com/bao/uploaded/i2/1634673647/O1CN01qvjW5N1coN1hpskmr_!!1634673647.jpg","pub_share_pre_fee":"1.14","alipay_total_price":"3.80","item_title":"对联大礼包2020春节鼠年烫金高档新年春联红纸大门过年对联纸企业","site_name":"YouShopping","item_num":1,"subsidy_fee":"0.00","alimama_share_fee":"0.11","trade_parent_id":"821039008321125986","order_type":"淘宝","tk_create_time":"2020-01-17 18:27:07","flow_source":"--","terminal_type":"无线","click_time":"2020-01-17 18:26:37","tk_status":12,"item_price":"10.80","item_id":607309460202,"adzone_name":"YouCouponChannel","total_commission_rate":"30.00","item_link":"http://item.taobao.com/item.htm?id=607309460202","site_id":45408225,"seller_shop_title":"尚都小屋","income_rate":"30.00","total_commission_fee":"0.00","tk_commission_pre_fee_for_media_platform":"0.00","tk_commission_fee_for_media_platform":"0.00","tk_commission_rate_for_media_platform":"0.00","special_id":0,"relation_id":2360417743,"deposit_price":"0.00","tb_deposit_time":"--","tk_deposit_time":"--"}],"totalCount":13}
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
         * orderList : [{"id":0,"tb_paid_time":"2020-01-17 18:27:02","tk_paid_time":"2020-01-17 18:27:12","pay_price":"","pub_share_fee":"0.00","trade_id":"821039008321125986","tk_order_role":2,"tk_earning_time":"","adzone_id":109946850496,"pub_share_rate":"0.00","unid":0,"refund_tag":0,"subsidy_rate":"0.00","tk_total_rate":"30.00","item_category_name":"节庆用品/礼品","seller_nick":"飞可家园幸福商场","pub_id":132021823,"alimama_rate":"10.00","subsidy_type":"--","item_img":"//img.alicdn.com/bao/uploaded/i2/1634673647/O1CN01qvjW5N1coN1hpskmr_!!1634673647.jpg","pub_share_pre_fee":"1.14","alipay_total_price":"3.80","item_title":"对联大礼包2020春节鼠年烫金高档新年春联红纸大门过年对联纸企业","site_name":"YouShopping","item_num":1,"subsidy_fee":"0.00","alimama_share_fee":"0.11","trade_parent_id":"821039008321125986","order_type":"淘宝","tk_create_time":"2020-01-17 18:27:07","flow_source":"--","terminal_type":"无线","click_time":"2020-01-17 18:26:37","tk_status":12,"item_price":"10.80","item_id":607309460202,"adzone_name":"YouCouponChannel","total_commission_rate":"30.00","item_link":"http://item.taobao.com/item.htm?id=607309460202","site_id":45408225,"seller_shop_title":"尚都小屋","income_rate":"30.00","total_commission_fee":"0.00","tk_commission_pre_fee_for_media_platform":"0.00","tk_commission_fee_for_media_platform":"0.00","tk_commission_rate_for_media_platform":"0.00","special_id":0,"relation_id":2360417743,"deposit_price":"0.00","tb_deposit_time":"--","tk_deposit_time":"--"}]
         * totalCount : 13
         */

        private long totalCount;
        private List<OrderListBean> orderList;

        public long getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(long totalCount) {
            this.totalCount = totalCount;
        }

        public List<OrderListBean> getOrderList() {
            return orderList;
        }

        public void setOrderList(List<OrderListBean> orderList) {
            this.orderList = orderList;
        }

        public static class OrderListBean {
            /**
             * id : 0
             * tb_paid_time : 2020-01-17 18:27:02
             * tk_paid_time : 2020-01-17 18:27:12
             * pay_price :
             * pub_share_fee : 0.00
             * trade_id : 821039008321125986
             * tk_order_role : 2
             * tk_earning_time :
             * adzone_id : 109946850496
             * pub_share_rate : 0.00
             * unid : 0
             * refund_tag : 0
             * subsidy_rate : 0.00
             * tk_total_rate : 30.00
             * item_category_name : 节庆用品/礼品
             * seller_nick : 飞可家园幸福商场
             * pub_id : 132021823
             * alimama_rate : 10.00
             * subsidy_type : --
             * item_img : //img.alicdn.com/bao/uploaded/i2/1634673647/O1CN01qvjW5N1coN1hpskmr_!!1634673647.jpg
             * pub_share_pre_fee : 1.14
             * alipay_total_price : 3.80
             * item_title : 对联大礼包2020春节鼠年烫金高档新年春联红纸大门过年对联纸企业
             * site_name : YouShopping
             * item_num : 1
             * subsidy_fee : 0.00
             * alimama_share_fee : 0.11
             * trade_parent_id : 821039008321125986
             * order_type : 淘宝
             * tk_create_time : 2020-01-17 18:27:07
             * flow_source : --
             * terminal_type : 无线
             * click_time : 2020-01-17 18:26:37
             * tk_status : 12
             * item_price : 10.80
             * item_id : 607309460202
             * adzone_name : YouCouponChannel
             * total_commission_rate : 30.00
             * item_link : http://item.taobao.com/item.htm?id=607309460202
             * site_id : 45408225
             * seller_shop_title : 尚都小屋
             * income_rate : 30.00
             * total_commission_fee : 0.00
             * tk_commission_pre_fee_for_media_platform : 0.00
             * tk_commission_fee_for_media_platform : 0.00
             * tk_commission_rate_for_media_platform : 0.00
             * special_id : 0
             * relation_id : 2360417743
             * deposit_price : 0.00
             * tb_deposit_time : --
             * tk_deposit_time : --
             */

            private long id;
            private String tb_paid_time;
            private String tk_paid_time;
            private String pay_price;
            private String pub_share_fee;
            private String trade_id;
            private double tk_order_role;
            private String tk_earning_time;
            private long adzone_id;
            private String pub_share_rate;
            private long unid;
            private long refund_tag;
            private String subsidy_rate;
            private String tk_total_rate;
            private String item_category_name;
            private String seller_nick;
            private long pub_id;
            private String alimama_rate;
            private String subsidy_type;
            private String item_img;
            private String pub_share_pre_fee;
            private String alipay_total_price;
            private String item_title;
            private String site_name;
            private long item_num;
            private String subsidy_fee;
            private String alimama_share_fee;
            private String trade_parent_id;
            private String order_type;
            private String tk_create_time;
            private String flow_source;
            private String terminal_type;
            private String click_time;
            private int tk_status;
            private String item_price;
            private long item_id;
            private String adzone_name;
            private String total_commission_rate;
            private String item_link;
            private long site_id;
            private String seller_shop_title;
            private String income_rate;
            private String total_commission_fee;
            private String tk_commission_pre_fee_for_media_platform;
            private String tk_commission_fee_for_media_platform;
            private String tk_commission_rate_for_media_platform;
            private long special_id;
            private long relation_id;
            private String deposit_price;
            private String tb_deposit_time;
            private String tk_deposit_time;

            public long getId() {
                return id;
            }

            public void setId(long id) {
                this.id = id;
            }

            public String getTb_paid_time() {
                return tb_paid_time;
            }

            public void setTb_paid_time(String tb_paid_time) {
                this.tb_paid_time = tb_paid_time;
            }

            public String getTk_paid_time() {
                return tk_paid_time;
            }

            public void setTk_paid_time(String tk_paid_time) {
                this.tk_paid_time = tk_paid_time;
            }

            public String getPay_price() {
                return pay_price;
            }

            public void setPay_price(String pay_price) {
                this.pay_price = pay_price;
            }

            public String getPub_share_fee() {
                return pub_share_fee;
            }

            public void setPub_share_fee(String pub_share_fee) {
                this.pub_share_fee = pub_share_fee;
            }

            public String getTrade_id() {
                return trade_id;
            }

            public void setTrade_id(String trade_id) {
                this.trade_id = trade_id;
            }

            public double getTk_order_role() {
                return tk_order_role;
            }

            public void setTk_order_role(double tk_order_role) {
                this.tk_order_role = tk_order_role;
            }

            public String getTk_earning_time() {
                return tk_earning_time;
            }

            public void setTk_earning_time(String tk_earning_time) {
                this.tk_earning_time = tk_earning_time;
            }

            public long getAdzone_id() {
                return adzone_id;
            }

            public void setAdzone_id(long adzone_id) {
                this.adzone_id = adzone_id;
            }

            public String getPub_share_rate() {
                return pub_share_rate;
            }

            public void setPub_share_rate(String pub_share_rate) {
                this.pub_share_rate = pub_share_rate;
            }

            public long getUnid() {
                return unid;
            }

            public void setUnid(long unid) {
                this.unid = unid;
            }

            public long getRefund_tag() {
                return refund_tag;
            }

            public void setRefund_tag(long refund_tag) {
                this.refund_tag = refund_tag;
            }

            public String getSubsidy_rate() {
                return subsidy_rate;
            }

            public void setSubsidy_rate(String subsidy_rate) {
                this.subsidy_rate = subsidy_rate;
            }

            public String getTk_total_rate() {
                return tk_total_rate;
            }

            public void setTk_total_rate(String tk_total_rate) {
                this.tk_total_rate = tk_total_rate;
            }

            public String getItem_category_name() {
                return item_category_name;
            }

            public void setItem_category_name(String item_category_name) {
                this.item_category_name = item_category_name;
            }

            public String getSeller_nick() {
                return seller_nick;
            }

            public void setSeller_nick(String seller_nick) {
                this.seller_nick = seller_nick;
            }

            public long getPub_id() {
                return pub_id;
            }

            public void setPub_id(long pub_id) {
                this.pub_id = pub_id;
            }

            public String getAlimama_rate() {
                return alimama_rate;
            }

            public void setAlimama_rate(String alimama_rate) {
                this.alimama_rate = alimama_rate;
            }

            public String getSubsidy_type() {
                return subsidy_type;
            }

            public void setSubsidy_type(String subsidy_type) {
                this.subsidy_type = subsidy_type;
            }

            public String getItem_img() {
                return item_img;
            }

            public void setItem_img(String item_img) {
                this.item_img = item_img;
            }

            public String getPub_share_pre_fee() {
                return pub_share_pre_fee;
            }

            public void setPub_share_pre_fee(String pub_share_pre_fee) {
                this.pub_share_pre_fee = pub_share_pre_fee;
            }

            public String getAlipay_total_price() {
                return alipay_total_price;
            }

            public void setAlipay_total_price(String alipay_total_price) {
                this.alipay_total_price = alipay_total_price;
            }

            public String getItem_title() {
                return item_title;
            }

            public void setItem_title(String item_title) {
                this.item_title = item_title;
            }

            public String getSite_name() {
                return site_name;
            }

            public void setSite_name(String site_name) {
                this.site_name = site_name;
            }

            public long getItem_num() {
                return item_num;
            }

            public void setItem_num(long item_num) {
                this.item_num = item_num;
            }

            public String getSubsidy_fee() {
                return subsidy_fee;
            }

            public void setSubsidy_fee(String subsidy_fee) {
                this.subsidy_fee = subsidy_fee;
            }

            public String getAlimama_share_fee() {
                return alimama_share_fee;
            }

            public void setAlimama_share_fee(String alimama_share_fee) {
                this.alimama_share_fee = alimama_share_fee;
            }

            public String getTrade_parent_id() {
                return trade_parent_id;
            }

            public void setTrade_parent_id(String trade_parent_id) {
                this.trade_parent_id = trade_parent_id;
            }

            public String getOrder_type() {
                return order_type;
            }

            public void setOrder_type(String order_type) {
                this.order_type = order_type;
            }

            public String getTk_create_time() {
                return tk_create_time;
            }

            public void setTk_create_time(String tk_create_time) {
                this.tk_create_time = tk_create_time;
            }

            public String getFlow_source() {
                return flow_source;
            }

            public void setFlow_source(String flow_source) {
                this.flow_source = flow_source;
            }

            public String getTerminal_type() {
                return terminal_type;
            }

            public void setTerminal_type(String terminal_type) {
                this.terminal_type = terminal_type;
            }

            public String getClick_time() {
                return click_time;
            }

            public void setClick_time(String click_time) {
                this.click_time = click_time;
            }

            public int getTk_status() {
                return tk_status;
            }

            public void setTk_status(int tk_status) {
                this.tk_status = tk_status;
            }

            public String getItem_price() {
                return item_price;
            }

            public void setItem_price(String item_price) {
                this.item_price = item_price;
            }

            public long getItem_id() {
                return item_id;
            }

            public void setItem_id(long item_id) {
                this.item_id = item_id;
            }

            public String getAdzone_name() {
                return adzone_name;
            }

            public void setAdzone_name(String adzone_name) {
                this.adzone_name = adzone_name;
            }

            public String getTotal_commission_rate() {
                return total_commission_rate;
            }

            public void setTotal_commission_rate(String total_commission_rate) {
                this.total_commission_rate = total_commission_rate;
            }

            public String getItem_link() {
                return item_link;
            }

            public void setItem_link(String item_link) {
                this.item_link = item_link;
            }

            public long getSite_id() {
                return site_id;
            }

            public void setSite_id(long site_id) {
                this.site_id = site_id;
            }

            public String getSeller_shop_title() {
                return seller_shop_title;
            }

            public void setSeller_shop_title(String seller_shop_title) {
                this.seller_shop_title = seller_shop_title;
            }

            public String getIncome_rate() {
                return income_rate;
            }

            public void setIncome_rate(String income_rate) {
                this.income_rate = income_rate;
            }

            public String getTotal_commission_fee() {
                return total_commission_fee;
            }

            public void setTotal_commission_fee(String total_commission_fee) {
                this.total_commission_fee = total_commission_fee;
            }

            public String getTk_commission_pre_fee_for_media_platform() {
                return tk_commission_pre_fee_for_media_platform;
            }

            public void setTk_commission_pre_fee_for_media_platform(String tk_commission_pre_fee_for_media_platform) {
                this.tk_commission_pre_fee_for_media_platform = tk_commission_pre_fee_for_media_platform;
            }

            public String getTk_commission_fee_for_media_platform() {
                return tk_commission_fee_for_media_platform;
            }

            public void setTk_commission_fee_for_media_platform(String tk_commission_fee_for_media_platform) {
                this.tk_commission_fee_for_media_platform = tk_commission_fee_for_media_platform;
            }

            public String getTk_commission_rate_for_media_platform() {
                return tk_commission_rate_for_media_platform;
            }

            public void setTk_commission_rate_for_media_platform(String tk_commission_rate_for_media_platform) {
                this.tk_commission_rate_for_media_platform = tk_commission_rate_for_media_platform;
            }

            public long getSpecial_id() {
                return special_id;
            }

            public void setSpecial_id(long special_id) {
                this.special_id = special_id;
            }

            public long getRelation_id() {
                return relation_id;
            }

            public void setRelation_id(long relation_id) {
                this.relation_id = relation_id;
            }

            public String getDeposit_price() {
                return deposit_price;
            }

            public void setDeposit_price(String deposit_price) {
                this.deposit_price = deposit_price;
            }

            public String getTb_deposit_time() {
                return tb_deposit_time;
            }

            public void setTb_deposit_time(String tb_deposit_time) {
                this.tb_deposit_time = tb_deposit_time;
            }

            public String getTk_deposit_time() {
                return tk_deposit_time;
            }

            public void setTk_deposit_time(String tk_deposit_time) {
                this.tk_deposit_time = tk_deposit_time;
            }
        }
    }
}
