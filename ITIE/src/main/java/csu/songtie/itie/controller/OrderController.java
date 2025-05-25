package csu.songtie.itie.controller;

import csu.songtie.itie.common.CommonResponse;
import csu.songtie.itie.common.ResponseCode;
import csu.songtie.itie.domain.entity.order.OrderRequest;
import csu.songtie.itie.domain.vo.OrderVO;
import csu.songtie.itie.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("{id}")
    @ResponseBody
    public CommonResponse<List<OrderVO>> getOrdersByUsername(String username){
        return orderService.getOrdersByUsername(username);
    }

    @PostMapping("submit")
    @ResponseBody
    public CommonResponse<List<OrderVO>> insertOrderVO(@RequestBody OrderRequest request){
        if(request.getLineItemList().isEmpty()){
            return CommonResponse.createForError(ResponseCode.ORDER_LINEITEM_EMPTY_ERROR.getCode(),
                    ResponseCode.ORDER_LINEITEM_EMPTY_ERROR.getDescription());
        }
        orderService.insertOrderVO(request.getOrderVO(),request.getLineItemList());
        return CommonResponse.createForSuccessMessage("成功提交订单");
    }

    @GetMapping("by{orderId}")
    public CommonResponse<OrderVO> getOrderByOrderId(String orderId){
        return orderService.getOrderByOrderId(orderId);
    }

}
