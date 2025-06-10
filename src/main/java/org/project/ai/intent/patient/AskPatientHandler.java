package org.project.ai.intent.patient;

import org.project.ai.chat.AIService;
import org.project.ai.intent.BasePromptHandler;
import org.project.ai.intent.IntentHandler;
import org.project.ai.prompt.patient.PatientPrompt;
import org.project.model.request.ChatMessageRequest;
import org.springframework.stereotype.Component;

@Component
public class AskPatientHandler extends BasePromptHandler<PatientPrompt> {


    public AskPatientHandler(AIService aiService, PatientPrompt patientPrompt) {
        super(aiService, patientPrompt);
    }

    @Override
    protected String buildPrompt(ChatMessageRequest chatMessageRequest) {
        return prompt.buildPrompt(chatMessageRequest);
    }


    @Override
    public String contextType() {
        return "ask_patient";
    }
}
