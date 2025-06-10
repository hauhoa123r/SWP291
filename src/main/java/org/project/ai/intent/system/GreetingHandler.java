package org.project.ai.intent.system;

import org.project.ai.intent.IntentHandler;
import org.project.model.request.ChatMessageRequest;
import org.springframework.stereotype.Component;

@Component
public class GreetingHandler implements IntentHandler {

    @Override
    public String handle(ChatMessageRequest chatMessageRequest) {
        return "Chào bạn tôi là nhân viên hỗ trợ ảo của hệ thống Kivicare, tôi có thể giúp gì cho bạn!";
    }

    @Override
    public String contextType() {
        return "greeting";
    }
}
