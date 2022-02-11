package com.xh.b20220105backend.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xh.b20220105backend.entity.response.resultMap;
import com.xh.b20220105backend.exception.loginException;
import com.xh.b20220105backend.util.JwtUtil;
import com.xh.b20220105backend.util.jsonUtil;
import com.xh.b20220105backend.util.resultMapUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class HandlerInterceptorrConfig implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("进入了");
        String str = "";
        try {
            String token = request.getHeader("token");
            if (request.getHeader("token").equals(null)) {
                throw new loginException("未登录");
            }
            if (token.equals("null")) {
                throw new loginException("未登录");
            }
            JwtUtil.verifyToken(token);
            return true;
        } catch (loginException e) {
            str = jsonUtil.toJson(resultMapUtil.backResultMap(e.getMessage(), "", -100));
            log.warn(e.getMessage());
        } catch (TokenExpiredException e) {
            str = jsonUtil.toJson(resultMapUtil.backResultMap("登录过期", "", -101));
            log.warn(e.getMessage());
        } catch (SignatureVerificationException e) {
            str = jsonUtil.toJson(resultMapUtil.backResultMap("身份验证失败，请重新尝试", "", -102));
            log.warn(e.getMessage());
        } catch (AlgorithmMismatchException e) {
            str = jsonUtil.toJson(resultMapUtil.backResultMap("身份验证失败，请重新尝试", "", -103));
            log.warn(e.getMessage());
        } catch (InvalidClaimException e) {
            str = jsonUtil.toJson(resultMapUtil.backResultMap("身份验证失败，请重新尝试", "", -104));
            log.warn(e.getMessage());
        } catch (NullPointerException e) {
            str = jsonUtil.toJson(resultMapUtil.backResultMap(e.getMessage(), "", -104));
        } catch (Exception e) {
            str = jsonUtil.toJson(resultMapUtil.backResultMap("其他错误:" + e.getMessage(), "", -105));
            log.warn(e.getMessage());
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().print(str);
        return false;
    }
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("出去了");
    }
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("完成一次进出");
    }
}
