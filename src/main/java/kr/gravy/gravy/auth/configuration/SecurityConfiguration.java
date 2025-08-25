package kr.gravy.gravy.auth.configuration;

import kr.gravy.gravy.auth.jwt.JWTAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyUtils;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JWTAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // JWT 기반이므로 서버 세션을 만들지 않음
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // CSRF는 세션/폼 기반일 때 주로 사용되므로 비활성화
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                // 공개 API는 인증 없이 접근 허용, 그 외에는 BASIC 이상 권한 필요
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .requestMatchers("/", "/ping", "/error", "/favicon.ico").permitAll()
                        // Swagger UI 및 에러 문서 접근 허용
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**",
                                "/error/**"
                        ).permitAll()
                        // 회원가입, 로그인 API 접근 허용
                        .requestMatchers(
                                "/api/v1/email-verifications",
                                "/api/v1/email-verifications/status",
                                "/api/v1/users/email/*/availability",
                                "/api/v1/users",
                                "/api/v1/auth/tokens",
                                "/api/v1/auth/tokens/reissue"
                        ).permitAll()
                        .anyRequest().hasAnyRole("BASIC")
                )
                // 폼 로그인/베이직 인증은 사용하지 않음
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                // JWT 필터를 UsernamePasswordAuthenticationFilter 앞에 두어 토큰을 먼저 검증
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration cors = new CorsConfiguration();
        cors.setAllowedOrigins(List.of("https://gravy.kr"));
        cors.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        cors.setAllowedHeaders(List.of("*"));
        cors.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", cors);
        return source;
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        Map<String, List<String>> hierarchy = new HashMap<>();
        hierarchy.put("ROLE_VIP", List.of("ROLE_TRUSTED"));
        hierarchy.put("ROLE_TRUSTED", List.of("ROLE_VERIFIED"));
        hierarchy.put("ROLE_VERIFIED", List.of("ROLE_BASIC"));

        String hierarchyStr = RoleHierarchyUtils.roleHierarchyFromMap(hierarchy);
        return RoleHierarchyImpl.fromHierarchy(hierarchyStr);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
