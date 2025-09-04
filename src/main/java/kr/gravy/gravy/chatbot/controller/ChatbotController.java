package kr.gravy.gravy.chatbot.controller;

import jakarta.validation.Valid;
import kr.gravy.gravy.chatbot.dto.ChatBotDto;
import kr.gravy.gravy.chatbot.service.ChatbotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatbotController {

    private final ChatbotService chatbotService;

    @PostMapping("/api/v1/chatbot")
    public ResponseEntity<ChatBotDto.Response> askQuestion(
            @Valid @RequestBody ChatBotDto.Request request) {
        ChatBotDto.Response response = chatbotService.askQuestion(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
