package com.kuangstudy.weixinpay.config;

import com.kuangstudy.weixinpay.common.handler.ApiTokenHandlerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author 徐柯
 * @Description 可以使用自定义类扩展MVC的功能
 * @Date 20:11 2021/4/18
 * @Param 
 * @return 
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * 纯路由注册
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
    }

    @Bean
    public ApiTokenHandlerInterceptor getApiTokenHandlerInterceptor() {
        return new ApiTokenHandlerInterceptor();
    }

   /**
    * @Author 徐柯
    * @Description 允许接口跨域访问请求
    * @Date 20:11 2021/4/18
    * @Param [registry]
    * @return void
    */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "DELETE", "PUT", "PATCH");
    }

    /**
     * @Author 徐柯
     * @Description 拦截器注册
     * @Date 20:11 2021/4/18
     * @Param [registry]
     * @return void
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 1：app接口处理
        registry.addInterceptor(getApiTokenHandlerInterceptor()).addPathPatterns("/api/**");
    }

}