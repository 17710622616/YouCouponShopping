package com.youcoupon.john_li.youcouponshopping.YouModel;

import java.util.List;

/**
 * Created by John_Li on 10/4/2019.
 */

public class ItemInfoOutModel {
    /**
     * status : 0
     * message :
     * data : {"results":[{"CatLeafName":"半身裙","CatName":"女装/女士精品","FreeShipment":false,"HGoodRate":false,"HPayRate30":false,"IRfdRate":false,"IsPrepay":false,"ItemUrl":"https://h5.m.taobao.com/awp/core/detail.htm?id=548611574744","MaterialLibType":"1","Nick":"西西比小妞","NumIid":548611574744,"PictUrl":"https://img.alicdn.com/bao/uploaded/i4/2231111757/TB1TsP1ah1YBuNjy1zcXXbNcXXa_!!0-item_pic.jpg","Provcity":"广东 广州","Ratesum":0,"ReservePrice":"37.66","SellerId":2231111757,"ShopDsr":0,"SmallImages":["https://img.alicdn.com/i4/2231111757/TB2HMEnnOpnpuFjSZFIXXXh2VXa_!!2231111757.jpg","https://img.alicdn.com/i3/2231111757/TB22kFblR8lpuFjy0FnXXcZyXXa_!!2231111757.jpg","https://img.alicdn.com/i1/2231111757/TB2cWMrlrBkpuFjy1zkXXbSpFXa_!!2231111757.jpg","https://img.alicdn.com/i2/2231111757/TB2Ph22DxGYBuNjy0FnXXX5lpXa_!!2231111757.jpg"],"Title":"夏装女装韩版学院风百搭高腰A字百褶裙宽松显瘦小半身裙短裙裤夏","UserType":0,"Volume":248,"ZkFinalPrice":"36.66"}]}
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
        private List<ItemInfoModel> results;

        public List<ItemInfoModel> getResults() {
            return results;
        }

        public void setResults(List<ItemInfoModel> results) {
            this.results = results;
        }

        public static class ItemInfoModel {
            /**
             * CatLeafName : 半身裙
             * CatName : 女装/女士精品
             * FreeShipment : false
             * HGoodRate : false
             * HPayRate30 : false
             * IRfdRate : false
             * IsPrepay : false
             * ItemUrl : https://h5.m.taobao.com/awp/core/detail.htm?id=548611574744
             * MaterialLibType : 1
             * Nick : 西西比小妞
             * NumIid : 548611574744
             * PictUrl : https://img.alicdn.com/bao/uploaded/i4/2231111757/TB1TsP1ah1YBuNjy1zcXXbNcXXa_!!0-item_pic.jpg
             * Provcity : 广东 广州
             * Ratesum : 0
             * ReservePrice : 37.66
             * SellerId : 2231111757
             * ShopDsr : 0
             * SmallImages : ["https://img.alicdn.com/i4/2231111757/TB2HMEnnOpnpuFjSZFIXXXh2VXa_!!2231111757.jpg","https://img.alicdn.com/i3/2231111757/TB22kFblR8lpuFjy0FnXXcZyXXa_!!2231111757.jpg","https://img.alicdn.com/i1/2231111757/TB2cWMrlrBkpuFjy1zkXXbSpFXa_!!2231111757.jpg","https://img.alicdn.com/i2/2231111757/TB2Ph22DxGYBuNjy0FnXXX5lpXa_!!2231111757.jpg"]
             * Title : 夏装女装韩版学院风百搭高腰A字百褶裙宽松显瘦小半身裙短裙裤夏
             * UserType : 0
             * Volume : 248
             * ZkFinalPrice : 36.66
             */

            private String CatLeafName;
            private String CatName;
            private boolean FreeShipment;
            private boolean HGoodRate;
            private boolean HPayRate30;
            private boolean IRfdRate;
            private boolean IsPrepay;
            private String ItemUrl;
            private String MaterialLibType;
            private String Nick;
            private long NumIid;
            private String PictUrl;
            private String Provcity;
            private int Ratesum;
            private String ReservePrice;
            private long SellerId;
            private int ShopDsr;
            private String Title;
            private int UserType;
            private int Volume;
            private String ZkFinalPrice;
            private List<String> SmallImages;

            public String getCatLeafName() {
                return CatLeafName;
            }

            public void setCatLeafName(String CatLeafName) {
                this.CatLeafName = CatLeafName;
            }

            public String getCatName() {
                return CatName;
            }

            public void setCatName(String CatName) {
                this.CatName = CatName;
            }

            public boolean isFreeShipment() {
                return FreeShipment;
            }

            public void setFreeShipment(boolean FreeShipment) {
                this.FreeShipment = FreeShipment;
            }

            public boolean isHGoodRate() {
                return HGoodRate;
            }

            public void setHGoodRate(boolean HGoodRate) {
                this.HGoodRate = HGoodRate;
            }

            public boolean isHPayRate30() {
                return HPayRate30;
            }

            public void setHPayRate30(boolean HPayRate30) {
                this.HPayRate30 = HPayRate30;
            }

            public boolean isIRfdRate() {
                return IRfdRate;
            }

            public void setIRfdRate(boolean IRfdRate) {
                this.IRfdRate = IRfdRate;
            }

            public boolean isIsPrepay() {
                return IsPrepay;
            }

            public void setIsPrepay(boolean IsPrepay) {
                this.IsPrepay = IsPrepay;
            }

            public String getItemUrl() {
                return ItemUrl;
            }

            public void setItemUrl(String ItemUrl) {
                this.ItemUrl = ItemUrl;
            }

            public String getMaterialLibType() {
                return MaterialLibType;
            }

            public void setMaterialLibType(String MaterialLibType) {
                this.MaterialLibType = MaterialLibType;
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

            public String getProvcity() {
                return Provcity;
            }

            public void setProvcity(String Provcity) {
                this.Provcity = Provcity;
            }

            public int getRatesum() {
                return Ratesum;
            }

            public void setRatesum(int Ratesum) {
                this.Ratesum = Ratesum;
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

            public int getShopDsr() {
                return ShopDsr;
            }

            public void setShopDsr(int ShopDsr) {
                this.ShopDsr = ShopDsr;
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

            public List<String> getSmallImages() {
                return SmallImages;
            }

            public void setSmallImages(List<String> SmallImages) {
                this.SmallImages = SmallImages;
            }
        }
    }
}
