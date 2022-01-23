package com.vinnikov.inbox.ru.insideJWTControlEx2022jan12.config.jwt;

import com.vinnikov.inbox.ru.insideJWTControlEx2022jan12.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
//import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
//import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import static org.springframework.util.StringUtils.hasText;

@Component
public class JwtFilter extends GenericFilterBean { // проверка: нет токена - нет разрешения на дальнейшие действия
    public static final String AUTHORIZATION = "Authorization";
    private JwtProvider jwtProvider;
    private CustomUserDetailsService customUserDetailsService;

    @Autowired // конструктор фильтра
    public JwtFilter(JwtProvider jwtProvider, CustomUserDetailsService customUserDetailsService) {
        this.jwtProvider = jwtProvider;
        this.customUserDetailsService = customUserDetailsService;
    }

    /*@Bean
    public EmbeddedServletContainerFactory servletContainer() {
        TomcatEmbeddedServletContainerFactory factory =
                new TomcatEmbeddedServletContainerFactory();
        return factory;
    }*/

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        System.out.println("-------doFilter void");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String token = getTokenFromRequest(request);
        System.out.println("------token->" + token);

        if (token != null && jwtProvider.validateToken(token)) {
            try {
                String userLogin = jwtProvider.getLoginFromToken(token);
                System.out.println("++++++++void doFilter userLogin:" + userLogin);
                UserDetails customUserDetails = customUserDetailsService.loadUserByUsername(userLogin);
                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(customUserDetails, null,
                                customUserDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (RuntimeException ignored) { }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        System.out.println("----типа действующий request:" + request);
        String bearer = request.getHeader(AUTHORIZATION);
        if (hasText(bearer) && bearer.startsWith("Bearer ")) {
            System.out.println("------JwtFilter getTokenFromRequest bearer.substring(7):" + bearer.substring(7));
            return bearer.substring(7);
        }
        return null;
    }
}
