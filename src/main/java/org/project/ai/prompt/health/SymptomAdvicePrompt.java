//package org.project.ai.prompt.health;
//
//import org.project.ai.prompt.PromptAnswer;
//import org.project.ai.prompt.PromptStrategy;
//import org.project.entity.PharmacyProductEntity;
//import org.project.model.request.ChatMessageRequest;
//import org.project.repository.PharmacyRepository;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Component
//public class SymptomAdvicePrompt implements PromptAnswer {
//
//    private final PharmacyRepository pharmacyRepository;
//
//    public SymptomAdvicePrompt(PharmacyRepository pharmacyRepository) {
//        this.pharmacyRepository = pharmacyRepository;
//    }
//
//    @Override
//    public String buildPrompt(ChatMessageRequest chatMessageRequest) {
//        List<PharmacyProductEntity> pharmacyProductEntities = pharmacyRepository.findAll();
//        StringBuilder nameMedicines = new StringBuilder(pharmacyProductEntities
//                .stream()
//                .map(i -> "- " + i.getName())
//                .collect(Collectors.joining("\n")));
//
//        return """
//        Bạn là bác sĩ tư vấn.
//
//        Danh sách thuốc hiện có trong hệ thống:
//        %s
//
//        Triệu chứng của bệnh nhân: "%s"
//
//        Hãy gợi ý loại thuốc phù hợp (nếu có) hoặc đưa ra lời khuyên. Nếu không có thuốc nào phù hợp, thì trả lời rằng trong hệ thống không có thuốc phù hợp, vui lòng gặp bác sĩ để biết rõ hơn.
//
//        Trả lời rõ ràng, chuyên môn, ngắn gọn. Nếu có thuốc phù hợp thì xét trường hợp nhẹ hay nặng để đề nghị
//        , những trường hợp năng thì nên gặp bác sĩ để được tư vấn kỹ hơn. Và nói là tôi chỉ là AI không thể thay thế bác sĩ.
//        """.formatted(nameMedicines, chatMessageRequest.getUserMessage());
//    }
//}
