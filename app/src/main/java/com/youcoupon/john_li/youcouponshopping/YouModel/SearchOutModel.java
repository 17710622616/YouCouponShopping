package com.youcoupon.john_li.youcouponshopping.YouModel;

import java.util.List;

/**
 * Created by John_Li on 10/5/2019.
 */

public class SearchOutModel {
    /**
     * status : 0
     * message :
     * data : {"request_id":0,"results":[{"Category":201162107,"CommissionRate":"1.35","CouponClickUrl":"https://uland.taobao.com/coupon/edetail?e=vuluz3WgQ1oGQASttHIRqfBRftmcw%2FkkEt%2BGaD%2BDEBbB76UlhXoJ9852Mbros37%2FVDPpjL%2FsR9rNY%2FGqRi606F31tEs1bNk1DfqEFBOhTcxNQ0HDGNWn0sKx7sA6hYu1TG%2BQkQpnVyCA%2BLj8OlpHELGk2dypsnowOqxB7l2SGtXk92%2BM7h46c9boO99VQREJuFuGhSUVywwfi2iNaylAvVS0HdanV%2B6vVUr%2B50mQ6dw%3D&traceId=0b01d39715574755532311942e798d","CouponEndTime":"2019-08-31","CouponInfo":"满700元减10元","CouponRemainCount":4,"CouponStartTime":"2019-03-06","CouponTotalCount":50,"ItemDescription":"","ItemUrl":"https://detail.tmall.com/item.htm?id=559612953012","Nick":"wojiaqipei","NumIid":559612953012,"PictUrl":"http://img.alicdn.com/tfscom/i3/515684941/TB2aMEWnRcHL1JjSZJiXXcKcpXa_!!515684941.jpg","SellerId":515684941,"ShopTitle":"弘宇方向机大全","SmallImages":[],"Title":"沙市B58 方向机 G0340140006A0 福田长沙 瑞琪5130 B66 B43","UserType":0,"Volume":0,"ZkFinalPrice":"1020.00"},{"Category":50025004,"CommissionRate":"4.50","CouponClickUrl":"https://uland.taobao.com/coupon/edetail?e=4n%2BXrbiQcQsGQASttHIRqVw09syQ7Kjd5I6YTofVku1YomNqLX%2FiFz6GFb7gCnTw%2BrBRZMdlFIItiLbo6gp0f5Idow67GtFsDfqEFBOhTcxNQ0HDGNWn0sKx7sA6hYu1TG%2BQkQpnVyCA%2BLj8OlpHELGk2dypsnowOqxB7l2SGtXk92%2BM7h46c9boO99VQREJuFuGhSUVywwfi2iNaylAvVS0HdanV%2B6vVUr%2B50mQ6dw%3D&traceId=0b01d39715574755532311942e798d","CouponEndTime":"2019-05-31","CouponInfo":"满48元减3元","CouponRemainCount":7480,"CouponStartTime":"2019-04-24","CouponTotalCount":10000,"ItemDescription":"数量有限 先到先得 时间：4.8-4.15","ItemUrl":"https://detail.tmall.com/item.htm?id=580811980346","Nick":"虎彩旗舰店","NumIid":580811980346,"PictUrl":"http://img.alicdn.com/tfscom/i2/1848892065/O1CN011R7oLd05hw2LQoA_!!0-item_pic.jpg","SellerId":1848892065,"ShopTitle":"虎彩旗舰店","SmallImages":[],"Title":"12寸照片书66P杂志相册定制（活动款）","UserType":1,"Volume":61,"ZkFinalPrice":"53.90"}]}
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
         * request_id : 0
         * results : [{"Category":201162107,"CommissionRate":"1.35","CouponClickUrl":"https://uland.taobao.com/coupon/edetail?e=vuluz3WgQ1oGQASttHIRqfBRftmcw%2FkkEt%2BGaD%2BDEBbB76UlhXoJ9852Mbros37%2FVDPpjL%2FsR9rNY%2FGqRi606F31tEs1bNk1DfqEFBOhTcxNQ0HDGNWn0sKx7sA6hYu1TG%2BQkQpnVyCA%2BLj8OlpHELGk2dypsnowOqxB7l2SGtXk92%2BM7h46c9boO99VQREJuFuGhSUVywwfi2iNaylAvVS0HdanV%2B6vVUr%2B50mQ6dw%3D&traceId=0b01d39715574755532311942e798d","CouponEndTime":"2019-08-31","CouponInfo":"满700元减10元","CouponRemainCount":4,"CouponStartTime":"2019-03-06","CouponTotalCount":50,"ItemDescription":"","ItemUrl":"https://detail.tmall.com/item.htm?id=559612953012","Nick":"wojiaqipei","NumIid":559612953012,"PictUrl":"http://img.alicdn.com/tfscom/i3/515684941/TB2aMEWnRcHL1JjSZJiXXcKcpXa_!!515684941.jpg","SellerId":515684941,"ShopTitle":"弘宇方向机大全","SmallImages":[],"Title":"沙市B58 方向机 G0340140006A0 福田长沙 瑞琪5130 B66 B43","UserType":0,"Volume":0,"ZkFinalPrice":"1020.00"},{"Category":50025004,"CommissionRate":"4.50","CouponClickUrl":"https://uland.taobao.com/coupon/edetail?e=4n%2BXrbiQcQsGQASttHIRqVw09syQ7Kjd5I6YTofVku1YomNqLX%2FiFz6GFb7gCnTw%2BrBRZMdlFIItiLbo6gp0f5Idow67GtFsDfqEFBOhTcxNQ0HDGNWn0sKx7sA6hYu1TG%2BQkQpnVyCA%2BLj8OlpHELGk2dypsnowOqxB7l2SGtXk92%2BM7h46c9boO99VQREJuFuGhSUVywwfi2iNaylAvVS0HdanV%2B6vVUr%2B50mQ6dw%3D&traceId=0b01d39715574755532311942e798d","CouponEndTime":"2019-05-31","CouponInfo":"满48元减3元","CouponRemainCount":7480,"CouponStartTime":"2019-04-24","CouponTotalCount":10000,"ItemDescription":"数量有限 先到先得 时间：4.8-4.15","ItemUrl":"https://detail.tmall.com/item.htm?id=580811980346","Nick":"虎彩旗舰店","NumIid":580811980346,"PictUrl":"http://img.alicdn.com/tfscom/i2/1848892065/O1CN011R7oLd05hw2LQoA_!!0-item_pic.jpg","SellerId":1848892065,"ShopTitle":"虎彩旗舰店","SmallImages":[],"Title":"12寸照片书66P杂志相册定制（活动款）","UserType":1,"Volume":61,"ZkFinalPrice":"53.90"}]
         */

