package kr.gravy.gravy.chatbot.configuration;

import kr.gravy.gravy.configuration.properties.ChatbotProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@RequiredArgsConstructor
public class WebClientConfiguration {

    private final ChatbotProperties chatbotProperties;

    @Bean
    public WebClient pythonServerWebClient() {
        return WebClient.builder()
                .baseUrl(chatbotProperties.pythonServerUrl())
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(1024 * 1024))
                .build();
    }
}
