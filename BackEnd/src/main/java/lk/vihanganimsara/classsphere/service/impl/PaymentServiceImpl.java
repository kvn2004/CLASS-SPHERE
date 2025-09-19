package lk.vihanganimsara.classsphere.service.impl;

import jakarta.transaction.Transactional;
import lk.vihanganimsara.classsphere.dto.DtoCourse;
import lk.vihanganimsara.classsphere.dto.PaymentRequestDto;
import lk.vihanganimsara.classsphere.dto.PaymentResponseDto;
import lk.vihanganimsara.classsphere.entity.Course;
import lk.vihanganimsara.classsphere.entity.CourseFee;
import lk.vihanganimsara.classsphere.entity.Payment;
import lk.vihanganimsara.classsphere.entity.Student;
import lk.vihanganimsara.classsphere.repository.ClasesRepo;
import lk.vihanganimsara.classsphere.repository.CourseFeeRepo;
import lk.vihanganimsara.classsphere.repository.PaymentRepository;
import lk.vihanganimsara.classsphere.repository.StudentRepo;
import lk.vihanganimsara.classsphere.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final StudentRepo studentRepository;
    private final ClasesRepo courseRepository;
    private final CourseFeeRepo courseFeeRepository;

    @Override
    public PaymentResponseDto createPayment(PaymentRequestDto dto) {
        Student student = studentRepository.findById(dto.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Course course = courseRepository.findById(dto.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found"));

        // Optional: Check if student is enrolled in course
        boolean enrolled = student.getEnrollments().stream()
                .anyMatch(e -> e.getCourse().getCourseId().equals(course.getCourseId()));
        if (!enrolled) {
            throw new RuntimeException("Student is not enrolled in the selected course");
        }

        Payment payment = new Payment();
        payment.setStudent(student);
        payment.setCourse(course);
        payment.setPaymentMonth(dto.getPaymentMonth());
        payment.setAmount(dto.getAmount());
        payment.setPaymentMethod(dto.getPaymentMethod());
        payment.setNotes(dto.getNotes());

        Payment saved = paymentRepository.save(payment);
        return mapToDto(saved);
    }

    @Override
    public List<PaymentResponseDto> getPaymentsByStudent(String studentId) {
        return paymentRepository.findByStudentId(studentId).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private PaymentResponseDto mapToDto(Payment payment) {
        PaymentResponseDto dto = new PaymentResponseDto();
        dto.setPaymentId(payment.getPaymentId());
        dto.setStudentId(payment.getStudent().getId());
        dto.setStudentName(payment.getStudent().getName());
        dto.setCourseId(payment.getCourse().getCourseId());
        dto.setCourseTitle(payment.getCourse().getTitle());
        dto.setPaymentMonth(payment.getPaymentMonth());
        dto.setAmount(payment.getAmount());
        dto.setPaymentMethod(payment.getPaymentMethod());
        dto.setPaidAt(payment.getPaidAt());
        dto.setNotes(payment.getNotes());
        return dto;
    }

    @Override
    public List<DtoCourse> getEnrolledCourses(String studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        return student.getEnrollments().stream()
                .map(e -> new DtoCourse(e.getCourse().getCourseId(), e.getCourse().getTitle()))
                .toList();
    }

    // Fetch latest course fee
    public BigDecimal getCourseFee(String courseId) {
        Pageable pageable = PageRequest.of(0, 1); // only get the latest fee
        List<CourseFee> fees = courseFeeRepository.findLatestFeeByCourseId(courseId, pageable);

        return fees.isEmpty() ? BigDecimal.ZERO : fees.get(0).getMonthlyFee();
    }

    public List<PaymentResponseDto> getRecentPayments(int limit) {
        List<Payment> payments = paymentRepository.findRecentPayments(PageRequest.of(0, limit));
        return payments.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<PaymentResponseDto> getRecentPaymentsByStudent(String studentId, int limit) {
        List<Payment> payments = paymentRepository.findRecentPaymentsByStudent(studentId, PageRequest.of(0, limit));
        return payments.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private PaymentResponseDto toDto(Payment p) {
        PaymentResponseDto dto = new PaymentResponseDto();
        dto.setPaymentId(p.getPaymentId());
        dto.setStudentId(p.getStudent().getId());
        dto.setCourseId(p.getCourse().getCourseId());
        dto.setPaymentMonth(p.getPaymentMonth());
        dto.setAmount(p.getAmount());
        dto.setPaymentMethod(p.getPaymentMethod());
        dto.setNotes(p.getNotes());
        return dto;
    }

}
