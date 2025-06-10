package org.project.ai.intent;


import org.project.model.request.ChatMessageRequest;

public interface IntentHandler {
    String handle(ChatMessageRequest chatMessageRequest);
    String contextType();
}