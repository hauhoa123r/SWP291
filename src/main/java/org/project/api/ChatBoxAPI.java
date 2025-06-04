package org.project.api;

import org.project.service.impl.AIServiceImpl;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ai")
public class ChatBoxAPI {

    private final AIServiceImpl aiService;

    public ChatBoxAPI(AIServiceImpl aiService) {
        this.aiService = aiService;
    }

    @PostMapping("/chat")
    public String chat(@RequestBody String prompt) {
        return aiService.chat(prompt);
    }
}