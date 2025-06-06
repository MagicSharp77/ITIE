package csu.songtie.itie.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("user_info")
public class UserInfo {
    @TableId()
    private Long id;
    @TableField(value = "user_id")
    private String userId;
    @TableField(value = "username")
    private String userName;
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
