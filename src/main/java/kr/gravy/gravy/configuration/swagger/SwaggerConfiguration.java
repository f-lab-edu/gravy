package kr.gravy.gravy.configuration.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import kr.gravy.gravy.configuration.properties.AppProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SwaggerConfiguration {

    private final AppProperties appProperties;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Gravy API")
                        .version("1.0.0")
                        .description("Gravy 실시간 경매 API"))
                .servers(List.of(new Server().url(appProperties.baseUrl()).description("Gravy server")));
    }
}
