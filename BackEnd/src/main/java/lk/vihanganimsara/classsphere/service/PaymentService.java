package lk.vihanganimsara.classsphere.service;

import lk.vihanganimsara.classsphere.dto.DtoCourse;
import lk.vihanganimsara.classsphere.dto.PaymentRequestDto;
import lk.vihanganimsara.classsphere.dto.PaymentResponseDto;

import java.math.BigDecimal;
import java.util.List;

public interface PaymentService {
    PaymentResponseDto createPayment(PaymentRequestDto dto);

    List<PaymentResponseDto> getPaymentsByStudent(String studentId);

    List<DtoCourse> getEnrolledCourses(String studentId);

    BigDecimal getCourseFee(String courseId);

    List<PaymentResponseDto> getRecentPayments(int limit);

    List<PaymentResponseDto> getRecentPaymentsByStudent(String studentId, int limit);
}
