package csu.songtie.itie.domain.entity.cart;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Date;

@Data
@TableName("cart_item")
public class CartItem {
    @TableId(value = "id",type = IdType.INPUT)
    private int id;
    @TableField(value = "user_id")
    private int userId;
    @TableField(value = "course_id")
    private int courseId;
    @TableField(value = "course_name")
    private String courseName;
    @TableField(value = "course_image")
    private String courseImage;
    @TableField(value = "course_original_price")
    private BigDecimal courseOriginalPrice;
    @TableField(value = "course_discount")
    private BigDecimal courseDiscount;
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
