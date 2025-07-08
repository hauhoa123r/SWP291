package org.project.api;

import jakarta.servlet.http.HttpServletRequest;
import org.project.service.VNPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@org.springframework.stereotype.Controller
@RequestMapping("api/payments")
public class VNPAYAPI {

    @Autowired
    private VNPayService vnPayService;

    @GetMapping("")
    public String home() {
        return "index";
    }

    @PostMapping("/submitOrder")
    public String submitOrder(@RequestParam("orderInfo") String orderInfo,
                              @RequestParam("orderId") Long orderId,
                              @RequestParam("userId") Long userId,
                              HttpServletRequest request, Model model) {
        BigDecimal totalAmount = vnPayService.calculateTotalAmount(userId);

        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        String vnpayUrl = vnPayService.createOrder(orderId, orderInfo, baseUrl, totalAmount);
        return "redirect:" + vnpayUrl;
    }


    @GetMapping("/vnpay-payment")
    public String GetMapping(HttpServletRequest request, Model model) {
        int paymentStatus = vnPayService.orderReturn(request);

        String orderInfo = request.getParameter("vnp_OrderInfo");
        String paymentTime = request.getParameter("vnp_PayDate");
        String transactionId = request.getParameter("vnp_TransactionNo");
        String totalPrice = request.getParameter("vnp_Amount");

        model.addAttribute("orderId", orderInfo);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("paymentTime", paymentTime);
        model.addAttribute("transactionId", transactionId);

        return paymentStatus == 1 ? "ordersuccess" : "orderfail";
    }
}
