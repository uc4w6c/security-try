package com.example.demo;

import com.example.demo.service.AccountUserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final AccountUserDetailService accountUserDetailService;

    public WebSecurityConfig(AccountUserDetailService accountUserDetailService) {
        this.accountUserDetailService = accountUserDetailService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(accountUserDetailService).passwordEncoder(passwordEncoder());
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/signup").permitAll()
                .antMatchers("/account_activation/**").permitAll()
                .antMatchers("/reissue/create").permitAll()
                .antMatchers("/reissue/resetpassword/**").permitAll()
                .anyRequest().authenticated()
                .and()
            // csrfのテストのためにX-Frame-Optionsを許可する
            .headers()
                .frameOptions().disable()
                .and()
            .formLogin()
                .loginPage("/login")
                .usernameParameter("email")
                .passwordParameter("password")
                .failureUrl("/login")
                .defaultSuccessUrl("/top", true)
                .permitAll();
        // securityテストを行うためにcsrfを無効化
        http.csrf().disable();
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .antMatchers("/webjars/**",
                                "/css/**",
                                "/font/**",
                                "/img/**",
                                "/js/**",
                                "/lib/**",
                                "/svg/**");
    }
}
