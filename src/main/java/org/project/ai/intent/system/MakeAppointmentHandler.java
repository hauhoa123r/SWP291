package org.project.ai.intent.system;

import org.project.ai.intent.IntentHandler;
import org.project.model.request.ChatMessageRequest;
import org.springframework.stereotype.Component;

@Component
public class MakeAppointmentHandler implements IntentHandler {

    @Override
    public String handle(ChatMessageRequest chatMessageRequest) {
        return "Bạn vui lòng truy cập: https://example.com/dat-lich để đặt lịch khám.";
    }

    @Override
    public String contextType() {
        return "make_appointment";
    }
}