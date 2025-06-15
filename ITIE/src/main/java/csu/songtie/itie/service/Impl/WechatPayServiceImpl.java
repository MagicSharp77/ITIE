//package csu.songtie.itie.service.Impl;
//
//import com.wechat.pay.java.core.Config;
//import com.wechat.pay.java.core.RSAAutoCertificateConfig;
//import com.wechat.pay.java.service.payments.jsapi.JsapiService;
//import com.wechat.pay.java.service.payments.jsapi.model.*;
//import com.wechat.pay.java.service.payments.model.Transaction;
//import csu.songtie.itie.config.WechatPayConfig;
//import csu.songtie.itie.service.WechatPayService;
//import csu.songtie.itie.common.CommonResponse;
//import csu.songtie.itie.common.ResponseCode;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import jakarta.annotation.PostConstruct;
//
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * 微信支付服务实现类
// */
//@Slf4j
//@Service
//public class WechatPayServiceImpl implements WechatPayService {
//
//    @Autowired
//    private WechatPayConfig wechatPayConfig;
//
//    private JsapiService jsapiService;
//
//    /**
//     * 初始化微信支付客户端
//     */
//    @PostConstruct
//    public void init() {
//        Config config = new RSAAutoCertificateConfig.Builder()
//            .merchantId(wechatPayConfig.getMerchantId())
//            .merchantSerialNumber(wechatPayConfig.getMerchantSerialNumber())
//            .privateKey(wechatPayConfig.getMerchantPrivateKey())
//            .apiV3Key(wechatPayConfig.getApiV3Key())
//            .build();
//        jsapiService = new JsapiService.Builder().config(config).build();
//    }
//
//    @Override
//    public CommonResponse<String> createPayment(String orderId, String amount, String description) {
//        // 创建支付请求
//        PrepayRequest request = new PrepayRequest();
//        Amount amountObj = new Amount();
//        amountObj.setTotal(Integer.parseInt(amount));
//        amountObj.setCurrency("CNY");
//
//        request.setAmount(amountObj);
//        request.setDescription(description);
//        request.setOutTradeNo(orderId);
//        request.setNotifyUrl(wechatPayConfig.getNotifyUrl());
//
//        // 调用微信支付接口
//        PrepayResponse response = jsapiService.prepay(request);
//        if (response != null) {
//            return CommonResponse.createForSuccess(
//                ResponseCode.SUCCESS.getCode(),
//                ResponseCode.SUCCESS.getDescription(),
//                response.getPrepayId()
//            );
//        }
//
//        log.error("创建微信支付订单失败");
//        return CommonResponse.createForError(
//            ResponseCode.PAYMENT_CREATE_FAILED.getCode(),
//            ResponseCode.PAYMENT_CREATE_FAILED.getDescription()
//        );
//    }
//
//    @Override
//    public CommonResponse<Boolean> handleNotify(String notifyData) {
//        // 验证签名
//        // TODO: 实现签名验证逻辑
//
//        // 解析通知数据
//        // TODO: 实现通知数据解析逻辑
//
//        // 处理支付结果
//        // TODO: 实现支付结果处理逻辑
//
//        return CommonResponse.createForSuccess(
//            ResponseCode.SUCCESS.getCode(),
//            ResponseCode.SUCCESS.getDescription(),
//            true
//        );
//    }
//
//    @Override
//    public CommonResponse<Map<String, String>> queryOrderStatus(String orderId) {
//        QueryOrderByOutTradeNoRequest request = new QueryOrderByOutTradeNoRequest();
//        request.setOutTradeNo(orderId);
//
//        Transaction transaction = jsapiService.queryOrderByOutTradeNo(request);
//        if (transaction != null) {
//            Map<String, String> result = new HashMap<>();
//            result.put("tradeState", transaction.getTradeState().name());
//            result.put("tradeStateDesc", transaction.getTradeStateDesc());
//            return CommonResponse.createForSuccess(
//                ResponseCode.SUCCESS.getCode(),
//                ResponseCode.SUCCESS.getDescription(),
//                result
//            );
//        }
//
//        log.error("查询订单状态失败");
//        return CommonResponse.createForError(
//            ResponseCode.PAYMENT_QUERY_FAILED.getCode(),
//            ResponseCode.PAYMENT_QUERY_FAILED.getDescription()
//        );
//    }
//
//    @Override
//    public CommonResponse<Boolean> closeOrder(String orderId) {
//        CloseOrderRequest request = new CloseOrderRequest();
//        request.setOutTradeNo(orderId);
//
//        jsapiService.closeOrder(request);
//        return CommonResponse.createForSuccess(
//            ResponseCode.SUCCESS.getCode(),
//            ResponseCode.SUCCESS.getDescription(),
//            true
//        );
//    }
//}