package org.project.ai.intent.health;

import org.project.ai.chat.AIService;
import org.project.ai.intent.BasePromptHandler;
import org.project.ai.prompt.health.HealthEffectPrompt;
import org.project.model.request.ChatMessageRequest;
import org.springframework.stereotype.Component;

@Component
public class HealthEffectHandler extends BasePromptHandler<HealthEffectPrompt>{


    public HealthEffectHandler(AIService aiService, HealthEffectPrompt healthEffectPrompt) {
        super(aiService,healthEffectPrompt);
    }

    @Override
    protected String buildPrompt(ChatMessageRequest chatMessageRequest, String historyWithUser) {
        return prompt.buildPrompt(chatMessageRequest, historyWithUser);
    }

    @Override
    public String contextType() {
        return "health_effect";
    }


}
