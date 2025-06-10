package org.project.ai.intent.patient;

import org.project.ai.chat.AIService;
import org.project.ai.intent.BasePromptHandler;
import org.project.ai.intent.IntentHandler;
import org.project.ai.prompt.patient.HealthPatientPrompt;
import org.project.model.request.ChatMessageRequest;
import org.springframework.stereotype.Component;

@Component
public class HealthPatientHandler extends BasePromptHandler<HealthPatientPrompt> {

    public HealthPatientHandler(HealthPatientPrompt healthPatientPrompt, AIService aiService) {
        super(aiService,healthPatientPrompt);
    }

    @Override
    protected String buildPrompt(ChatMessageRequest chatMessageRequest) {
        return prompt.buildPrompt(chatMessageRequest);
    }


    @Override
    public String contextType() {
        return "ask_health_patient";
    }
}
