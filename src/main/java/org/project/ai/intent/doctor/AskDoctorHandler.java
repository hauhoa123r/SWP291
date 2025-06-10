package org.project.ai.intent.doctor;

import org.project.ai.chat.AIService;
import org.project.ai.intent.BasePromptHandler;
import org.project.ai.prompt.doctor.DoctorPrompt;
import org.project.model.request.ChatMessageRequest;
import org.springframework.stereotype.Component;

@Component
public class AskDoctorHandler extends BasePromptHandler<DoctorPrompt>{

    public AskDoctorHandler(AIService aiService, DoctorPrompt doctorPrompt) {
        super(aiService, doctorPrompt);
    }

    @Override
    protected String buildPrompt(ChatMessageRequest chatMessageRequest) {
        return prompt.buildPrompt(chatMessageRequest.getUserMessage());
    }

    @Override
    public String contextType() {
        return "ask_doctor";
    }
}
