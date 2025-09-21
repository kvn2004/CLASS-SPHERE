package lk.vihanganimsara.classsphere.controller;

import lk.vihanganimsara.classsphere.dto.TeacherPaymentReportDTO;
import lk.vihanganimsara.classsphere.service.TeacherPaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/teacher-payments")
@RequiredArgsConstructor
public class TeacherPaymentController {

    private final TeacherPaymentService teacherPaymentService;

    /**
     * Get teacher payment report for a specific month
     * Example: /api/teacher-payments/report?month=2025-09-01
     */
    @GetMapping("/report")
    public ResponseEntity<List<TeacherPaymentReportDTO>> getMonthlyReport(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate month) {

        List<TeacherPaymentReportDTO> reports = teacherPaymentService.generateMonthlyReport(month);
        return ResponseEntity.ok(reports);
    }

    @GetMapping("/report/pdf")
    public ResponseEntity<byte[]> getMonthlyReportPdf(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate month) {

        byte[] pdfBytes = teacherPaymentService.generateMonthlyReportPdf(month); // implement in service
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=teacher-report.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }
}