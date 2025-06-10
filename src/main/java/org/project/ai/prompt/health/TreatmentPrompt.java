package org.project.ai.prompt.health;

import org.project.ai.prompt.PromptAnswer;
import org.project.ai.prompt.PromptStrategy;
import org.project.model.request.ChatMessageRequest;
import org.springframework.stereotype.Component;

@Component

public class TreatmentPrompt implements PromptAnswer {
    @Override
    public String buildPrompt(ChatMessageRequest chatMessageRequest) {
        return """
            Bạn là bác sĩ chuyên môn trong hệ thống.

            Câu hỏi của người dùng: "%s"

            Nếu người dùng hỏi về cách điều trị, hãy trả lời:
            - Các phương pháp điều trị thông thường
            - Lưu ý khi điều trị tại nhà (nếu có)
            - Trường hợp nào cần đến bác sĩ chuyên khoa

            Nếu người dùng không nói rõ bệnh, hãy khuyên họ mô tả thêm triệu chứng hoặc đến gặp bác sĩ để được chẩn đoán chính xác.

            Trả lời ngắn gọn, đúng chuyên môn, dễ hiểu cho bệnh nhân.
            """.formatted(chatMessageRequest.getUserMessage());
    }
}
