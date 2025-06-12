package csu.songtie.itie.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户信息实体
 *
 * <p>包含字段：</p>
 * <ul>
 *   <li>id - 主键ID，自增</li>
 *   <li>userId - 关联用户ID</li>
 *   <li>username - 用户名</li>
 *   <li>lastName - 姓</li>
 *   <li>firstName - 名</li>
 *   <li>major - 专业</li>
 *   <li>allowEmailNotify - 是否允许邮件通知</li>
 *   <li>allowSMSNotify - 是否允许短信通知</li>
 *   <li>avatarUrl - 头像URL</li>
 *   <li>createdAt - 创建时间</li>
 *   <li>updatedAt - 最后更新时间</li>
 *   <li>deletedAt - 删除时间（软删除标记）</li>
 *   <li>unused - 预留字段（未使用）</li>
 * </ul>
 */
@Data
@TableName("user_info")
public class UserInfo {
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    @TableField(value = "user_id")
    private String userId;
    @TableField(value = "username")
    private String username;
    @TableField(value = "last_name")
    private String lastName;
    @TableField(value = "first_name")
    private String firstName;
    @TableField(value = "major")
    private String major;
    @TableField(value = "allow_email_notify")
    private Boolean allowEmailNotify;
    @TableField(value = "allow_sms_notify")
    private Boolean allowSMSNotify;
    @TableField(value = "avatar_url")
    private String avatarUrl;
    @TableField(value = "created_at")
    private LocalDateTime createdAt;
    @TableField(value = "updated_at")
    private LocalDateTime updatedAt;
    @TableField(value = "deleted_at")
    private LocalDateTime deletedAt;
    @TableField(value = "unused")
    private String unused;
}
