package csu.songtie.itie.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 操作日志记录注解
 * <p>用于标记需要记录操作日志的方法，配合AOP实现日志记录</p>
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LogAction {
    /**
     * 操作类型描述
     * @return 返回操作类型字符串（示例："用户登录"、"修改密码" 等）
     */
    String value() default "";
}
