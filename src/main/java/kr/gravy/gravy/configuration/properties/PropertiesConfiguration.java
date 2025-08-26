package kr.gravy.gravy.configuration.properties;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 애플리케이션 전체의 Configuration Properties를 등록하는 설정 클래스
 */
@Configuration
@EnableConfigurationProperties({
        AppProperties.class,
        CorsProperties.class,
        JwtProperties.class,
        MailProperties.class,
        ServerProperties.class
})
public class PropertiesConfiguration {
}
