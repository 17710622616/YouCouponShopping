package com.youcoupon.john_li.youcouponshopping.YouModel;

import java.util.List;

/**
 * Created by John_Li on 8/4/2019.
 */

public class FavoriteItemOutModel {
    /**
     * status : 0
     * message :
     * data : {"total_results":10,"results":[{"volume":2529,"userType":0,"type":4,"tkRate":"1.53","title":"珊珊网红同款2019女装中长款裙子a字性感多色无袖露背吊带连衣裙","status":1,"smallImages":["https://img.alicdn.com/tfscom/i2/131077237/O1CN01z7LtHm23KapMLNm4Y_!!131077237.jpg","https://img.alicdn.com/tfscom/i4/131077237/O1CN01IuW0DN23KapMoiFQ9_!!131077237.jpg","https://img.alicdn.com/tfscom/i3/131077237/O1CN01mb0wLg23KapKAJfbU_!!131077237.jpg","https://img.alicdn.com/tfscom/i1/131077237/O1CN01C2k7fh23KapTL4fzb_!!131077237.jpg"],"shopTitle":"珊珊 Sunny33小超人","sellerId":131077237,"reservePrice":"119.00","provcity":"浙江 杭州","pictUrl":"https://img.alicdn.com/tfscom/i4/131077237/O1CN01GJqLjp23KapL9paRZ_!!131077237.jpg","zkFinalPrice":"119.00","numIid":587077852534,"itemUrl":"http://h5.m.taobao.com/awp/core/detail.htm?id=587077852534","eventStartTime":"1970-01-01 00:00:00","eventEndTime":"1970-01-01 00:00:00","couponTotalCount":0,"couponStartTime":"1970-01-01 00:00:00","couponRemainCount":0,"couponInfo":"","couponEndTime":"1970-01-01 00:00:00","couponClickUrl":"","commissionRate":0,"clickUrl":"https://s.click.taobao.com/t?e=m%3D2%26s%3DAS%2B9m0N5oddw4vFB6t2Z2ueEDrYVVa64XoO8tOebS%2Bfjf2vlNIV67iD6iXGn0dYZsmcYjUfw1pL9VCSQqTLsa4%2BDpQeWfDJaVpEUn89CVjnJsAA91U3vce%2FBas0tvb4eZz42eVlqqJT6BXppXS5x5z0BDlc5vrJjopr%2FSerwmYO1Fa9PP7QS01hoqmVF7wB2y5tP4N3LeONJ0tN9JhlF88dNHPgQ8CSz0uBSfZ82uo%2FGDmntuH4VtA%3D%3D","category":16,"nick":"珊珊宝贝0332008","zkFinalPriceWap":"119.00"},{"volume":10,"userType":0,"type":1,"tkRate":"1.50","title":"日着原创设计师女装2019夏季新款潮 宽松网红上衣不对称解构T恤女","status":1,"smallImages":["https://img.alicdn.com/tfscom/i4/90489528/TB2mKrcbuuSBuNjSsplXXbe8pXa_!!90489528.jpg","https://img.alicdn.com/tfscom/i4/90489528/TB2tOLpbv1TBuNjy0FjXXajyXXa_!!90489528.jpg","https://img.alicdn.com/tfscom/i3/90489528/TB2CsPmbpuWBuNjSspnXXX1NVXa_!!90489528.jpg","https://img.alicdn.com/tfscom/i4/90489528/TB2l92ebxSYBuNjSsphXXbGvVXa_!!90489528.jpg"],"shopTitle":"日着設計師服飾品牌","sellerId":90489528,"reservePrice":"299.00","provcity":"浙江 杭州","pictUrl":"https://img.alicdn.com/tfscom/i4/90489528/TB1_KncbuuSBuNjSsplXXbe8pXa_!!0-item_pic.jpg","zkFinalPrice":"299.00","numIid":565152848615,"itemUrl":"http://h5.m.taobao.com/awp/core/detail.htm?id=565152848615","eventStartTime":"1970-01-01 00:00:00","eventEndTime":"1970-01-01 00:00:00","couponTotalCount":0,"couponStartTime":null,"couponRemainCount":0,"couponInfo":null,"couponEndTime":null,"couponClickUrl":null,"commissionRate":null,"clickUrl":"https://s.click.taobao.com/t?e=m%3D2%26s%3DJJ%2FPp6M2%2F8Nw4vFB6t2Z2ueEDrYVVa64XoO8tOebS%2Bfjf2vlNIV67iD6iXGn0dYZsmcYjUfw1pL9VCSQqTLsa4%2BDpQeWfDJaVpEUn89CVjnJsAA91U3vce%2FBas0tvb4eZz42eVlqqJT6BXppXS5x5z0BDlc5vrJjopr%2FSerwmYOCMHtpFwM8vWOcw86FTGy78rZl6sW6CV5IJ6G0ZTXBaxieH6jSrdxpcSpj5qSCmbA%3D","category":16,"nick":"fengshengj","zkFinalPriceWap":"299.00"}]}
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
         * total_results : 10
         * results : [{"volume":2529,"userType":0,"type":4,"tkRate":"1.53","title":"珊珊网红同款2019女装中长款裙子a字性感多色无袖露背吊带连衣裙","status":1,"smallImages":["https://img.alicdn.com/tfscom/i2/131077237/O1CN01z7LtHm23KapMLNm4Y_!!131077237.jpg","https://img.alicdn.com/tfscom/i4/131077237/O1CN01IuW0DN23KapMoiFQ9_!!131077237.jpg","https://img.alicdn.com/tfscom/i3/131077237/O1CN01mb0wLg23KapKAJfbU_!!131077237.jpg","https://img.alicdn.com/tfscom/i1/131077237/O1CN01C2k7fh23KapTL4fzb_!!131077237.jpg"],"shopTitle":"珊珊 Sunny33小超人","sellerId":131077237,"reservePrice":"119.00","provcity":"浙江 杭州","pictUrl":"https://img.alicdn.com/tfscom/i4/131077237/O1CN01GJqLjp23KapL9paRZ_!!131077237.jpg","zkFinalPrice":"119.00","numIid":587077852534,"itemUrl":"http://h5.m.taobao.com/awp/core/detail.htm?id=587077852534","eventStartTime":"1970-01-01 00:00:00","eventEndTime":"1970-01-01 00:00:00","couponTotalCount":0,"couponStartTime":"1970-01-01 00:00:00","couponRemainCount":0,"couponInfo":"","couponEndTime":"1970-01-01 00:00:00","couponClickUrl":"","commissionRate":0,"clickUrl":"https://s.click.taobao.com/t?e=m%3D2%26s%3DAS%2B9m0N5oddw4vFB6t2Z2ueEDrYVVa64XoO8tOebS%2Bfjf2vlNIV67iD6iXGn0dYZsmcYjUfw1pL9VCSQqTLsa4%2BDpQeWfDJaVpEUn89CVjnJsAA91U3vce%2FBas0tvb4eZz42eVlqqJT6BXppXS5x5z0BDlc5vrJjopr%2FSerwmYO1Fa9PP7QS01hoqmVF7wB2y5tP4N3LeONJ0tN9JhlF88dNHPgQ8CSz0uBSfZ82uo%2FGDmntuH4VtA%3D%3D","category":16,"nick":"珊珊宝贝0332008","zkFinalPriceWap":"119.00"},{"volume":10,"userType":0,"type":1,"tkRate":"1.50","title":"日着原创设计师女装2019夏季新款潮 宽松网红上衣不对称解构T恤女","status":1,"smallImages":["https://img.alicdn.com/tfscom/i4/90489528/TB2mKrcbuuSBuNjSsplXXbe8pXa_!!90489528.jpg","https://img.alicdn.com/tfscom/i4/90489528/TB2tOLpbv1TBuNjy0FjXXajyXXa_!!90489528.jpg","https://img.alicdn.com/tfscom/i3/90489528/TB2CsPmbpuWBuNjSspnXXX1NVXa_!!90489528.jpg","https://img.alicdn.com/tfscom/i4/90489528/TB2l92ebxSYBuNjSsphXXbGvVXa_!!90489528.jpg"],"shopTitle":"日着設計師服飾品牌","sellerId":90489528,"reservePrice":"299.00","provcity":"浙江 杭州","pictUrl":"https://img.alicdn.com/tfscom/i4/90489528/TB1_KncbuuSBuNjSsplXXbe8pXa_!!0-item_pic.jpg","zkFinalPrice":"299.00","numIid":565152848615,"itemUrl":"http://h5.m.taobao.com/awp/core/detail.htm?id=565152848615","eventStartTime":"1970-01-01 00:00:00","eventEndTime":"1970-01-01 00:00:00","couponTotalCount":0,"couponStartTime":null,"couponRemainCount":0,"couponInfo":null,"couponEndTime":null,"couponClickUrl":null,"commissionRate":null,"clickUrl":"https://s.click.taobao.com/t?e=m%3D2%26s%3DJJ%2FPp6M2%2F8Nw4vFB6t2Z2ueEDrYVVa64XoO8tOebS%2Bfjf2vlNIV67iD6iXGn0dYZsmcYjUfw1pL9VCSQqTLsa4%2BDpQeWfDJaVpEUn89CVjnJsAA91U3vce%2FBas0tvb4eZz42eVlqqJT6BXppXS5x5z0BDlc5vrJjopr%2FSerwmYOCMHtpFwM8vWOcw86FTGy78rZl6sW6CV5IJ6G0ZTXBaxieH6jSrdxpcSpj5qSCmbA%3D","category":16,"nick":"fengshengj","zkFinalPriceWap":"299.00"}]
         */

