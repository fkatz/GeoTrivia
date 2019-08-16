package javatp.security;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javatp.domain.User;

public class JWTFilter extends GenericFilterBean {
    private AuthenticationProvider authManager;

    public JWTFilter(AuthenticationProvider authManager) {
        this.authManager = authManager;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
            throws IOException, ServletException {
        try {
            User user = authManager.authenticateToken(((HttpServletRequest) req).getHeader("Authorization"));
            if (user != null) {
                ArrayList<SimpleGrantedAuthority> list = new ArrayList<SimpleGrantedAuthority>();
                list.add(new SimpleGrantedAuthority("ROLE_USER"));
                Authentication auth = new UsernamePasswordAuthenticationToken(user, "", list);
                SecurityContextHolder.getContext().setAuthentication(auth);
            } else {
                SecurityContextHolder.getContext().setAuthentication(null);
            }
        } catch (Exception e) {
            ((HttpServletResponse)res).setHeader("JWTError", e.getMessage());
        }
        finally {
            filterChain.doFilter(req, res);
        }
    }
}