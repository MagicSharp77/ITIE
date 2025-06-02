package csu.songtie.itie.service;

import csu.songtie.itie.common.CommonResponse;
import com.alipay.api.AlipayApiException;

/**
 * 支付宝支付服务接口
 */
public interface AlipayService {
    /**
     * 创建支付订单
     * @param orderId 订单ID
     * @param amount 支付金额
     * @param subject 商品标题
     * @return 支付表单HTML
     */
    CommonResponse<String> createPayment(String orderId, String amount, String subject) throws AlipayApiException;

    /**
     * 处理支付回调通知
     * @param params 回调参数
     * @return 处理结果
     */
    CommonResponse<Boolean> handleNotify(String params) throws AlipayApiException;

    /**
     * 查询订单状态
     * @param orderId 订单ID
     * @return 订单状态
     */
    CommonResponse<String> queryOrderStatus(String orderId) throws AlipayApiException;
} 