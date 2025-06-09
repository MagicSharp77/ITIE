package csu.songtie.itie.domain.dto;

import csu.songtie.itie.domain.entity.order.OrderItem;
import csu.songtie.itie.domain.entity.order.Order;
import csu.songtie.itie.domain.vo.OrderVO;

import java.util.List;

public class OrderDTO {
    // 将 order 与  orderItem 组装成 OrderVO
    public static OrderVO entityToVO(Order order, List<OrderItem> orderItemList){
        OrderVO orderVO = new OrderVO();

        // 插入 order 字段
        orderVO.setOrderId(order.getOrderId());
        orderVO.setUserId(order.getUserId());
        orderVO.setStatus(order.getStatus());
        orderVO.setPaymentType(order.getPaymentType());
        orderVO.setPaymentTime(order.getPaymentTime());
        orderVO.setPaymentPrice(order.getPaymentPrice());
        orderVO.setOriginalPrice(order.getOriginalPrice());
        orderVO.setCouponId(order.getCouponId());
        orderVO.setCouponOwnerId(order.getCouponOwnerId());
        orderVO.setCouponPrice(order.getCouponPrice());
        orderVO.setCreateTime(order.getCreateTime());
        orderVO.setUpdateTime(order.getUpdateTime());
        orderVO.setDeleteTime(order.getDeleteTime());
        orderVO.setUnused(order.getUnused());

        // 插入 LineItem 字段
        orderVO.setOrderItemList(orderItemList);

        return orderVO;
    }

    // 将 orderVO 拆成 order
    public static Order vOToEntity(OrderVO orderVO){
        Order order = new Order();

        order.setOrderId(orderVO.getOrderId());
        order.setUserId(orderVO.getUserId());
        order.setStatus(orderVO.getStatus());
        order.setPaymentType(orderVO.getPaymentType());
        order.setPaymentTime(orderVO.getPaymentTime());
        order.setPaymentPrice(orderVO.getPaymentPrice());
        order.setOriginalPrice(orderVO.getOriginalPrice());
        order.setCouponId(orderVO.getCouponId());
        order.setCouponOwnerId(orderVO.getCouponOwnerId());
        order.setCouponPrice(orderVO.getCouponPrice());
        order.setCreateTime(orderVO.getCreateTime());
        order.setUpdateTime(orderVO.getUpdateTime());
        order.setDeleteTime(orderVO.getDeleteTime());
        order.setUnused(orderVO.getUnused());

        return order;
    }

    // 将 orderVO 拆成 itemList
    public static List<OrderItem> vOToItemList(OrderVO orderVO){
        return orderVO.getOrderItemList();
    }
}
