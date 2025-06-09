package csu.songtie.itie.domain.vo;

import csu.songtie.itie.domain.entity.order.OrderItem;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

@Data
public class OrderVO {
    // order 字段
    private int id;
    private String orderId;
    private int userId;
    private int status;
    private String paymentType;
    private Date paymentTime;
    private BigDecimal paymentPrice;
    private BigDecimal originalPrice;
    private int couponId;
    private int couponOwnerId;
    private BigDecimal couponPrice;
    private Date createTime;
    private Date updateTime;
    private Date deleteTime;
    private String unused;

    // orderItem 字段
    List<OrderItem> orderItemList;
}