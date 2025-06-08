package csu.songtie.itie.util;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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
