//package org.project.ai.prompt.health;
//
//import org.project.ai.prompt.PromptAnswer;
//import org.project.ai.prompt.PromptStrategy;
//import org.project.entity.MedicalProfileEntity;
//import org.project.entity.PatientEntity;
//import org.project.model.request.ChatMessageRequest;
//import org.project.repository.MedicalProfileRepository;
//import org.project.repository.PatientRepository;
//import org.springframework.stereotype.Component;
//
//@Component
//
//public class HealthEffectPrompt implements PromptAnswer {
//
//    private final MedicalProfileRepository  medicalProfileRepository;
//
//    public HealthEffectPrompt(MedicalProfileRepository medicalProfileRepository) {
//        this.medicalProfileRepository = medicalProfileRepository;
//    }
//
////    private String getMedicalRecord(ChatMessageRequest chatMessageRequest){
////        MedicalProfileEntity medicalProfileEntity = medicalProfileRepository.findByPatient_ID(chatMessageRequest.getPatientId());
////        return "";
////    }
//    @Override
//    public String buildPrompt(ChatMessageRequest chatMessageRequest) {
//        return """
//            Bạn là chuyên gia y tế của hệ thống.
//
//            Câu hỏi của người dùng: "%s"
//
//            Bệnh án của người dùng: "%s"
//
//            Nếu người dùng hỏi về ảnh hưởng đến sức khỏe của một loại thuốc cụ thể, hãy trả lời về:
//            - Tác dụng phụ thường gặp
//            - Tác hại nếu dùng sai cách hoặc quá liều
//            - Khuyến nghị an toàn khi sử dụng
//
//            Nếu người dùng không đề cập thuốc cụ thể, hãy đưa ra lời khuyên chung về việc sử dụng thuốc đúng cách.
//
//            Trả lời rõ ràng, đúng chuyên môn và không gây hoang mang.
//            """.formatted(chatMessageRequest.getUserMessage());
//    }
//}
