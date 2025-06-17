package org.project.ai.prompt;

import org.project.model.request.ChatMessageRequest;

public interface PromptStrategy {
    String buildPrompt(ChatMessageRequest userMessage, String historyWithUser);
}
