package org.project.controller;

import org.project.model.response.PaymentResponse;
import org.project.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/payment")
public class PaymentController {

    private final int PAGE_SIZE = 10;
    private PaymentService paymentService;

    @Autowired
    public void setPaymentService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @RequestMapping("/patient")
    public String payment() {
        return "dashboard/patient-payments";
    }

    @RequestMapping("/list/page/{pageIndex}")
    public String paymentList(@PathVariable int pageIndex, Model model) {
        Page<PaymentResponse> paymentResponsePage = paymentService.getAllPayments(pageIndex, PAGE_SIZE);
        model.addAttribute("payments", paymentResponsePage.getContent());
        model.addAttribute("currentPage", pageIndex);
        model.addAttribute("totalPages", paymentResponsePage.getTotalPages());
        return "dashboard/payment-list";
    }

    @RequestMapping("/checkout")
    public String checkout() {
        return "frontend/checkout.html";
    }

    @RequestMapping("/order-received.html")
    public String order() {
        return "frontend/order-received";
    }


}
