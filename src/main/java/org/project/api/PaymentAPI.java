package org.project.api;

import org.project.enums.PaymentMethod;
import org.project.model.request.PaymentRequest;
import org.project.model.response.PaymentResponse;
import org.project.service.PaymentService;
import org.project.service.impl.PaymentServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponse> getPaymentById(@PathVariable Long id) {
        Optional<PaymentResponse> payment = paymentService.getPaymentById(id);
        return payment.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/save")
    public ResponseEntity<PaymentResponse> savePayment(@RequestBody PaymentResponse paymentResponse) {
        PaymentResponse savedPayment = paymentService.savePayment(paymentResponse);
        return ResponseEntity.status(savedPayment.getPaymentId() != null ? HttpStatus.OK : HttpStatus.CREATED).body(savedPayment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable Long id) {
        paymentService.deletePayment(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/methods")
    public ResponseEntity<List<PaymentMethod>> getAllPaymentMethods() {
        List<PaymentMethod> methods = paymentService.getAllPaymentMethods();
        return ResponseEntity.ok(methods);
    }


}
