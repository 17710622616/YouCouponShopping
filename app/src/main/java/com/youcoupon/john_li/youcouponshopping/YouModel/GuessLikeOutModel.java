package com.youcoupon.john_li.youcouponshopping.YouModel;

import java.util.List;

public class GuessLikeOutModel {
    /**
     * status : 0
     * message : 查询成功
     * data : {"is_default":"false","map_data":[{"CategoryId":50011999,"CategoryName":"单方精油","ClickUrl":"//s.click.taobao.com/1231","CommissionRate":"90.0","CouponAmount":30,"CouponClickUrl":"123","CouponEndTime":"1577548799000","CouponInfo":null,"CouponRemainCount":95000,"CouponShareUrl":"123","CouponStartFee":"35.0","CouponStartTime":"1577376000000","CouponTotalCount":100000,"ItemDescription":"全场精油买2送1  巨划算","ItemId":577039868323,"JddNum":0,"JddPrice":null,"JuOnlineEndTime":null,"JuOnlineStartTime":null,"JuPlayEndTime":0,"JuPlayStartTime":0,"LevelOneCategoryId":1801,"LevelOneCategoryName":"美容护肤/美体/精油","LockRate":null,"LockRateEndTime":0,"LockRateStartTime":0,"MaochaoPlayConditions":null,"MaochaoPlayDiscounts":null,"MaochaoPlayDiscountType":null,"MaochaoPlayEndTime":null,"MaochaoPlayFreePostFee":null,"MaochaoPlayStartTime":null,"MultiCouponItemCount":null,"MultiCouponZkRate":null,"NewUserPrice":null,"Nick":"茁美化妆品旗舰店","Oetime":null,"OrigPrice":null,"Ostime":null,"PictUrl":"//img.alicdn.com/bao/uploaded/i1/2911477564/O1CN01RdE8bX25kMQA9ZE74_!!2911477564-0-pixelsss.jpg","PlayInfo":null,"PresaleDeposit":null,"PresaleDiscountFeeText":null,"PresaleEndTime":0,"PresaleStartTime":0,"PresaleTailEndTime":0,"PresaleTailStartTime":0,"PriceAfterMultiCoupon":null,"PromotionCondition":null,"PromotionDiscount":null,"PromotionInfo":null,"PromotionType":null,"ReservePrice":null,"SellerId":2911477564,"SellNum":0,"ShopTitle":"茁美化妆品旗舰店","ShortTitle":"生姜艾草全身按摩通经络发热精油","SmallImages":["//img.alicdn.com/i1/2911477564/O1CN01fmrx1J25kMP8MdO1z_!!2911477564.jpg","//img.alicdn.com/i3/2911477564/O1CN01dggSLQ25kMOv2mjuK_!!2911477564.jpg"],"Stock":0,"Title":"生姜艾草全身按摩精油通经络发热姜玫瑰面部开背推背刮痧推油肩颈","TmallPlayActivityEndTime":0,"TmallPlayActivityInfo":null,"TmallPlayActivityStartTime":0,"TotalStock":0,"UserType":1,"UvSumPreSale":0,"Volume":88789,"WhiteImage":"https://img.alicdn.com/bao/uploaded/TB18YAJqW61gK0jSZFlXXXDKFXa.png","WordList":[],"XId":"6Qortzn5qxx7xLjpM39EJaxvzrElvAXtOZUoth6szi5c3zVdmdyJAjXfRrbXOSVXsT5yvIoTQp9M1HhYf8yDTx798j1Y5UbAf6EQfXz66oVX","YsylClickUrl":null,"YsylCommissionRate":null,"YsylTljFace":null,"YsylTljSendTime":null,"YsylTljUseEndTime":null,"YsylTljUseStartTime":null,"ZkFinalPrice":"35.8"}]}
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
         * is_default : false
         * map_data : [{"CategoryId":50011999,"CategoryName":"单方精油","ClickUrl":"//s.click.taobao.com/1231","CommissionRate":"90.0","CouponAmount":30,"CouponClickUrl":"123","CouponEndTime":"1577548799000","CouponInfo":null,"CouponRemainCount":95000,"CouponShareUrl":"123","CouponStartFee":"35.0","CouponStartTime":"1577376000000","CouponTotalCount":100000,"ItemDescription":"全场精油买2送1  巨划算","ItemId":577039868323,"JddNum":0,"JddPrice":null,"JuOnlineEndTime":null,"JuOnlineStartTime":null,"JuPlayEndTime":0,"JuPlayStartTime":0,"LevelOneCategoryId":1801,"LevelOneCategoryName":"美容护肤/美体/精油","LockRate":null,"LockRateEndTime":0,"LockRateStartTime":0,"MaochaoPlayConditions":null,"MaochaoPlayDiscounts":null,"MaochaoPlayDiscountType":null,"MaochaoPlayEndTime":null,"MaochaoPlayFreePostFee":null,"MaochaoPlayStartTime":null,"MultiCouponItemCount":null,"MultiCouponZkRate":null,"NewUserPrice":null,"Nick":"茁美化妆品旗舰店","Oetime":null,"OrigPrice":null,"Ostime":null,"PictUrl":"//img.alicdn.com/bao/uploaded/i1/2911477564/O1CN01RdE8bX25kMQA9ZE74_!!2911477564-0-pixelsss.jpg","PlayInfo":null,"PresaleDeposit":null,"PresaleDiscountFeeText":null,"PresaleEndTime":0,"PresaleStartTime":0,"PresaleTailEndTime":0,"PresaleTailStartTime":0,"PriceAfterMultiCoupon":null,"PromotionCondition":null,"PromotionDiscount":null,"PromotionInfo":null,"PromotionType":null,"ReservePrice":null,"SellerId":2911477564,"SellNum":0,"ShopTitle":"茁美化妆品旗舰店","ShortTitle":"生姜艾草全身按摩通经络发热精油","SmallImages":["//img.alicdn.com/i1/2911477564/O1CN01fmrx1J25kMP8MdO1z_!!2911477564.jpg","//img.alicdn.com/i3/2911477564/O1CN01dggSLQ25kMOv2mjuK_!!2911477564.jpg"],"Stock":0,"Title":"生姜艾草全身按摩精油通经络发热姜玫瑰面部开背推背刮痧推油肩颈","TmallPlayActivityEndTime":0,"TmallPlayActivityInfo":null,"TmallPlayActivityStartTime":0,"TotalStock":0,"UserType":1,"UvSumPreSale":0,"Volume":88789,"WhiteImage":"https://img.alicdn.com/bao/uploaded/TB18YAJqW61gK0jSZFlXXXDKFXa.png","WordList":[],"XId":"6Qortzn5qxx7xLjpM39EJaxvzrElvAXtOZUoth6szi5c3zVdmdyJAjXfRrbXOSVXsT5yvIoTQp9M1HhYf8yDTx798j1Y5UbAf6EQfXz66oVX","YsylClickUrl":null,"YsylCommissionRate":null,"YsylTljFace":null,"YsylTljSendTime":null,"YsylTljUseEndTime":null,"YsylTljUseStartTime":null,"ZkFinalPrice":"35.8"}]
         */

