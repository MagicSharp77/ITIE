package csu.songtie.itie.domain.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户实体
 *
 * <p>包含字段：</p>
 * <ul>
 *   <li>id - 主键ID，自增</li>
 *   <li>userId - 用户ID</li>
 *   <li>email - 邮箱地址</li>
 *   <li>phone - 手机号码</li>
 *   <li>passwordHash - 密码哈希值</li>
 *   <li>qqId - QQ用户ID</li>
 *   <li>wxId - 微信用户ID</li>
 *   <li>studentId - 学生ID</li>
 *   <li>role - 用户角色</li>
 *   <li>createdAt - 创建时间</li>
 *   <li>updatedAt - 最后更新时间</li>
 *   <li>deletedAt - 删除时间（软删除标记）</li>
 *   <li>unused - 预留字段（未使用）</li>
 * </ul>
 */
@TableName("user")
@Data
public class User {
    @TableId(value = "id",type = IdType.AUTO)
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
    private int role;
    @TableField(value = "created_at")
    private LocalDateTime createdAt;
    @TableField(value = "updated_at")
    private LocalDateTime updatedAt;
    @TableField(value = "deleted_at")
    private LocalDateTime deletedAt;
    @TableField(value = "unused")
    private String unused;
}
