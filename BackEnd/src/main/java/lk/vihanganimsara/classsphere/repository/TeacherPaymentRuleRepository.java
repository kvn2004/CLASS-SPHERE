package lk.vihanganimsara.classsphere.repository;

import lk.vihanganimsara.classsphere.entity.TeacherPaymentRule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherPaymentRuleRepository extends JpaRepository<TeacherPaymentRule, Integer> {
    TeacherPaymentRule findTopByOrderByEffectiveDateDesc();
}