        private boolean is_default;
        private List<MapGuessLikeBean> map_data;

        public boolean getIs_default() {
            return is_default;
        }

        public void setIs_default(boolean is_default) {
            this.is_default = is_default;
        }

        public List<MapGuessLikeBean> getMap_data() {
            return map_data;
        }

        public void setMap_data(List<MapGuessLikeBean> map_data) {
            this.map_data = map_data;
        }

        public static class MapGuessLikeBean {
            /**
             * CategoryId : 50011999
             * CategoryName : 单方精油
             * ClickUrl : //s.click.taobao.com/1231
             * CommissionRate : 90.0
             * CouponAmount : 30
             * CouponClickUrl : 123
             * CouponEndTime : 1577548799000
             * CouponInfo : null
             * CouponRemainCount : 95000
             * CouponShareUrl : 123
             * CouponStartFee : 35.0
             * CouponStartTime : 1577376000000
             * CouponTotalCount : 100000
             * ItemDescription : 全场精油买2送1  巨划算
             * ItemId : 577039868323
             * JddNum : 0
             * JddPrice : null
             * JuOnlineEndTime : null
             * JuOnlineStartTime : null
             * JuPlayEndTime : 0
             * JuPlayStartTime : 0
             * LevelOneCategoryId : 1801
             * LevelOneCategoryName : 美容护肤/美体/精油
             * LockRate : null
             * LockRateEndTime : 0
             * LockRateStartTime : 0
             * MaochaoPlayConditions : null
             * MaochaoPlayDiscounts : null
             * MaochaoPlayDiscountType : null
             * MaochaoPlayEndTime : null
             * MaochaoPlayFreePostFee : null
             * MaochaoPlayStartTime : null
             * MultiCouponItemCount : null
             * MultiCouponZkRate : null
             * NewUserPrice : null
             * Nick : 茁美化妆品旗舰店
             * Oetime : null
             * OrigPrice : null
             * Ostime : null
             * PictUrl : //img.alicdn.com/bao/uploaded/i1/2911477564/O1CN01RdE8bX25kMQA9ZE74_!!2911477564-0-pixelsss.jpg
             * PlayInfo : null
             * PresaleDeposit : null
             * PresaleDiscountFeeText : null
             * PresaleEndTime : 0
             * PresaleStartTime : 0
             * PresaleTailEndTime : 0
             * PresaleTailStartTime : 0
             * PriceAfterMultiCoupon : null
             * PromotionCondition : null
             * PromotionDiscount : null
             * PromotionInfo : null
             * PromotionType : null
             * ReservePrice : null
             * SellerId : 2911477564
             * SellNum : 0
             * ShopTitle : 茁美化妆品旗舰店
             * ShortTitle : 生姜艾草全身按摩通经络发热精油
             * SmallImages : ["//img.alicdn.com/i1/2911477564/O1CN01fmrx1J25kMP8MdO1z_!!2911477564.jpg","//img.alicdn.com/i3/2911477564/O1CN01dggSLQ25kMOv2mjuK_!!2911477564.jpg"]
             * Stock : 0
             * Title : 生姜艾草全身按摩精油通经络发热姜玫瑰面部开背推背刮痧推油肩颈
             * TmallPlayActivityEndTime : 0
             * TmallPlayActivityInfo : null
             * TmallPlayActivityStartTime : 0
             * TotalStock : 0
             * UserType : 1
             * UvSumPreSale : 0
             * Volume : 88789
             * WhiteImage : https://img.alicdn.com/bao/uploaded/TB18YAJqW61gK0jSZFlXXXDKFXa.png
             * WordList : []
             * XId : 6Qortzn5qxx7xLjpM39EJaxvzrElvAXtOZUoth6szi5c3zVdmdyJAjXfRrbXOSVXsT5yvIoTQp9M1HhYf8yDTx798j1Y5UbAf6EQfXz66oVX
             * YsylClickUrl : null
             * YsylCommissionRate : null
             * YsylTljFace : null
             * YsylTljSendTime : null
             * YsylTljUseEndTime : null
             * YsylTljUseStartTime : null
             * ZkFinalPrice : 35.8
             */

