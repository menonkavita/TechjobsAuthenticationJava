package org.launchcode.techjobsauth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebApplicationConfig implements WebMvcConfigurer {

    // create spring-managed object to allow app to access out filter
    @Bean
    public AuthenticationFilter authenticationFilter(){
        return new AuthenticationFilter();
    }

    // register filter with Spring container
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(authenticationFilter());
    }


}
