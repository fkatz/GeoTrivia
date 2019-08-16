package javatp.security;

import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javatp.domain.Message;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    AuthenticationProvider authProvider;

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return (req, res, authException) -> {
            res.setStatus(HttpServletResponse.SC_FORBIDDEN);
            String innerExMsg = res.getHeader("JWTError");
            ObjectMapper mapper = new ObjectMapper();
            String msg = mapper.writeValueAsString(new Message("Authentication error",innerExMsg));
            res.setContentType("application/json");
            res.setContentLength(msg.length());
            res.getWriter().write(msg);
        };
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        JWTFilter customFilter = new JWTFilter(authProvider);
        //@formatter:off
        http
            .httpBasic().disable()
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
                .authorizeRequests()
                .antMatchers("/api/users/auth").permitAll()
                .antMatchers(HttpMethod.POST, "/api/users/user").permitAll()
                .anyRequest().authenticated()
            .and()
            .exceptionHandling()
            .authenticationEntryPoint(authenticationEntryPoint())
            .and().addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
            
        //@formatter:on
    }
}