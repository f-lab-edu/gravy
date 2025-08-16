package kr.gravy.gravy.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JWTAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // REST API 특성상 CSRF 비활성화
                .csrf(csrf -> csrf.disable())
                // 세션 사용 안 함 (JWT 등 토큰 기반 인증 대비)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                // 권한 설정
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers(PUBLIC_URI).permitAll()
//                        .anyRequest().authenticated()
//                )
                // 폼 로그인, 기본 로그인 UI 비활성화
                .httpBasic(Customizer.withDefaults()) // 필요 시 Basic Auth 사용
                .formLogin(form -> form.disable())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return (webSecurity) ->
//                webSecurity.ignoring()
//                        .requestMatchers(PUBLIC_URI);
//    }


}
