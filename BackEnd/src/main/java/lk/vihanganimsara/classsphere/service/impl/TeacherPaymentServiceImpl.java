package lk.vihanganimsara.classsphere.service.impl;

import jakarta.transaction.Transactional;
import lk.vihanganimsara.classsphere.dto.PaymentDetailDTO;
import lk.vihanganimsara.classsphere.dto.TeacherPaymentReportDTO;
import lk.vihanganimsara.classsphere.entity.Payment;
import lk.vihanganimsara.classsphere.entity.Teacher;
import lk.vihanganimsara.classsphere.entity.TeacherPayment;

import lk.vihanganimsara.classsphere.entity.TeacherPaymentRule;
import lk.vihanganimsara.classsphere.repository.PaymentRepository;
import lk.vihanganimsara.classsphere.repository.TeacherPaymentRepository;
import lk.vihanganimsara.classsphere.repository.TeacherPaymentRuleRepository;
import lk.vihanganimsara.classsphere.service.TeacherPaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeacherPaymentServiceImpl implements TeacherPaymentService {
    private final PaymentRepository paymentRepo;
    private final TeacherPaymentRuleRepository ruleRepo;
    private final TeacherPaymentRepository teacherPaymentRepo;

    // Generate payment report for a month
    @Override
    @Transactional
    public List<TeacherPaymentReportDTO> generateMonthlyReport(LocalDate month) {

        // 1. Get all payments for the month
        List<Payment> payments = paymentRepo.findByPaymentMonth(month);

        // 2. Group by Teacher
        Map<Teacher, List<Payment>> grouped = payments.stream()
                .collect(Collectors.groupingBy(p -> p.getCourse().getTeacher()));

        List<TeacherPaymentReportDTO> reports = new ArrayList<>();

        for (Map.Entry<Teacher, List<Payment>> entry : grouped.entrySet()) {
            Teacher teacher = entry.getKey();
            List<Payment> teacherPayments = entry.getValue();

            BigDecimal totalCollected = teacherPayments.stream()
                    .map(Payment::getAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            // 3. Calculate teacher share (example using percentage from first rule)
            TeacherPaymentRule rule = ruleRepo.findTopByOrderByEffectiveDateDesc(); // adjust query
            BigDecimal teacherShare = totalCollected.multiply(rule.getPercentage())
                    .divide(BigDecimal.valueOf(100));

            // 4. Build DTO
            TeacherPaymentReportDTO reportDTO = new TeacherPaymentReportDTO();
            reportDTO.setTeacherId(teacher.getId());
            reportDTO.setTeacherName(teacher.getName());
            reportDTO.setPaymentMonth(month);
            reportDTO.setTotalCollected(totalCollected);
            reportDTO.setTeacherShare(teacherShare);

            // Optional: add session/student breakdown
            List<PaymentDetailDTO> details = teacherPayments.stream().map(p -> {
                PaymentDetailDTO d = new PaymentDetailDTO();
                d.setStudentName(p.getStudent().getName());
                d.setCourseName(p.getCourse().getTitle());
                d.setAmount(p.getAmount());
                d.setPaymentDate(p.getPaymentMonth());
                return d;
            }).toList();

            reportDTO.setPaymentDetails(details);

            reports.add(reportDTO);

//            // Optional: save TeacherPayment entity
//            TeacherPayment tp = new TeacherPayment();
//            tp.setTeacher(teacher);
//            tp.setPaymentMonth(month);
//            tp.setTotalCollected(totalCollected);
//            tp.setTeacherShare(teacherShare);
//            tp.setCalcDetails(details.toString()); // optional JSON
//            teacherPaymentRepo.save(tp);
        }

        return reports;
    }

    @Override
    public byte[] generateMonthlyReportPdf(LocalDate month) {
        return new byte[0];
    }
}
