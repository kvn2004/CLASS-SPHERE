package lk.vihanganimsara.classsphere.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherPaymentReportDTO {
    private String teacherId;
    private String teacherName;
    private LocalDate paymentMonth;
    private BigDecimal totalCollected;
    private BigDecimal teacherShare;
    private List<PaymentDetailDTO> paymentDetails; // optional session-wise breakdown
}