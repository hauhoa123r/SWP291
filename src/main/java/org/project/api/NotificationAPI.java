package org.project.api;


import lombok.RequiredArgsConstructor;
import org.project.entity.UserEntity;
import org.project.model.request.NotificationRequest;
import org.project.model.response.NotificationResponse;
import org.project.repository.UserRepository;
import org.project.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationAPI {

    private final NotificationService notificationService;
    private final UserRepository userRepository;


    // gửi thông báo thu cong
    @PostMapping("/send")
    public ResponseEntity<String> sendNotification(@RequestBody NotificationRequest request) {
        UserEntity user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy user"));

        notificationService.send(user, request.getTitle(), request.getContent());

        return ResponseEntity.ok("Notification sent successfully");
    }


    //    @GetMapping("/user/{userId}") // my
//    public ResponseEntity<List<NotificationEntity>> getByUser(@PathVariable Long userId) {
//
//        //        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        //        String email = auth.getName(); // username từ token
//        //        UserEntity user = userRepository.findByUsername(username).orElseThrow();
//
//        return ResponseEntity.ok(notificationService.getAllByUserId(userId));
//
//    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<NotificationResponse>> getByUserId(@PathVariable Long userId) {
        List<NotificationResponse> notifications = notificationService.getAllByUserId(userId);
        return ResponseEntity.ok(notifications);
    }

    // Thông báo đặt lịch
    @PostMapping("/appointment/{id}/notify")
    public ResponseEntity<String> notifyAppointment(@PathVariable("id") Long appointmentId) {
        notificationService.sendAppointmentNotification(appointmentId);
        return ResponseEntity.ok("Đã gửi thông báo xác nhận đặt lịch thành công.");
    }

    // Thông báo thay đổi lịch
    @PostMapping("/appointment/{id}/change")
    public ResponseEntity<String> notifyAppointmentChange(@PathVariable("id") Long appointmentId) {
        notificationService.sendAppointmentChangeNotification(appointmentId);
        return ResponseEntity.ok("Đã gửi thông báo thay đổi lịch hẹn.");
    }

    // Thông báo hủy lịch
    @PostMapping("/appointment/{id}/cancel")
    public ResponseEntity<String> notifyAppointmentCancel(@PathVariable("id") Long appointmentId) {
        notificationService.sendAppointmentCancelNotification(appointmentId);
        return ResponseEntity.ok("Đã gửi thông báo hủy lịch hẹn.");
    }


    // Thông báo sinh nhật
    @GetMapping("/remind-birthday")
    public ResponseEntity<String> sendBirthdayReminders() {
        int count = notificationService.sendBirthdayNotifications();
        return ResponseEntity.ok("Đã gửi sinh nhật cho " + count + " bệnh nhân hôm nay.");
    }

    // Thông báo nhắc trước 1 ngày
    @GetMapping("/remind-tomorrow")
    public ResponseEntity<String> sendReminderForTomorrowAppointments() {
        int count = notificationService.sendTomorrowAppointmentReminders();
        return ResponseEntity.ok("Đã gửi thông báo nhắc lịch cho " + count + " cuộc hẹn ngày mai.");
    }

    // Thông báo kết quả
//    @PostMapping("/appointment/{id}/lab-result")
//    public ResponseEntity<String> notifyLabResult(@PathVariable("id") Long appointmentId) {
//        // Gọi phương thức của NotificationService để gửi thông báo kết quả xét nghiệm
//        boolean isSent = notificationService.sendLabResultNotification(appointmentId);
//
//        if (isSent) {
//            return ResponseEntity.ok("Đã gửi thông báo kết quả xét nghiệm.");
//        } else {
//            return ResponseEntity.ok("Chưa có kết quả xét nghiệm để gửi.");
//        }
//    }


    // Thông báo kết quả
//    @PostMapping("/appointment/{id}/lab-result")
//    public ResponseEntity<String> notifyLabResult(@PathVariable("id") Long appointmentId) {
//        AppointmentEntity appt = appointmentRepository.findById(appointmentId)
//                .orElseThrow(() -> new RuntimeException("Không tìm thấy lịch hẹn"));
//
//        PatientEntity patient = appt.getPatientEntity();
//        UserEntity user = patient.getUserEntity();
//        StaffEntity doctor = appt.getDoctorEntity().getStaffEntity();
//
//        // Lấy các test_request_item có result không null
//        List<TestRequestEntity> testRequests = testRequestRepository.findByAppointmentId(appointmentId);
//
//        boolean hasResult = false;
//
//        for (TestRequestEntity testRequest : testRequests) {
//            List<TestRequestItemEntity> items = testRequestItemRepository.findByTestRequestId(testRequest.getId());
//            for (TestRequestItemEntity item : items) {
//                if (item.getResult() != null && !item.getResult().isBlank()) {
//                    hasResult = true;
//                    break;
//                }
//            }
//            if (hasResult) break;
//        }
//
//        if (!hasResult) {
//            return ResponseEntity.ok("Chưa có kết quả xét nghiệm để gửi.");
//        }
//
//        String doctorName = doctor.getFullName();
//        String patientName = patient.getFullName();
//
//        String title = "🧪 Đã có kết quả xét nghiệm";
//        String content = "Bệnh nhân " + patientName + " đã có kết quả xét nghiệm từ bác sĩ " + doctorName + ".";
//
//        notificationService.sendNotification(user.getId(), title, content);
//
//        return ResponseEntity.ok("Đã gửi thông báo kết quả xét nghiệm.");
//    }

}
