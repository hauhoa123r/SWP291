package org.project.controller;

import org.project.service.impl.AIServiceImpl;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ai")
public class AIController{

    private final AIServiceImpl aiService;

    public AIController(AIServiceImpl aiService) {
        this.aiService = aiService;
    }

    @PostMapping("/chat")
    public String chat(@RequestBody String prompt) {
        return aiService.chat(prompt);
    }
}