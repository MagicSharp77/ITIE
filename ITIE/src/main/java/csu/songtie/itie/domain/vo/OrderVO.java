package csu.songtie.itie.domain.vo;

import csu.songtie.itie.domain.entity.order.LineItem;
import lombok.Data;

import java.util.List;

@Data
public class OrderVO {
    // order 字段
    private String orderId;
    private String userName;
    private int lineNum;
    private String payMethod;
    private float totalPrice;

    // orderStatus 字段
    private String status;

    // LineItem 字段
    private List<LineItem> lineItemList;

}