        private int request_id;
        private List<SearchModel> results;

        public int getRequest_id() {
            return request_id;
        }

        public void setRequest_id(int request_id) {
            this.request_id = request_id;
        }

        public List<SearchModel> getResults() {
            return results;
        }

        public void setResults(List<SearchModel> results) {
            this.results = results;
        }

        public static class SearchModel {
            /**
             * Category : 201162107
             * CommissionRate : 1.35
             * CouponClickUrl : https://uland.taobao.com/coupon/edetail?e=vuluz3WgQ1oGQASttHIRqfBRftmcw%2FkkEt%2BGaD%2BDEBbB76UlhXoJ9852Mbros37%2FVDPpjL%2FsR9rNY%2FGqRi606F31tEs1bNk1DfqEFBOhTcxNQ0HDGNWn0sKx7sA6hYu1TG%2BQkQpnVyCA%2BLj8OlpHELGk2dypsnowOqxB7l2SGtXk92%2BM7h46c9boO99VQREJuFuGhSUVywwfi2iNaylAvVS0HdanV%2B6vVUr%2B50mQ6dw%3D&traceId=0b01d39715574755532311942e798d
             * CouponEndTime : 2019-08-31
             * CouponInfo : 满700元减10元
             * CouponRemainCount : 4
             * CouponStartTime : 2019-03-06
             * CouponTotalCount : 50
             * ItemDescription :
             * ItemUrl : https://detail.tmall.com/item.htm?id=559612953012
             * Nick : wojiaqipei
             * NumIid : 559612953012
             * PictUrl : http://img.alicdn.com/tfscom/i3/515684941/TB2aMEWnRcHL1JjSZJiXXcKcpXa_!!515684941.jpg
             * SellerId : 515684941
             * ShopTitle : 弘宇方向机大全
             * SmallImages : []
             * Title : 沙市B58 方向机 G0340140006A0 福田长沙 瑞琪5130 B66 B43
             * UserType : 0
             * Volume : 0
             * ZkFinalPrice : 1020.00
             */

