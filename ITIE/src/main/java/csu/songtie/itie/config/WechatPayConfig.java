package csu.songtie.itie.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 微信支付配置类
 */
@Data
@Component
@ConfigurationProperties(prefix = "wx.pay")
public class WechatPayConfig {
    // 应用ID
    private String appId;
    
    // 商户号
    private String merchantId;
    
    // 商户序列号
    private String merchantSerialNumber;
    
    // 商户私钥
    private String merchantPrivateKey;
    
    // APIv3密钥
    private String apiV3Key;
    
    // 回调通知地址
    private String notifyUrl;
} 