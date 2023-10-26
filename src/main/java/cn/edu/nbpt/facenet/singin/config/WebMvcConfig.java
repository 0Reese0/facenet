package cn.edu.nbpt.facenet.singin.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.io.File;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private TokenInterceptor tokenInterceptor;

    /**
     * 跨域
     *
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/v1/**")
                .allowCredentials(true)
                .allowedOriginPatterns("*")
                .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
                .maxAge(3600)
                .allowedHeaders("*");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/api/v1/users/result")
                .excludePathPatterns("/api/v1/users/login")
                .excludePathPatterns("/api/v1/users/reg")
                .excludePathPatterns("/js/*.js")
                .excludePathPatterns("/css/*.css")
                .excludePathPatterns("/*.ico")
                .excludePathPatterns("/")
                .excludePathPatterns("/*.html")
                .excludePathPatterns("/avatar/*")
                .excludePathPatterns("/models/*")
                .excludePathPatterns("/fonts/*");

    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //获取jar项目路径
        ApplicationHome h = new ApplicationHome(getClass());
        File jarFile = h.getSource();
        String filePath = jarFile.getParentFile().toString() + "/avatar/";
        registry.addResourceHandler("/avatar/**").addResourceLocations("file:///"+ filePath);
    }
}
