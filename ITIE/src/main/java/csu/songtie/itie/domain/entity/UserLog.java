package csu.songtie.itie.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("user_log")
public class UserLog {
    @TableId()
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
