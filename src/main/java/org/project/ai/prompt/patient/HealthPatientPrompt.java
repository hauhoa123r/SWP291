package org.project.ai.prompt.patient;

import org.project.ai.prompt.PromptAnswer;
import org.project.ai.prompt.PromptStrategy;
import org.project.model.request.ChatMessageRequest;
import org.springframework.stereotype.Component;

@Component
public class HealthPatientPrompt implements PromptAnswer {
    @Override
    public String buildPrompt(ChatMessageRequest chatMessageRequest) {
        return """
            Bạn là bác sĩ tư vấn sức khỏe cá nhân.

            Người dùng hỏi: "%s"

            Thông tin bệnh nhân đã được lưu trong hệ thống qua session (ID: %s).

            Dựa trên tình trạng sức khỏe hiện tại, hãy:
            - Đưa ra lời khuyên cải thiện sức khỏe
            - Nhắc nhở các thói quen tốt (ăn uống, vận động, nghỉ ngơi)
            - Nếu có dấu hiệu nghiêm trọng thì đề nghị đi khám bác sĩ

            Trả lời rõ ràng, có chuyên môn, ngắn gọn, không gây lo lắng không cần thiết.
            """.formatted(chatMessageRequest.getUserMessage(), "{patientId}");
    }
}
