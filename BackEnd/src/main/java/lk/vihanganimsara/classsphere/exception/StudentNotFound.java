package lk.vihanganimsara.classsphere.exception;

public class StudentNotFound extends RuntimeException {
    public StudentNotFound() {
        super("Student not found");
    }
}
