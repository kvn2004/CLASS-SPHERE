package lk.vihanganimsara.classsphere.repository;

import lk.vihanganimsara.classsphere.entity.Attendance;
import lk.vihanganimsara.classsphere.entity.CourseSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ClassSessionsRepo extends JpaRepository<CourseSession, String> {
    @Query("SELECT c FROM CourseSession c WHERE TRIM(c.sessionId) = :sessionId")
    Optional<CourseSession> findByTrimmedSessionId(@Param("sessionId") String sessionId);

    List<CourseSession> findBySessionDateBetween(LocalDate start, LocalDate end);
    List<CourseSession> findBySessionDate(LocalDate date);
}
