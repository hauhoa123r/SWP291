package org.project.ai.intent;

import org.project.model.request.ChatMessageRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class IntentHandlerRegistry {

    private final Map<String, IntentHandler> handlerMap;

    public IntentHandlerRegistry(List<IntentHandler> handlers) {
        this.handlerMap = handlers.stream()
                .collect(Collectors.toMap(IntentHandler::contextType, h -> h));
    }

    public String handle(ChatMessageRequest chatMessageRequest, String historyWithUser) {
        IntentHandler handler = handlerMap.get(chatMessageRequest.getPrompt());
        return handler.handle(chatMessageRequest, historyWithUser);
    }
}