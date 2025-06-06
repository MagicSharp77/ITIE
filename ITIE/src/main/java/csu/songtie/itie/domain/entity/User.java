package csu.songtie.itie.domain.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@TableName("user")
@Data
public class User {
    @TableId()
    private Long id;
    @TableField(value = "user_id")
    private String userId;
    @TableField(value = "email")
    private String email;
    @TableField(value = "phone")
    private String phone;
    @TableField(value = "password_hash")
    private String passwordHash;
    @TableField(value = "qq_id")
    private String qqId;
    @TableField(value = "wx_id")
    private String wxId;
    @TableField(value = "student_id")
    private String studentId;
    @TableField(value = "role")
    private String role;
    @TableField(value = "username")
    private String username;
    @TableField(value = "created_at")
    private LocalDateTime createdAt;
    @TableField(value = "updated_at")
    private LocalDateTime updatedAt;
    @TableField(value = "deleted_at")
    private LocalDateTime deletedAt;
    @TableField(value = "unused")
    private String unused;
}
