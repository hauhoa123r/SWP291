package org.project.ai.intent.service.appointment;

import org.project.ai.chat.AIService;
import org.project.ai.intent.BasePromptHandler;
import org.project.ai.prompt.service.appointment.RescheduleAppointmentPrompt;
import org.project.model.request.ChatMessageRequest;
import org.springframework.stereotype.Component;

@Component
public class RescheduleAppointmentHandler extends BasePromptHandler<RescheduleAppointmentPrompt> {
    public RescheduleAppointmentHandler(AIService aiService, RescheduleAppointmentPrompt rescheduleAppointmentPrompt) {
        super(aiService, rescheduleAppointmentPrompt);
    }

    @Override
    protected String buildPrompt(ChatMessageRequest chatMessageRequest, String historyWithUser) {
        return prompt.buildPrompt(chatMessageRequest, historyWithUser);
    }

    @Override
    public String contextType() {
        return "reschedule_appointment";
    }
}
