package csu.songtie.itie.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 短信服务配置类
 * 通过@Value注解从application.properties中注入阿里云短信服务相关参数
 */
@Data
@Component
public class SmsConfig {
    @Value("${ly.sms.accessKeyId}")
    private String accessKeyId;
    @Value("${ly.sms.accessKeySecret}")
    private String accessKeySecret;
    @Value("${ly.sms.signName}")
    private String signName;
    @Value("${ly.sms.verifyTemplateCode}")
    private String verifyTemplateCode;
}
