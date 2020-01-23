package com.youcoupon.john_li.youcouponshopping.YouModel;

import java.util.List;

public class MaterialClassifyItemOutModel {
    /**
     * status : 0
     * message : 查询成功
     * data : {"is_default":"false","map_data":[{"CategoryId":50011993,"CategoryName":null,"CommissionRate":"70.0","CouponEndTime":"1579103999000","CouponInfo":null,"CouponRemainCount":98789,"CouponShareUrl":"//uland.taobao.com/coupon/edetail?1","CouponStartFee":"338.0","CouponStartTime":"1578672000000","CouponTotalCount":100000,"ItemDescription":"因活动量大！素颜霜、面霜随机发出！！","ItemId":598129827832,"JddNum":0,"JddPrice":null,"LevelOneCategoryId":1801,"LevelOneCategoryName":"美容护肤/美体/精油","LockRate":null,"LockRateEndTime":0,"LockRateStartTime":0,"Nick":"一枝春旗舰店","Oetime":null,"OrigPrice":null,"Ostime":null,"PictUrl":"//gw.alicdn.com/bao/uploaded/i3/1586515126/O1CN01acCecn1njkobiWZrQ_!!0-item_pic.jpg","PresaleDeposit":null,"PresaleDiscountFeeText":null,"PresaleEndTime":0,"PresaleStartTime":0,"PresaleTailEndTime":0,"PresaleTailStartTime":0,"ReservePrice":null,"SellerId":1586515126,"SellNum":0,"ShopTitle":"一枝春旗舰店","ShortTitle":null,"SmallImages":["//img.alicdn.com/i1/1586515126/TB2L.QPXGnyQeBjy1zkXXXmyXXa_!!1586515126.jpg","//img.alicdn.com/i1/1586515126/TB22mecb4vzQeBjSZPfXXbWGFXa_!!1586515126.jpg"],"Stock":0,"Title":"红石榴六件套装护肤品补水保湿水乳全套学生化妆品正品男女士","TmallPlayActivityInfo":null,"TotalStock":0,"UserType":1,"UvSumPreSale":0,"Volume":28579,"WhiteImage":"https://img.alicdn.com/imgextra/TB1Ajlclvb2gK0jSZK9XXaEgFXa.png","XId":null,"YsylClickUrl":null,"YsylCommissionRate":null,"YsylTljFace":null,"YsylTljSendTime":null,"YsylTljUseEndTime":null,"YsylTljUseStartTime":null,"ZkFinalPrice":"338"}]}
     */

    private long status;
    private String message;
    private DataBean data;

    public long getStatus() {
        return status;
    }

