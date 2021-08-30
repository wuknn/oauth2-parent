package com.oauth2.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.sql.DataSource;

/**
 * 模拟本地用户配置
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * 表单登录
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //禁用跨站伪造
        http.csrf().disable()
                // 1.loginpage 为自定义登录页面url,
                // loginProcessingUrl为表单登录处理的URL，
                // 不设置则表单提交url为 /login。UsernamePasswordAuthenticationFilter中会它拦截/login的post请求的url
                .formLogin();
//                .loginPage("http://localhost:8080/loginpage");

    }

    /**
     * 内存中虚拟用户和角色
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                // 设置用户详情信息加载实现
                .userDetailsService(userDetailsService)
                // 设置的密码加密器
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
