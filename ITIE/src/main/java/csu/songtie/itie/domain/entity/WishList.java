package csu.songtie.itie.domain.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("wish_list")
public class WishList {
    @TableId()
    private Long id;
    @TableField(value = "user_id")
    private String userId;
    @TableField(value = "course_id")
    private String courseId;
    @TableField(value = "title")
    private String title;
    @TableField(value = "original_price")
    private BigDecimal originalPrice;
    @TableField(value = "current_price")
    private BigDecimal currentPrice;
    @TableField(value = "cover_img_url")
    private String coverImgUrl;
    @TableField(value = "score")
    private String score;
    @TableField(value = "total_minutes")
    private int totalMinutes;
    @TableField(value = "created_at")
    private LocalDateTime createdAt;
    @TableField(value = "updated_at")
    private LocalDateTime updatedAt;
    @TableField(value = "deleted_at")
    private LocalDateTime deletedAt;
    @TableField(value = "unused")
    private String unused;
}
