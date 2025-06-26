package exception;

import java.io.IOException;

public class RoomNotFoundException extends RuntimeException {
    public RoomNotFoundException() {
        super();
    }

    public RoomNotFoundException(String message) {
        super(message);
    }
}
