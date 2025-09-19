package lk.vihanganimsara.classsphere.dto;

import lk.vihanganimsara.classsphere.entity.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequestDto {
    private String studentId;       // Student making payment
    private String courseId;        // Selected course
    private LocalDate paymentMonth; // Month for which payment is made
    private BigDecimal amount;      // Payment amount
    private PaymentMethod paymentMethod;
    private String notes;
}
