package org.project.ai.intent.system;

import org.project.ai.chat.AIService;
import org.project.ai.intent.BasePromptHandler;
import org.project.ai.prompt.system.MedicinePrompt;
import org.project.model.request.ChatMessageRequest;
import org.springframework.stereotype.Component;

@Component
public class AskMedicineHandler extends BasePromptHandler<MedicinePrompt>{


    public AskMedicineHandler(AIService aiService, MedicinePrompt medicinePrompt) {
        super(aiService, medicinePrompt);
    }

    @Override
    protected String buildPrompt(ChatMessageRequest chatMessageRequest) {
        return prompt.buildPrompt(chatMessageRequest);
    }

    @Override
    public String contextType() {
        return "ask_medicine";
    }
}