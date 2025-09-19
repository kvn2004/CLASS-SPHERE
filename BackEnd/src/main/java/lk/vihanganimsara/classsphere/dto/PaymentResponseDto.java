package lk.vihanganimsara.classsphere.dto;

import lk.vihanganimsara.classsphere.entity.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponseDto {
    private Integer paymentId;
    private String studentId;
    private String studentName;
    private String courseId;
    private String courseTitle;
    private LocalDate paymentMonth;
    private BigDecimal amount;
    private PaymentMethod paymentMethod;
    private LocalDateTime paidAt;
    private String notes;
}
