package org.project.ai.prompt;

public interface PromptStrategy {
    String buildPrompt(String userMessage);
}
