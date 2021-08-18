package org.online.shop.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

 @Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();
        http.cors();

        http.authorizeRequests()
                .antMatchers("/web/login-form","/webjars/**","/web/login",
                        "/css/**","/js/**","/web/main-page","/web/user/add","/web/user/save").permitAll();

        http.authorizeRequests().anyRequest().authenticated();

        http.formLogin().loginPage("/web/login-form")
                .loginProcessingUrl("/web/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .defaultSuccessUrl("/web/main-page")
                .failureForwardUrl("/web/login-form");

        http.logout().logoutUrl("/web/logout");
        http.exceptionHandling().accessDeniedPage("/web/login-form");
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void globalConfiguration(AuthenticationManagerBuilder auth, PasswordEncoder passwordEncoder)
            throws Exception{

        auth.jdbcAuthentication().dataSource(this.dataSource).passwordEncoder(passwordEncoder);
//        System.out.println(passwordEncoder.encode("1234"));
    }
}
