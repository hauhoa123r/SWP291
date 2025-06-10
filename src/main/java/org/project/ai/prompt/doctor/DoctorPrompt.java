package org.project.ai.prompt.doctor;

import org.project.ai.prompt.PromptStrategy;
import org.springframework.stereotype.Component;

@Component
public class DoctorPrompt implements PromptStrategy {

    @Override
    public String buildPrompt(String userMessage) {
        return "";
    }
}
