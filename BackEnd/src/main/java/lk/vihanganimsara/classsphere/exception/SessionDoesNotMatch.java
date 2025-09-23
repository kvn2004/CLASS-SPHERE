package lk.vihanganimsara.classsphere.exception;

public class SessionDoesNotMatch extends RuntimeException {
    public SessionDoesNotMatch() {
        super("Session date does not match today");
    }
}
