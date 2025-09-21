package lk.vihanganimsara.classsphere.controller;

import lk.vihanganimsara.classsphere.dto.ApiResponse;
import lk.vihanganimsara.classsphere.dto.AttendanceDto;
import lk.vihanganimsara.classsphere.entity.AttendanceStatus;
import lk.vihanganimsara.classsphere.service.impl.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/attendance")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AttendanceController {

    private final AttendanceService attendanceService;

    @PostMapping("/mark")
    public ResponseEntity<ApiResponse> markAttendance(
            @RequestParam String qrCode,
            @RequestParam String sessionId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String performedBy = authentication != null ? authentication.getName() : "SYSTEM";
        attendanceService.markAttendance(qrCode, sessionId, performedBy);

        return new ResponseEntity<>(
                new ApiResponse(200, "Attendance marked successfully", true),
                HttpStatus.OK
        );
    }

    @PostMapping("/markAttendanceByStudentId")
    public ResponseEntity<ApiResponse> markAttendanceByStudentId(
            @RequestParam String studentId,
            @RequestParam String sessionId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String performedBy = authentication != null ? authentication.getName() : "SYSTEM";

        attendanceService.markAttendanceByStudentId(studentId, sessionId, performedBy);

        return new ResponseEntity<>(
                new ApiResponse(200, "Attendance marked successfully", true),
                HttpStatus.OK
        );
    }



    @GetMapping("/filter")
    public ApiResponse filterAttendance(
            @RequestParam(required = false) String sessionId,
            @RequestParam(required = false) String studentId,
            @RequestParam(required = false) AttendanceStatus status,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate
    ) {
        return new ApiResponse(
                200,
                "Filtered attendance records",
                attendanceService.filterAttendance(sessionId, studentId, status, startDate, endDate)
        );
    }

    @GetMapping("/recent")
    public List<AttendanceDto> getRecentAttendances(@RequestParam int limit) {
        return attendanceService.getRecentAttendances(limit);
    }

    @GetMapping("/summaryToday")
    public ResponseEntity<ApiResponse> getTodaySummary() {
        Map<String, Long> summary = attendanceService.getTodaySummary();
        return ResponseEntity.ok(
                new ApiResponse(200, "Attendance summary for today", summary)
        );
    }

}

