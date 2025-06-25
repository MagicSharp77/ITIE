package csu.songtie.itie.controller;

import csu.songtie.itie.common.CommonResponse;
import csu.songtie.itie.common.ResponseCode;
import csu.songtie.itie.domain.vo.OrderVO;
import csu.songtie.itie.service.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order/")
public class OrderController {
    @Autowired
    private OrderService orderService;

    private int userId = 123;   // TODO: 测试用，后续从JWT中获取

    // TODO: api 的 url 需要更改，不能将其明文传出
    // @GetMapping("myorders")
    // public CommonResponse<List<OrderVO>> getOrdersByUserId(@RequestBody int userId){
    //     return orderService.getOrdersByUserId(userId);
    // }

    /**
     * 获取当前用户的订单列表（测试用，userId写死，后续可替换为JWT解析）
     */
    @GetMapping("myorders")
    public CommonResponse<List<OrderVO>> getOrdersByUserId() {
        return orderService.getOrdersByUserId(userId);
    }

    @PostMapping("insert")
    public CommonResponse<OrderVO> insertOrder(@RequestBody OrderVO orderVO) {
        // 订单内容是否为空
        if (orderVO.getOrderItemList() == null || orderVO.getOrderItemList().isEmpty()) {
            return CommonResponse.createForError(ResponseCode.ORDER_LINEITEM_EMPTY_ERROR.getCode(),
                    ResponseCode.ORDER_LINEITEM_EMPTY_ERROR.getDescription());
        }
        // 订单号是否重复
        if(orderService.getOrderByOrderId(orderVO.getOrderId()) != null){
            return CommonResponse.createForError(ResponseCode.ORDER_EXIST_ERROR.getCode(),
                    ResponseCode.ORDER_EXIST_ERROR.getDescription());
        }
        orderService.insertOrderVO(orderVO);
        return CommonResponse.createForSuccess(ResponseCode.ORDER_CREATE_SUCCESS.getCode(),
                ResponseCode.ORDER_CREATE_SUCCESS.getDescription());
    }

    @PostMapping("update")
    public CommonResponse<OrderVO> updateOrderById(@RequestBody OrderVO orderVO) {
        // 首先检查订单是否存在
        CommonResponse<OrderVO> orderResponse = orderService.getOrderByOrderId(orderVO.getOrderId());
        if (orderResponse.getStatus() == ResponseCode.ORDER_EXIST_ERROR.getCode()) {
            return CommonResponse.createForError(ResponseCode.ORDER_EXIST_ERROR.getCode(),
                    ResponseCode.ORDER_EXIST_ERROR.getDescription());
        }
        
        // 订单存在，执行更新操作
        orderService.updateOrder(orderVO.getOrderId(), orderVO.getStatus());
        return CommonResponse.createForSuccess(ResponseCode.ORDER_UPDATE_SUCCESS.getCode(),
                ResponseCode.ORDER_UPDATE_SUCCESS.getDescription());
    }

    @GetMapping("single/{orderId}")
    public CommonResponse<OrderVO> getOrderByOrderId(@PathVariable String orderId){
        return orderService.getOrderByOrderId(orderId);
    }

}
