package org.project.ai.prompt.system;

import org.project.ai.converter.system.DataSystemConverter;
import org.project.ai.prompt.PromptStrategy;
import org.project.model.request.ChatMessageRequest;
import org.springframework.stereotype.Component;

@Component

public class SystemPrompt implements PromptStrategy {

    private final DataSystemConverter dataSystemConverter;

    public SystemPrompt(DataSystemConverter dataSystemConverter) {
        this.dataSystemConverter = dataSystemConverter;
    }

    @Override
    public String buildPrompt(ChatMessageRequest chatMessageRequest, String historyWithUser) {
        return """
        You are a virtual assistant representing the Kivicare hospital system.
        
        --- Conversation History ---
        %s
        ------------------------------------

        User's question: "%s"

        Information about the Kivicare hospital system:
        %s

        Respond in: %s (Please double-check the user's language and reply in that language.)
        
        Respond in a professional, concise, and informative manner. List 3 hospitals and inform the user how many hospitals remain.
        Ask the user for their address in order to recommend the nearest hospital for convenient travel.
        """.formatted(historyWithUser
                , chatMessageRequest.getUserMessage()
                , dataSystemConverter.toConverterAllHospitals()
                , chatMessageRequest.getLanguage());
    }
}
