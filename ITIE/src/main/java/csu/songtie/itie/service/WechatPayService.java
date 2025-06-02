package csu.songtie.itie.service;

import csu.songtie.itie.common.CommonResponse;
import java.util.Map;

/**
 * 微信支付服务接口
 */
public interface WechatPayService {
    /**
     * 创建支付订单
     * @param orderId 订单ID
     * @param amount 支付金额（单位：分）
     * @param description 商品描述
     * @return 支付二维码链接
     */
    CommonResponse<String> createPayment(String orderId, String amount, String description);

    /**
     * 处理支付回调通知
     * @param notifyData 回调通知数据
     * @return 处理结果
     */
    CommonResponse<Boolean> handleNotify(String notifyData);

    /**
     * 查询订单状态
     * @param orderId 订单ID
     * @return 订单状态信息
     */
    CommonResponse<Map<String, String>> queryOrderStatus(String orderId);

    /**
     * 关闭订单
     * @param orderId 订单ID
     * @return 关闭结果
     */
    CommonResponse<Boolean> closeOrder(String orderId);
} 