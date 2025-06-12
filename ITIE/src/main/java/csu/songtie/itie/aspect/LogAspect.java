package csu.songtie.itie.aspect;

import csu.songtie.itie.annotation.LogAction;
import csu.songtie.itie.domain.entity.UserLog;
import csu.songtie.itie.service.UserLogService;
import csu.songtie.itie.util.IPUtil;
import csu.songtie.itie.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 操作日志切面类
 * <p>通过AOP实现自动记录用户操作日志，配合@LogAction注解使用</p>
 */
@Aspect
@Component
@Slf4j
public class LogAspect {
    final private UserLogService userLogService;

    @Autowired
    public LogAspect(UserLogService userLogService, JwtUtil jwtUtil) {
        this.userLogService = userLogService;
    }

    /**
     * 定义日志切点（匹配被@LogAction注解标记的方法）
     */
    @Pointcut("@annotation(csu.songtie.itie.annotation.LogAction)")
    public void logActionPointcut() {}

    /**
     * 环绕通知 - 记录方法执行耗时
     * @param joinPoint 连接点对象
     * @return 目标方法执行结果
     * @throws Throwable 可能抛出的异常
     * 处理流程：
     * 1. 记录方法开始时间
     * 2. 执行目标方法
     * 3. 计算方法执行耗时
     * 4. 调用日志记录逻辑
     */
    @Around("logActionPointcut()")
    public Object recordLog(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        // 执行被拦截的方法
        Object result = joinPoint.proceed();

        long endTime = System.currentTimeMillis();
        // 记录日志
        log.info("Request processed in: {} ms", endTime - startTime);
        // 执行日志记录操作
        handleLog(joinPoint);

        return result;
    }

    /**
     * 处理日志记录的具体逻辑
     * @param joinPoint 连接点对象
     * 实现步骤：
     * 1. 获取HTTP请求对象
     * 2. 解析方法上的@LogAction注解
     * 3. 从请求属性获取用户ID
     * 4. 收集IP地址等请求信息
     * 5. 构建日志实体并持久化
     */
    private void handleLog(ProceedingJoinPoint joinPoint) {
        try {
            // 获取请求信息
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            // 从注解中获取操作类型
            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            LogAction logAction = methodSignature.getMethod().getAnnotation(LogAction.class);
            String actionDescription = logAction.value();

            // 获取用户ID
            Object userIdAttribute = request.getAttribute("userId");
            if (userIdAttribute == null) {
                // 如果 userId 不存在，说明是未认证的访问（如登录/注册）
                // 或者 JwtFilter 未能注入。此时不记录日志(也可记录匿名日志)。
                // 登录/注册的日志在 AuthService 中手动记录。
                log.warn("User ID not found in request attributes. Skipping log for action: {}", actionDescription);
                return;
            }
            String userId = String.valueOf(userIdAttribute);

            // 记录日志
            UserLog userLog = new UserLog();
            userLog.setUserId(userId);
            // 获取IP
            String ipAddress = IPUtil.getIp(request);
            userLog.setIp(ipAddress);

            userLog.setLocation("暂未实现");
            userLog.setAction(actionDescription);
            userLog.setCreatedAt(LocalDateTime.now());
            userLog.setUpdatedAt(LocalDateTime.now());

            // 可实现异步保存日志（推荐），避免影响主流程性能
            userLogService.saveUserLog(userLog);
        } catch (Exception e) {
            log.error("Error occurred while recording log: {}", e.getMessage(), e);
        }
    }
}
