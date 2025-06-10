package org.project.ai.intent;

import org.project.ai.chat.AIService;
import org.project.ai.prompt.IntentPrompt;
import org.project.entity.PatientEntity;
import org.project.enums.Intent;
import org.project.model.request.ChatMessageRequest;
import org.springframework.stereotype.Service;

@Service
public class ChatRouterService {

    private final AIService aiService;
    private final IntentPrompt intentPrompt;
    private final IntentHandlerRegistry handlerRegistry;

    public ChatRouterService(AIService aiService,
                             IntentPrompt intentPrompt,
                             IntentHandlerRegistry handlerRegistry) {
        this.aiService = aiService;
        this.intentPrompt = intentPrompt;
        this.handlerRegistry = handlerRegistry;
    }

    private String findContextType(String userMessage) {
        String prompt = intentPrompt.buildPrompt(userMessage);
        return aiService.complete(prompt).toLowerCase();
    }

    public String convertToUserMessage(ChatMessageRequest chatMessageRequest) {
        chatMessageRequest.setPrompt(findContextType(chatMessageRequest.getUserMessage()));
        if (Intent.fromKey(chatMessageRequest.getPrompt()).isEmpty() || ("unknown").equals(chatMessageRequest.getPrompt())) {

        }

        return handlerRegistry.handle(chatMessageRequest);
    }
}
