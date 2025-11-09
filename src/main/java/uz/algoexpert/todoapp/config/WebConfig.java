// src/main/java/uz/algoexpert/todoapp/config/WebConfig.java
package uz.algoexpert.todoapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        // ✅ Trailing slashni qo'llab-quvvatlash (GET /api/tasks/ ishlaydi)
        configurer.setUseTrailingSlashMatch(true);
    }
}