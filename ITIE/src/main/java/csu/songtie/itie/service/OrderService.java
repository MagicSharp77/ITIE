package csu.songtie.itie.service;

import csu.songtie.itie.common.CommonResponse;
import csu.songtie.itie.domain.entity.order.OrderItem;
import csu.songtie.itie.domain.vo.OrderVO;

import java.util.List;

public interface OrderService {
    // 通过 userId 获取该用户的所有订单
    public CommonResponse<List<OrderVO>> getOrdersByUserId(int userId);

    // 插入订单(创建订单时)
    public void insertOrderVO(OrderVO orderVO, String payMethod);
    
    // 更新订单状态 - 支付完成/订单退款
    public void updateOrder(String orderId,int status);

    // 通过 orderId 获取单个订单信息
    public CommonResponse<OrderVO> getOrderByOrderId(String orderId);

}