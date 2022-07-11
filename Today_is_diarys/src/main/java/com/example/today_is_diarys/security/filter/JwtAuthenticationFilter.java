package com.example.today_is_diarys.security.filter;

import com.example.today_is_diarys.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //헤더에서 filter를 받아온다.
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
        //유효한 토큰인지 확인한다.
        if(token != null && jwtTokenProvider.validateToken(token)){
            //토큰이 유효하면 토큰으로부터 유저 정보를 가져옵니다.
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            //SpringContext 에 Authentication 객체를 저장합니다.
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request,response);
    }
}
