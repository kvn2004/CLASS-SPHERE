package lk.vihanganimsara.classsphere.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "Teacher_PaymentRule")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherPaymentRule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ruleId;
    private LocalDate paymentMonth;
    @Enumerated(EnumType.STRING)
    private HallType hallType;

    @Column(nullable = false)
    private BigDecimal percentage;
    private LocalDate effectiveDate;
}

