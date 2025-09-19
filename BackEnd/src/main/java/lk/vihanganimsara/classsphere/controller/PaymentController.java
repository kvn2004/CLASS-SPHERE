package lk.vihanganimsara.classsphere.controller;




import lk.vihanganimsara.classsphere.dto.DtoCourse;
import lk.vihanganimsara.classsphere.dto.PaymentRequestDto;
import lk.vihanganimsara.classsphere.dto.PaymentResponseDto;
import lk.vihanganimsara.classsphere.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin(origins = "*")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<PaymentResponseDto> createPayment(@RequestBody PaymentRequestDto dto) {
        return ResponseEntity.ok(paymentService.createPayment(dto));
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<PaymentResponseDto>> getPaymentsByStudent(@PathVariable String studentId) {
        return ResponseEntity.ok(paymentService.getPaymentsByStudent(studentId));
    }

    @GetMapping("/student/{studentId}/courses")
    public ResponseEntity<List<DtoCourse>> getEnrolledCourses(@PathVariable String studentId) {
        return ResponseEntity.ok(paymentService.getEnrolledCourses(studentId));
    }
    @GetMapping("/course/{courseId}/fee")
    public ResponseEntity<BigDecimal> getCourseFee(@PathVariable String courseId) {
        BigDecimal fee = paymentService.getCourseFee(courseId);
        return ResponseEntity.ok(fee);
    }

    // Fetch recent payments globally
    @GetMapping("/recent")
    public ResponseEntity<List<PaymentResponseDto>> getRecentPayments(@RequestParam(defaultValue = "5") int limit) {
        return ResponseEntity.ok(paymentService.getRecentPayments(limit));
    }

    // Fetch recent payments for a student
    @GetMapping("/student/{studentId}/recent")
    public ResponseEntity<List<PaymentResponseDto>> getRecentPaymentsByStudent(
            @PathVariable String studentId,
            @RequestParam(defaultValue = "5") int limit
    ) {
        return ResponseEntity.ok(paymentService.getRecentPaymentsByStudent(studentId, limit));
    }
}

