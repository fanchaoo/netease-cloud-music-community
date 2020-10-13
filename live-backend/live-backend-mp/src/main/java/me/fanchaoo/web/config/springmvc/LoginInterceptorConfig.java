package me.fanchaoo.web.config.springmvc;

import me.fanchaoo.web.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@Configuration
public class LoginInterceptorConfig extends WebMvcConfigurerAdapter {

    @Bean
    LoginInterceptor loginInterceptor() {
        return new LoginInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        LoginInterceptor jwtTokenInterceptor = loginInterceptor();
        registry.addInterceptor(jwtTokenInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/login/login");

        super.addInterceptors(registry);
    }

}
