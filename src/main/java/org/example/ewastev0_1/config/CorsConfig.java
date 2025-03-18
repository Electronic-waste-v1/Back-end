package org.example.ewastev0_1.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // Change from "/api/**" to "/**"
                .allowedOrigins("http://localhost:5173")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // Add OPTIONS
                .allowedHeaders("*")  // Allow all headers
                .exposedHeaders("Authorization")  // Expose Authorization header
                .allowCredentials(true);
    }
}