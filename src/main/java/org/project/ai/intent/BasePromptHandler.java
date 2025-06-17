package org.project.ai.intent;

import org.project.ai.chat.AIService;
import org.project.model.request.ChatMessageRequest;

public abstract class BasePromptHandler<TPrompt> implements IntentHandler{
    protected final AIService aiService;
    protected final TPrompt prompt;


    protected BasePromptHandler(AIService aiService, TPrompt prompt) {
        this.aiService = aiService;
        this.prompt = prompt;
    }

    protected abstract String buildPrompt(ChatMessageRequest chatMessageRequest, String historyWithUser);

    @Override
    public String handle(ChatMessageRequest chatMessageRequest, String historyWithUser) {
        return aiService.complete(buildPrompt(chatMessageRequest, historyWithUser));
    }
}
