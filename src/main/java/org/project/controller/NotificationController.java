package org.project.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {

    // lấy danh sách thông báo của user
    @GetMapping("/user/{userId}")
    public String getByUser(@PathVariable Long userId, HttpSession session) {
        session.setAttribute("userId", userId);
        return "dashboard/report";
    }

    //    @GetMapping("/my")
//  public ResponseEntity<List<NotificationDTO>> getMyNotifications()
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        String username = auth.getName(); // lấy username từ token
//        UserEntity user = userRepository.findByUsername(username)
//                .orElseThrow(() -> new RuntimeException("User not found"));
}