            private int Category;
            private String CommissionRate;
            private String CouponClickUrl;
            private String CouponEndTime;
            private String CouponInfo;
            private long CouponRemainCount;
            private String CouponStartTime;
            private long CouponTotalCount;
            private String ItemDescription;
            private String ItemUrl;
            private String Nick;
            private long NumIid;
            private String PictUrl;
            private long SellerId;
            private String ShopTitle;
            private String Title;
            private int UserType;
            private int Volume;
            private String ZkFinalPrice;
            private List<?> SmallImages;

            public int getCategory() {
                return Category;
            }

            public void setCategory(int Category) {
                this.Category = Category;
            }

            public String getCommissionRate() {
                return CommissionRate;
            }

            public void setCommissionRate(String CommissionRate) {
                this.CommissionRate = CommissionRate;
            }

            public String getCouponClickUrl() {
                return CouponClickUrl;
            }

            public void setCouponClickUrl(String CouponClickUrl) {
                this.CouponClickUrl = CouponClickUrl;
            }

            public String getCouponEndTime() {
                return CouponEndTime;
            }

            public void setCouponEndTime(String CouponEndTime) {
                this.CouponEndTime = CouponEndTime;
            }

            public String getCouponInfo() {
                return CouponInfo;
            }

            public void setCouponInfo(String CouponInfo) {
                this.CouponInfo = CouponInfo;
            }

            public long getCouponRemainCount() {
                return CouponRemainCount;
            }

            public void setCouponRemainCount(long CouponRemainCount) {
                this.CouponRemainCount = CouponRemainCount;
            }

            public String getCouponStartTime() {
                return CouponStartTime;
            }

            public void setCouponStartTime(String CouponStartTime) {
                this.CouponStartTime = CouponStartTime;
            }

            public long getCouponTotalCount() {
                return CouponTotalCount;
            }

            public void setCouponTotalCount(long CouponTotalCount) {
                this.CouponTotalCount = CouponTotalCount;
            }

            public String getItemDescription() {
                return ItemDescription;
            }

            public void setItemDescription(String ItemDescription) {
                this.ItemDescription = ItemDescription;
            }

            public String getItemUrl() {
                return ItemUrl;
            }

            public void setItemUrl(String ItemUrl) {
                this.ItemUrl = ItemUrl;
            }

            public String getNick() {
                return Nick;
            }

            public void setNick(String Nick) {
                this.Nick = Nick;
            }

            public long getNumIid() {
                return NumIid;
            }

            public void setNumIid(long NumIid) {
                this.NumIid = NumIid;
            }

            public String getPictUrl() {
                return PictUrl;
            }

            public void setPictUrl(String PictUrl) {
                this.PictUrl = PictUrl;
            }

            public long getSellerId() {
                return SellerId;
            }

            public void setSellerId(long SellerId) {
                this.SellerId = SellerId;
            }

            public String getShopTitle() {
                return ShopTitle;
            }

            public void setShopTitle(String ShopTitle) {
                this.ShopTitle = ShopTitle;
            }

            public String getTitle() {
                return Title;
            }

            public void setTitle(String Title) {
                this.Title = Title;
            }

            public int getUserType() {
                return UserType;
            }

            public void setUserType(int UserType) {
                this.UserType = UserType;
            }

            public int getVolume() {
                return Volume;
            }

            public void setVolume(int Volume) {
                this.Volume = Volume;
            }

            public String getZkFinalPrice() {
                return ZkFinalPrice;
            }

            public void setZkFinalPrice(String ZkFinalPrice) {
                this.ZkFinalPrice = ZkFinalPrice;
            }

            public List<?> getSmallImages() {
                return SmallImages;
            }

            public void setSmallImages(List<?> SmallImages) {
                this.SmallImages = SmallImages;
            }
        }
    }
}
