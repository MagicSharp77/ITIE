package csu.songtie.itie.domain.vo;


import csu.songtie.itie.domain.entity.cart.CartItem;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

@Data
public class CartVO {
    // cart 字段
    private int userId;
    private int itemNum;
    private BigDecimal originalPrice;
    private BigDecimal currentPrice;
    private Date createTime;
    private Date updateTime;
    private Date deleteTime;
    private String unused;

    // cartItem
    private List<CartItem> cartItemList;
}
