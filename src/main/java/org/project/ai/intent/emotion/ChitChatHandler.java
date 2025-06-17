package org.project.ai.intent.emotion;

import org.project.ai.chat.AIService;
import org.project.ai.intent.BasePromptHandler;
import org.project.ai.prompt.emotion.ChitChatPrompt;
import org.project.model.request.ChatMessageRequest;
import org.springframework.stereotype.Component;

@Component
public class ChitChatHandler extends BasePromptHandler<ChitChatPrompt> {

    public ChitChatHandler(AIService aiService, ChitChatPrompt chitChatPrompt) {
        super(aiService, chitChatPrompt);
    }

    @Override
    protected String buildPrompt(ChatMessageRequest chatMessageRequest, String historyWithUser) {
        return prompt.buildPrompt(chatMessageRequest, historyWithUser);
    }

    @Override
    public String contextType() {
        return "chitchat";
    }
}
