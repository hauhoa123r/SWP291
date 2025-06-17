package org.project.ai.intent.service.medicine;

import org.project.ai.chat.AIService;
import org.project.ai.intent.BasePromptHandler;
import org.project.ai.prompt.service.medicine.OrderMedicinePrompt;
import org.project.model.request.ChatMessageRequest;
import org.springframework.stereotype.Component;

@Component
public class OrderMedicineHandler extends BasePromptHandler<OrderMedicinePrompt> {

    public OrderMedicineHandler(AIService aiService, OrderMedicinePrompt orderMedicinePrompt) {
        super(aiService, orderMedicinePrompt);
    }

    @Override
    protected String buildPrompt(ChatMessageRequest chatMessageRequest, String historyWithUser) {
        return prompt.buildPrompt(chatMessageRequest, historyWithUser);
    }

    @Override
    public String contextType() {
        return "order_medicine";
    }
}
