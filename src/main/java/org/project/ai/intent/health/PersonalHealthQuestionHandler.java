package org.project.ai.intent.health;

import org.project.ai.chat.AIService;
import org.project.ai.intent.BasePromptHandler;
import org.project.ai.prompt.health.PersonalHealthQuestionPrompt;
import org.project.model.request.ChatMessageRequest;
import org.springframework.stereotype.Component;

@Component
public class PersonalHealthQuestionHandler extends BasePromptHandler<PersonalHealthQuestionPrompt> {
    public PersonalHealthQuestionHandler(AIService aiService, PersonalHealthQuestionPrompt personalHealthQuestionPrompt) {
        super(aiService, personalHealthQuestionPrompt);
    }

    @Override
    protected String buildPrompt(ChatMessageRequest chatMessageRequest, String historyWithUser) {
        return prompt.buildPrompt(chatMessageRequest, historyWithUser);
    }

    @Override
    public String contextType() {
        return "personal_health_question";
    }
}
