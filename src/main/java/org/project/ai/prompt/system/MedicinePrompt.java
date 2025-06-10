package org.project.ai.prompt.system;

import org.project.ai.converter.DataPatientConverter;
import org.project.ai.prompt.PromptAnswer;
import org.project.entity.ProductEntity;
import org.project.enums.ProductStatus;
import org.project.enums.ProductType;
import org.project.model.request.ChatMessageRequest;
import org.project.repository.ProductRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MedicinePrompt implements PromptAnswer {
    private final ProductRepository productRepository;
    private final DataPatientConverter dataPatientConverter;
    public MedicinePrompt(ProductRepository productRepository, DataPatientConverter dataPatientConverter) {
        this.productRepository = productRepository;
        this.dataPatientConverter = dataPatientConverter;
    }

    private String getAllPharmacy(){
        List<ProductEntity> results = productRepository.findAllByProductTypeAndProductStatus(ProductType.MEDICINE, ProductStatus.ACTIVE);
        return results
                .stream()
                .map(i -> "- " + i.getName())
                .collect(Collectors.joining("\n"));

    }

    @Override
    public String buildPrompt(ChatMessageRequest chatMessageRequest) {
        String userData = "";
        if(chatMessageRequest.getUserId() != null){
             userData = dataPatientConverter.toConverterDataUser(chatMessageRequest.getUserId());
        }
        return """
                You are a highly professional and medically precise medication advisor in a hospital Kivicare system.
                Your primary goal is to provide accurate, safe, and helpful medication-related information to the user, strictly adhering to medical guidelines and patient safety.
                Always prioritize patient well-being and clearly state when professional medical consultation is necessary.
                
                Respond in: %s(Please check what language the user is in again, then reply in that language.)
                
                ---
                
                **Patient Information and Medical History:**
                %s
                
                ---
                
                **Available Medications in Our System (Active Status):**
                %s
                
                ---
                
                **User's Inquiry:**(user wants to ask)
                "%s"
                
                ---
                
                **Guidance for Your Response:**
                1.  **Analyze the user's inquiry** in conjunction with the patient's medical history (allergies, existing conditions, recent discharge status).
                2.  **Suggest suitable medications ONLY if they are available in the system and are NOT contraindicated** by the patient's allergies or medical conditions. Explicitly mention the name of the suggested medication(s).
                3.  If a suitable medication is found, briefly explain its primary use and how it relates to the user's inquiry.
                4.  **If no suitable medication exists, or if there are any potential contraindications/risks** given the patient's history (especially allergies or recent discharge), **immediately state that you cannot recommend any medication.**
                5.  **Critically important**: If you cannot recommend a medication, or if the user's inquiry is complex or potentially dangerous, provide a clear warning about the potential health risks of self-medication and **strongly advise the user to consult a qualified doctor or pharmacist immediately for proper diagnosis and treatment.**
                6.  Always include a disclaimer that you are an AI and cannot replace a real doctor's professional advice.
                7.  Maintain a concise, factual, and empathetic tone. Include general rest recommendations if appropriate, but avoid giving specific medical diagnoses or treatment plans.
                8.  **NEVER provide incorrect, misleading, or potentially harmful medical information.**
                
                Please provide your professional advice based on the above.
        """.formatted(chatMessageRequest.getLanguage(), userData, getAllPharmacy(),
                chatMessageRequest.getUserMessage());

    }
}
