package org.project.api;

import jakarta.servlet.http.HttpSession;
import org.project.ai.context.ChatMessage;
import org.project.ai.intent.ChatRouterService;
import org.project.model.request.ChatMessageRequest;
import org.project.service.LanguageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/chatbox")
public class ChatBoxAPI {

    private final ChatRouterService chatRouterService;
    private final LanguageService languageService;
    private static final int MAX_HISTORY_SIZE = 10;
    public ChatBoxAPI(ChatRouterService chatRouterService, LanguageService languageService) {
        this.chatRouterService = chatRouterService;
        this.languageService = languageService;
    }

    @PostMapping
    public ResponseEntity<String> handleMessageUser(@RequestBody ChatMessageRequest chatMessageRequest,
    HttpSession session) {
        @SuppressWarnings("unchecked")
        List<ChatMessage> chatHisroty = (List<ChatMessage>) session.getAttribute("chatHistory");
        if(chatHisroty == null) {
            chatHisroty = new ArrayList<>();
        }

        chatHisroty.add(new ChatMessage("user", chatMessageRequest.getUserMessage()));
        chatMessageRequest.setLanguage(languageService.getLanguageFromChatUser(chatMessageRequest.getUserMessage()));
        String aiResponse = chatRouterService.convertToUserMessage(chatMessageRequest);

        chatHisroty.add(new ChatMessage("assistant", aiResponse));

        if(chatHisroty.size() > MAX_HISTORY_SIZE) {
            chatHisroty = chatHisroty.subList(chatHisroty.size() - MAX_HISTORY_SIZE, chatHisroty.size());
        }
        session.setAttribute("chatHistory", chatHisroty);

        return ResponseEntity.ok(aiResponse);
    }
}
