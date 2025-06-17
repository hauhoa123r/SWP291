package org.project.ai.intent.emotion;

import org.project.ai.chat.AIService;
import org.project.ai.intent.BasePromptHandler;
import org.project.ai.prompt.emotion.EmotionalSupportPrompt;
import org.project.model.request.ChatMessageRequest;
import org.springframework.stereotype.Component;

@Component
public class EmotionalSupportHandler extends BasePromptHandler<EmotionalSupportPrompt> {


    public EmotionalSupportHandler(AIService aiService, EmotionalSupportPrompt emotionalSupportPrompt) {
        super(aiService, emotionalSupportPrompt);
    }

    @Override
    protected String buildPrompt(ChatMessageRequest chatMessageRequest, String historyWithUser) {
        return prompt.buildPrompt(chatMessageRequest, historyWithUser);
    }

    @Override
    public String contextType() {
        return "emotional_support";
    }
}
