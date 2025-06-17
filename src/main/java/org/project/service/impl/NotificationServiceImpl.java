package org.project.service.impl;

import lombok.RequiredArgsConstructor;
import org.project.entity.AppointmentEntity;
import org.project.entity.NotificationEntity;
import org.project.entity.PatientEntity;
import org.project.entity.UserEntity;
import org.project.repository.AppointmentRepository;
import org.project.repository.NotificationRepository;
import org.project.repository.PatientRepository;
import org.project.repository.UserRepository;
import org.project.service.EmailService;
import org.project.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final PatientRepository patientRepository;
    private final EmailService emailService;
    private final AppointmentRepository appointmentRepository;

    private static final Logger log = LoggerFactory.getLogger(NotificationServiceImpl.class);

    // Trả danh sách thông báo của user (dùng cho giao diện web)
    @Override
    public List<NotificationEntity> getAllByUserId(Long userId) {
        return notificationRepository.findByUserEntity_IdOrderByCreatedAtDesc(userId);
    }


    // Tạo một thông báo trong DB (hiện trên web)
    @Override
    public NotificationEntity send(UserEntity user, String title, String content) {
        NotificationEntity notification = NotificationEntity.builder()
                .userEntity(user)
                .title(title)
                .content(content)
                .createdAt(LocalDateTime.now())
                .build();
        return notificationRepository.save(notification);
    }

    // Gửi thông báo ( email)
    @Override
    public void sendNotification(Long userId, String title, String content) {
        // Lấy user
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Lưu thông báo cho web
        send(user, title, content);

        // Tìm tất cả bệnh nhân mà user đang sở hữu
        List<PatientEntity> patientList = patientRepository.findByUserEntity_Id(userId);

        // Gửi email cho chính user
        if (user.getEmail() != null && !user.getEmail().isBlank()) {
            emailService.sendEmail(user.getEmail(), title, content);
        }

        // Gửi email đến từng bệnh nhân (nếu có email)
        for (PatientEntity patient : patientList) {
            if (patient.getEmail() != null && !patient.getEmail().isEmpty()) {
                emailService.sendEmail(patient.getEmail(), title, content);
            }
        }
    }

    @Override
    public void sendAppointmentNotification(Long appointmentId) {
        AppointmentEntity appt = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy lịch hẹn"));

        String content = buildAppointmentContent(appt, "Xác nhận lịch hẹn",
                "🔔 Bệnh nhân %s đã đặt lịch với bác sĩ %s vào lúc %s.");

        sendNotification(appt.getPatientEntity().getUserEntity().getId(), "🔔 Xác nhận lịch hẹn", content);
    }

    @Override
    public void sendAppointmentChangeNotification(Long appointmentId) {
        AppointmentEntity appt = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy lịch hẹn"));

        String content = buildAppointmentContent(appt, "Lịch hẹn đã thay đổi",
                "🔄 Lịch hẹn của bệnh nhân %s với bác sĩ %s đã được cập nhật. Thời gian mới: %s.");

        sendNotification(appt.getPatientEntity().getUserEntity().getId(), "🔄 Lịch hẹn đã thay đổi", content);
    }

    @Override
    public void sendAppointmentCancelNotification(Long appointmentId) {
        AppointmentEntity appt = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy lịch hẹn"));

        String content = buildAppointmentContent(appt,"Lịch hẹn đã bị hủy",
                "🚫 Lịch hẹn của bệnh nhân %s với bác sĩ %s vào lúc %s đã bị hủy.");

        sendNotification(appt.getPatientEntity().getUserEntity().getId(), "🚫 Lịch hẹn bị hủy", content);
    }

    @Scheduled(cron = "0 0 8 * * *")
    public int sendTomorrowAppointmentReminders() {
        LocalDateTime tomorrowStart = LocalDate.now().plusDays(1).atStartOfDay();
        LocalDateTime tomorrowEnd = tomorrowStart.plusDays(1).minusSeconds(1);

        List<AppointmentEntity> appts = appointmentRepository.findByStartTimeBetween(tomorrowStart, tomorrowEnd);

        int count = 0;

        for (AppointmentEntity appt : appts) {
            try {
                String content = buildAppointmentContent(appt, "📅 Nhắc lịch khám",
                        "📅 Nhắc nhở: Lịch khám của bệnh nhân %s với bác sĩ %s sẽ diễn ra vào lúc %s.");
                sendNotification(appt.getPatientEntity().getUserEntity().getId(), "📅 Nhắc lịch khám", content);
                count++;
            } catch (Exception e) {
                log.error("Lỗi khi gửi nhắc lịch hẹn ngày mai cho ID: " + appt.getId(), e);
            }
        }

        return count;
    }





    private String buildAppointmentContent(AppointmentEntity appt, String title, String pattern) {
        String patientName = appt.getPatientEntity().getFullName();
        String doctorName = appt.getDoctorEntity().getStaffEntity().getFullName();
        String time = appt.getStartTime().toLocalDateTime().format(DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy"));
        return String.format(pattern, patientName, doctorName, time);
    }


    // Thông báo sinh nhật
    @Scheduled(cron = "0 0 8 * * *")
    public int sendBirthdayNotifications() {
        LocalDate today = LocalDate.now();

        List<PatientEntity> birthdayPatients = patientRepository.findByBirthDateMonthDay(
                today.getMonthValue(), today.getDayOfMonth());

        int count = 0;

        for (PatientEntity patient : birthdayPatients) {
            try {
                UserEntity user = patient.getUserEntity();
                if (user == null || user.getId() == null) continue;

                String patientName = patient.getFullName();
                String email = patient.getEmail();

                String title = "🎉 Chúc mừng sinh nhật!";
                String content = "Chúc mừng sinh nhật " + patientName +
                        "! Chúc bạn một ngày thật vui vẻ và nhiều sức khỏe 💖";

                sendNotification(user.getId(), title, content); // Thông báo web

                if (email != null && !email.isBlank()) {
                    String emailSubject = "🎂 Chúc mừng sinh nhật từ Bệnh viện";
                    String emailContent = "<h3>Chào " + patientName + ",</h3>"
                            + "<p>🎉 Chúc bạn sinh nhật thật vui vẻ, hạnh phúc và thành công!</p>"
                            + "<p>❤️ Cảm ơn bạn đã đồng hành cùng hệ thống của chúng tôi.</p>";

                    emailService.sendEmail(email, emailSubject, emailContent);
                }

                count++;
            } catch (Exception e) {
                log.error("Lỗi khi gửi sinh nhật cho " + patient.getFullName(), e);
            }
        }

        return count;
    }





}
