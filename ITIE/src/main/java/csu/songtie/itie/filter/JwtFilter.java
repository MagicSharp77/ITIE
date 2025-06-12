package csu.songtie.itie.filter;

import com.auth0.jwt.interfaces.Claim;
import com.fasterxml.jackson.databind.ObjectMapper;
import csu.songtie.itie.common.CommonResponse;
import csu.songtie.itie.common.ResponseCode;
import csu.songtie.itie.util.JwtUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
public class JwtFilter implements Filter {
    final JwtUtil jwtUtil;
    final RedisTemplate<String, String> redisTemplate;
    final ObjectMapper objectMapper;

    @Autowired
    public JwtFilter(JwtUtil jwtUtil, RedisTemplate<String, String> redisTemplate, ObjectMapper objectMapper) {
        this.jwtUtil = jwtUtil;
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    /**
     * JWT认证过滤器核心方法
     * @param request  Servlet请求对象
     * @param response Servlet响应对象
     * @param chain    过滤器链
     * 处理流程：
     * 1. 处理CORS预检请求（OPTIONS方法）
     * 2. 放行允许的路径("/auth")
     * 3. 验证Authorization请求头中的JWT令牌
     * 4. 令牌无效时返回401状态码
     * 5. 验证通过后设置用户ID属性并放行请求
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 类型转换获取HTTP协议对象
        final HttpServletResponse res = (HttpServletResponse) response;
        final HttpServletRequest req = (HttpServletRequest) request;

        // 设置响应字符编码
        res.setCharacterEncoding("UTF-8");
        // 获取请求路径
        final String path = req.getRequestURI();

        // 处理CORS预检请求
        if ("OPTIONS".equals(req.getMethod())) {
            res.setStatus(HttpServletResponse.SC_OK);
            chain.doFilter(req, res);
            return;
        }
        // 放行允许的路径("/auth")
        if (path.startsWith("/auth")) {
            chain.doFilter(req, res);
            return;
        }
        // 从请求头中获取令牌
        final String token = req.getHeader("Authorization");
        // 如果令牌不存在，直接返回401状态码
        if (token == null) {
            sendErrorResponse(res, ResponseCode.TOKEN_MISSING);
            return;
        }
        // 验证令牌是否在黑名单中
        if (redisTemplate.hasKey(token)) {
            sendErrorResponse(res, ResponseCode.TOKEN_EXPIRED);
            return;
        }
        // 验证令牌是否有效
        try {
            Map<String, Claim> tokenData = jwtUtil.verifyToken(token);
            String userId = tokenData.get("userId").asString();
            // 所有请求认证通过后，都会设置用户ID属性
            req.setAttribute("userId", userId);
            chain.doFilter(req, res);
        } catch (Exception e) {
            // 验证失败，返回401状态码
            sendErrorResponse(res, ResponseCode.TOKEN_INVALID);
        }
    }

    private void sendErrorResponse(HttpServletResponse response, ResponseCode responseCode) throws IOException{
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");
        CommonResponse<String> failResponse = CommonResponse.createForError(
                responseCode.getCode(),
                responseCode.getDescription());
        response.getWriter().write(objectMapper.writeValueAsString(failResponse));
    }

    @Override
    public void destroy() {}
}
