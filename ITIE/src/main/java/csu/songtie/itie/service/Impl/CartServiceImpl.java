package csu.songtie.itie.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import csu.songtie.itie.common.CommonResponse;
import csu.songtie.itie.common.ResponseCode;
import csu.songtie.itie.domain.dto.CartDTO;
import csu.songtie.itie.domain.entity.cart.Cart;
import csu.songtie.itie.domain.entity.cart.CartItem;
import csu.songtie.itie.domain.vo.CartVO;
import csu.songtie.itie.mapper.cart.CartItemMapper;
import csu.songtie.itie.mapper.cart.CartMapper;
import csu.songtie.itie.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("cartService")
public class CartServiceImpl implements CartService {
    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private CartItemMapper cartItemMapper;

    @Override
    public CommonResponse<CartVO> initUserCart(int userId) {
        return null;
    }

    @Override
    public CommonResponse<CartVO> getCart(int userId) {
        // 获取 cart
        QueryWrapper<Cart> cartQueryWrapper = new QueryWrapper<>();
        cartQueryWrapper.eq("user_id",userId);
        Cart cart = cartMapper.selectOne(cartQueryWrapper);

        // 获取 cartItemList
        QueryWrapper<CartItem> cartItemQueryWrapper = new QueryWrapper<>();
        cartItemQueryWrapper.eq("user_id",userId);
        List<CartItem> cartItemList = cartItemMapper.selectList(cartItemQueryWrapper);

        CartVO cartVO = CartDTO.entityToVO(cart, cartItemList);

        return CommonResponse.createForSuccess(ResponseCode.CART_GET_SUCCESS.getCode(),
                ResponseCode.CART_GET_SUCCESS.getDescription(),
                cartVO);
    }

    @Override
    public CommonResponse<CartVO> updateCart(Cart cart) {
        return null;
    }

    @Override
    public CommonResponse<CartVO> addCourseToCart(int courseId) {
        return null;
    }

    @Override
    public CommonResponse<CartVO> removeCourseFromCart(int courseId) {
        return null;
    }

    @Override
    public CommonResponse<CartVO> clearCart() {
        return null;
    }
}
