package csu.songtie.itie.service;

import csu.songtie.itie.common.CommonResponse;
import csu.songtie.itie.domain.vo.CartVO;

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
     * 添加课程到购物车
     * @param courseId 课程ID
     * @param userId 用户ID
     */
    public CommonResponse<CartVO> addCourseToCart(int courseId, int userId);

    /**
     * 从购物车移除课程
     * @param courseId 课程ID
     */
    public CommonResponse<CartVO> removeCourseFromCart(int courseId,int userId);

    /**
     * 清空购物车
     * @param userId 用户ID
     */
    public CommonResponse<CartVO> clearCart(int userId);
}
