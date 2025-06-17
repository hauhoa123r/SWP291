package org.project.ai.prompt.system;

import org.project.ai.converter.patient.DataConverterPatient;
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
    private final DataConverterPatient dataConverterPatient;
    public MedicinePrompt(ProductRepository productRepository, DataConverterPatient dataConverterPatient) {
        this.productRepository = productRepository;
        this.dataConverterPatient = dataConverterPatient;
    }

    private String getAllPharmacy(){
        List<ProductEntity> results = productRepository.findAllByProductTypeAndProductStatus(ProductType.MEDICINE, ProductStatus.ACTIVE);
        return results
                .stream()
                .map(i -> "- " + i.getName())
                .collect(Collectors.joining("\n"));

    }

    @Override
    public String buildPrompt(ChatMessageRequest chatMessageRequest, String historyWithUser) {
        String userData = "";
        if (chatMessageRequest.getUserId() != null) {
            userData = dataConverterPatient.toConverterDataUser(chatMessageRequest.getUserId());
        }

        return String.format("""
        You are a highly professional and medically accurate medication advisor. Your primary role is to provide safe, precise, and reliable medication-related information.

        In the KiviCare hospital system, you are known as KiviCare AI, a friendly and supportive virtual assistant helping patients and their families.

        Always prioritize patient safety, follow strict medical standards, and clearly recommend consulting professional doctors when needed.

        --- Patient Information ---
        %s
        ---------------------------

        --- Available Medications (Active Only) ---
        %s
        --------------------------------------------
        
        --- Conversation History ---
        %s
        -------------------------------------------------------------

        --- User's Message ---
        %s
        --------------------------------------------

        Respond in: %s (Please double-check and reply in this language.)

        --- Instructions ---
        1. If the patient's medical information is missing or incomplete:
            → Reply: "I currently do not have enough medical records to provide specific medication advice. Please consult your doctor directly or update your medical records in the KiviCare system."
        2. If patient information is available:
            → Analyze allergies, existing conditions, and recent discharge status carefully.
        3. Suggest medications ONLY IF:
            - They are active in the system.
            - They are safe based on the patient's medical history (no allergies, no contraindications).
        4. If a suitable medication is found:
            → Mention the medication name and briefly explain its purpose related to the user's question.
        5. If no safe medication is available:
            → Politely state that no suitable medication can be recommended.
        6. If the question is complex or carries health risks:
            → Strongly advise consulting a doctor or pharmacist immediately.
        7. Always add this disclaimer:
            → "I am KiviCare AI. My advice is supportive and does not replace professional medical consultation."
        8. Keep responses brief, clear, and empathetic.
        9. If the user’s question is general (not patient-specific):
            → Provide basic, medically accepted information.
        10. NEVER:
            - Suggest incorrect, harmful, illegal, or dangerous advice.
            - Support non-medical, unethical, or violent topics.
        11. If the question is not about medications:
            → Politely guide the user back to medication-related topics in KiviCare.

        Please answer directly, safely, and professionally.
        """, userData, getAllPharmacy(), historyWithUser,chatMessageRequest.getUserMessage(), chatMessageRequest.getLanguage());
    }

}
