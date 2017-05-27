package com.example.administrator.a3dmark.util;

/**
 * Created by Administrator on 2017/3/1.
 */

public class Contants {
    /**
     * 统一接口
     */
    //内网/开发用

//   public static final String SERVER = "http://192.168.1.104:8080/3DMark";
//    public static final String SERVER = "http://192.168.1.119:8080/3DMark";
    //外网
    public static final String SERVER = "http://116.255.191.199:80/3DMark";
    //版本号验证
    public static final String VERSION = SERVER + "/version";
    //注册/验证/
    public static final String MESSAGE_CODE = SERVER + "/register/sendMsg";
    //注册/提交
    public static final String MESSAGE_REGIS = SERVER + "/register/phoneRegister";
    //登录
    public static final String MESSAGE_LOGIN = SERVER + "/login";
    //首页轮播图
    public static final String MAIN_HEAD = SERVER + "/index/carousel";//首页轮播图图片
    //好货  好店
    public static final String IS_GOODS_BUSSINESS = SERVER + "/index/isGoodsAndBussiness";
    //首页商品
    public static final String HEAD_GOODS = SERVER + "/index/headGoods";
    //推荐商品
    public static final String SHOW_GOODS_INSTORE = SERVER + "/showgoodsinstore/show";
    //推荐商品第一行
    public static final String FIGUREINSTOREONE = SERVER + "/showgoodsinstore/figureInSotreOne";
    //商城其他商品
    public static final String FIGUREINSTOREONE_SHOW = SERVER + "/showgoodsinstore/showType";
    //分类商品
    public static final String AGRICULTURAL = SERVER + "/goods/agricultural";
    //限时抢购时间
    public static final String LIMIT_TIME = SERVER + "/limitTime/activityTime";
    //限时抢购商品/轮播图
    public static final String LIMIT_TIME_GOODS = SERVER + "/limitTime";
    //购物车列表
    public static final String CARD_GOODS = SERVER + "/scart/newScart";
    //加入商品到购物车
    public static final String ADD_CARD_GOODS = SERVER + "/scart/addScart";
    //编辑购物车商品
    public static final String EDIT_CARD_GOODS = SERVER + "/scart/editScart";
    //删除购物车商品
    public static final String DELETE_CARD_GOODS = SERVER + "/scart/deleteScart";
    //今日推荐
    public static final String RECOMMEND = SERVER + "/recommend";
    //精品宝贝
    public static final String GOODS = SERVER + "/goods";
    public static final String RECOMM = SERVER + "/hot/carousel";
    //人气商品
    public static final String HOT = SERVER + "/hot";
    //收藏店铺
    public static final String COLLECTION_SHTORE = SERVER + "/collectionstore/collerctionStore";
    //收藏店铺轮播图
    public static final String COLLECTION_SHTOREPAGER = SERVER + "/collectionstore/showPage";
    //收藏店铺商品展示
    public static final String SHOWGOODS_INSTORE = SERVER + "/collectionstore/showGoodsInStore";
    //待发货
    public static final String ORDER_STATE = SERVER + "/order/orderState";
    //待付款
    public static final String NOT_PAYING = SERVER + "/order/notPaying";
    //待付款/马上付款
    public static final String ORDERPAY = SERVER + "/order/orderPay";
    //待收货
    public static final String ORDER_STATE_GOODS = SERVER + "/order/orderStateGoods";
    //待评价
    public static final String NOEVALUATION = SERVER + "/order/noEvaluation";
    //退款列表
    public static final String REFUND = SERVER + "/order/refund";
    //收藏商铺
    public static final String COLLECTION_SHOP = SERVER + "/collectionstore/collectionStoreList";
    //收藏商品
    public static final String COLLECTION_GOODS = SERVER + "/collectionstore/collectionGoodsList";
    //收货地址
    public static final String PERSON_ADDR = SERVER + "/myinfo/manageAddress";
    //编辑地址
    public static final String ADD_NEA_ADDR = SERVER + "/myinfo/editAddress";
    //新增地址
    public static final String ADD_ADDR = SERVER + "/myinfo/addAddress";
    //删除地址
    public static final String DELETE_ADDR = SERVER + "/myinfo/deleteAddress";
    //默认地址
    public static final String DEFAULT_ADDR = SERVER + "/myinfo/defaultAddress";
    //修改密码
    public static final String UPDATE_PASSWORD = SERVER + "/myinfo/updataPassword";
    //收藏商品
    public static final String COLLECTION_GOODS_BOUTIQUE = SERVER + "/collectionstore/collectionGoods";
    //选择商品
    public static final String CHOICE_GOODS = SERVER + "/scart/addScartGoodsType";
    //商品评价标签
    public static final String EVALUATION_GOODS_LIST = SERVER + "/evaluation/findEvaluationList";
    //商品评价
    public static final String EVALUATION_LIST = SERVER + "/evaluation";
    //删除收藏店铺
    public static final String DELETE_COLLECTION_SHOP = SERVER + "/collectionstore/deleteStoreList";
    //删除收藏商品
    public static final String DELETE_COLLECTION_GOODS = SERVER + "/collectionstore/deleteGoodsList";
    //立即购买
    public static final String PAYMENT_NOW = SERVER + "/order/buyNow";
    //退款申请
    public static final String REFUNDAPPLICATION = SERVER + "/order/refundApplication";
    //购买成功
    public static final String PAYSUCCESSS = SERVER + "/order/paySuccess";
    //立即购买的默认地址
    public static final String DEFAULE_ADDR = SERVER + "/order/buyNowInterface";
    //从购物车提交订单
    public static final String SCART_STANDBY = SERVER + "/order/scartStandby";
    //从购物车生成订单的默认地址
    public static final String DEFAULE_ADDRESS = SERVER + "/order/findDefaultAddress";
    //商品详情服务
    public static final String GOODS_SERVICE = SERVER + "/goods/goodsService";
    //店铺详情
    public static final String DTORE = SERVER + "/store";
    //加
    public static final String ADD = SERVER + "/order/judgeNum";
    //取消订单
    public static final String REMOVE_ORDER = SERVER + "/order/killOrder";
    //提交评价
    public static final String COMMENT_SUBMIT = SERVER + "/order/orderEvalute";
    //订单详情
    public static final String ORDER_DETAIL = SERVER + "/order/orderInfo";
    //物流信息
    public static final String LOGISTIC_MESSAGE = SERVER + "/order/logisticDetail";
    //修改昵称
    public static final String MODIFY_NICKNAME = SERVER + "/myinfo/enditNickname";
    //删除人物
    public static final String DELETE_PERSON = SERVER + "/myinfo/deletePersonal";
    // 用户
    public static String userid;
    public static String userName;
    public static String passWord;
    //个性签名
    public static final String Signature = SERVER + "/myinfo/upSignature";
    //人物管理
    public static final String mangeManPhoto = SERVER + "/myinfo/mangeManPhoto";
    //头像上传
    public static final String PORTRAIT = SERVER + "/myinfo/photo";
    //试衣记录
    public static final String FITTING_RECORD = SERVER + "/myinfo/fitRecordList";
    //刪除试衣记录
    public static final String DELETE_FITTING = SERVER + "/myinfo/deleteFitMain";
    //添加人物
    public static final String ADD_PERSON = SERVER + "/myinfo/upPersion";
    //商城推荐
    public static final String MARK_RECOMMEND = SERVER + "/showgoodsinstore/show";
    //商城其他商品
    public static final String MARK_GOODS = SERVER + "/showgoodsinstore/types";
    //搜索
    public static final String SEARCH = SERVER + "/goods/lookGoods";
    //个人信息请求
    public static final String MYSELF = SERVER + "/login/returnPhotosAndNickName";


}
