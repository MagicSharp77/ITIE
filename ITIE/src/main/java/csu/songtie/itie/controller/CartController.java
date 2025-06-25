package csu.songtie.itie.controller;

import csu.songtie.itie.common.CommonResponse;
import csu.songtie.itie.domain.vo.CartVO;
import csu.songtie.itie.service.CartService;
import csu.songtie.itie.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/cart/")
public class CartController {
    @Autowired
    private CartService cartService;

    private int userId = 123;

    // TODO：JWT 版本
//    @GetMapping("my")
//    public CommonResponse<CartVO> getMyCart(@RequestHeader("Authorization") String token) {
//        Map<String, Object> claims = JwtUtil.ParseToken(token);
//        int userId = (int) claims.get("userId");
//        return cartService.getCart(userId);
//    }
//
//    @PostMapping("courses/{courseId}")
//    public CommonResponse<CartVO> addCourseToCart(
//            @RequestHeader("Authorization") String token,
//            @PathVariable int courseId) {
//        Map<String, Object> claims = JwtUtil.ParseToken(token);
//        int userId = (int) claims.get("userId");
//        return cartService.addCourseToCart(courseId, userId);
//    }
//
//    @DeleteMapping("courses/{courseId}")
//    public CommonResponse<CartVO> removeCourseFromCart(
//            @RequestHeader("Authorization") String token,
//            @PathVariable int courseId) {
//        Map<String, Object> claims = JwtUtil.ParseToken(token);
//        int userId = (int) claims.get("userId");
//        return cartService.removeCourseFromCart(courseId, userId);
//    }
//
//    @DeleteMapping("clear")
//    public CommonResponse<CartVO> clearCart(@RequestHeader("Authorization") String token) {
//        Map<String, Object> claims = JwtUtil.ParseToken(token);
//        int userId = (int) claims.get("userId");
//        return cartService.clearCart(userId);
//    }

    @GetMapping("mycart")
    public CommonResponse<CartVO> getMyCart(){
        return cartService.getCart(userId);
    }

    @PostMapping("add/course/{courseId}")
    public CommonResponse<CartVO> addCourseToCart(@PathVariable int courseId) {
        return cartService.addCourseToCart(courseId, userId);
    }

    @PostMapping("remove/course/{courseId}")
    public CommonResponse<CartVO> removeCourseFromCart(
            @PathVariable int courseId) {
        return cartService.removeCourseFromCart(courseId, userId);
    }

    @DeleteMapping("clear")
    public CommonResponse<CartVO> clearCart(){
        return cartService.clearCart(userId);
    }
}
