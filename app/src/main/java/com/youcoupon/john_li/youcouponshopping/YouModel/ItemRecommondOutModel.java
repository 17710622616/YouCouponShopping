package com.youcoupon.john_li.youcouponshopping.YouModel;

import java.util.List;

/**
 * Created by John_Li on 10/4/2019.
 */

public class ItemRecommondOutModel {
    /**
     * status : 0
     * message :
     * data : {"results":[{"ClickUrl":"","ItemUrl":"http://item.taobao.com/item.htm?id=576516350100","Nick":"风疯锋1988","NumIid":0,"PictUrl":"https://img.alicdn.com/tfscom/i2/2231111757/TB1THFeudknBKNjSZKPXXX6OFXa_!!0-item_pic.jpg","Provcity":"广东 广州","ReservePrice":"39.00","SellerId":2231111757,"ShopTitle":null,"SmallImages":["https://img.alicdn.com/tfscom/i4/2231111757/TB2nvYyuiMnBKNjSZFoXXbOSFXa_!!2231111757.jpg","https://img.alicdn.com/tfscom/i2/2231111757/TB2N4M.tZUrBKNjSZPxXXX00pXa_!!2231111757.jpg","https://img.alicdn.com/tfscom/i2/2231111757/TB2lGFFumMmBKNjSZTEXXasKpXa_!!2231111757.jpg","https://img.alicdn.com/tfscom/i1/2231111757/O1CN011OqkLQvlUnJy5Kn_!!2231111757.jpg"],"Title":"春装新款女装日系甜美星星闪亮片网纱中长款学生蓬蓬裙短裙半身裙","TkRate":null,"UserType":0,"Volume":960,"ZkFinalPrice":"38.98","ZkFinalPriceWap":null},{"ClickUrl":"","ItemUrl":"http://item.taobao.com/item.htm?id=585458912186","Nick":"风疯锋1988","NumIid":0,"PictUrl":"https://img.alicdn.com/tfscom/i3/2231111757/O1CN01G5bjlq1OqkNczW85n_!!0-item_pic.jpg","Provcity":"广东 广州","ReservePrice":"40.00","SellerId":2231111757,"ShopTitle":null,"SmallImages":["https://img.alicdn.com/tfscom/i3/2231111757/O1CN01cCMkH61OqkNdF0QKM_!!2231111757.jpg","https://img.alicdn.com/tfscom/i2/2231111757/O1CN01Ag6g6z1OqkNbHfBDi_!!2231111757.jpg","https://img.alicdn.com/tfscom/i1/2231111757/O1CN01Ym7MST1OqkNbHcZ5f_!!2231111757.jpg","https://img.alicdn.com/tfscom/i4/2231111757/O1CN01SJ8yr41OqkNaBZDWJ_!!2231111757.jpg"],"Title":"春季女装新款韩版小清新高腰显瘦复古黑色a字裙女松紧腰半身裙女","TkRate":null,"UserType":0,"Volume":20,"ZkFinalPrice":"39.98","ZkFinalPriceWap":null}]}
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
        private List<ItemRecommendModel> results;

        public List<ItemRecommendModel> getResults() {
            return results;
        }

        public void setResults(List<ItemRecommendModel> results) {
            this.results = results;
        }

        public static class ItemRecommendModel {
            /**
             * ClickUrl :
             * ItemUrl : http://item.taobao.com/item.htm?id=576516350100
             * Nick : 风疯锋1988
             * NumIid : 0
             * PictUrl : https://img.alicdn.com/tfscom/i2/2231111757/TB1THFeudknBKNjSZKPXXX6OFXa_!!0-item_pic.jpg
             * Provcity : 广东 广州
             * ReservePrice : 39.00
             * SellerId : 2231111757
             * ShopTitle : null
             * SmallImages : ["https://img.alicdn.com/tfscom/i4/2231111757/TB2nvYyuiMnBKNjSZFoXXbOSFXa_!!2231111757.jpg","https://img.alicdn.com/tfscom/i2/2231111757/TB2N4M.tZUrBKNjSZPxXXX00pXa_!!2231111757.jpg","https://img.alicdn.com/tfscom/i2/2231111757/TB2lGFFumMmBKNjSZTEXXasKpXa_!!2231111757.jpg","https://img.alicdn.com/tfscom/i1/2231111757/O1CN011OqkLQvlUnJy5Kn_!!2231111757.jpg"]
             * Title : 春装新款女装日系甜美星星闪亮片网纱中长款学生蓬蓬裙短裙半身裙
             * TkRate : null
             * UserType : 0
             * Volume : 960
             * ZkFinalPrice : 38.98
             * ZkFinalPriceWap : null
             */

            private String ClickUrl;
            private String ItemUrl;
            private String Nick;
            private int NumIid;
            private String PictUrl;
            private String Provcity;
            private String ReservePrice;
            private long SellerId;
            private Object ShopTitle;
            private String Title;
            private Object TkRate;
            private int UserType;
            private int Volume;
            private String ZkFinalPrice;
            private Object ZkFinalPriceWap;
            private List<String> SmallImages;

            public String getClickUrl() {
                return ClickUrl;
            }

            public void setClickUrl(String ClickUrl) {
                this.ClickUrl = ClickUrl;
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

            public int getNumIid() {
                return NumIid;
            }

            public void setNumIid(int NumIid) {
                this.NumIid = NumIid;
            }

            public String getPictUrl() {
                return PictUrl;
            }

            public void setPictUrl(String PictUrl) {
                this.PictUrl = PictUrl;
            }

            public String getProvcity() {
                return Provcity;
            }

            public void setProvcity(String Provcity) {
                this.Provcity = Provcity;
            }

            public String getReservePrice() {
                return ReservePrice;
            }

            public void setReservePrice(String ReservePrice) {
                this.ReservePrice = ReservePrice;
            }

            public long getSellerId() {
                return SellerId;
            }

            public void setSellerId(long SellerId) {
                this.SellerId = SellerId;
            }

            public Object getShopTitle() {
                return ShopTitle;
            }

            public void setShopTitle(Object ShopTitle) {
                this.ShopTitle = ShopTitle;
            }

            public String getTitle() {
                return Title;
            }

            public void setTitle(String Title) {
                this.Title = Title;
            }

            public Object getTkRate() {
                return TkRate;
            }

            public void setTkRate(Object TkRate) {
                this.TkRate = TkRate;
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

            public Object getZkFinalPriceWap() {
                return ZkFinalPriceWap;
            }

            public void setZkFinalPriceWap(Object ZkFinalPriceWap) {
                this.ZkFinalPriceWap = ZkFinalPriceWap;
            }

            public List<String> getSmallImages() {
                return SmallImages;
            }

            public void setSmallImages(List<String> SmallImages) {
                this.SmallImages = SmallImages;
            }
        }
    }
}
