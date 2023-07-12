package com.example.CustomerWebsite.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import static org.hibernate.criterion.Restrictions.and;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserDetailsService userDetailsService;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/webjars/**", "/css/**", "/login/**", "/images/**", "/register").permitAll()
                .antMatchers("/customer-view").hasRole("USER")
                .antMatchers("/cars").hasRole("ADMIN")
                .antMatchers("/car/edit/{id}").hasRole("ADMIN")
                .antMatchers("/car/delete/{id}").hasRole("ADMIN")
                .antMatchers("/new_car").hasRole("ADMIN")
                .antMatchers("/customer-list").hasRole("ADMIN")
                .antMatchers("/new").hasRole("ADMIN")
                .antMatchers("/delete/{id}").hasRole("ADMIN")
                .antMatchers("/edit/{id}").hasRole("ADMIN")
                .antMatchers("/update/{id}").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .defaultSuccessUrl("/default", true)
                .and()
                .logout()
                //.logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID");

    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder)
                .and()
                .inMemoryAuthentication()
                .passwordEncoder(bCryptPasswordEncoder)
                .withUser("user").password(bCryptPasswordEncoder.encode("user")).roles("USER")
                .and()
                .withUser("admin").password(bCryptPasswordEncoder.encode("admin")).roles("USER", "ADMIN");
    }




}