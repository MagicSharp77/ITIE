package csu.songtie.itie.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 支付宝配置类
 * 用于读取application.properties中的支付宝相关配置
 */
@Data
@Component
@ConfigurationProperties(prefix = "alipay")
public class AlipayConfig {
    // 应用ID，支付宝分配给开发者的应用ID
    private String appId;
    
    // 商户私钥，用于对请求进行签名
    private String privateKey;
    
    // 支付宝公钥，用于验证支付宝的响应
    private String publicKey;
    
    // 支付宝异步通知地址，用于接收支付结果通知
    private String notifyUrl;
    
    // 支付宝同步返回地址，用于支付完成后跳转
    private String returnUrl;
    
    // 支付宝网关地址
    private String gateway;
} 