            private int CategoryId;
            private String CategoryName;
            private String ClickUrl;
            private String CommissionRate;
            private int CouponAmount;
            private String CouponClickUrl;
            private String CouponEndTime;
            private String CouponInfo;
            private int CouponRemainCount;
            private String CouponShareUrl;
            private String CouponStartFee;
            private String CouponStartTime;
            private int CouponTotalCount;
            private String ItemDescription;
            private long ItemId;
            private int JddNum;
            private Object JddPrice;
            private Object JuOnlineEndTime;
            private Object JuOnlineStartTime;
            private int JuPlayEndTime;
            private int JuPlayStartTime;
            private int LevelOneCategoryId;
            private String LevelOneCategoryName;
            private Object LockRate;
            private int LockRateEndTime;
            private int LockRateStartTime;
            private Object MaochaoPlayConditions;
            private Object MaochaoPlayDiscounts;
            private Object MaochaoPlayDiscountType;
            private Object MaochaoPlayEndTime;
            private Object MaochaoPlayFreePostFee;
            private Object MaochaoPlayStartTime;
            private Object MultiCouponItemCount;
            private Object MultiCouponZkRate;
            private Object NewUserPrice;
            private String Nick;
            private Object Oetime;
            private Object OrigPrice;
            private Object Ostime;
            private String PictUrl;
            private Object PlayInfo;
            private Object PresaleDeposit;
            private Object PresaleDiscountFeeText;
            private int PresaleEndTime;
            private int PresaleStartTime;
            private int PresaleTailEndTime;
            private int PresaleTailStartTime;
            private Object PriceAfterMultiCoupon;
            private Object PromotionCondition;
            private Object PromotionDiscount;
            private Object PromotionInfo;
            private Object PromotionType;
            private Object ReservePrice;
            private long SellerId;
            private int SellNum;
            private String ShopTitle;
            private String ShortTitle;
            private int Stock;
            private String Title;
            private int TmallPlayActivityEndTime;
            private Object TmallPlayActivityInfo;
            private int TmallPlayActivityStartTime;
            private int TotalStock;
            private int UserType;
            private int UvSumPreSale;
            private int Volume;
            private String WhiteImage;
            private String XId;
            private Object YsylClickUrl;
            private Object YsylCommissionRate;
            private Object YsylTljFace;
            private Object YsylTljSendTime;
            private Object YsylTljUseEndTime;
            private Object YsylTljUseStartTime;
            private String ZkFinalPrice;
            private List<String> SmallImages;
            private List<?> WordList;

