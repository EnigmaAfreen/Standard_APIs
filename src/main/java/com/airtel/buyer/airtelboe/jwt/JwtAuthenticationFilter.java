package com.airtel.buyer.airtelboe.jwt;


import com.airtel.buyer.airtelboe.user.UserDetailsImpl;
import com.airtel.buyer.airtelboe.user.UserDetailsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String jwt = this.getJwtFromRequest(request);

//        log.info("JwtAuthenticationFilter :: method :: doFilterInternal :: jwt :: " + jwt);
        log.info("JwtAuthenticationFilter :: method :: doFilterInternal");

        if (!StringUtils.isBlank(jwt)) {
            String username = this.jwtService.extractUsername(jwt);

            if (!StringUtils.isBlank(username) && SecurityContextHolder.getContext().getAuthentication() == null) {

                UserDetailsImpl userDetails = this.userDetailsService.loadUserByUsername(username);

                if (this.jwtService.isTokenValid(jwt, userDetails)) {
                    log.info("logged in User :: " + userDetails.getUsername() + " :: with IP :: " + request.getHeader("X-Forwarded-For") + "," + request.getRemoteAddr() + " :: performed Activity :: " + request.getRequestURI().substring(request.getRequestURI().lastIndexOf("/") + 1) + " :: timestamp :: " + LocalDateTime.now());
                    log.info("JwtAuthenticationFilter :: method :: doFilterInternal :: token is valid");

                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                }
            }
        }
        filterChain.doFilter(request, response);

    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (!StringUtils.isBlank(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

}
