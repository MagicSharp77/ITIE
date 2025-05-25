package csu.songtie.itie.domain.dto;

import csu.songtie.itie.domain.entity.order.LineItem;
import csu.songtie.itie.domain.entity.order.Order;
import csu.songtie.itie.domain.entity.order.OrderStatus;
import csu.songtie.itie.domain.vo.OrderVO;

import java.util.List;

public class OrderDTO {
    public static OrderVO entityToVO(Order order, OrderStatus orderStatus, List<LineItem> lineItemList){
        OrderVO orderVO = new OrderVO();

        // 插入 order 字段
        orderVO.setOrderId(order.getOrderId());
        orderVO.setUserName(order.getUserName());
        orderVO.setLineNum(order.getLineNum());
        orderVO.setPayMethod(order.getPayMethod());
        orderVO.setTotalPrice(order.getTotalPrice());

        // 插入 orderStatus 字段
        orderVO.setStatus(orderStatus.getStatus());

        // 插入 LineItem 字段
        orderVO.setLineItemList(lineItemList);

        return orderVO;
    }

    public static Order vOToEntity(OrderVO orderVO){
        Order order = new Order();

        order.setOrderId(orderVO.getOrderId());
        order.setUserName(orderVO.getUserName());
        order.setLineNum(orderVO.getLineNum());
        order.setPayMethod(orderVO.getPayMethod());
        order.setTotalPrice(orderVO.getTotalPrice());

        return order;
    }

    public static OrderStatus vOToOrderStatus(OrderVO orderVO){
        OrderStatus orderStatus = new OrderStatus();

        orderStatus.setOrderId(orderVO.getOrderId());
        orderStatus.setStatus(orderVO.getStatus());

        return orderStatus;
    }
}