    public void setStatus(long status) {
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
         * is_default : false
         * map_data : [{"CategoryId":50011993,"CategoryName":null,"CommissionRate":"70.0","CouponEndTime":"1579103999000","CouponInfo":null,"CouponRemainCount":98789,"CouponShareUrl":"//uland.taobao.com/coupon/edetail?1","CouponStartFee":"338.0","CouponStartTime":"1578672000000","CouponTotalCount":100000,"ItemDescription":"因活动量大！素颜霜、面霜随机发出！！","ItemId":598129827832,"JddNum":0,"JddPrice":null,"LevelOneCategoryId":1801,"LevelOneCategoryName":"美容护肤/美体/精油","LockRate":null,"LockRateEndTime":0,"LockRateStartTime":0,"Nick":"一枝春旗舰店","Oetime":null,"OrigPrice":null,"Ostime":null,"PictUrl":"//gw.alicdn.com/bao/uploaded/i3/1586515126/O1CN01acCecn1njkobiWZrQ_!!0-item_pic.jpg","PresaleDeposit":null,"PresaleDiscountFeeText":null,"PresaleEndTime":0,"PresaleStartTime":0,"PresaleTailEndTime":0,"PresaleTailStartTime":0,"ReservePrice":null,"SellerId":1586515126,"SellNum":0,"ShopTitle":"一枝春旗舰店","ShortTitle":null,"SmallImages":["//img.alicdn.com/i1/1586515126/TB2L.QPXGnyQeBjy1zkXXXmyXXa_!!1586515126.jpg","//img.alicdn.com/i1/1586515126/TB22mecb4vzQeBjSZPfXXbWGFXa_!!1586515126.jpg"],"Stock":0,"Title":"红石榴六件套装护肤品补水保湿水乳全套学生化妆品正品男女士","TmallPlayActivityInfo":null,"TotalStock":0,"UserType":1,"UvSumPreSale":0,"Volume":28579,"WhiteImage":"https://img.alicdn.com/imgextra/TB1Ajlclvb2gK0jSZK9XXaEgFXa.png","XId":null,"YsylClickUrl":null,"YsylCommissionRate":null,"YsylTljFace":null,"YsylTljSendTime":null,"YsylTljUseEndTime":null,"YsylTljUseStartTime":null,"ZkFinalPrice":"338"}]
         */

        private String is_default;
        private List<MaterialItemModel> map_data;

        public String getIs_default() {
            return is_default;
        }

        public void setIs_default(String is_default) {
            this.is_default = is_default;
        }

        public List<MaterialItemModel> getMap_data() {
            return map_data;
        }

        public void setMap_data(List<MaterialItemModel> map_data) {
            this.map_data = map_data;
        }

        public static class MaterialItemModel {
            /**
             * CategoryId : 50011993
             * CategoryName : null
             * CommissionRate : 70.0
             * CouponEndTime : 1579103999000
             * CouponInfo : null
             * CouponRemainCount : 98789
             * CouponShareUrl : //uland.taobao.com/coupon/edetail?1
             * CouponStartFee : 338.0
             * CouponStartTime : 1578672000000
             * CouponTotalCount : 100000
             * ItemDescription : 因活动量大！素颜霜、面霜随机发出！！
             * ItemId : 598129827832
             * JddNum : 0
             * JddPrice : null
             * LevelOneCategoryId : 1801
             * LevelOneCategoryName : 美容护肤/美体/精油
             * LockRate : null
             * LockRateEndTime : 0
             * LockRateStartTime : 0
             * Nick : 一枝春旗舰店
             * Oetime : null
             * OrigPrice : null
             * Ostime : null
             * PictUrl : //gw.alicdn.com/bao/uploaded/i3/1586515126/O1CN01acCecn1njkobiWZrQ_!!0-item_pic.jpg
             * PresaleDeposit : null
             * PresaleDiscountFeeText : null
             * PresaleEndTime : 0
             * PresaleStartTime : 0
             * PresaleTailEndTime : 0
             * PresaleTailStartTime : 0
             * ReservePrice : null
             * SellerId : 1586515126
             * SellNum : 0
             * ShopTitle : 一枝春旗舰店
             * ShortTitle : null
             * SmallImages : ["//img.alicdn.com/i1/1586515126/TB2L.QPXGnyQeBjy1zkXXXmyXXa_!!1586515126.jpg","//img.alicdn.com/i1/1586515126/TB22mecb4vzQeBjSZPfXXbWGFXa_!!1586515126.jpg"]
             * Stock : 0
             * Title : 红石榴六件套装护肤品补水保湿水乳全套学生化妆品正品男女士
             * TmallPlayActivityInfo : null
             * TotalStock : 0
             * UserType : 1
             * UvSumPreSale : 0
             * Volume : 28579
             * WhiteImage : https://img.alicdn.com/imgextra/TB1Ajlclvb2gK0jSZK9XXaEgFXa.png
             * XId : null
             * YsylClickUrl : null
             * YsylCommissionRate : null
             * YsylTljFace : null
             * YsylTljSendTime : null
             * YsylTljUseEndTime : null
             * YsylTljUseStartTime : null
             * ZkFinalPrice : 338
             */

            private long CategoryId;
            private Object CategoryName;
            private String CommissionRate;
            private String CouponEndTime;
            private String CouponInfo;
            private long CouponRemainCount;
            private String CouponShareUrl;
            private String CouponStartFee;
            private String CouponStartTime;
            private long CouponTotalCount;
            private String ItemDescription;
            private long ItemId;
            private long JddNum;
            private String JddPrice;
            private long LevelOneCategoryId;
            private String LevelOneCategoryName;
            private long LockRate;
            private long LockRateEndTime;
            private long LockRateStartTime;
            private String Nick;
            private String Oetime;
            private String OrigPrice;
            private String Ostime;
            private String PictUrl;
            private String PresaleDeposit;
            private String PresaleDiscountFeeText;
            private long PresaleEndTime;
            private long PresaleStartTime;
            private long PresaleTailEndTime;
            private long PresaleTailStartTime;
            private String ReservePrice;
            private long SellerId;
            private long SellNum;
            private String ShopTitle;
            private String ShortTitle;
            private long Stock;
            private String Title;
            private String TmallPlayActivityInfo;
            private long TotalStock;
            private long UserType;
            private long UvSumPreSale;
            private long Volume;
            private String WhiteImage;
            private String XId;
            private String YsylClickUrl;
            private String YsylCommissionRate;
            private String YsylTljFace;
            private String YsylTljSendTime;
            private String YsylTljUseEndTime;
            private String YsylTljUseStartTime;
            private String ZkFinalPrice;
            private List<String> SmallImages;
            private String ItemUrl;
            private String Provcity;

            public String getProvcity() {
                return Provcity;
            }

            public void setProvcity(String provcity) {
                Provcity = provcity;
            }

            public String getItemUrl() {
                return ItemUrl;
            }

            public void setItemUrl(String itemUrl) {
                ItemUrl = itemUrl;
            }

            public long getCategoryId() {
                return CategoryId;
            }

            public void setCategoryId(long CategoryId) {
                this.CategoryId = CategoryId;
            }

            public Object getCategoryName() {
                return CategoryName;
            }

            public void setCategoryName(Object CategoryName) {
                this.CategoryName = CategoryName;
            }

            public String getCommissionRate() {
                return CommissionRate;
            }

            public void setCommissionRate(String CommissionRate) {
                this.CommissionRate = CommissionRate;
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

            public String getCouponShareUrl() {
                return CouponShareUrl;
            }

            public void setCouponShareUrl(String CouponShareUrl) {
                this.CouponShareUrl = CouponShareUrl;
            }

            public String getCouponStartFee() {
                return CouponStartFee;
            }

            public void setCouponStartFee(String CouponStartFee) {
                this.CouponStartFee = CouponStartFee;
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

            public long getItemId() {
                return ItemId;
            }

            public void setItemId(long ItemId) {
                this.ItemId = ItemId;
            }

            public long getJddNum() {
                return JddNum;
            }

            public void setJddNum(long JddNum) {
                this.JddNum = JddNum;
            }

            public String getJddPrice() {
                return JddPrice;
            }

            public void setJddPrice(String JddPrice) {
                this.JddPrice = JddPrice;
            }

            public long getLevelOneCategoryId() {
                return LevelOneCategoryId;
            }

            public void setLevelOneCategoryId(long LevelOneCategoryId) {
                this.LevelOneCategoryId = LevelOneCategoryId;
            }

            public String getLevelOneCategoryName() {
                return LevelOneCategoryName;
            }

            public void setLevelOneCategoryName(String LevelOneCategoryName) {
                this.LevelOneCategoryName = LevelOneCategoryName;
            }

            public long getLockRate() {
                return LockRate;
            }

            public void setLockRate(long LockRate) {
                this.LockRate = LockRate;
            }

            public long getLockRateEndTime() {
                return LockRateEndTime;
            }

            public void setLockRateEndTime(long LockRateEndTime) {
                this.LockRateEndTime = LockRateEndTime;
            }

            public long getLockRateStartTime() {
                return LockRateStartTime;
            }

            public void setLockRateStartTime(long LockRateStartTime) {
                this.LockRateStartTime = LockRateStartTime;
            }

            public String getNick() {
                return Nick;
            }

            public void setNick(String Nick) {
                this.Nick = Nick;
            }

            public String getOetime() {
                return Oetime;
            }

            public void setOetime(String Oetime) {
                this.Oetime = Oetime;
            }

            public String getOrigPrice() {
                return OrigPrice;
            }

            public void setOrigPrice(String OrigPrice) {
                this.OrigPrice = OrigPrice;
            }

            public String getOstime() {
                return Ostime;
            }

            public void setOstime(String Ostime) {
                this.Ostime = Ostime;
            }

            public String getPictUrl() {
                return PictUrl;
            }

            public void setPictUrl(String PictUrl) {
                this.PictUrl = PictUrl;
            }

            public String getPresaleDeposit() {
                return PresaleDeposit;
            }

            public void setPresaleDeposit(String PresaleDeposit) {
                this.PresaleDeposit = PresaleDeposit;
            }

            public String getPresaleDiscountFeeText() {
                return PresaleDiscountFeeText;
            }

            public void setPresaleDiscountFeeText(String PresaleDiscountFeeText) {
                this.PresaleDiscountFeeText = PresaleDiscountFeeText;
            }

            public long getPresaleEndTime() {
                return PresaleEndTime;
            }

            public void setPresaleEndTime(long PresaleEndTime) {
                this.PresaleEndTime = PresaleEndTime;
            }

            public long getPresaleStartTime() {
                return PresaleStartTime;
            }

            public void setPresaleStartTime(long PresaleStartTime) {
                this.PresaleStartTime = PresaleStartTime;
            }

            public long getPresaleTailEndTime() {
                return PresaleTailEndTime;
            }

            public void setPresaleTailEndTime(long PresaleTailEndTime) {
                this.PresaleTailEndTime = PresaleTailEndTime;
            }

            public long getPresaleTailStartTime() {
                return PresaleTailStartTime;
            }

            public void setPresaleTailStartTime(long PresaleTailStartTime) {
                this.PresaleTailStartTime = PresaleTailStartTime;
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

            public long getSellNum() {
                return SellNum;
            }

            public void setSellNum(long SellNum) {
                this.SellNum = SellNum;
            }

            public String getShopTitle() {
                return ShopTitle;
            }

            public void setShopTitle(String ShopTitle) {
                this.ShopTitle = ShopTitle;
            }

            public String getShortTitle() {
                return ShortTitle;
            }

            public void setShortTitle(String ShortTitle) {
                this.ShortTitle = ShortTitle;
            }

            public long getStock() {
                return Stock;
            }

            public void setStock(long Stock) {
                this.Stock = Stock;
            }

            public String getTitle() {
                return Title;
            }

            public void setTitle(String Title) {
                this.Title = Title;
            }

            public String getTmallPlayActivityInfo() {
                return TmallPlayActivityInfo;
            }

            public void setTmallPlayActivityInfo(String TmallPlayActivityInfo) {
                this.TmallPlayActivityInfo = TmallPlayActivityInfo;
            }

            public long getTotalStock() {
                return TotalStock;
            }

            public void setTotalStock(long TotalStock) {
                this.TotalStock = TotalStock;
            }

            public long getUserType() {
                return UserType;
            }

            public void setUserType(long UserType) {
                this.UserType = UserType;
            }

            public long getUvSumPreSale() {
                return UvSumPreSale;
            }

            public void setUvSumPreSale(long UvSumPreSale) {
                this.UvSumPreSale = UvSumPreSale;
            }

            public long getVolume() {
                return Volume;
            }

            public void setVolume(long Volume) {
                this.Volume = Volume;
            }

            public String getWhiteImage() {
                return WhiteImage;
            }

            public void setWhiteImage(String WhiteImage) {
                this.WhiteImage = WhiteImage;
            }

            public String getXId() {
                return XId;
            }

            public void setXId(String XId) {
                this.XId = XId;
            }

            public String getYsylClickUrl() {
                return YsylClickUrl;
            }

            public void setYsylClickUrl(String YsylClickUrl) {
                this.YsylClickUrl = YsylClickUrl;
            }

            public String getYsylCommissionRate() {
                return YsylCommissionRate;
            }

            public void setYsylCommissionRate(String YsylCommissionRate) {
                this.YsylCommissionRate = YsylCommissionRate;
            }

            public String getYsylTljFace() {
                return YsylTljFace;
            }

            public void setYsylTljFace(String YsylTljFace) {
                this.YsylTljFace = YsylTljFace;
            }

            public String getYsylTljSendTime() {
                return YsylTljSendTime;
            }

            public void setYsylTljSendTime(String YsylTljSendTime) {
                this.YsylTljSendTime = YsylTljSendTime;
            }

            public String getYsylTljUseEndTime() {
                return YsylTljUseEndTime;
            }

            public void setYsylTljUseEndTime(String YsylTljUseEndTime) {
                this.YsylTljUseEndTime = YsylTljUseEndTime;
            }

            public String getYsylTljUseStartTime() {
                return YsylTljUseStartTime;
            }

            public void setYsylTljUseStartTime(String YsylTljUseStartTime) {
                this.YsylTljUseStartTime = YsylTljUseStartTime;
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
