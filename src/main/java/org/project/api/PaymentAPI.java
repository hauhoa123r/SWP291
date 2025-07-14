package org.project.api;

import org.project.enums.PaymentMethod;
import org.project.model.request.PaymentRequest;
import org.project.model.response.PaymentResponse;
import org.project.service.PaymentService;
import org.project.service.impl.PaymentServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payment")
public class PaymentAPI {
    private final PaymentService paymentService;
    private final PaymentServiceImpl paymentServiceImpl;

    public PaymentAPI(PaymentService paymentService, PaymentServiceImpl paymentServiceImpl) {
        this.paymentService = paymentService;
        this.paymentServiceImpl = paymentServiceImpl;
    }


    @PostMapping("/execute-payment")
    public ResponseEntity<PaymentResponse> executePayment(@RequestBody PaymentRequest paymentRequest) {

        PaymentResponse paymentResponse = paymentService.executePaymentTransaction(paymentRequest);

        return ResponseEntity.ok(paymentResponse);
    }


//    @GetMapping
//    public ResponseEntity<List<PaymentResponse>> getAllPayments(
//            @RequestParam(defaultValue = "1") int page,
//            @RequestParam(defaultValue = "10") int size) {
//
//        List<PaymentResponse> payments = paymentService.getAllPayments(page, size);
//        return ResponseEntity.ok(payments);
//    }

    @GetMapping("/methods")
    public ResponseEntity<List<PaymentMethod>> getAllPaymentMethods() {
        List<PaymentMethod> methods = paymentService.getAllPaymentMethods();
        return ResponseEntity.ok(methods);
    }


}
