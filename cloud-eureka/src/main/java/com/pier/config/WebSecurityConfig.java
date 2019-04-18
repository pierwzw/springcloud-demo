package com.pier.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

/**
 * @auther zhongweiwu
 * @date 2019/4/16 19:33
 */
@EnableWebSecurity
class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //http.csrf().ignoringAntMatchers("/eureka/**");
        //http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER);
        //http.csrf().disable();
        http.authorizeRequests().anyRequest().authenticated().and().httpBasic().and().csrf().disable();
        //super.configure(http);
    }
}
