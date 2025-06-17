package org.project.ai.intent.service.appointment;

import org.project.ai.chat.AIService;
import org.project.ai.intent.BasePromptHandler;
import org.project.ai.prompt.service.appointment.CancelAppointmentPrompt;
import org.project.model.request.ChatMessageRequest;
import org.springframework.stereotype.Component;

@Component
public class CancelAppointmentHandler extends BasePromptHandler<CancelAppointmentPrompt> {
    public CancelAppointmentHandler(AIService aiService, CancelAppointmentPrompt cancelAppointmentPrompt) {
        super(aiService, cancelAppointmentPrompt);
    }

    @Override
    protected String buildPrompt(ChatMessageRequest chatMessageRequest, String historyWithUser) {
        return prompt.buildPrompt(chatMessageRequest, historyWithUser);
    }

    @Override
    public String contextType() {
        return "cancel_appointment";
    }
}
