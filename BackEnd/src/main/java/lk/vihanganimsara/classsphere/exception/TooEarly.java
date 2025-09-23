package lk.vihanganimsara.classsphere.exception;

public class TooEarly extends RuntimeException {
    public TooEarly() {
        super("Too early to mark attendance for this session");
    }
}
