package org.project.ai.converter.appointment;

import org.project.ai.chat.AIService;
import org.project.model.dai.AppointmentDAI;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class FilterDataMakeAppointment {

    private final AIService aiService;

    public FilterDataMakeAppointment(AIService aiService) {
        this.aiService = aiService;
    }

    public AppointmentDAI parseExtractedData(String extractedString) {
        AppointmentDAI session = new AppointmentDAI();
        String[] lines = extractedString.split("\\n");
        for (String line : lines) {
            if (line.startsWith("Hours:")) {
                String time = line.replace("Hours:", "").trim();
                session.setHours(time.equals("?") ? null : time);
            } else if (line.startsWith("Date:")) {
                String date = line.replace("Date:", "").trim();
                session.setDate(date.equals("?") ? null : date);
            } else if (line.startsWith("Doctor:")) {
                String doctor = line.replace("Doctor:", "").trim();
                session.setDoctorName(doctor.equals("?") ? null : doctor);
            } else if (line.startsWith("Hospital:")) {
                String hospital = line.replace("Hospital:", "").trim();
                session.setHospitalName(hospital.equals("?") ? null : hospital);
            }
        }
        return session;
    }



    private String promptToGetDataMakeAppointment(String userMessage, String historyWithUser) {
        Date date = new Date();
        String prompt = String.format("""
    You are a highly accurate data extraction system for medical appointment scheduling in the KiviCare hospital system.
    
    Today is: %s
    
    Task:
    - Analyze the entire conversation history and especially the most recent user message.
    - Always prioritize the information in the most recent user message. If there are conflicting details, the user's most recent message should override the previous ones.
    - Extract the following information if available:
        • Hours (appointment time) in 24-hour format as HH:mm (Example: 14:30)
        • Date (appointment date) in format dd/MM/yyyy
        • Doctor (if mentioned)
        • Hospital (facility name)

    Response Format:
    Hours: ?
    Date: ?
    Doctor: ?
    Hospital: ?

    Notes:
    - Hours must always be returned in 24-hour format as HH:mm (Example: 09:00, 14:30). If time is mentioned vaguely (e.g., "sáng", "chiều"), you must try to resolve it to an exact hour. If unable to determine, keep '?'.
    - Date must always be returned in dd/MM/yyyy format. If the date is relative (e.g., "ngày mai", "hôm nay"), you must convert it to an exact date based on today's date (%s).
    - If any information is missing, keep the question mark (?) for that field.
    - Do NOT return anything else. Strictly follow the exact response format.
    - Conversation history may contain outdated information. The user's most recent message is always the most up-to-date and must override any previous conflicting information.

    --- Conversation History ---
    %s

    --- Current User Message (Highest Priority) ---
    %s

    Provide the extracted information now:
    """, date.toString(),new SimpleDateFormat("dd/MM/yyyy").format(date), historyWithUser, userMessage);

        return aiService.complete(prompt);
    }





    public String toFilterDataMakeAppointment(String userMessage, String historyWithUser){
        StringBuilder dataMakeAppointment = new StringBuilder();
        dataMakeAppointment.append(promptToGetDataMakeAppointment(userMessage,historyWithUser));

        AppointmentDAI appointmentDAI = parseExtractedData(dataMakeAppointment.toString());
        System.out.println(dataMakeAppointment);
        return dataMakeAppointment.toString();
    }
}
