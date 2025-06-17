package org.project.ai.context;

import jakarta.servlet.http.HttpSession;
import org.project.model.request.ChatMessageRequest;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class ChatSessionManager {

    private static final int MAX_HISTORY_SIZE = 30;

    public List<ChatMessage> getChatSessionWithUser(HttpSession session){
        @SuppressWarnings("unchecked")
        List<ChatMessage> chatHisroty = (List<ChatMessage>) session.getAttribute("chatHistory");
        if(chatHisroty == null) {
            chatHisroty = new ArrayList<>();
        }
        return chatHisroty;
    }

    public void setChatSessionWithUser(HttpSession session, ChatMessageRequest chatMessageRequest, String aiResponse) {
        List<ChatMessage> chatHistory = getChatSessionWithUser(session);
        chatHistory.add(new ChatMessage("user", chatMessageRequest.getUserMessage()));
        chatHistory.add(new ChatMessage("assistant", aiResponse)); // Sửa "assistant(you)" thành "assistant"
        checkMaxHistory(chatHistory);
        session.setAttribute("chatHistory", chatHistory);
    }

    private void checkMaxHistory(List<ChatMessage> chatHistory){
        if(chatHistory.size() > MAX_HISTORY_SIZE) {
            chatHistory = chatHistory.subList(chatHistory.size() - MAX_HISTORY_SIZE, chatHistory.size());
        }
    }

}
