package kr.gravy.gravy.chatbot.service;

import com.fasterxml.jackson.databind.JsonNode;
import kr.gravy.gravy.chatbot.dto.ChatBotDto;
import kr.gravy.gravy.common.exception.GravyException;
import kr.gravy.gravy.common.exception.Status;
import kr.gravy.gravy.configuration.properties.ChatbotProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.time.Duration;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatbotService {

    private final WebClient pythonServerWebClient;
    private final ChatbotProperties chatbotProperties;

    public ChatBotDto.Response askQuestion(final ChatBotDto.Request request) {
        try {
            Map<String, String> pythonRequest = Map.of("question", request.question());

            JsonNode response = pythonServerWebClient
                    .post()
                    .uri("/rag/answer")
                    .bodyValue(pythonRequest)
                    .retrieve()
                    .bodyToMono(JsonNode.class)
                    .timeout(Duration.ofSeconds(chatbotProperties.timeoutSeconds()))
                    .onErrorMap(WebClientResponseException.class, ex -> {
                        log.error("Python RAG 서버 응답 오류: status={}, body={}",
                                ex.getStatusCode(), ex.getResponseBodyAsString());
                        return new GravyException(Status.EXTERNAL_SERVER_ERROR);
                    })
                    .onErrorMap(Exception.class, ex -> {
                        log.error("Python RAG 서버 통신 실패: {}", ex.getMessage(), ex);
                        return new GravyException(Status.EXTERNAL_SERVER_ERROR);
                    })
                    .block();

            return new ChatBotDto.Response(response);

        } catch (GravyException e) {
            throw e;
        } catch (Exception e) {
            throw new GravyException(Status.INTERNAL_SERVER_ERROR);
        }
    }
}
