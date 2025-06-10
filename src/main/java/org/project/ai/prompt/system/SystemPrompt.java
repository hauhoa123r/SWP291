package org.project.ai.prompt.system;

import org.project.ai.converter.DataSystemConverter;
import org.project.ai.intent.system.AskSystemHandler;
import org.project.ai.prompt.PromptStrategy;
import org.springframework.stereotype.Component;

@Component

public class SystemPrompt implements PromptStrategy {

    private final DataSystemConverter dataSystemConverter;

    public SystemPrompt(DataSystemConverter dataSystemConverter) {
        this.dataSystemConverter = dataSystemConverter;
    }

    @Override
    public String buildPrompt(String userMessage) {
        return """
            Bạn là trợ lý ảo đại diện cho hệ thống bệnh viện Kivicare.

            Người dùng hỏi: "%s"

            Thông tin về bệnh viện Kivicare:
            %s

            Trả lời một cách chuyên nghiệp, ngắn gọn, đầy đủ thông tin cho người dùng, liêt kê 3 bênh viện và trả lời còn bao nhiêu bênh viện.
            Hỏi người dùng địa chỉ của người dùng ở đâu để tư vấn bệnh viện gần nhất cho tiện đường đi.
            """.formatted(userMessage,dataSystemConverter.toConverterAllHospitals());
    }
}
