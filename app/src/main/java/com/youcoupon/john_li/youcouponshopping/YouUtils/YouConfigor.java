package com.youcoupon.john_li.youcouponshopping.YouUtils;

/**
 * Created by John_Li on 2/4/2019.
 */

public class YouConfigor {
    public static final int LOGIN_FOR_RQUEST = 10001;

    /*********************************************************************地址********************************************************************************/
    // 測試地址
    //public static final String BASE_URL = "http://youcouponapi.azurewebsites.net/";
    //public static final String BASE_URL = "http://114.67.76.158:10082/";
    public static final String BASE_URL = "http://118.190.1.209:10082/";

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
    //獲取一级当月收益
    public static final String GET_FIRST_PERFORMANCE_THIS_MONTH = "/api/Performance/GetFirstThisMonthPerformance?token=";
    //獲取二级当月收益
    public static final String GET_SECOND_PERFORMANCE_THIS_MONTH = "/api/Performance/GetSecondThisMonthPerformance?token=";
    //獲取用戶上月收益
    public static final String GET_PERFORMANCE_LAST_MONTH = "/api/Performance/GetLastMonthPerformance?token=";
    //獲取一级上月收益
    public static final String GET_FIRST_PERFORMANCE_LAST_MONTH = "/api/Performance/GetFirstLastMonthPerformance?token=";
    //獲取二级上月收益
    public static final String GET_SECOND_PERFORMANCE_LAST_MONTH = "/api/Performance/GetSecondLastMonthPerformance?token=";
    //獲取用戶当日收益
    public static final String GET_PERFORMANCE_TODAY = "/api/Performance/GetTodayPerformance?token=";
    //一级合伙人本今日预估绩效查看
    public static final String GET_FIRST_PERFORMANCE_TODAY = "/api/Performance/GetTodayPerformance?token=";
    //二级合伙人本今日预估绩效查看
    public static final String GET_SECOND_PERFORMANCE_TODAY = "/api/Performance/GetSecondTodayPerformance?token=";
    //检查是否是合作者(通过H5方式时)
    public static final String CHECK_IS_PARTNER = "/api/User/becomePartnerByH5?";
    // 获取验证码
    public static final String GET_VERIFICATION_CODE = "/api/Register/getVerifyCode?";
    // 用户注册
    public static final String USER_REGISTER = "/api/Register/userRegister?";
    // 修改密码
    public static final String CHG_PWD = "/api/Register/chgPwd?";
    // 修改头像
    public static final String UPDATE_HEAD_IMG = "/api/User/chgHeadImg?";
    // 修改用户信息
    public static final String UPDATE_USER_INFO = "/api/User/chgUserInfo?";
    // 提交邀请码
    public static final String SUBMIT_INVITATION_CODE = "/api/User/submitInvitationCode?";
    // 订单列表
    public static final String GET_ORDER_LIST = "/api/Order/getOwnOrderList?";
    // 获取一级下线订单
    public static final String GET_FIRST_LEVEL_ORDER_LIST = "/api/Order/getFirstLevelOrderList?";
    // 获取二级下线订单
    public static final String GET_SECONDE_LEVEL_ORDER_LIST = "/api/Order/getSecondLevelOrderList?";
    // 获取一级下线成员
    public static final String GET_FIRST_LEVEL_MEMBER_LIST = "/api/Team/getFirstLevelMember?";
    // 获取二级下线成员
    public static final String GET_SECOND_LEVEL_MEMBER_LIST = "/api/Team/getSecondLevelMember?";
    // 获取淘口令
    public static final String ITEM_GET_TPWD = "/api/Item/GetItemTPWD?";
    // 获取余额
    public static final String GET_BALANCE = "/api/Wallet/getBalance";
    // 余额提现
    public static final String POST_WITH_DRAWAL = "/api/Wallet/createWithDraw?token=";
    // 钱包记录
    public static final String GET_WALLET_RECORDE = "/api/Wallet/getWalletRecordList?";
    // 修改密码
    public static final String UPDATE_PAY_PWD = "/api/User/chgPayPwd?";
    // 新增密码
    public static final String ADD_PAY_PWD = "/api/User/addPayPwd?";
    // 获取版本号
    public static final String CHECK_VERSION = "/api/Tool/GetAPKVer?";
    // 版本更新下载地址
    public static final String GET_NEW_APK = "/api/Tool/GetAPK?";
    // 好券直播分类
    public static final String CLASSIFY_TITLE = "/api/Classify/classifyTitle?";
    //获取主页活动
    public static final String GET_MAIN_ACTIVITY = "/api/Activity/GetMainActivity?";
    //提交商业合作
    public static final String POST_BUSINESS = "api/Tool/submitBusinessCooperation?";
    //提交意见反馈
    public static final String POST_FEEDBACK = "api/Tool/submitFeedBack?";
    // 订单找回
    public static final String POST_ORDER_RETRIRVAL = "/api/Order/orderRetrieval?";
    // 绑定邀请人
    public static final String POST_VISITOR_RETRIEVAL = "/api/User/submitInvitationCode";
    // 查询上下级
    public static final String GET_SUPIOR = "/api/User/getSuperior";
    // 上传头像
    public static String UPLOAD_FILE = "api/Tool/uploadTempFile?";
}
