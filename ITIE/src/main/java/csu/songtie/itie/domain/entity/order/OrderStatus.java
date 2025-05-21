package csu.songtie.itie.domain.entity.order;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("orderstatus")
public class OrderStatus {
    @TableId(value = "order_id",type = IdType.INPUT)
    private String orderId;
    private String status;
}