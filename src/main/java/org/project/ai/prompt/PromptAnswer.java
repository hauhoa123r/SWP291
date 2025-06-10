package org.project.ai.prompt;

import org.project.model.request.ChatMessageRequest;

public interface PromptAnswer {
    String buildPrompt(ChatMessageRequest chatMessageRequest);
}
