package com.youcoupon.john_li.youcouponshopping.YouUtils;

/**
 * Created by John_Li on 2/4/2019.
 */

public class YouConfigor {
    public static final int LOGIN_FOR_RQUEST = 10001;

    /*********************************************************************地址********************************************************************************/
    // 測試地址
    //public static final String BASE_URL = "http://youcouponapi.azurewebsites.net/";
    public static final String BASE_URL = "http://114.67.76.158:10082/";

    /*********************************************************************api********************************************************************************/
    // 選品庫API
    public static final String FAVORITES_LIST = "api/Uatm/GetFavorites?";
    // 選品庫宝贝详情API
    public static final String FAVORITES_ITEM_LIST = "api/Uatm/GetFavoritesItem?";
    // 熱賣API
    public static final String HOT_MERCHANDISE_LIST = "api/Material/materialHotItemList?";
    // 分类API
    public static final String MATERIAL_CLASSIFY = "api/Material/materialClassify?";
    // 分类列表API
    public static final String MATERIAL_CLASSIFY_ITEM = "api/Material/materialClassifyItem?";
    // 获取猜你喜欢API
    public static final String GUESS_LIKE_LIST = "api/Material/materialGuessYouLike?";
    // 商品详情API
    public static final String ITEM_INFO = "api/Item/GetItemInfo?";
    // 商家详情API
    public static final String SHOP_INFO = "api/Shop/GetShopInfo?";
    // 相关商家API
    public static final String SELLER_LIST = "api/Shop/GetShopRecommend?";
    // 商品推荐API
    public static final String ITEM_RECOMMEND_LIST = "api/Material/materialRecommend?";
    // 查询API
    public static final String SEARCH_MATERIAL = "api/Material/searchMaterial?";
    // 熱門關鍵詞
    public static final String SEARCH_HOT_WORD = "api/Item/getHotQueryWord?";
    // 活动列表
    public static final String ACTIVITY_LIST = "api/Activity/getActivityList?";
    // 用户登录
    public static final String USER_LOGIN = "api/Login/userLogin?";
    //获取用户信息
    public static final String GET_USER_INFO = "api/User/getUserInfo?token=";
    //獲取用戶是否有支付密碼
    public static final String GET_USER_HAS_PAY_PW = "/api/User/hasPayPwd?token=";
    //獲取用戶当月收益
    public static final String GET_PERFORMANCE_THIS_MONTH = "/api/Performance/GetThisMonthPerformance?token=";
    //獲取用戶上月收益
    public static final String GET_PERFORMANCE_LAST_MONTH = "/api/Performance/GetLastMonthPerformance?token=";
    //检查是否是合作者(通过H5方式时)
    public static final String CHECK_IS_PARTNER = "/api/User/becomePartnerByH5?";
    // 获取验证码
    public static final String GET_VERIFICATION_CODE = "/api/Register/getVerifyCode?";
    // 用户注册
    public static final String USER_REGISTER = "/api/Register/userRegister?";
}
