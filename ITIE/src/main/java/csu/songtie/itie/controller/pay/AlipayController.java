package csu.songtie.itie.controller.pay;

import com.alipay.api.AlipayApiException;
import csu.songtie.itie.domain.entity.order.Order;
import csu.songtie.itie.service.pay.AlipayService;
import csu.songtie.itie.service.order.OrderService;
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
    @Autowired
    private OrderService orderService;

    /**
     * 创建支付订单 - 此处需要订单的持久化-未支付状态    此处的 createPayment 方法参数可能需要更改
     * @param order 订单对象
     * @return 支付表单HTML
     */
    @PostMapping("/create")
    public CommonResponse<String> createPayment(@RequestBody Order order) throws AlipayApiException {
        // TODO:订单持久化 调用orderService的逻辑
        // orderService.insertOrderVO(null, null);
        return alipayService.createPayment(order);
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
    @GetMapping("/query")
    public CommonResponse<String> queryOrderStatus(@RequestParam String orderId) throws AlipayApiException {
        return alipayService.queryOrderStatus(orderId);
    }
} 