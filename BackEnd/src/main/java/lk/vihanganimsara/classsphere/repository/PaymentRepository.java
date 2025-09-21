package lk.vihanganimsara.classsphere.repository;

import lk.vihanganimsara.classsphere.entity.Payment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    List<Payment> findByStudentId(String studentId);

    // Fetch recent payments (e.g., latest N)
    @Query("SELECT p FROM Payment p ORDER BY p.paymentMonth DESC")
    List<Payment> findRecentPayments(Pageable pageable);

    // Fetch recent payments for a student
    @Query("SELECT p FROM Payment p WHERE p.student.id = :studentId ORDER BY p.paymentMonth DESC")
    List<Payment> findRecentPaymentsByStudent(String studentId, Pageable pageable);

    List<Payment> findByPaymentMonth(LocalDate paymentMonth);
}
