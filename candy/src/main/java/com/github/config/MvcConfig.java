package com.github.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Value("${upload.path}")
    private String uploadPath;

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img/**") // это типа путь на сервере, а не на жестком диске
                .addResourceLocations("file:///" + uploadPath + "/"); // а это уже на диске, сюда перенаправляются все запросы
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/"); //файлы будут искаться не где-то на компе, а в дереве проекта
    }
}