package csu.songtie.itie.controller;

import csu.songtie.itie.service.WechatPayService;
import csu.songtie.itie.common.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 微信支付控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/pay/wechat")
public class WechatPayController {
    @Autowired
    private WechatPayService wechatPayService;

    /**
     * 创建微信支付订单
     * @param orderId 订单ID
     * @param amount 支付金额（单位：分）
     * @param description 商品描述
     * @return 支付二维码链接
     */
    @PostMapping("/create")
    public CommonResponse<String> createPayment(
            @RequestParam String orderId,
            @RequestParam String amount,
            @RequestParam String description) {
        return wechatPayService.createPayment(orderId, amount, description);
    }

    /**
     * 处理微信支付回调通知
     * @param notifyData 回调数据
     * @return 处理结果
     */
    @PostMapping("/notify")
    public CommonResponse<Boolean> handleNotify(@RequestBody String notifyData) {
        return wechatPayService.handleNotify(notifyData);
    }

    /**
     * 查询订单支付状态
     * @param orderId 订单ID
     * @return 支付状态
     */
    @GetMapping("/status/{orderId}")
    public CommonResponse<Map<String, String>> queryOrderStatus(@PathVariable String orderId) {
        return wechatPayService.queryOrderStatus(orderId);
    }

    /**
     * 关闭订单
     * @param orderId 订单ID
     * @return 处理结果
     */
    @PostMapping("/close/{orderId}")
    public CommonResponse<Boolean> closeOrder(@PathVariable String orderId) {
        return wechatPayService.closeOrder(orderId);
    }
} 