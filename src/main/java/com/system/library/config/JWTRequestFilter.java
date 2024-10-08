package com.system.library.config;

import com.system.library.exception.InvalidTokenException;
import com.system.library.service.TokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JWTRequestFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestTokenHeader = request.getHeader("Authorization");
        String locale = request.getHeader("X-Locale");
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            try {
                String jwtToken = requestTokenHeader.substring(7);
                Claims claims = tokenService.getAllClaimsFromToken(jwtToken);
                String issuer = claims.getIssuer();
                if (issuer.equals("web")) {
                    List<String> roles = tokenService.extractRoles(jwtToken);
                    List<GrantedAuthority> authorities = roles.stream()
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());
                    SecurityContextHolder.getContext().setAuthentication(new CustomAuthenticationToken(jwtToken, authorities));
                } else {
                    SecurityContextHolder.getContext().setAuthentication(new CustomAuthenticationToken(jwtToken, null));
                }
            } catch (ExpiredJwtException ex) {
                logger.error("Token Expired : " + ex.getMessage());
                throw new InvalidTokenException();
            } catch (Exception ex) {
                logger.error("Token Invalid : " + ex.getMessage());
                throw new InvalidTokenException();
            }
        }
        if (request.getMethod().equals("OPTIONS")) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            filterChain.doFilter(request, response);
        }
    }
}
