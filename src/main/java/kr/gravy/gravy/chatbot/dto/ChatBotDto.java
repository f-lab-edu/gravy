package kr.gravy.gravy.chatbot.dto;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.constraints.NotBlank;

public class ChatBotDto {

    public record Request(@NotBlank(message = "질문은 필수값입니다.") String question) {
    }

    public record Response(JsonNode data) {
    }
}
