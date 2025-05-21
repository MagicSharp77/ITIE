package csu.songtie.itie.service.Impl;

import csu.songtie.itie.common.CommonResponse;
import csu.songtie.itie.domain.entity.order.LineItem;
import csu.songtie.itie.domain.vo.OrderVO;
import csu.songtie.itie.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("orderService")
public class OrderServiceImpl implements OrderService {
    @Override
    public CommonResponse<List<OrderVO>> getOrdersByUsername(String username) {
        return null;
    }

    @Override
    public void insertOrderVO(OrderVO orderVO, List<LineItem> lineItemList) {

    }

    @Override
    public CommonResponse<OrderVO> getOrderByOrderId(String orderId) {
        return null;
    }
}
