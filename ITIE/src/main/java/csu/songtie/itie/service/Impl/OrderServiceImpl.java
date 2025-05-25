package csu.songtie.itie.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import csu.songtie.itie.common.CommonResponse;
import csu.songtie.itie.common.ResponseCode;
import csu.songtie.itie.domain.dto.OrderDTO;
import csu.songtie.itie.domain.entity.order.LineItem;
import csu.songtie.itie.domain.entity.order.Order;
import csu.songtie.itie.domain.entity.order.OrderStatus;
import csu.songtie.itie.domain.vo.OrderVO;
import csu.songtie.itie.mapper.order.LineItemMapper;
import csu.songtie.itie.mapper.order.OrderMapper;
import csu.songtie.itie.mapper.order.OrderStatusMapper;
import csu.songtie.itie.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("orderService")
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private LineItemMapper lineItemMapper;
    @Autowired
    private OrderStatusMapper orderStatusMapper;

    // 通过 username 获取用户的历史订单
    @Override
    public CommonResponse<List<OrderVO>> getOrdersByUsername(String username) {
        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
        orderQueryWrapper.eq("user_id",username);       // 注意 这里数据库未定，需要依据实际的数据库字段名来更改
        List<Order> orderList = orderMapper.selectList(orderQueryWrapper);

        if(orderList.isEmpty()){
            return CommonResponse.createForError(ResponseCode.ORDER_LIST_EXIST_ERROR.getCode(),
                    ResponseCode.ORDER_LIST_EXIST_ERROR.getDescription());
        }

        // VO 转换
        List<OrderVO> orderVOList = new ArrayList<>();
        for(Order order : orderList){
            OrderStatus orderStatus = orderStatusMapper.selectById(order.getOrderId());

            QueryWrapper<LineItem> lineItemQueryWrapper = new QueryWrapper<>();
            lineItemQueryWrapper.eq("order_id",order.getOrderId());       // 注意 这里数据库未定，需要依据实际的数据库字段名来更改
            List<LineItem> lineItemList = lineItemMapper.selectList(lineItemQueryWrapper);

            orderVOList.add(OrderDTO.entityToVO(order,orderStatus,lineItemList));
        }
        return CommonResponse.createForSuccess(ResponseCode.ORDER_LIST_GET_SUCCESS.getCode(),
                ResponseCode.ORDER_LIST_GET_SUCCESS.getDescription(),
                orderVOList);
    }

    // 插入订单
    @Override
    public void insertOrderVO(OrderVO orderVO, List<LineItem> lineItemList) {
        Order order = OrderDTO.vOToEntity(orderVO);
        orderMapper.insert(order);

        OrderStatus orderStatus = OrderDTO.vOToOrderStatus(orderVO);
        orderStatusMapper.insert(orderStatus);
    }

    // 获取订单详细信息
    @Override
    public CommonResponse<OrderVO> getOrderByOrderId(String orderId) {
        Order order = orderMapper.selectById(orderId);

        if(order == null){
            return CommonResponse.createForError(ResponseCode.ORDER_EXIST_ERROR.getCode(),
                    ResponseCode.ORDER_EXIST_ERROR.getDescription());
        }

        // 获取 lineItem
        QueryWrapper<LineItem> lineItemQueryWrapper = new QueryWrapper<>();
        lineItemQueryWrapper.eq("order_id",orderId);
        List<LineItem> lineItemList = lineItemMapper.selectList(lineItemQueryWrapper);

        // 获取 orderStatus
        OrderStatus orderStatus = orderStatusMapper.selectById(orderId);

        OrderVO orderVO = OrderDTO.entityToVO(order,orderStatus,lineItemList);

        return CommonResponse.createForSuccess(ResponseCode.ORDER_GET_SUCCESS.getCode(),
                ResponseCode.ORDER_GET_SUCCESS.getDescription(),
                orderVO);
    }
}
