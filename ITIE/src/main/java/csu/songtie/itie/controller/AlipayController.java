package csu.songtie.itie.controller;

import com.alipay.api.AlipayApiException;
import csu.songtie.itie.service.AlipayService;
import csu.songtie.itie.common.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 支付宝支付控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/pay/alipay")
public class AlipayController {

    @Autowired
    private AlipayService alipayService;

    /**
     * 创建支付订单 - 此处需要订单的持久化-未支付状态    此处的 createPayment 方法参数可能需要更改
     * @param orderId 订单ID
     * @param amount 支付金额
     * @param subject 订单标题
     * @return 支付表单HTML
     */
    @PostMapping("/create")
    public CommonResponse<String> createPayment(
            @RequestParam String orderId,
            @RequestParam String amount,
            @RequestParam String subject) throws AlipayApiException {
        return alipayService.createPayment(orderId, amount, subject);
    }

    /**
     * 处理支付宝异步通知 - 此处需要进行订单状态的更新
     * @param params 支付宝异步通知参数
     * @return 处理结果
     */
    @PostMapping("/notify")
    public CommonResponse<Boolean> handleNotify(@RequestParam String params) throws AlipayApiException {
        return alipayService.handleNotify(params);
    }

    /**
     * 查询订单支付状态
     * @param orderId 订单ID
     * @return 支付状态
     */
    @GetMapping("/status/{orderId}")
    public CommonResponse<String> queryOrderStatus(@PathVariable String orderId) throws AlipayApiException {
        return alipayService.queryOrderStatus(orderId);
    }
} 