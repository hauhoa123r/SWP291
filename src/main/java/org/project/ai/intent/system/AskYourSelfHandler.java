package org.project.ai.intent.system;

import org.project.ai.chat.AIService;
import org.project.ai.intent.BasePromptHandler;
import org.project.ai.intent.IntentHandler;
import org.project.ai.prompt.system.YourSelfPrompt;
import org.project.model.request.ChatMessageRequest;
import org.springframework.stereotype.Component;

@Component
public class AskYourSelfHandler extends BasePromptHandler<YourSelfPrompt> {


    public AskYourSelfHandler(AIService aiService, YourSelfPrompt yourSelfPrompt) {
        super(aiService, yourSelfPrompt);
    }

    @Override
    protected String buildPrompt(ChatMessageRequest chatMessageRequest) {
        return prompt.buildPrompt(chatMessageRequest);
    }

    @Override
    public String contextType() {
        return "ask_yourself";
    }
}
