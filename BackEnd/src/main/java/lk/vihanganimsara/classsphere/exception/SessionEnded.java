package lk.vihanganimsara.classsphere.exception;

public class SessionEnded extends RuntimeException {
    public SessionEnded() {
        super("Session already ended");
    }
}
