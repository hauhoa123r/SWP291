package org.project.ai.chat;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class OpenAiServiceImpl implements AIService{

    private final ChatClient chatClient;

    public OpenAiServiceImpl(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }
    @Override
    public String complete(String prompt) {
        return chatClient
                .prompt(prompt)
                .call()
                .content()
                .trim();
    }
}
