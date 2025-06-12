package csu.songtie.itie.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户操作日志实体
 *
 * <p>包含字段：</p>
 * <ul>
 *   <li>id - 主键ID，自增</li>
 *   <li>userId - 关联用户ID</li>
 *   <li>username - 用户名</li>
 *   <li>ip - 操作IP地址</li>
 *   <li>location - IP解析的地理位置</li>
 *   <li>action - 操作行为描述</li>
 *   <li>createdAt - 日志创建时间</li>
 *   <li>updatedAt - 最后更新时间</li>
 *   <li>deletedAt - 删除时间（软删除标记）</li>
 *   <li>unused - 预留字段（未使用）</li>
 * </ul>
 */
@Data
@TableName("user_log")
public class UserLog {
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    @TableField(value = "user_id")
    private String userId;
    @TableField(value = "username")
    private String username;
    @TableField(value = "ip")
    private String ip;
    @TableField(value = "location")
    private String location;
    @TableField(value = "action")
    private String action;
    @TableField(value = "created_at")
    private LocalDateTime createdAt;
    @TableField(value = "updated_at")
    private LocalDateTime updatedAt;
    @TableField(value = "deleted_at")
    private LocalDateTime deletedAt;
    @TableField(value = "unused")
    private String unused;
}
