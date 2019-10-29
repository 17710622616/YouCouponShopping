package com.youcoupon.john_li.youcouponshopping.YouUtils;

/**
 * Created by John_Li on 2/4/2019.
 */

public class YouConfigor {
    // 測試地址
    //public static final String BASE_URL = "http://youcouponapi.azurewebsites.net/";
    public static final String BASE_URL = "http://114.67.76.158:10082/";
    // 選品庫API
    public static final String FAVORITES_LIST = "api/Uatm/GetFavorites?";
    // 選品庫宝贝详情API
    public static final String FAVORITES_ITEM_LIST = "api/Uatm/GetFavoritesItem?";
    // 商品详情API
    public static final String ITEM_INFO = "api/Item/GetItemInfo?";
    // 相关商家API
    public static final String SELLER_LIST = "api/Shop/GetShopRecommend?";
    // 商品推荐API
    public static final String ITEM_RECOMMEND_LIST = "api/Item/GetRecommend?";
    // 好券查询API
    public static final String COUPON_SEARCH_LIST = "api/Item/SearchCouponItem?";
}
