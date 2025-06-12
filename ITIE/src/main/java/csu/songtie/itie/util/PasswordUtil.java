package csu.songtie.itie.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 密码加密验证工具类
 * 基于Spring Security的BCrypt强哈希算法实现
 */
public class PasswordUtil {
    // 使用强度为10的BCrypt加密器（默认强度）
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    /**
     * 密码加密方法
     * @param rawPassword 原始明文密码
     * @return 返回不可逆的BCrypt哈希字符串
     */
    public static String encode(String rawPassword) {
        return encoder.encode(rawPassword);
    }

    /**
     * 密码验证方法
     * @param rawPassword 待验证的明文密码
     * @param encodedPassword 存储的加密密码
     * @return 匹配成功返回true，否则false
     */
    public static boolean matches(String rawPassword, String encodedPassword) {
        return encoder.matches(rawPassword, encodedPassword);
    }
}
