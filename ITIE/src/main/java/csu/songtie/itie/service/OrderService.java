package csu.songtie.itie.service;

import csu.songtie.itie.common.CommonResponse;
import csu.songtie.itie.domain.entity.order.LineItem;
import csu.songtie.itie.domain.vo.OrderVO;

import java.util.List;

public interface OrderService {
    public CommonResponse<List<OrderVO>> getOrdersByUsername(String username);
    public void insertOrderVO(OrderVO orderVO, List<LineItem> lineItemList);
    public CommonResponse<OrderVO> getOrderByOrderId(String orderId);

}