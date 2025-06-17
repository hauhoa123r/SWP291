package org.project.ai.intent.patient;

import org.project.ai.chat.AIService;
import org.project.ai.intent.BasePromptHandler;
import org.project.ai.prompt.patient.AskPersonPrompt;
import org.project.model.request.ChatMessageRequest;
import org.springframework.stereotype.Component;

@Component
public class AskPersonHandler extends BasePromptHandler<AskPersonPrompt> {
    public AskPersonHandler(AIService aiService, AskPersonPrompt askPersonPrompt) {
        super(aiService, askPersonPrompt);
    }

    @Override
    protected String buildPrompt(ChatMessageRequest chatMessageRequest, String historyWithUser) {
        return prompt.buildPrompt(chatMessageRequest, historyWithUser);
    }

    @Override
    public String contextType() {
        return "ask_person";
    }
}
