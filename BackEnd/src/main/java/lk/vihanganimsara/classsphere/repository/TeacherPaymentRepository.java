package lk.vihanganimsara.classsphere.repository;

import lk.vihanganimsara.classsphere.entity.TeacherPayment;
import lk.vihanganimsara.classsphere.entity.TeacherPaymentRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface TeacherPaymentRepository extends JpaRepository<TeacherPaymentRule, Long> {
    @Query("SELECT t FROM TeacherPaymentRule t WHERE t.paymentMonth = :date")
    List<TeacherPaymentRule> findByPaymentMonth(@Param("date") LocalDate date);
}
