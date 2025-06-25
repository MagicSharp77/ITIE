package csu.songtie.itie.domain.entity.cart;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Date;

@Data
@TableName("cart")
public class Cart {
    @TableId(value = "id",type = IdType.INPUT)
    private Integer id;
    @TableField(value = "user_id")
    private int userId;
    @TableField(value = "item_num")
    private int itemNum;
    @TableField(value = "original_price")
    private BigDecimal originalPrice;
    @TableField(value = "current_price")
    private BigDecimal currentPrice;
    @TableField(value = "create_time")
    private Date createTime;
    @TableField(value = "update_time")
    private Date updateTime;
    @TableField(value = "delete_time")
    private Date deleteTime;
    private String unused;
}
