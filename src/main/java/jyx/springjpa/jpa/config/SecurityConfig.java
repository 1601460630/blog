package jyx.springjpa.jpa.config;

import jyx.springjpa.jpa.config.encoder.MyPasswordEncoder;
import jyx.springjpa.jpa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.Key;

/**
 * @author jyx
 * @date 2019/3/20
 */

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    public static final String KEY = "waylau.com";

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public PasswordEncoder passwordEncoder() {
        // 使用 BCrypt 加密
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();

        authenticationProvider.setUserDetailsService(userDetailsService);
        // 设置密码加密方式
        authenticationProvider.setPasswordEncoder(passwordEncoder);

        return authenticationProvider;
    }

    /**
     * 自定义配置
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                //都可以访问
                .antMatchers("/css/**", "/js/**", "/fonts/**", "/index").permitAll()
                //需要相应的角色才能访问
                .antMatchers("/users/**").hasRole("ADMIN")
                .and()
                .formLogin()
                //基于Form表单登录验证
                .loginPage("/login").failureUrl("/login-error")
                //启用rememberMe
                .and().rememberMe().key(KEY)
                //处理异常，拒接访问就重定向到403页面
                .and().exceptionHandling().accessDeniedPage("/403");
        //禁用H2控制台的CSRF防护
        http.csrf().ignoringAntMatchers("/h2-console/**");
        //允许来自同一来源的H2控制台的请求
        http.headers().frameOptions().sameOrigin();

    }

    /**
     * 认证信息管理
     *
     * @param auth
     * @throws Exception
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(userDetailsService);
        auth.authenticationProvider(authenticationProvider());
    }
}
