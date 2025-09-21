package lk.vihanganimsara.classsphere.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDetailDTO {
    private String studentName;
    private String courseName;
    private BigDecimal amount;
    private LocalDate paymentDate;
}
