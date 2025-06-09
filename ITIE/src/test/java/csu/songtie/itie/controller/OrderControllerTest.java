package csu.songtie.itie.controller;

import csu.songtie.itie.common.CommonResponse;
import csu.songtie.itie.common.ResponseCode;
import csu.songtie.itie.domain.vo.OrderVO;
import csu.songtie.itie.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
    }

    @Test
    void getOrdersByUsername_Success() throws Exception {
        // 准备测试数据
        List<OrderVO> mockOrders = new ArrayList<>();
        OrderVO order1 = new OrderVO();
        order1.setOrderId("ORDER001");
        mockOrders.add(order1);

        CommonResponse<List<OrderVO>> mockResponse = CommonResponse.createForSuccess(mockOrders);

        // 模拟service层返回
        when(orderService.getOrdersByUserId(anyInt())).thenReturn(mockResponse);

        // 执行测试
        mockMvc.perform(get("/order/myOrders")
                .contentType("application/json")
                .content("1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(ResponseCode.SUCCESS.getCode()))
                .andExpect(jsonPath("$.data[0].orderId").value("ORDER001"));
    }

    @Test
    void getOrdersByUsername_EmptyList() throws Exception {
        // 准备测试数据
        List<OrderVO> emptyList = new ArrayList<>();
        CommonResponse<List<OrderVO>> mockResponse = CommonResponse.createForSuccess(emptyList);

        // 模拟service层返回
        when(orderService.getOrdersByUserId(anyInt())).thenReturn(mockResponse);

        // 执行测试
        mockMvc.perform(get("/order/myOrders")
                .contentType("application/json")
                .content("1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(ResponseCode.SUCCESS.getCode()))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    void getOrdersByUsername_InvalidUserId() throws Exception {
        // 执行测试
        mockMvc.perform(get("/order/myOrders")
                .contentType("application/json")
                .content("invalid"))
                .andExpect(status().isBadRequest());
    }
} 