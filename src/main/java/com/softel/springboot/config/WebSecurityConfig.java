package com.softel.springboot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import com.softel.springboot.provider.CustomAuthenticationProvider;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
    private CustomAuthenticationProvider customAuthenticationProvider;
	
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    	auth.authenticationProvider(customAuthenticationProvider);
    }
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //所有请求均可访问
        http.authorizeRequests()
                .antMatchers("/", "/login", "rsa", "/login?error", "/css/**", "/js/**")
                .permitAll();

        //其余所有请求均需要权限
        http.csrf().disable()
        		.authorizeRequests()
                .anyRequest()
                .authenticated();

        //配置登录页面的表单 action 必须是 '/login', 用户名和密码的参数名必须是 'username' 和 'password'，
        http.formLogin()
                .loginPage("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .defaultSuccessUrl("/index")
                .failureUrl("/login?error")
                .permitAll();
        
        //退出登录
        http.logout()
        		.logoutUrl("/logout")
        		.logoutSuccessUrl("/login")
        		.permitAll();
        
    }
    
}
