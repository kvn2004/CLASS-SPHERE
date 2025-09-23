package lk.vihanganimsara.classsphere.exception;

public class DuplicateAttendence extends RuntimeException {
    public DuplicateAttendence() {
        super("Attendance is already in Marked");
    }
}
