package org.project.controller;

import org.project.entity.*;
import org.project.enums.PaymentMethod;
import org.project.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;
    @GetMapping("/calculateTotalAmount")
    public BigDecimal calculateTotalAmount(@RequestParam Long prescriptionId) {
        return paymentService.calculateTotalAmount(prescriptionId);
    }
    @GetMapping("/checkWalletBalance")
    public String checkWalletBalance(@RequestParam Long userId, @RequestParam BigDecimal totalAmount) {
        try {
            WalletEntity wallet = paymentService.checkWalletBalance(userId, totalAmount);
            return "Sufficient balance in wallet ID " + wallet.getId();
        } catch (RuntimeException e) {
            return "Insufficient balance: " + e.getMessage();
        }
    }
    @PostMapping("/createPayment")
    public String createPayment(@RequestParam BigDecimal totalAmount, @RequestParam PaymentMethod paymentMethod) {
        PaymentEntity payment = paymentService.createPayment(totalAmount, paymentMethod);
        return "Payment created with ID: " + payment.getId() + " and status: " + payment.getPaymentStatus();
    }
    @PostMapping("/updateWalletBalance")
    public String updateWalletBalance(@RequestParam Long userId, @RequestParam BigDecimal totalAmount) {
        WalletEntity wallet = paymentService.checkWalletBalance(userId, totalAmount);
        paymentService.updateWalletBalance(wallet, totalAmount);
        return "Wallet balance updated for wallet ID: " + wallet.getId();
    }
    @PostMapping("/saveWalletTransaction")
    public String saveWalletTransaction(@RequestParam Long walletId, @RequestParam Long paymentId, @RequestParam BigDecimal totalAmount, @RequestParam Long prescriptionId) {
        WalletEntity wallet = new WalletEntity();
        PaymentEntity payment = new PaymentEntity();
        paymentService.saveWalletTransaction(wallet, payment, totalAmount, prescriptionId);
        return "Wallet transaction saved for payment ID: " + paymentId;
    }
    @PostMapping("/finalizePayment")
    public String finalizePayment(@RequestParam Long paymentId) {
        PaymentEntity payment = new PaymentEntity();
        paymentService.finalizePayment(payment);
        return "Payment finalized with status: " + payment.getPaymentStatus();
    }
}
