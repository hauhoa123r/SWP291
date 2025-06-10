package org.project.ai.intent.system;

import org.project.ai.chat.AIService;
import org.project.ai.intent.BasePromptHandler;
import org.project.ai.intent.IntentHandler;
import org.project.ai.prompt.system.SystemPrompt;
import org.project.model.request.ChatMessageRequest;
import org.springframework.stereotype.Component;

@Component
public class AskSystemHandler extends BasePromptHandler<SystemPrompt> {


    public AskSystemHandler(AIService aiService, SystemPrompt systemPrompt){
        super(aiService, systemPrompt);
    }

    @Override
    protected String buildPrompt(ChatMessageRequest chatMessageRequest) {
        return prompt.buildPrompt(chatMessageRequest.getUserMessage());
    }

    @Override
    public String contextType() {
        return "ask_hospital";
    }
}