        private int total_results;
        private List<FavoriteItemModel> results;

        public int getTotal_results() {
            return total_results;
        }

        public void setTotal_results(int total_results) {
            this.total_results = total_results;
        }

        public List<FavoriteItemModel> getResults() {
            return results;
        }

        public void setResults(List<FavoriteItemModel> results) {
            this.results = results;
        }

        public static class FavoriteItemModel {
            /**
             * volume : 2529
             * userType : 0
             * type : 4
             * tkRate : 1.53
             * title : 珊珊网红同款2019女装中长款裙子a字性感多色无袖露背吊带连衣裙
             * status : 1
             * smallImages : ["https://img.alicdn.com/tfscom/i2/131077237/O1CN01z7LtHm23KapMLNm4Y_!!131077237.jpg","https://img.alicdn.com/tfscom/i4/131077237/O1CN01IuW0DN23KapMoiFQ9_!!131077237.jpg","https://img.alicdn.com/tfscom/i3/131077237/O1CN01mb0wLg23KapKAJfbU_!!131077237.jpg","https://img.alicdn.com/tfscom/i1/131077237/O1CN01C2k7fh23KapTL4fzb_!!131077237.jpg"]
             * shopTitle : 珊珊 Sunny33小超人
             * sellerId : 131077237
             * reservePrice : 119.00
             * provcity : 浙江 杭州
             * pictUrl : https://img.alicdn.com/tfscom/i4/131077237/O1CN01GJqLjp23KapL9paRZ_!!131077237.jpg
             * zkFinalPrice : 119.00
             * numIid : 587077852534
             * itemUrl : http://h5.m.taobao.com/awp/core/detail.htm?id=587077852534
             * eventStartTime : 1970-01-01 00:00:00
             * eventEndTime : 1970-01-01 00:00:00
             * couponTotalCount : 0
             * couponStartTime : 1970-01-01 00:00:00
             * couponRemainCount : 0
             * couponInfo :
             * couponEndTime : 1970-01-01 00:00:00
             * couponClickUrl :
             * commissionRate : 0.0
             * clickUrl : https://s.click.taobao.com/t?e=m%3D2%26s%3DAS%2B9m0N5oddw4vFB6t2Z2ueEDrYVVa64XoO8tOebS%2Bfjf2vlNIV67iD6iXGn0dYZsmcYjUfw1pL9VCSQqTLsa4%2BDpQeWfDJaVpEUn89CVjnJsAA91U3vce%2FBas0tvb4eZz42eVlqqJT6BXppXS5x5z0BDlc5vrJjopr%2FSerwmYO1Fa9PP7QS01hoqmVF7wB2y5tP4N3LeONJ0tN9JhlF88dNHPgQ8CSz0uBSfZ82uo%2FGDmntuH4VtA%3D%3D
             * category : 16
             * nick : 珊珊宝贝0332008
             * zkFinalPriceWap : 119.00
             */

