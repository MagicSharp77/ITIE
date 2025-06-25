package csu.songtie.itie.service.Impl.pay;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import csu.songtie.itie.config.AlipayConfig;
import csu.songtie.itie.service.pay.AlipayService;
import csu.songtie.itie.common.CommonResponse;
import csu.songtie.itie.common.ResponseCode;
import csu.songtie.itie.domain.entity.order.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;

import java.util.Map;
import java.util.HashMap;
/**
 * 支付宝支付服务实现类
 */
@Slf4j
@Service
public class AlipayServiceImpl implements AlipayService {

    @Autowired
    private AlipayConfig alipayConfig;
    private AlipayClient alipayClient;

    /**
     * 初始化支付宝客户端
     */
    @PostConstruct
    public void init() {
        alipayClient = new DefaultAlipayClient(
            alipayConfig.getGateway(),
            alipayConfig.getAppId(),
            alipayConfig.getPrivateKey(),
            "json",
            "UTF-8",
            alipayConfig.getPublicKey(),
            "RSA2"
        );
    }

    @Override
    public CommonResponse<String> createPayment(Order order) throws AlipayApiException {
        // 创建支付请求
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        request.setNotifyUrl(alipayConfig.getNotifyUrl());
        request.setReturnUrl(alipayConfig.getReturnUrl());

        // 构建请求参数
        String bizContent = String.format(
            "{\"out_trade_no\":\"%s\"," +
            "\"total_amount\":\"%.2f\"," +
            "\"subject\":\"%s\"," +
            "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}",
            order.getOrderId(),
            order.getPaymentPrice(),
            order.getUserId() + "的订单"  // 使用用户名+订单作为subject
        );
        request.setBizContent(bizContent);

        // 调用支付宝接口
        AlipayTradePagePayResponse response = alipayClient.pageExecute(request);
        if (response.isSuccess()) {
            return CommonResponse.createForSuccess(
                ResponseCode.SUCCESS.getCode(),
                ResponseCode.SUCCESS.getDescription(),
                response.getBody()
            );
        }
        
        log.error("创建支付宝支付订单失败：{}", response.getSubMsg());
        return CommonResponse.createForError(
            ResponseCode.PAYMENT_CREATE_FAILED.getCode(),
            ResponseCode.PAYMENT_CREATE_FAILED.getDescription()
        );
    }

    @Override
    public CommonResponse<Boolean> handleNotify(String params) throws AlipayApiException {
        // 解析参数
        Map<String, String> paramsMap = new HashMap<>();
        String[] paramPairs = params.split("&");
        for (String pair : paramPairs) {
            String[] keyValue = pair.split("=");
            if (keyValue.length == 2) {
                paramsMap.put(keyValue[0], keyValue[1]);
            }
        }

        // 验证签名
        boolean signVerified = AlipaySignature.rsaCheckV1(
            paramsMap,
            alipayConfig.getPublicKey(),
            "UTF-8",
            "RSA2"
        );

        if (!signVerified) {
            return CommonResponse.createForError(
                ResponseCode.PAYMENT_SIGNATURE_INVALID.getCode(),
                ResponseCode.PAYMENT_SIGNATURE_INVALID.getDescription()
            );
        }

        // 获取交易状态
        String tradeStatus = paramsMap.get("trade_status");
        if ("TRADE_SUCCESS".equals(tradeStatus)) {
            String orderId = paramsMap.get("out_trade_no");
            String tradeNo = paramsMap.get("trade_no");
            
            // TODO: 更新订单状态
            // 1. 查询订单
            // 2. 更新订单状态为已支付
            // 3. 设置支付时间和交易流水号
            // 4. 保存更新后的订单
            
            return CommonResponse.createForSuccess(
                ResponseCode.SUCCESS.getCode(),
                ResponseCode.SUCCESS.getDescription(),
                true
            );
        }
        
        return CommonResponse.createForError(
            ResponseCode.PAYMENT_NOTIFY_FAILED.getCode(),
            ResponseCode.PAYMENT_NOTIFY_FAILED.getDescription()
        );
    }

    @Override
    public CommonResponse<String> queryOrderStatus(String orderId) throws AlipayApiException {
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        String bizContent = String.format(
            "{\"out_trade_no\":\"%s\"}",
            orderId
        );
        request.setBizContent(bizContent);

        AlipayTradeQueryResponse response = alipayClient.execute(request);
        if (response.isSuccess()) {
            return CommonResponse.createForSuccess(
                ResponseCode.SUCCESS.getCode(),
                ResponseCode.SUCCESS.getDescription(),
                response.getTradeStatus()
            );
        }
        
        log.error("查询订单状态失败：{}", response.getSubMsg());
        return CommonResponse.createForError(
            ResponseCode.PAYMENT_QUERY_FAILED.getCode(),
            ResponseCode.PAYMENT_QUERY_FAILED.getDescription()
        );
    }
} 