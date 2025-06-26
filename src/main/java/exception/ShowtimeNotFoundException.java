package exception;

import java.io.IOException;

public class ShowtimeNotFoundException extends RuntimeException {
    public ShowtimeNotFoundException() {
        super();
    }

    public ShowtimeNotFoundException(String message) {
        super(message);
    }
}
