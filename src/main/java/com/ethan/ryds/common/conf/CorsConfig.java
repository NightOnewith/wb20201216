package com.ethan.ryds.common.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 处理AJAX请求跨域(方式一)
 * Created by ASUS on 2020/5/14.
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Value("${file.staticAccessPath}")
    private String staticAccessPath;

    @Value("${file.uploadFolder}")
    private String uploadFolder;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .maxAge(3600)
                .allowCredentials(true);
    }

    /**
     * 设置虚拟路径，访问绝对路径下资源
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler(staticAccessPath).addResourceLocations("file:" + uploadFolder);
    }
}
