package lk.vihanganimsara.classsphere.service;

import lk.vihanganimsara.classsphere.dto.TeacherPaymentReportDTO;

import java.time.LocalDate;
import java.util.List;

public interface TeacherPaymentService {
    List<TeacherPaymentReportDTO> generateMonthlyReport(LocalDate month);

    byte[] generateMonthlyReportPdf(LocalDate month);
}
