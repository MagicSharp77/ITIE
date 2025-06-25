package csu.songtie.itie.service.pay;

import csu.songtie.itie.common.CommonResponse;
import com.alipay.api.AlipayApiException;
import csu.songtie.itie.domain.entity.order.Order;

/**
 * 支付宝支付服务接口
 */
public interface AlipayService {
    /**
     * 创建支付订单
     * @param order 订单信息
     * @return 支付表单HTML
     */
    CommonResponse<String> createPayment(Order order) throws AlipayApiException;

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