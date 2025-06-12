package csu.songtie.itie.util;

import jakarta.servlet.http.HttpServletRequest;

/**
 * IP地址工具类
 * 用于获取客户端的真实IP地址（考虑代理服务器场景）
 */
public class IPUtil {
    public static String getIp(HttpServletRequest request) {
        // 检查反向代理设置的通用头字段
        String ip = request.getHeader("x-forwarded-for");
        // 后续依次检查不同代理服务器的专用头字段
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        // 如果以上都没有获取到IP地址，则使用请求的远程IP地址
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
