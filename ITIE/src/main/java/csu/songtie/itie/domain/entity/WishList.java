package csu.songtie.itie.domain.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 用户心愿单实体
 *
 * <p>包含字段：</p>
 * <ul>
 *   <li>id - 主键ID，自增</li>
 *   <li>userId - 关联用户ID</li>
 *   <li>courseId - 关联课程ID</li>
 *   <li>title - 课程标题</li>
 *   <li>originalPrice - 课程原价</li>
 *   <li>currentPrice - 课程现价</li>
 *   <li>coverImgUrl - 课程封面图URL</li>
 *   <li>score - 课程评分</li>
 *   <li>totalMinutes - 课程总时长（分钟）</li>
 *   <li>createdAt - 记录创建时间</li>
 *   <li>updatedAt - 最后更新时间</li>
 *   <li>deletedAt - 删除时间（软删除标记）</li>
 *   <li>unused - 预留字段（未使用）</li>
 * </ul>
 */
@Data
@TableName("wish_list")
public class WishList {
    @TableId(value = "id",type = IdType.AUTO)
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
