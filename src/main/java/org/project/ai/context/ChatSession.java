package org.project.ai.context;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ChatSession {


    public String getChatSessionWithUser(List<ChatMessage> chatMessages){
        StringBuilder historyChatWithUser = new StringBuilder();
        if(chatMessages != null && !chatMessages.isEmpty()) {
            StringBuilder historyBuilder = new StringBuilder();
            for (ChatMessage chatMessage : chatMessages) {
                historyBuilder.append(chatMessage.getRole())
                        .append(": ")
                        .append(chatMessage.getContent())
                        .append("\n");
            }
            historyChatWithUser.append("Please consider the entire conversation history provided below when generating your response. The history includes previous messages from both the user and yourself (the assistant). Use this context to ensure your response is relevant and coherent with the ongoing conversation.:\n" + historyBuilder.toString());
        }
        return historyChatWithUser.toString();
        }

}
