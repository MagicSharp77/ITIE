package csu.songtie.itie.domain.entity.order;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("lineitem")
public class LineItem {
    @TableId(value = "order_id",type = IdType.INPUT)
    private String orderId;
    @TableField(value = "linenum")
    private int lineNum;
    @TableField(value = "course_id")
    private String courseId;
    private int quantity;
    @TableField(value = "uniprice")
    private float uniPrice;
    private float discount;
}
