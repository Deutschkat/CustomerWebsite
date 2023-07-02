package com.example.CustomerWebsite.security;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/webjars/**", "/css/**", "/login/**", "/images/**", "/register").permitAll()
                .antMatchers("/customer-view").hasRole("USER_ROLE")
                .anyRequest().hasRole("ADMIN_ROLE")
                .and()
                .formLogin();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("user").password("user").roles("USER_ROLE")
                .and()
                .withUser("admin").password("admin").roles("USER_ROLE", "ADMIN_ROLE");
    }
}