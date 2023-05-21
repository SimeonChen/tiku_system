package com.tamguo.config.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.tamguo.web.interceptor.MemberInterceptor;


@Configuration
public class WebConfig implements WebMvcConfigurer {
	
	@Value("${file.storage.path}")
	private String fileStoragePath;
	@Value("${cookie.domian.name}")
	private String cookieDomianName;
	
	@Autowired
	private MemberInterceptor memberInterceptor;
	
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(memberInterceptor).addPathPatterns("/**").excludePathPatterns("/login.html","/register.html","/password/**","/captcha.jpg" , "/submitLogin.html" , "/miniLogin.html","/static/**","/sendFindPasswordSms","/subRegister","/checkMobile.html","/checkUsername.html");
	}
	
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
	   registry.addResourceHandler("/files/**").addResourceLocations("file:"+fileStoragePath);
    }
    
    @Bean
    public CookieSerializer defaultCookieSerializer(){
        DefaultCookieSerializer defaultCookieSerializer = new DefaultCookieSerializer();
        defaultCookieSerializer.setCookieName("sessionId");
        defaultCookieSerializer.setDomainName(cookieDomianName);
        defaultCookieSerializer.setCookiePath("/");
        return defaultCookieSerializer;
    }
    
}
