package csu.songtie.itie.domain.dto;

import csu.songtie.itie.domain.entity.cart.Cart;
import csu.songtie.itie.domain.entity.cart.CartItem;
import csu.songtie.itie.domain.vo.CartVO;

import java.util.List;

// TODO: 该类方法尚未实现
public class CartDTO {
    public static CartVO entityToVO(Cart cart, List<CartItem> cartItemList){
        return null;
    }

    public static  Cart vOToEntity(CartVO cartVO){
        return null;
    }

    public static List<CartItem>  vOToItemList(CartVO cartVO){
        return null;
    }

}
