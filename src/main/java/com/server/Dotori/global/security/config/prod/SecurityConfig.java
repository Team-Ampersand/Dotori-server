package com.server.Dotori.global.security.config.prod;

import com.server.Dotori.global.security.exception.ExceptionHandlerFilter;
import com.server.Dotori.global.security.exception.ExceptionHandlerFilterConfig;
import com.server.Dotori.global.security.handler.CustomAccessDeniedHandler;
import com.server.Dotori.global.security.handler.CustomAuthenticationEntryPointHandler;
import com.server.Dotori.global.security.jwt.JwtTokenFilter;
import com.server.Dotori.global.security.jwt.JwtTokenFilterConfigurer;
import com.server.Dotori.global.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Profile(value = {"prod"})
@RequiredArgsConstructor
@EnableWebSecurity(debug = false)
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;
    private final ExceptionHandlerFilter exceptionHandlerFilter;
    private final JwtTokenFilter jwtTokenFilter;

    @Override // 접근 가능
    public void configure(WebSecurity web) throws Exception {

        web.ignoring().antMatchers("/v2/api-docs")//
                .antMatchers("/swagger-resources/**")//
                .antMatchers("/swagger-ui.html")//
                .antMatchers("/configuration/**")//
                .antMatchers("/webjars/**")//
                .antMatchers("/public")

                // Un-secure H2 Database (for testing purposes, H2 console shouldn't be unprotected in production)
                .and()
                .ignoring()
                .antMatchers("/h2-console/**/**");;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Disable CSRF (cross site request forgery)
        http
                .cors().and()
                .csrf().disable()
                .httpBasic().disable();

        // No session will be created or used by spring security
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // Entry points
        http.authorizeRequests() // 권한 처리를 할 메서드

                // 회원 관리
                .antMatchers("/v1/signup").permitAll()
                .antMatchers("/v1/signin").permitAll()
                .antMatchers("/v1/signup/email").permitAll()
                .antMatchers("/v1/signup/email/check").permitAll()
                .antMatchers("/v1/health-check").permitAll()
                .antMatchers("/v1/members/password/email").permitAll()
                .antMatchers("/v1/members/password/email/check").permitAll()
                .antMatchers("/v1/members/gender").permitAll()
                .antMatchers("/v1/refresh").permitAll()

                // 권한 별 url 접근
                .antMatchers("/v1/admin/**").hasRole("ADMIN")
                .antMatchers("/v1/councillor/**").hasRole("COUNCILLOR")
                .antMatchers("/v1/member/**").hasRole("MEMBER")
                .antMatchers("/v1/developer/**").hasRole("DEVELOPER")

                .antMatchers("/exception/**").permitAll()
                .antMatchers("/h2-console").permitAll()
                .antMatchers("/h2-console/**/**").permitAll()

                // Disallow everything else..
                .anyRequest().authenticated();

        http.exceptionHandling()
                .accessDeniedHandler(new CustomAccessDeniedHandler())
                .authenticationEntryPoint(new CustomAuthenticationEntryPointHandler());

        http.apply(new JwtTokenFilterConfigurer(jwtTokenProvider));
        http.apply(new ExceptionHandlerFilterConfig(exceptionHandlerFilter));
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManagerBean();
    }
}