            private long volume;
            private int userType;
            private int type;
            private String tkRate;
            private String title;
            private int status;
            private String shopTitle;
            private long sellerId;
            private String reservePrice;
            private String provcity;
            private String pictUrl;
            private String zkFinalPrice;
            private long numIid;
            private String itemUrl;
            private String eventStartTime;
            private String eventEndTime;
            private long couponTotalCount;
            private String couponStartTime;
            private int couponRemainCount;
            private String couponInfo;
            private String couponEndTime;
            private String couponClickUrl;
            private double commissionRate;
            private String clickUrl;
            private long category;
            private String nick;
            private String zkFinalPriceWap;
            private List<String> smallImages;

            public long getVolume() {
                return volume;
            }

            public void setVolume(long volume) {
                this.volume = volume;
            }

            public int getUserType() {
                return userType;
            }

            public void setUserType(int userType) {
                this.userType = userType;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getTkRate() {
                return tkRate;
            }

            public void setTkRate(String tkRate) {
                this.tkRate = tkRate;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getShopTitle() {
                return shopTitle;
            }

            public void setShopTitle(String shopTitle) {
                this.shopTitle = shopTitle;
            }

            public long getSellerId() {
                return sellerId;
            }

            public void setSellerId(long sellerId) {
                this.sellerId = sellerId;
            }

            public String getReservePrice() {
                return reservePrice;
            }

            public void setReservePrice(String reservePrice) {
                this.reservePrice = reservePrice;
            }

            public String getProvcity() {
                return provcity;
            }

            public void setProvcity(String provcity) {
                this.provcity = provcity;
            }

            public String getPictUrl() {
                return pictUrl;
            }

            public void setPictUrl(String pictUrl) {
                this.pictUrl = pictUrl;
            }

            public String getZkFinalPrice() {
                return zkFinalPrice;
            }

            public void setZkFinalPrice(String zkFinalPrice) {
                this.zkFinalPrice = zkFinalPrice;
            }

            public long getNumIid() {
                return numIid;
            }

            public void setNumIid(long numIid) {
                this.numIid = numIid;
            }

            public String getItemUrl() {
                return itemUrl;
            }

            public void setItemUrl(String itemUrl) {
                this.itemUrl = itemUrl;
            }

            public String getEventStartTime() {
                return eventStartTime;
            }

            public void setEventStartTime(String eventStartTime) {
                this.eventStartTime = eventStartTime;
            }

            public String getEventEndTime() {
                return eventEndTime;
            }

            public void setEventEndTime(String eventEndTime) {
                this.eventEndTime = eventEndTime;
            }

            public long getCouponTotalCount() {
                return couponTotalCount;
            }

            public void setCouponTotalCount(long couponTotalCount) {
                this.couponTotalCount = couponTotalCount;
            }

            public String getCouponStartTime() {
                return couponStartTime;
            }

            public void setCouponStartTime(String couponStartTime) {
                this.couponStartTime = couponStartTime;
            }

            public int getCouponRemainCount() {
                return couponRemainCount;
            }

            public void setCouponRemainCount(int couponRemainCount) {
                this.couponRemainCount = couponRemainCount;
            }

            public String getCouponInfo() {
                return couponInfo;
            }

            public void setCouponInfo(String couponInfo) {
                this.couponInfo = couponInfo;
            }

            public String getCouponEndTime() {
                return couponEndTime;
            }

            public void setCouponEndTime(String couponEndTime) {
                this.couponEndTime = couponEndTime;
            }

            public String getCouponClickUrl() {
                return couponClickUrl;
            }

            public void setCouponClickUrl(String couponClickUrl) {
                this.couponClickUrl = couponClickUrl;
            }

            public double getCommissionRate() {
                return commissionRate;
            }

            public void setCommissionRate(double commissionRate) {
                this.commissionRate = commissionRate;
            }

            public String getClickUrl() {
                return clickUrl;
            }

            public void setClickUrl(String clickUrl) {
                this.clickUrl = clickUrl;
            }

            public long getCategory() {
                return category;
            }

            public void setCategory(long category) {
                this.category = category;
            }

            public String getNick() {
                return nick;
            }

            public void setNick(String nick) {
                this.nick = nick;
            }

            public String getZkFinalPriceWap() {
                return zkFinalPriceWap;
            }

            public void setZkFinalPriceWap(String zkFinalPriceWap) {
                this.zkFinalPriceWap = zkFinalPriceWap;
            }

            public List<String> getSmallImages() {
                return smallImages;
            }

            public void setSmallImages(List<String> smallImages) {
                this.smallImages = smallImages;
            }
        }
    }
}
