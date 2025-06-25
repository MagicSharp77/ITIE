package csu.songtie.itie.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import csu.songtie.itie.common.CommonResponse;
import csu.songtie.itie.common.ResponseCode;
import csu.songtie.itie.domain.dto.CartDTO;
import csu.songtie.itie.domain.entity.Course;
import csu.songtie.itie.domain.entity.cart.Cart;
import csu.songtie.itie.domain.entity.cart.CartItem;
import csu.songtie.itie.domain.vo.CartVO;
import csu.songtie.itie.mapper.CourseMapper;
import csu.songtie.itie.mapper.cart.CartItemMapper;
import csu.songtie.itie.mapper.cart.CartMapper;
import csu.songtie.itie.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service("cartService")
public class CartServiceImpl implements CartService {
    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private CartItemMapper cartItemMapper;
    @Autowired
     private CourseMapper courseMapper;

    @Override
    public CommonResponse<CartVO> initUserCart(int userId) {
        Cart cart = new Cart();
        cart.setUserId(userId);
        cart.setItemNum(0);
        cart.setOriginalPrice(BigDecimal.ZERO);
        cart.setCurrentPrice(BigDecimal.ZERO);
        cart.setCreateTime(new Date(System.currentTimeMillis()));
        cart.setUpdateTime(new Date(System.currentTimeMillis()));
        
        cartMapper.insert(cart);
        
        return CommonResponse.createForSuccess(ResponseCode.CART_CREATE_SUCCESS.getCode(),
                ResponseCode.CART_CREATE_SUCCESS.getDescription());
    }

    @Override
    public CommonResponse<CartVO> getCart(int userId) {
        // 获取 cart
        QueryWrapper<Cart> cartQueryWrapper = new QueryWrapper<>();
        cartQueryWrapper.eq("user_id",userId);
        Cart cart = cartMapper.selectOne(cartQueryWrapper);

        if(cart == null){
            Cart c = new Cart();
            c.setUserId(userId);
            c.setItemNum(0);
            c.setOriginalPrice(BigDecimal.ZERO);
            c.setCurrentPrice(BigDecimal.ZERO);
            c.setCreateTime(new Date(System.currentTimeMillis()));
            c.setUpdateTime(new Date(System.currentTimeMillis()));
            cart = c;

            cartMapper.insert(cart);
        }

        // 获取 cartItemList
        QueryWrapper<CartItem> cartItemQueryWrapper = new QueryWrapper<>();
        cartItemQueryWrapper.eq("user_id",userId)
                          .eq("deleted", 0);
        List<CartItem> cartItemList = cartItemMapper.selectList(cartItemQueryWrapper);

        CartVO cartVO = CartDTO.entityToVO(cart, cartItemList);

        return CommonResponse.createForSuccess(ResponseCode.CART_GET_SUCCESS.getCode(),
                ResponseCode.CART_GET_SUCCESS.getDescription(),
                cartVO);
    }

    @Override
    @Transactional
    public CommonResponse<CartVO> addCourseToCart(int courseId, int userId) {
        // 检查课程是否已在购物车中
        QueryWrapper<CartItem> cartItemQueryWrapper = new QueryWrapper<>();
        cartItemQueryWrapper.eq("user_id", userId)
                   .eq("course_id", courseId)
                   .eq("deleted", 0);
        CartItem existingItem = cartItemMapper.selectOne(cartItemQueryWrapper);

        // 确保课程存在
        if (existingItem != null) {
            return CommonResponse.createForError(ResponseCode.CART_ITEM_EXIST_ERROR.getCode(),
                    ResponseCode.CART_ITEM_EXIST_ERROR.getDescription());
        }

        // 获取课程信息
        CartItem cartItem = new CartItem();
        cartItem.setUserId(userId);
        cartItem.setCourseId(courseId);
        cartItem.setCreateTime(new Date(System.currentTimeMillis()));
        cartItem.setUpdateTime(new Date(System.currentTimeMillis()));
        cartItem.setDeleted(0);

        // 暂用
        cartItem.setCourseName("示例课程"); // 实际应从Course表获取
        cartItem.setCourseImage("示例图片URL"); // 实际应从Course表获取
        cartItem.setCourseOriginalPrice(BigDecimal.valueOf(100.00)); // 实际应从Course表获取
        cartItem.setCourseDiscount(BigDecimal.valueOf(0.8)); // 实际应从Course表获取
        cartItem.setCurrentPrice(BigDecimal.valueOf(80.00)); // 实际应从Course表获取

        // TODO：待获取 course 数据库后
//        QueryWrapper<Course> courseQueryWrapper = new QueryWrapper<>();
//        courseQueryWrapper.eq("course_id",courseId);
//        Course course = courseMapper.selectOne(courseQueryWrapper);
//        cartItem.setCourseName(course.getCategoryName());
//        cartItem.setCourseImage(course.getCoverImgUrl());
//        cartItem.setCourseOriginalPrice(BigDecimal.valueOf(course.getOriginalPrice()));
//        cartItem.setCourseDiscount(course.getDiscountValue());
//        cartItem.setCurrentPrice(BigDecimal.valueOf(course.getCurrentPrice()));

        // 插入购物车项
        cartItemMapper.insert(cartItem);
        
        // 更新购物车总价和商品数量
        updateCartTotal(userId);
        
        return getCart(userId);
    }

