package org.project.ai.intent.health;

import org.project.ai.chat.AIService;
import org.project.ai.intent.BasePromptHandler;
import org.project.ai.prompt.health.SymptomAdvicePrompt;
import org.project.model.request.ChatMessageRequest;
import org.springframework.stereotype.Component;

@Component
public class SymptomAdviceHandler extends BasePromptHandler<SymptomAdvicePrompt>{

    public SymptomAdviceHandler(AIService aiService, SymptomAdvicePrompt symptomAdvicePrompt) {
        super(aiService,symptomAdvicePrompt);
    }

    @Override
    protected String buildPrompt(ChatMessageRequest chatMessageRequest, String historyWithUser) {
        return prompt.buildPrompt(chatMessageRequest, historyWithUser);
    }

    @Override
    public String contextType() {
        return "symptom_advice";
    }
}