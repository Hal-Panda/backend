package com.xh.b20220105backend.beanManage;

import com.xh.b20220105backend.config.HandlerInterceptorrConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class handlerIntercepetorConfigBean implements WebMvcConfigurer {
    @Autowired
    private HandlerInterceptorrConfig handlerInterceptorrConfig;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(handlerInterceptorrConfig).addPathPatterns("/**")
                .excludePathPatterns("/sellGoods/selectAll")
                .excludePathPatterns("/**/open/**")
                .excludePathPatterns("/categories/**")
                .excludePathPatterns("/classify/**")
                .excludePathPatterns("/show/mainGood/**");

//        registry.addInterceptor(handlerInterceptorrConfig).addPathPatterns("/no");

    }
}
