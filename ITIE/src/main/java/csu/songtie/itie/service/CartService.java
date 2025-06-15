package csu.songtie.itie.service;

import csu.songtie.itie.common.CommonResponse;
import csu.songtie.itie.domain.vo.CartVO;
import csu.songtie.itie.domain.entity.cart.Cart;

public interface CartService {
    /**
     * 初始化用户购物车
     * @param userId 用户ID
     * @return 初始化后的购物车信息
     */
    public CommonResponse<CartVO> initUserCart(int userId);

    /**
     * 获取购物车内容
     */
    public CommonResponse<CartVO> getCart(int userId);

    /**
     * 更新购物车
     * @param cart 购物车信息
     */
    public CommonResponse<CartVO> updateCart(Cart cart);

    /**
     * 添加课程到购物车  操作 cart_item 表
     * @param courseId 课程ID
     */
    public CommonResponse<CartVO> addCourseToCart(int courseId);

    /**
     * 从购物车移除课程  操作 cart_item 表
     * @param courseId 课程ID
     */
    public CommonResponse<CartVO> removeCourseFromCart(int courseId);

    // 清空购物车
    public CommonResponse<CartVO> clearCart();
}
