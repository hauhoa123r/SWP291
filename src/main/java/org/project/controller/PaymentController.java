package org.project.controller;

import org.project.model.response.PaymentResponse;
import org.project.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.Optional;

@Controller
@RequestMapping("/payment") // Base path for Thymeleaf views
public class PaymentController {

    private final int PAGE_SIZE = 10; // Default page size for Thymeleaf view
    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    /**
     * Displays a paginated list of payments with search and filter capabilities.
     * This method handles the GET request for the main payment list page.
     *
     * @param model        The Spring Model to pass data to the Thymeleaf template.
     * @param pageIndex    The 1-indexed page number from the path variable.
     * @param size         The number of items per page.
     * @param sort         An array of strings representing the sort criteria (e.g., "id,desc").
     * @param searchTerm   An optional search term for filtering by customer full name.
     * @param filterStatus An optional filter for payment status (e.g., "SUCCESSED", "PENDING", "FAILED").
     * @return The name of the Thymeleaf template to render (e.g., "dashboard/payment-list").
     */
    @GetMapping("/list/page/{pageIndex}")
    public String listPayments(
            Model model,
            @PathVariable int pageIndex,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String[] sort,
            @RequestParam(required = false) String searchTerm,
            @RequestParam(required = false) String filterStatus) {

        // Manually assign default values if not provided
        int currentPage = (pageIndex > 0) ? pageIndex : 1;
        int currentSize = (size != null && size > 0) ? size : 10;
        // Changed default sort from "paymentId,desc" to "id,desc" to match PaymentEntity's ID field
        String[] currentSort = (sort != null && sort.length > 0) ? sort : new String[]{"id", "desc"};
        String currentFilterStatus = (filterStatus != null && !filterStatus.isEmpty()) ? filterStatus : "All";


        String sortBy = currentSort[0];
        Sort.Direction direction = currentSort.length > 1 && currentSort[1].equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sortOrder = Sort.by(direction, sortBy);

        // Convert 1-indexed pageIndex to 0-indexed for Spring Data JPA Pageable
        Pageable pageable = PageRequest.of(currentPage - 1, currentSize, sortOrder);
        Page<PaymentResponse> paymentPage = paymentService.getAllPayments(pageable, searchTerm, currentFilterStatus);

        model.addAttribute("paymentPage", paymentPage);
        model.addAttribute("payments", paymentPage.getContent());
        model.addAttribute("currentPage", currentPage); // Pass 1-indexed page to Thymeleaf
        model.addAttribute("pageSize", currentSize);
        model.addAttribute("totalPages", paymentPage.getTotalPages());
        model.addAttribute("totalItems", paymentPage.getTotalElements());
        model.addAttribute("searchTerm", searchTerm);
        model.addAttribute("filterStatus", currentFilterStatus);
        model.addAttribute("sortField", sortBy);
        model.addAttribute("sortDirection", direction.toString().toLowerCase());

        // Add an empty PaymentResponse object for the add/edit form in the modal
        model.addAttribute("newPayment", new PaymentResponse());

        return "dashboard/payment-list"; // Assuming this is your Thymeleaf template name
    }

    /**
     * Retrieves a single payment by its ID. This endpoint is typically used by AJAX
     * requests from the frontend to populate an edit form.
     *
     * @param id The ID of the payment to retrieve.
     * @return A ResponseEntity containing the PaymentResponse if found, or a 404 Not Found status.
     */
    @GetMapping("/{id}")
    @ResponseBody // Indicates that the return value should be bound to the web response body (JSON)
    public ResponseEntity<PaymentResponse> getPaymentById(@PathVariable Long id) {
        Optional<PaymentResponse> payment = paymentService.getPaymentById(id);
        return payment.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Saves a payment (either creates a new one or updates an existing one).
     * This method handles the POST request from the add/edit form.
     *
     * @param paymentResponse    The PaymentResponse object received from the form submission.
     * @param redirectAttributes Used to add flash attributes for success/error messages
     *                           that will be available after a redirect.
     * @return A redirect string to the payment list page.
     */
    @PostMapping("/save")
    public String savePayment(@ModelAttribute("newPayment") PaymentResponse paymentResponse,
                              RedirectAttributes redirectAttributes) {
        try {
            // Simple validation (can be replaced with @Valid and BindingResult for more robust validation)
            if (paymentResponse.getAmount() == null || paymentResponse.getAmount().compareTo(BigDecimal.ZERO) < 0) {
                redirectAttributes.addFlashAttribute("errorMessage", "Số tiền không hợp lệ.");
                return "redirect:/payment/list/page/1"; // Redirect to first page on error
            }
            if (paymentResponse.getPaymentMethod() == null || paymentResponse.getPaymentMethod().isEmpty()) {
                redirectAttributes.addFlashAttribute("errorMessage", "Phương thức thanh toán không được để trống.");
                return "redirect:/payment/list/page/1";
            }
            if (paymentResponse.getPaymentStatus() == null || paymentResponse.getPaymentStatus().isEmpty()) {
                redirectAttributes.addFlashAttribute("errorMessage", "Trạng thái thanh toán không được để trống.");
                return "redirect:/payment/list/page/1";
            }

            paymentService.savePayment(paymentResponse);
            redirectAttributes.addFlashAttribute("successMessage", "Thanh toán đã được lưu thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi lưu thanh toán: " + e.getMessage());
        }
        return "redirect:/payment/list/page/1"; // Redirect to first page on success
    }

    /**
     * Deletes a payment by its ID.
     * This method handles the POST request for deleting a payment.
     *
     * @param id                 The ID of the payment to delete.
     * @param redirectAttributes Used to add flash attributes for success/error messages.
     * @return A redirect string to the payment list page.
     */
    @PostMapping("/delete/{id}")
    public String deletePayment(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            paymentService.deletePayment(id);
            redirectAttributes.addFlashAttribute("successMessage", "Thanh toán đã được xóa thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi xóa thanh toán: " + e.getMessage());
        }
        return "redirect:/payment/list/page/1"; // Redirect to first page after delete
    }

    // Existing methods from your provided controller, kept for completeness
    @RequestMapping("/patient")
    public String payment() {
        return "dashboard/patient-payments";
    }

    @RequestMapping("/checkout")
    public String checkout() {
        return "frontend/checkout";
    }

    @RequestMapping("/order-received.html")
    public String order() {
        return "frontend/order-received";
    }
}
