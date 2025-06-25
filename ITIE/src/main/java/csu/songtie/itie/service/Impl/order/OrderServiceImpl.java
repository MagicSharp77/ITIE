package csu.songtie.itie.service.Impl.order;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import csu.songtie.itie.common.CommonResponse;
import csu.songtie.itie.common.ResponseCode;
import csu.songtie.itie.domain.dto.OrderDTO;
import csu.songtie.itie.domain.entity.order.OrderItem;
import csu.songtie.itie.domain.entity.order.Order;
import csu.songtie.itie.domain.vo.OrderVO;
import csu.songtie.itie.mapper.order.OrderItemMapper;
import csu.songtie.itie.mapper.order.OrderMapper;
import csu.songtie.itie.service.CartService;
import csu.songtie.itie.service.order.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service("orderService")
public class OrderServiceImpl implements OrderService {
    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
    
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderItemMapper orderItemMapper;
    @Autowired
    private CartService cartService;
    
    private static final int UNPAID = 2;
    private static final int PAID = 3;
    private static final int REFUNDED = 4;

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
    public void insertOrderVO(OrderVO orderVO) {
        Order order = OrderDTO.vOToEntity(orderVO);
        order.setStatus(UNPAID);
        order.setCreateTime(new Date(System.currentTimeMillis()));
        order.setUpdateTime(new Date(System.currentTimeMillis()));
        orderMapper.insert(order);

        List<OrderItem> orderItemList = OrderDTO.vOToItemList(orderVO);
        for (OrderItem orderItem : orderItemList){
            orderItem.setOrderId(order.getOrderId());
            orderItemMapper.insert(orderItem);
        }
    }

    @Override
    public void updateOrder(String orderId, int status) {
        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
        orderQueryWrapper.eq("order_id",orderId);
        Order order = orderMapper.selectOne(orderQueryWrapper);
        order.setStatus(status);
        order.setUpdateTime(new Date(System.currentTimeMillis()));
        UpdateWrapper<Order> orderUpdateWrapper = new UpdateWrapper<>();
        orderUpdateWrapper.eq("order_id",orderId);
        orderMapper.update(order,orderUpdateWrapper);
    }

//    @Override
//    @Transactional
//    public CommonResponse<String> handlePaymentSuccess(String orderId, double paymentAmount) {
//        try {
//            logger.info("开始处理支付成功回调，订单号：{}，支付金额：{}", orderId, paymentAmount);
//
//            // 1. 获取订单信息
//            QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
//            orderQueryWrapper.eq("order_id", orderId);
//            Order order = orderMapper.selectOne(orderQueryWrapper);
//
//            if (order == null) {
//                logger.error("订单不存在，订单号：{}", orderId);
//                return CommonResponse.createForError(ResponseCode.ORDER_EXIST_ERROR.getCode(),
//                        ResponseCode.ORDER_EXIST_ERROR.getDescription());
//            }
//
//            // 2. 验证订单状态
//            if (order.getStatus() == PAID) {
//                logger.warn("订单已支付，订单号：{}", orderId);
//                return CommonResponse.createForError(ResponseCode.PAYMENT_ALREADY_PAID.getCode(),
//                        ResponseCode.PAYMENT_ALREADY_PAID.getDescription());
//            }
//
//            // 3. 验证支付金额
//            if (Math.abs(order.getPaymentPrice().doubleValue() - paymentAmount) > 0.01) {
//                logger.error("支付金额不匹配，订单金额：{}，支付金额：{}", order.getPaymentPrice(), paymentAmount);
//                return CommonResponse.createForError(ResponseCode.PAYMENT_AMOUNT_INVALID.getCode(),
//                        ResponseCode.PAYMENT_AMOUNT_INVALID.getDescription());
//            }
//
//            // 4. 获取订单项
//            QueryWrapper<OrderItem> orderItemQueryWrapper = new QueryWrapper<>();
//            orderItemQueryWrapper.eq("order_id", orderId);
//            List<OrderItem> orderItems = orderItemMapper.selectList(orderItemQueryWrapper);
//
//            if (orderItems.isEmpty()) {
//                logger.error("订单项为空，订单号：{}", orderId);
//                return CommonResponse.createForError(ResponseCode.ORDER_LINEITEM_EMPTY_ERROR.getCode(),
//                        ResponseCode.ORDER_LINEITEM_EMPTY_ERROR.getDescription());
//            }
//
//            // 5. 从购物车中移除已购买商品
//            for (OrderItem item : orderItems) {
//                cartService.removeCourseFromCart(item.getCourseId());
//            }
//
//            // 6. 更新订单状态
//            order.setStatus(PAID);
//            order.setPaymentTime(new Date(System.currentTimeMillis()));
//            orderMapper.updateById(order);
//
//            logger.info("支付成功处理完成，订单号：{}", orderId);
//            return CommonResponse.createForSuccess(ResponseCode.PAYMENT_SUCCESS.getCode(),
//                    ResponseCode.PAYMENT_SUCCESS.getDescription());
//
//        } catch (Exception e) {
//            logger.error("处理支付成功回调异常，订单号：{}", orderId, e);
//            return CommonResponse.createForError(ResponseCode.PAYMENT_NOTIFY_FAILED.getCode(),
//                    ResponseCode.PAYMENT_NOTIFY_FAILED.getDescription());
//        }
//    }

    @Override
    public CommonResponse<OrderVO> getOrderByOrderId(String orderId) {
        // 获取订单信息
        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
        orderQueryWrapper.eq("order_id", orderId);
        Order order = orderMapper.selectOne(orderQueryWrapper);
        
        if (order == null) {
            return CommonResponse.createForError(ResponseCode.ORDER_EXIST_ERROR.getCode(),
                    ResponseCode.ORDER_EXIST_ERROR.getDescription());
        }
        
        // 获取订单项
        QueryWrapper<OrderItem> orderItemQueryWrapper = new QueryWrapper<>();
        orderItemQueryWrapper.eq("order_id", orderId);
        List<OrderItem> orderItems = orderItemMapper.selectList(orderItemQueryWrapper);
        
        OrderVO orderVO = OrderDTO.entityToVO(order, orderItems);
        return CommonResponse.createForSuccess(ResponseCode.ORDER_GET_SUCCESS.getCode(),
                ResponseCode.ORDER_GET_SUCCESS.getDescription(),
                orderVO);
    }
}
