package kr.gravy.gravy.configuration.properties;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({
        GravyProperties.class,
        CorsProperties.class,
        JwtProperties.class,
        MailProperties.class,
        ChatbotProperties.class
})
public class PropertiesConfiguration {
}
