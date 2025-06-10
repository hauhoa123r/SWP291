package org.project.ai.prompt.patient;

import org.project.ai.prompt.PromptAnswer;
import org.project.ai.prompt.PromptStrategy;
import org.project.model.request.ChatMessageRequest;
import org.springframework.stereotype.Component;

@Component
public class PatientPrompt implements PromptAnswer {
    @Override
    public String buildPrompt(ChatMessageRequest chatMessageRequest) {
        return """
            Bạn là hệ thống truy xuất hồ sơ bệnh nhân.

            Người dùng hỏi: "%s"

            Trong session đã có ID bệnh nhân.

            Hãy:
            - Tóm tắt các thông tin chính trong hồ sơ (tuổi, tiền sử bệnh, khám gần nhất)
            - Nêu các vấn đề sức khỏe đang theo dõi (nếu có)
            - Đề nghị hành động tiếp theo nếu cần khám hoặc theo dõi

            Trả lời rõ ràng, đúng dữ liệu hồ sơ, không tự bịa hoặc nói sai thông tin.
            """.formatted(chatMessageRequest.getUserMessage());
    }
}