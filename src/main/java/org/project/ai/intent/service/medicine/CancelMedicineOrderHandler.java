package org.project.ai.intent.service.medicine;

import org.project.ai.chat.AIService;
import org.project.ai.intent.BasePromptHandler;
import org.project.ai.prompt.service.medicine.CancelMedicineOrderPrompt;
import org.project.model.request.ChatMessageRequest;
import org.springframework.stereotype.Component;

@Component
public class CancelMedicineOrderHandler extends BasePromptHandler<CancelMedicineOrderPrompt> {

    public CancelMedicineOrderHandler(AIService aiService, CancelMedicineOrderPrompt cancelMedicineOrderPrompt) {
        super(aiService, cancelMedicineOrderPrompt);
    }

    @Override
    protected String buildPrompt(ChatMessageRequest chatMessageRequest, String historyWithUser) {
        return prompt.buildPrompt(chatMessageRequest, historyWithUser);
    }

    @Override
    public String contextType() {
        return "cancel_medicine_order";
    }
}
