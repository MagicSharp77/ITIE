package csu.songtie.itie.service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import csu.songtie.itie.common.ResponseCode;
import csu.songtie.itie.exception.BusinessException;
import csu.songtie.itie.service.SmsService;
import csu.songtie.itie.config.SmsConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class SmsServiceImpl implements SmsService {
    @Autowired
    public SmsConfig smsConfig;

    @Override
    public void send(Map<String, Object> param, String phone) {
        if (phone == null || phone.isEmpty()) {
            throw  new BusinessException(ResponseCode.INVALID_PARAMETER);
        }
        // 初始化acsClient
        DefaultProfile profile = DefaultProfile.getProfile("default", smsConfig.getAccessKeyId(), smsConfig.getAccessKeySecret());
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        //request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");

        request.putQueryParameter("PhoneNumbers", phone);                    //手机号
        request.putQueryParameter("SignName", "阿里云短信测试");
        request.putQueryParameter("TemplateCode", smsConfig.getVerifyTemplateCode());
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(param));

        try {
            CommonResponse response = client.getCommonResponse(request);
            if (!response.getHttpResponse().isSuccess()) {
                // API调用是通的，但业务层面失败了（如模板错误、签名错误等）
                // 记录下来自阿里云的详细错误信息，用于调试
                log.warn("Failed to send SMS. Alibaba Cloud response: {}", response.getData());
                // 抛出我们统一的业务异常给上层
                throw new BusinessException(ResponseCode.CAPTCHA_SEND_FAILED);
            }
        } catch (ClientException e) {
            log.warn("Failed to send SMS. ClientException: {}", e.getMessage());
            // 将原始异常作为“原因(cause)”包装起来，一起抛出
            throw new BusinessException(ResponseCode.CAPTCHA_SEND_FAILED, e);
        }
    }
}

