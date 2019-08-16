package javatp.security;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javatp.entities.User;
import javatp.util.Message;

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
                Authentication auth = new UsernamePasswordAuthenticationToken("pepe", "", list);
                SecurityContextHolder.getContext().setAuthentication(auth);
            } else {
                SecurityContextHolder.getContext().setAuthentication(null);
            }
        } catch (Exception e) {
            ObjectMapper mapper = new ObjectMapper();
            String responseJSON = mapper.writeValueAsString(new Message("Authentication error",e.getMessage()));
            ((HttpServletResponse)res).setStatus(401);
            res.setContentType("application/json");
            res.setContentLength(responseJSON.length());
            res.getWriter().write(responseJSON);
        } finally {
            filterChain.doFilter(req, res);
        }
    }
}