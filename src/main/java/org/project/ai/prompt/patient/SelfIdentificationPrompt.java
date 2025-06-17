package org.project.ai.prompt.patient;

import org.project.ai.converter.patient.DataConverterPatient;
import org.project.ai.prompt.PromptAnswer;
import org.project.model.request.ChatMessageRequest;
import org.project.repository.ProductRepository;
import org.springframework.stereotype.Component;

@Component
public class SelfIdentificationPrompt implements PromptAnswer {

    private final ProductRepository productRepository;

    private final DataConverterPatient dataConverterPatient;
    public SelfIdentificationPrompt(ProductRepository productRepository, DataConverterPatient dataConverterPatient) {
        this.productRepository = productRepository;
        this.dataConverterPatient = dataConverterPatient;
    }

    @Override
    public String buildPrompt(ChatMessageRequest chatMessageRequest, String historyWithUser) {
        String userData = "";
        if (chatMessageRequest.getUserId() != null) {
            userData = dataConverterPatient.toConverterDataUser(chatMessageRequest.getUserId());
        }

        return String.format("""
            You are KiviCare AI, a friendly and professional virtual assistant of the KiviCare hospital system.

            --- User's Question About Their Identity ---
            "%s"
            --------------------------------------------

            --- User and Linked Patient Information ---
            %s
            --------------------------------------------

            --- Conversation History ---
            %s
            --------------------------------------------

            Respond in: %s
            (Always reply in the correct language.)

            --- Key Instructions ---
            1. You MUST fully analyze both the user's current question and the entire conversation history before answering.
            2. NEVER ignore the provided user and patient data. NEVER assume anything outside the provided information.
            3. Your tone MUST always be warm, natural, and professional.

            --- Decision Rules ---
            • If the user is linked to EXACTLY ONE patient:
              → Reply: "You are the patient [Patient Name] registered in the KiviCare system."

            • If the user is linked to MULTIPLE patients:
              → Reply: "You are the guardian of the following patients: [List of Patient Names]. If you are asking about yourself, please clarify."

            • If the user is linked to NO patient:
              → Reply: "I could not find any patient linked to your account. Please update your patient profile so I can assist you better."

            Keep your response short, friendly, and directly address the user's identity question.
            """,
                chatMessageRequest.getUserMessage(),
                userData,
                historyWithUser,
                chatMessageRequest.getLanguage()
        );
    }



}
