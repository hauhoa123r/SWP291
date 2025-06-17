package org.project.api;

import jakarta.servlet.http.HttpSession;
import org.project.ai.context.ChatMessage;
import org.project.ai.context.ChatSession;
import org.project.ai.context.ChatSessionManager;
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
    private final ChatSessionManager chatSessionManager;
    private final ChatSession chatSession;
    public ChatBoxAPI(ChatRouterService chatRouterService,
                      LanguageService languageService,
                      ChatSessionManager chatSessionManager,
                      ChatSession chatSession) {
        this.chatRouterService = chatRouterService;
        this.languageService = languageService;
        this.chatSessionManager = chatSessionManager;
        this.chatSession = chatSession;
    }

    @PostMapping
    public ResponseEntity<String> handleMessageUser(@RequestBody ChatMessageRequest chatMessageRequest,
    HttpSession session) {

        List<ChatMessage> chatHisroty = chatSessionManager.getChatSessionWithUser(session);

        String historyWithUser = chatSession.getChatSessionWithUser(chatHisroty);

        chatMessageRequest.setLanguage(languageService.getLanguageFromChatUser(chatMessageRequest.getUserMessage()));
        String aiResponse = chatRouterService.convertToUserMessage(chatMessageRequest, historyWithUser);

        chatSessionManager.setChatSessionWithUser(session, chatMessageRequest, aiResponse);

        return ResponseEntity.ok(aiResponse);
    }
}