            public int getCategoryId() {
                return CategoryId;
            }

            public void setCategoryId(int CategoryId) {
                this.CategoryId = CategoryId;
            }

            public String getCategoryName() {
                return CategoryName;
            }

            public void setCategoryName(String CategoryName) {
                this.CategoryName = CategoryName;
            }

            public String getClickUrl() {
                return ClickUrl;
            }

            public void setClickUrl(String ClickUrl) {
                this.ClickUrl = ClickUrl;
            }

            public String getCommissionRate() {
                return CommissionRate;
            }

            public void setCommissionRate(String CommissionRate) {
                this.CommissionRate = CommissionRate;
            }

            public int getCouponAmount() {
                return CouponAmount;
            }

            public void setCouponAmount(int CouponAmount) {
                this.CouponAmount = CouponAmount;
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

            public int getCouponRemainCount() {
                return CouponRemainCount;
            }

            public void setCouponRemainCount(int CouponRemainCount) {
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

            public int getCouponTotalCount() {
                return CouponTotalCount;
            }

            public void setCouponTotalCount(int CouponTotalCount) {
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

            public int getJddNum() {
                return JddNum;
            }

            public void setJddNum(int JddNum) {
                this.JddNum = JddNum;
            }

            public Object getJddPrice() {
                return JddPrice;
            }

            public void setJddPrice(Object JddPrice) {
                this.JddPrice = JddPrice;
            }

            public Object getJuOnlineEndTime() {
                return JuOnlineEndTime;
            }

            public void setJuOnlineEndTime(Object JuOnlineEndTime) {
                this.JuOnlineEndTime = JuOnlineEndTime;
            }

            public Object getJuOnlineStartTime() {
                return JuOnlineStartTime;
            }

            public void setJuOnlineStartTime(Object JuOnlineStartTime) {
                this.JuOnlineStartTime = JuOnlineStartTime;
            }

            public int getJuPlayEndTime() {
                return JuPlayEndTime;
            }

            public void setJuPlayEndTime(int JuPlayEndTime) {
                this.JuPlayEndTime = JuPlayEndTime;
            }

            public int getJuPlayStartTime() {
                return JuPlayStartTime;
            }

            public void setJuPlayStartTime(int JuPlayStartTime) {
                this.JuPlayStartTime = JuPlayStartTime;
            }

            public int getLevelOneCategoryId() {
                return LevelOneCategoryId;
            }

            public void setLevelOneCategoryId(int LevelOneCategoryId) {
                this.LevelOneCategoryId = LevelOneCategoryId;
            }

            public String getLevelOneCategoryName() {
                return LevelOneCategoryName;
            }

            public void setLevelOneCategoryName(String LevelOneCategoryName) {
                this.LevelOneCategoryName = LevelOneCategoryName;
            }

            public Object getLockRate() {
                return LockRate;
            }

            public void setLockRate(Object LockRate) {
                this.LockRate = LockRate;
            }

            public int getLockRateEndTime() {
                return LockRateEndTime;
            }

            public void setLockRateEndTime(int LockRateEndTime) {
                this.LockRateEndTime = LockRateEndTime;
            }

            public int getLockRateStartTime() {
                return LockRateStartTime;
            }

            public void setLockRateStartTime(int LockRateStartTime) {
                this.LockRateStartTime = LockRateStartTime;
            }

            public Object getMaochaoPlayConditions() {
                return MaochaoPlayConditions;
            }

            public void setMaochaoPlayConditions(Object MaochaoPlayConditions) {
                this.MaochaoPlayConditions = MaochaoPlayConditions;
            }

            public Object getMaochaoPlayDiscounts() {
                return MaochaoPlayDiscounts;
            }

            public void setMaochaoPlayDiscounts(Object MaochaoPlayDiscounts) {
                this.MaochaoPlayDiscounts = MaochaoPlayDiscounts;
            }

            public Object getMaochaoPlayDiscountType() {
                return MaochaoPlayDiscountType;
            }

            public void setMaochaoPlayDiscountType(Object MaochaoPlayDiscountType) {
                this.MaochaoPlayDiscountType = MaochaoPlayDiscountType;
            }

            public Object getMaochaoPlayEndTime() {
                return MaochaoPlayEndTime;
            }

            public void setMaochaoPlayEndTime(Object MaochaoPlayEndTime) {
                this.MaochaoPlayEndTime = MaochaoPlayEndTime;
            }

            public Object getMaochaoPlayFreePostFee() {
                return MaochaoPlayFreePostFee;
            }

            public void setMaochaoPlayFreePostFee(Object MaochaoPlayFreePostFee) {
                this.MaochaoPlayFreePostFee = MaochaoPlayFreePostFee;
            }

            public Object getMaochaoPlayStartTime() {
                return MaochaoPlayStartTime;
            }

            public void setMaochaoPlayStartTime(Object MaochaoPlayStartTime) {
                this.MaochaoPlayStartTime = MaochaoPlayStartTime;
            }

            public Object getMultiCouponItemCount() {
                return MultiCouponItemCount;
            }

            public void setMultiCouponItemCount(Object MultiCouponItemCount) {
                this.MultiCouponItemCount = MultiCouponItemCount;
            }

            public Object getMultiCouponZkRate() {
                return MultiCouponZkRate;
            }

            public void setMultiCouponZkRate(Object MultiCouponZkRate) {
                this.MultiCouponZkRate = MultiCouponZkRate;
            }

            public Object getNewUserPrice() {
                return NewUserPrice;
            }

            public void setNewUserPrice(Object NewUserPrice) {
                this.NewUserPrice = NewUserPrice;
            }

            public String getNick() {
                return Nick;
            }

            public void setNick(String Nick) {
                this.Nick = Nick;
            }

            public Object getOetime() {
                return Oetime;
            }

            public void setOetime(Object Oetime) {
                this.Oetime = Oetime;
            }

            public Object getOrigPrice() {
                return OrigPrice;
            }

            public void setOrigPrice(Object OrigPrice) {
                this.OrigPrice = OrigPrice;
            }

            public Object getOstime() {
                return Ostime;
            }

            public void setOstime(Object Ostime) {
                this.Ostime = Ostime;
            }

            public String getPictUrl() {
                return PictUrl;
            }

            public void setPictUrl(String PictUrl) {
                this.PictUrl = PictUrl;
            }

            public Object getPlayInfo() {
                return PlayInfo;
            }

            public void setPlayInfo(Object PlayInfo) {
                this.PlayInfo = PlayInfo;
            }

            public Object getPresaleDeposit() {
                return PresaleDeposit;
            }

            public void setPresaleDeposit(Object PresaleDeposit) {
                this.PresaleDeposit = PresaleDeposit;
            }

            public Object getPresaleDiscountFeeText() {
                return PresaleDiscountFeeText;
            }

            public void setPresaleDiscountFeeText(Object PresaleDiscountFeeText) {
                this.PresaleDiscountFeeText = PresaleDiscountFeeText;
            }

            public int getPresaleEndTime() {
                return PresaleEndTime;
            }

            public void setPresaleEndTime(int PresaleEndTime) {
                this.PresaleEndTime = PresaleEndTime;
            }

            public int getPresaleStartTime() {
                return PresaleStartTime;
            }

            public void setPresaleStartTime(int PresaleStartTime) {
                this.PresaleStartTime = PresaleStartTime;
            }

            public int getPresaleTailEndTime() {
                return PresaleTailEndTime;
            }

            public void setPresaleTailEndTime(int PresaleTailEndTime) {
                this.PresaleTailEndTime = PresaleTailEndTime;
            }

            public int getPresaleTailStartTime() {
                return PresaleTailStartTime;
            }

            public void setPresaleTailStartTime(int PresaleTailStartTime) {
                this.PresaleTailStartTime = PresaleTailStartTime;
            }

            public Object getPriceAfterMultiCoupon() {
                return PriceAfterMultiCoupon;
            }

            public void setPriceAfterMultiCoupon(Object PriceAfterMultiCoupon) {
                this.PriceAfterMultiCoupon = PriceAfterMultiCoupon;
            }

            public Object getPromotionCondition() {
                return PromotionCondition;
            }

            public void setPromotionCondition(Object PromotionCondition) {
                this.PromotionCondition = PromotionCondition;
            }

            public Object getPromotionDiscount() {
                return PromotionDiscount;
            }

            public void setPromotionDiscount(Object PromotionDiscount) {
                this.PromotionDiscount = PromotionDiscount;
            }

            public Object getPromotionInfo() {
                return PromotionInfo;
            }

            public void setPromotionInfo(Object PromotionInfo) {
                this.PromotionInfo = PromotionInfo;
            }

            public Object getPromotionType() {
                return PromotionType;
            }

            public void setPromotionType(Object PromotionType) {
                this.PromotionType = PromotionType;
            }

            public Object getReservePrice() {
                return ReservePrice;
            }

            public void setReservePrice(Object ReservePrice) {
                this.ReservePrice = ReservePrice;
            }

            public long getSellerId() {
                return SellerId;
            }

            public void setSellerId(long SellerId) {
                this.SellerId = SellerId;
            }

            public int getSellNum() {
                return SellNum;
            }

            public void setSellNum(int SellNum) {
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

            public int getStock() {
                return Stock;
            }

            public void setStock(int Stock) {
                this.Stock = Stock;
            }

            public String getTitle() {
                return Title;
            }

            public void setTitle(String Title) {
                this.Title = Title;
            }

            public int getTmallPlayActivityEndTime() {
                return TmallPlayActivityEndTime;
            }

            public void setTmallPlayActivityEndTime(int TmallPlayActivityEndTime) {
                this.TmallPlayActivityEndTime = TmallPlayActivityEndTime;
            }

            public Object getTmallPlayActivityInfo() {
                return TmallPlayActivityInfo;
            }

            public void setTmallPlayActivityInfo(Object TmallPlayActivityInfo) {
                this.TmallPlayActivityInfo = TmallPlayActivityInfo;
            }

            public int getTmallPlayActivityStartTime() {
                return TmallPlayActivityStartTime;
            }

            public void setTmallPlayActivityStartTime(int TmallPlayActivityStartTime) {
                this.TmallPlayActivityStartTime = TmallPlayActivityStartTime;
            }

            public int getTotalStock() {
                return TotalStock;
            }

            public void setTotalStock(int TotalStock) {
                this.TotalStock = TotalStock;
            }

            public int getUserType() {
                return UserType;
            }

            public void setUserType(int UserType) {
                this.UserType = UserType;
            }

            public int getUvSumPreSale() {
                return UvSumPreSale;
            }

            public void setUvSumPreSale(int UvSumPreSale) {
                this.UvSumPreSale = UvSumPreSale;
            }

            public int getVolume() {
                return Volume;
            }

            public void setVolume(int Volume) {
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

            public Object getYsylClickUrl() {
                return YsylClickUrl;
            }

            public void setYsylClickUrl(Object YsylClickUrl) {
                this.YsylClickUrl = YsylClickUrl;
            }

            public Object getYsylCommissionRate() {
                return YsylCommissionRate;
            }

            public void setYsylCommissionRate(Object YsylCommissionRate) {
                this.YsylCommissionRate = YsylCommissionRate;
            }

            public Object getYsylTljFace() {
                return YsylTljFace;
            }

            public void setYsylTljFace(Object YsylTljFace) {
                this.YsylTljFace = YsylTljFace;
            }

            public Object getYsylTljSendTime() {
                return YsylTljSendTime;
            }

            public void setYsylTljSendTime(Object YsylTljSendTime) {
                this.YsylTljSendTime = YsylTljSendTime;
            }

            public Object getYsylTljUseEndTime() {
                return YsylTljUseEndTime;
            }

            public void setYsylTljUseEndTime(Object YsylTljUseEndTime) {
                this.YsylTljUseEndTime = YsylTljUseEndTime;
            }

            public Object getYsylTljUseStartTime() {
                return YsylTljUseStartTime;
            }

            public void setYsylTljUseStartTime(Object YsylTljUseStartTime) {
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

            public List<?> getWordList() {
                return WordList;
            }

            public void setWordList(List<?> WordList) {
                this.WordList = WordList;
            }
        }
    }
}
