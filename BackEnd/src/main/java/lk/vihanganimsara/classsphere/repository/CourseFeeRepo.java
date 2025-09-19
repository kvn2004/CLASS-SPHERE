package lk.vihanganimsara.classsphere.repository;

import aj.org.objectweb.asm.commons.Remapper;
import lk.vihanganimsara.classsphere.entity.CourseFee;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CourseFeeRepo extends JpaRepository<CourseFee, Long> {
    @Query("SELECT cf FROM CourseFee cf WHERE cf.course.courseId = :courseId ORDER BY cf.effectiveDate DESC")
    List<CourseFee> findLatestFeeByCourseId(@Param("courseId") String courseId, Pageable pageable);
}
