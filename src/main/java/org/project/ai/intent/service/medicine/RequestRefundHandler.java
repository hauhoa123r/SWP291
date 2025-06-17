package org.project.ai.intent.service.medicine;

import org.project.ai.chat.AIService;
import org.project.ai.intent.BasePromptHandler;
import org.project.ai.prompt.service.medicine.RequestRefundPrompt;
import org.project.model.request.ChatMessageRequest;
import org.springframework.stereotype.Component;

@Component
public class RequestRefundHandler extends BasePromptHandler<RequestRefundPrompt> {

    public RequestRefundHandler(AIService aiService, RequestRefundPrompt requestRefundPrompt) {
        super(aiService, requestRefundPrompt);
    }

    @Override
    protected String buildPrompt(ChatMessageRequest chatMessageRequest, String historyWithUser) {
        return prompt.buildPrompt(chatMessageRequest, historyWithUser);
    }

    @Override
    public String contextType() {
        return "request_refund";
    }
}
