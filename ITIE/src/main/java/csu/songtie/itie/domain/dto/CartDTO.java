package csu.songtie.itie.domain.dto;

import csu.songtie.itie.domain.entity.cart.Cart;
import csu.songtie.itie.domain.entity.cart.CartItem;
import csu.songtie.itie.domain.vo.CartVO;

import java.util.List;

// TODO: 可能有冗余字段(比如前端用不到的字段)
public class CartDTO {
    public static CartVO entityToVO(Cart cart, List<CartItem> cartItemList){
        CartVO cartVO = new CartVO();

        // 插入 cart 字段
        cartVO.setUserId(cart.getUserId());
        cartVO.setItemNum(cart.getItemNum());
        cartVO.setOriginalPrice(cart.getOriginalPrice());
        cartVO.setCurrentPrice(cart.getCurrentPrice());
        cartVO.setCreateTime(cart.getCreateTime());
        cartVO.setUpdateTime(cart.getUpdateTime());
        cartVO.setDeleteTime(cart.getDeleteTime());
        cartVO.setUnused(cart.getUnused());

        // 插入 cartItemList 字段
        cartVO.setCartItemList(cartItemList);

        return cartVO;
    }

    public static  Cart vOToEntity(CartVO cartVO){
        Cart cart = new Cart();

        cart.setUserId(cartVO.getUserId());
        cart.setItemNum(cartVO.getItemNum());
        cart.setOriginalPrice(cartVO.getOriginalPrice());
        cart.setCurrentPrice(cartVO.getCurrentPrice());
        cart.setCreateTime(cartVO.getCreateTime());
        cart.setUpdateTime(cartVO.getUpdateTime());
        cart.setDeleteTime(cartVO.getDeleteTime());
        cart.setUnused(cartVO.getUnused());

        return cart;
    }

    public static List<CartItem>  vOToItemList(CartVO cartVO){
        return cartVO.getCartItemList();
    }

}
