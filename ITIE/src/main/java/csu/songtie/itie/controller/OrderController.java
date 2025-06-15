package csu.songtie.itie.controller;

import csu.songtie.itie.common.CommonResponse;
import csu.songtie.itie.common.ResponseCode;
import csu.songtie.itie.domain.entity.order.Order;
import csu.songtie.itie.domain.vo.OrderVO;
import csu.songtie.itie.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order/")
public class OrderController {
    @Autowired
    private OrderService orderService;

    // TODO: api 的 url 需要更改，不能将其明文传出
    @GetMapping("myOrders")
    public CommonResponse<List<OrderVO>> getOrdersByUsername(@RequestBody int userId){
        return orderService.getOrdersByUserId(userId);
    }

    @PostMapping("insert")
    public CommonResponse<OrderVO> insertOrder(@RequestBody OrderVO orderVO) {
        if (orderVO.getOrderItemList() == null || orderVO.getOrderItemList().isEmpty()) {
            return CommonResponse.createForError(ResponseCode.ORDER_LINEITEM_EMPTY_ERROR.getCode(),
                    ResponseCode.ORDER_LINEITEM_EMPTY_ERROR.getDescription());
        }
        orderService.insertOrderVO(orderVO, orderVO.getPaymentType());
        return CommonResponse.createForSuccess(ResponseCode.ORDER_CREATE_SUCCESS.getCode(),
                ResponseCode.ORDER_CREATE_SUCCESS.getDescription());
    }

    @PostMapping("update")
    public CommonResponse<OrderVO> updateOrderById(@RequestParam String orderId, @RequestParam int status) {
        // 首先检查订单是否存在
        CommonResponse<OrderVO> orderResponse = orderService.getOrderByOrderId(orderId);
        if (orderResponse.getStatus() == ResponseCode.ORDER_EXIST_ERROR.getCode()) {
            return CommonResponse.createForError(ResponseCode.ORDER_EXIST_ERROR.getCode(),
                    ResponseCode.ORDER_EXIST_ERROR.getDescription());
        }
        
        // 订单存在，执行更新操作
        orderService.updateOrder(orderId, status);
        return CommonResponse.createForSuccess(ResponseCode.ORDER_UPDATE_SUCCESS.getCode(),
                ResponseCode.ORDER_UPDATE_SUCCESS.getDescription());
    }

    @GetMapping("by/{orderId}")
    public CommonResponse<OrderVO> getOrderByOrderId(@PathVariable String orderId){
        return orderService.getOrderByOrderId(orderId);
    }

}
