package org.project.service.impl;

import org.project.service.AIService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class AIServiceImpl implements AIService {

    private ChatClient chatClient;

    public AIServiceImpl(ChatClient.Builder builder) {
        chatClient = builder.build();
    }
    @Override
    public String chat(String prompt) {
        return chatClient
                .prompt(prompt)
                .call()
                .content();
    }
}
