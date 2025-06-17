package org.project.ai.prompt;

import org.project.enums.Intent;
import org.project.model.request.ChatMessageRequest;
import org.springframework.stereotype.Component;
@Component
public class IntentPrompt implements PromptStrategy {

    @Override
    public String buildPrompt(ChatMessageRequest userMessage, String historyWithUser) {
        return """
    You are a highly accurate intent classification system for the KiviCare hospital virtual assistant.

    Task:
    - Based on the user's current message and the entire conversation history, classify the user's intent into one of the following labels:
    %s

    Rules:
    1. Consider the entire conversation to understand the context.
    2. Focus primarily on the most recent user message.
    3. Always classify based on the overall flow, not just isolated sentences.
    5. If the user's message is casual or social (greeting, thanks, small talk), classify as "chitchat".
    6. Respond ONLY with the intent label. No extra words, no punctuation.

    Conversation History:
    %s

    Current User Message:
    "%s"

    Respond with only one of the intent labels above.
    """.formatted(Intent.getAllDescriptions(), historyWithUser, userMessage.getUserMessage());
    }


}
