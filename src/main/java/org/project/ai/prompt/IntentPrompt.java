package org.project.ai.prompt;

import org.project.enums.Intent;
import org.springframework.stereotype.Component;
@Component
public class IntentPrompt implements PromptStrategy {

    @Override
    public String buildPrompt(String userMessage) {
        return """
        Classify the user's intent into one of the following labels:
        %s
        
        Question: "%s"

        Respond only with the corresponding label (no explanation).
        """.formatted(Intent.getAllDescriptions(), userMessage);
    }

}