    @Override
    @Transactional
    public CommonResponse<CartVO> removeCourseFromCart(int courseId,int userId) {
        // 软删除购物车项
        QueryWrapper<CartItem> cartItemQueryWrapper = new QueryWrapper<>();
        cartItemQueryWrapper.eq("course_id", courseId)
                .eq("user_id",userId)
                .eq("deleted", 0);
        CartItem cartItem = cartItemMapper.selectOne(cartItemQueryWrapper);
        
        if (cartItem == null) {
            return CommonResponse.createForError(ResponseCode.CART_ITEM_NOT_EXIST_ERROR.getCode(),
                    ResponseCode.CART_ITEM_NOT_EXIST_ERROR.getDescription());
        }

        // 更新删除状态
        CartItem updateItem = new CartItem();
        updateItem.setDeleted(1);
        updateItem.setDeleteTime(new Date(System.currentTimeMillis()));
        cartItemMapper.update(updateItem, cartItemQueryWrapper);
        
        // 更新购物车总价和商品数量
        updateCartTotal(userId);
        
        return getCart(userId);
    }

    @Override
    @Transactional
    public CommonResponse<CartVO> clearCart(int userId) {
        // 软删除所有购物车项
        QueryWrapper<CartItem> cartItemQueryWrapper = new QueryWrapper<>();
        cartItemQueryWrapper.eq("user_id", userId)
                   .eq("deleted", 0);

        List<CartItem> cartItemList = cartItemMapper.selectList(cartItemQueryWrapper);
        for (CartItem updateCartItem : cartItemList){
            UpdateWrapper<CartItem> cartItemUpdateWrapper = new UpdateWrapper<>();
            cartItemUpdateWrapper.eq("user_id",userId)
                            .eq("deleted",0)
                                    .eq("course_id",updateCartItem.getCourseId());

            updateCartItem.setDeleted(1);
            updateCartItem.setDeleteTime(new Date(System.currentTimeMillis()));
            cartItemMapper.update(updateCartItem,cartItemUpdateWrapper);
        }
        
        // 重置购物车
        Cart cart = new Cart();
        cart.setUserId(userId);
        cart.setItemNum(0);
        cart.setOriginalPrice(BigDecimal.ZERO);
        cart.setCurrentPrice(BigDecimal.ZERO);
        cart.setUpdateTime(new Date(System.currentTimeMillis()));
        
        UpdateWrapper<Cart> cartUpdateWrapper = new UpdateWrapper<>();
        cartUpdateWrapper.eq("user_id", userId);
        cartMapper.update(cart, cartUpdateWrapper);
        
        return getCart(userId);
    }

    /**
     * 更新购物车总价和商品数量
     */
    private void updateCartTotal(int userId) {
        // 获取购物车项列表
        QueryWrapper<CartItem> cartItemQueryWrapper = new QueryWrapper<>();
        cartItemQueryWrapper.eq("user_id", userId)
                   .eq("deleted", 0);
        List<CartItem> cartItemList = cartItemMapper.selectList(cartItemQueryWrapper);
        
        // 计算总价和商品数量
        BigDecimal totalOriginalPrice = BigDecimal.ZERO;
        BigDecimal totalCurrentPrice = BigDecimal.ZERO;
        int itemNum = cartItemList.size();
        
        for (CartItem item : cartItemList) {
            totalOriginalPrice = totalOriginalPrice.add(item.getCourseOriginalPrice());
            totalCurrentPrice = totalCurrentPrice.add(item.getCurrentPrice());
        }
        
        // 更新购物车
        QueryWrapper<Cart> cartQueryWrapper = new QueryWrapper<>();
        cartQueryWrapper.eq("user_id",userId);
        Cart cart = cartMapper.selectOne(cartQueryWrapper);
        cart.setItemNum(itemNum);
        cart.setOriginalPrice(totalOriginalPrice);
        cart.setCurrentPrice(totalCurrentPrice);
        cart.setUpdateTime(new Date(System.currentTimeMillis()));
        
        UpdateWrapper<Cart> cartUpdateWrapper = new UpdateWrapper<>();
        cartUpdateWrapper.eq("user_id", userId);
        cartMapper.update(cart, cartUpdateWrapper);
    }
}
