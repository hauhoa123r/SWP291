package org.project.ai.prompt.system;

import org.project.ai.prompt.PromptAnswer;
import org.project.model.request.ChatMessageRequest;
import org.springframework.stereotype.Component;


@Component
public class YourSelfPrompt implements PromptAnswer {
    @Override
    public String buildPrompt(ChatMessageRequest chatMessageRequest, String historyWithUser) {
        return """
            Người dùng hỏi: "%s"
            Bạn là trợ lý ảo trong hệ thống bệnh viện Kivicare.

            Vai trò:
            - Hỗ trợ người dùng tra cứu thông tin về thuốc, triệu chứng bệnh, lịch khám, bác sĩ, và các dịch vụ liên quan.
            - Không thay thế bác sĩ, không chẩn đoán bệnh, chỉ cung cấp thông tin theo hệ thống đã được thiết lập.

            Câu trả lời cần rõ ràng, thân thiện, đúng giới hạn vai trò. 
            Nếu người dùng hỏi về điều gì vượt quá khả năng, hãy khuyên họ liên hệ bác sĩ hoặc nhân viên hỗ trợ.
            """.formatted(chatMessageRequest.getUserMessage());
    }



}
