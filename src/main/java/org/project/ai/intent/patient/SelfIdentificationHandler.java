package org.project.ai.intent.patient;

import org.project.ai.chat.AIService;
import org.project.ai.intent.BasePromptHandler;
import org.project.ai.prompt.patient.SelfIdentificationPrompt;
import org.project.model.request.ChatMessageRequest;
import org.springframework.stereotype.Component;

@Component
public class SelfIdentificationHandler extends BasePromptHandler<SelfIdentificationPrompt> {
    public SelfIdentificationHandler(AIService aiService, SelfIdentificationPrompt selfIdentificationPrompt) {
        super(aiService, selfIdentificationPrompt);
    }

    @Override
    protected String buildPrompt(ChatMessageRequest chatMessageRequest, String historyWithUser) {
        return prompt.buildPrompt(chatMessageRequest, historyWithUser);
    }

    @Override
    public String contextType() {
        return "self_identification";
    }
}
