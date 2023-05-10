package com.adm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    
    // @Autowired
    // private UserService userService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public AuthenticationManager customAuthenticationManager() throws Exception {
        return authenticationManagerBean();
    }

    
    // @Bean
    // public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    //     JwtFilter jwtFilter = new JwtFilter(http.getSharedObject(AuthenticationManager.class));

    //     http
    //         .csrf().disable()
    //         .authorizeRequests()
    //             .antMatchers("/api/boards/**").hasAnyRole("USER", "ADMIN")
    //             .antMatchers("/api/users/**").hasRole("ADMIN")
    //             .antMatchers("/api/users/**").permitAll()
    //             .anyRequest().authenticated()
    //         .and()
    //         .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
    //         .httpBasic()
    //         .and()
    //         .authenticationProvider(authenticationProvider())
    //         .logout()
    //             .logoutUrl("/api/logout")
    //             .logoutSuccessHandler((request, response, authentication) -> {
    //                 response.setStatus(HttpServletResponse.SC_OK);
    //             })
    //             .deleteCookies("JSESSIONID")
    //             .invalidateHttpSession(true);

    //     return http.build();

    // }
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtTokenUtil, userDetailsService);
        jwtAuthenticationFilter.setAuthenticationManager(customAuthenticationManager());

        http
            .csrf().disable()
            .authorizeRequests()
                // JWT 인증을 사용하는 엔드포인트
                .antMatchers("/api/jwt-secured/**").authenticated()
                .antMatchers("/api/boards/**").hasAnyRole("USER", "ADMIN")
                .antMatchers("/api/users/**").hasRole("ADMIN")
                .antMatchers("/api/users/**").permitAll()
                .anyRequest().authenticated()
            .and()
            .formLogin()
                .loginProcessingUrl("/api/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .successHandler(...)
                .failureHandler(...)
            .and()
            .logout()
                .logoutUrl("/api/logout")
                .logoutSuccessHandler(...)
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
            .and()
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // @Bean
    // public DaoAuthenticationProvider authenticationProvider() {
    //     DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    //     provider.setUserDetailsService(userService);
    //     provider.setPasswordEncoder(passwordEncoder());
    //     return provider;
    // }

}
