package csu.songtie.itie.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import csu.songtie.itie.common.CommonResponse;
import csu.songtie.itie.common.ResponseCode;
import csu.songtie.itie.domain.dto.OrderDTO;
import csu.songtie.itie.domain.entity.order.OrderItem;
import csu.songtie.itie.domain.entity.order.Order;
import csu.songtie.itie.domain.vo.OrderVO;
import csu.songtie.itie.mapper.order.OrderItemMapper;
import csu.songtie.itie.mapper.order.OrderMapper;
import csu.songtie.itie.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

// TODO: 尚未完成重构，需要配合数据库进行大量更改
@Service("orderService")
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderItemMapper orderItemMapper;
    private int UNPAID = 2;

    // 通过 userId 获取用户的历史订单
    @Override
    public CommonResponse<List<OrderVO>> getOrdersByUserId(int userId) {
        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
        orderQueryWrapper.eq("user_id",userId);
        List<Order> orderList = orderMapper.selectList(orderQueryWrapper);

        if(orderList.isEmpty()){
            return CommonResponse.createForError(ResponseCode.ORDER_LIST_EXIST_ERROR.getCode(),
                    ResponseCode.ORDER_LIST_EXIST_ERROR.getDescription());
        }

        // VO 转换
        List<OrderVO> orderVOList = new ArrayList<>();
        for(Order order : orderList){
            QueryWrapper<OrderItem> orderItemQueryWrapper = new QueryWrapper<>();
            orderItemQueryWrapper.eq("order_id",order.getOrderId());
            List<OrderItem> orderItemList = orderItemMapper.selectList(orderItemQueryWrapper);

            orderVOList.add(OrderDTO.entityToVO(order, orderItemList));
        }
        return CommonResponse.createForSuccess(ResponseCode.ORDER_LIST_GET_SUCCESS.getCode(),
                ResponseCode.ORDER_LIST_GET_SUCCESS.getDescription(),
                orderVOList);
    }


    // 创建订单-持久化
    @Override
    public void insertOrderVO(OrderVO orderVO, String payMethod) {
        Order order = OrderDTO.vOToEntity(orderVO);
        order.setStatus(UNPAID);
        orderMapper.insert(order);

        List<OrderItem> orderItemList = OrderDTO.vOToItemList(orderVO);
        for (OrderItem orderItem : orderItemList){
            orderItem.setOrderId(order.getOrderId());
            orderItemMapper.insert(orderItem);
        }
    }

    // 更新订单状态
    @Override
    public void updateOrder(String orderId, int status) {
        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
        orderQueryWrapper.eq("order_id",orderId);
        Order order = orderMapper.selectOne(orderQueryWrapper);
        order.setStatus(status);
        orderMapper.updateById(order);
    }

    // 获取订单详细信息
    @Override
    public CommonResponse<OrderVO> getOrderByOrderId(String orderId) {
        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
        orderQueryWrapper.eq("order_id",orderId);
        Order order = orderMapper.selectOne(orderQueryWrapper);

        if(order == null){
            return CommonResponse.createForError(ResponseCode.ORDER_EXIST_ERROR.getCode(),
                    ResponseCode.ORDER_EXIST_ERROR.getDescription());
        }

        // 获取 orderItem
        QueryWrapper<OrderItem> orderItemQueryWrapper = new QueryWrapper<>();
        orderItemQueryWrapper.eq("order_id",orderId);
        List<OrderItem> orderItemList = orderItemMapper.selectList(orderItemQueryWrapper);
        
        OrderVO orderVO = OrderDTO.entityToVO(order, orderItemList);

        return CommonResponse.createForSuccess(ResponseCode.ORDER_GET_SUCCESS.getCode(),
                ResponseCode.ORDER_GET_SUCCESS.getDescription(),
                orderVO);
    }
}
