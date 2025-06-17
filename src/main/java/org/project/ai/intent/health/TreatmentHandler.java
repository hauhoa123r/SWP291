package org.project.ai.intent.health;

import org.project.ai.chat.AIService;
import org.project.ai.intent.BasePromptHandler;
import org.project.ai.intent.IntentHandler;
import org.project.ai.prompt.health.TreatmentPrompt;
import org.project.model.request.ChatMessageRequest;
import org.springframework.stereotype.Component;

@Component
public class TreatmentHandler extends BasePromptHandler<TreatmentPrompt> {

    public TreatmentHandler(AIService aiService,TreatmentPrompt treatmentPrompt) {
        super(aiService,treatmentPrompt);
    }

    @Override
    protected String buildPrompt(ChatMessageRequest chatMessageRequest, String historyWithUser) {
        return prompt.buildPrompt(chatMessageRequest, historyWithUser);
    }

    @Override
    public String contextType() {
        return "treatment";
    }

}
