package lk.vihanganimsara.classsphere.exception;

public class SessionNotFound extends RuntimeException {
    public SessionNotFound() {
        super("Session not found");
    }
}