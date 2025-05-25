package csu.songtie.itie.domain.entity.order;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("order")
public class Order {
    @TableId(value = "order_id",type = IdType.INPUT)
    private String orderId;
    @TableField(value = "username")
    private String userName;
    @TableField(value = "linenum")
    private int lineNum;
    @TableField("paymethod")
    private String payMethod;
    @TableField(value = "totalprice")
    private float totalPrice;
}
