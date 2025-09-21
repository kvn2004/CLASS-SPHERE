package lk.vihanganimsara.classsphere.repository;

import lk.vihanganimsara.classsphere.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StudentRepo extends JpaRepository<Student, Long> {
    Optional<Student> findByQrCodeContent(String qrCodeContent);

    @Query("SELECT s.id FROM Student s WHERE s.qrCodeContent = :qrCodeContent AND s.deleted = false")
    Optional<String> findIdByQrCodeContent(@Param("qrCodeContent") String qrCodeContent);


    Optional<Student> findById(String id);
}
