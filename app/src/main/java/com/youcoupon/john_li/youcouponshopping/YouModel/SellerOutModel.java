package com.youcoupon.john_li.youcouponshopping.YouModel;

import java.util.List;

/**
 * Created by John_Li on 9/4/2019.
 */

public class SellerOutModel {
    /**
     * status : 0
     * message :
     * data : {"total_results":0,"results":[{"ClickUrl":"","PictUrl":"http://logo.taobaocdn.com/shop-logo/i3/2231111757/O1CN01EX86da1OqkOZdTB8Y_!!2231111757.jpg","SellerNick":"风疯锋1988","ShopTitle":"西西比小妞","ShopType":"C","ShopUrl":"http://store.taobao.com/shop/view_shop.htm?user_number_id=2231111757","UserId":2231111757}]}
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
         * total_results : 0
         * results : [{"ClickUrl":"","PictUrl":"http://logo.taobaocdn.com/shop-logo/i3/2231111757/O1CN01EX86da1OqkOZdTB8Y_!!2231111757.jpg","SellerNick":"风疯锋1988","ShopTitle":"西西比小妞","ShopType":"C","ShopUrl":"http://store.taobao.com/shop/view_shop.htm?user_number_id=2231111757","UserId":2231111757}]
         */

        private int total_results;
        private List<SellerModel> results;

        public int getTotal_results() {
            return total_results;
        }

        public void setTotal_results(int total_results) {
            this.total_results = total_results;
        }

        public List<SellerModel> getResults() {
            return results;
        }

        public void setResults(List<SellerModel> results) {
            this.results = results;
        }

        public static class SellerModel {
            /**
             * ClickUrl :
             * PictUrl : http://logo.taobaocdn.com/shop-logo/i3/2231111757/O1CN01EX86da1OqkOZdTB8Y_!!2231111757.jpg
             * SellerNick : 风疯锋1988
             * ShopTitle : 西西比小妞
             * ShopType : C
             * ShopUrl : http://store.taobao.com/shop/view_shop.htm?user_number_id=2231111757
             * UserId : 2231111757
             */
            private String PictUrl;
            private String SellerNick;
            private String ShopTitle;
            private String ShopType;
            private String ShopUrl;
            private long UserId;

            public String getPictUrl() {
                return PictUrl;
            }

            public void setPictUrl(String PictUrl) {
                this.PictUrl = PictUrl;
            }

            public String getSellerNick() {
                return SellerNick;
            }

            public void setSellerNick(String SellerNick) {
                this.SellerNick = SellerNick;
            }

            public String getShopTitle() {
                return ShopTitle;
            }

            public void setShopTitle(String ShopTitle) {
                this.ShopTitle = ShopTitle;
            }

            public String getShopType() {
                return ShopType;
            }

            public void setShopType(String ShopType) {
                this.ShopType = ShopType;
            }

            public String getShopUrl() {
                return ShopUrl;
            }

            public void setShopUrl(String ShopUrl) {
                this.ShopUrl = ShopUrl;
            }

            public long getUserId() {
                return UserId;
            }

            public void setUserId(long UserId) {
                this.UserId = UserId;
            }
        }
    }
}
