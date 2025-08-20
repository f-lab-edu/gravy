package kr.gravy.gravy.auth.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.gravy.gravy.common.exception.GravyException;
import kr.gravy.gravy.common.exception.Status;
import kr.gravy.gravy.auth.model.Grade;
import kr.gravy.gravy.auth.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
    private final UserMapper userMapper;


    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws ServletException, IOException {

        // 이미 다른 필터에서 인증이 설정되어 있으면 그대로 진행한다.
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            chain.doFilter(req, res);
            return;
        }

        String token = extractAccessTokenFromCookie(req);
        if (token == null || token.isBlank()) {
            // 토큰이 없으면 "인증 없음" 상태로 다음 단계로 넘긴다.
            // (이후 인가 규칙에서 401/403 판단)
            chain.doFilter(req, res);
            return;
        }

        try {
            UUID userPublicId = jwtUtil.validateAndGetSubjectAsUUID(token);
            Grade grade = userMapper.getUserGradeByPublicId(userPublicId)
                    .orElseThrow(() -> new GravyException(Status.USER_NOT_FOUND));

            List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + grade.name()));

            //  인증 객체 생성 후 컨텍스트 저장한다.
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userPublicId, null, authorities);
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (Exception e) {
            // 토큰 불일치/만료/위조 등: 인증 실패 → 인증 없음 상태로 진행한다.
            SecurityContextHolder.clearContext();
        }

        chain.doFilter(req, res);
    }

    private String extractAccessTokenFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if ("access_token".equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }
}
