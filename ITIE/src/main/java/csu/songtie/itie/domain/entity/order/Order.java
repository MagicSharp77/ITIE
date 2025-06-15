package csu.songtie.itie.domain.entity.order;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDateTime;

@Data
@TableName("`order`")
public class Order {
    @TableId(value = "id",type = IdType.INPUT)
    private int id;
    @TableField(value = "order_id")
    private String orderId;
    @TableField(value = "user_id")
    private int userId;
    private int status;
    @TableField(value = "payment_type")
    private String paymentType;
    @TableField(value = "payment_time")
    private Date paymentTime;
    @TableField(value = "payment_price")
    private BigDecimal paymentPrice;
    @TableField(value = "original_price")
    private BigDecimal originalPrice;
    @TableField(value = "coupon_id")
    private int couponId;
    @TableField(value = "coupon_owner_id")
    private int couponOwnerId;
    @TableField(value = "coupon_price")
    private BigDecimal couponPrice;
    @TableField(value = "create_time")
    private Date createTime;
    @TableField(value = "update_time")
    private Date updateTime;
    @TableField(value = "delete_time")
    private Date deleteTime;
    private String unused;
}
