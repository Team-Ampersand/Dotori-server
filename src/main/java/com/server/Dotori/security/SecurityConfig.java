package com.server.Dotori.security;

import com.server.Dotori.security.jwt.JwtTokenFilter;
import com.server.Dotori.security.jwt.JwtTokenFilterConfigurer;
import com.server.Dotori.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity(debug = true)
@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;

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
        http.csrf().disable();

        // No session will be created or used by spring security
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // Entry points
        http.authorizeRequests() // 권한 처리를 할 메서드

                // 회원 관리
//                .antMatchers("/v1/signin").permitAll()
//                .antMatchers("/v1/signup").permitAll()
//                .antMatchers("/v1/refreshtoken").permitAll()
//                .antMatchers("/v1/auth").permitAll()
//                .antMatchers("/v1/auth/check").permitAll()
//                .antMatchers("/v1/change/password").permitAll()
//                .antMatchers("/v1/auth/password").permitAll()
//                .antMatchers("/v1/member/logout").authenticated()
//                .antMatchers("/v1/member/delete").authenticated()

                // 권한 별 url 접근
//                .antMatchers("/v1/admin/**").hasRole("ADMIN")
//                .antMatchers("/v1/councillor/**").hasRole("COUNCILLOR")
//                .antMatchers("/v1/member/**").hasRole("MEMBER")
//                .antMatchers("/v1/developer/**").hasRole("DEVELOPER")

                // exception 메세지, h2-console 모두 접근 가능
                .antMatchers("/**").permitAll()
                .antMatchers("/").permitAll()
                .antMatchers("/exception/**").permitAll()
                .antMatchers("/h2-console").permitAll()
                .antMatchers("/h2-console/**/**").permitAll()

                // Disallow everything else..
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new JwtTokenFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class); // 모든 기능이 동작하기전 권한을 체크해줌


        // If a user try to access a resource without having enough permissions
        http.exceptionHandling().accessDeniedPage("/login");
    }

    //===========================================================//

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
