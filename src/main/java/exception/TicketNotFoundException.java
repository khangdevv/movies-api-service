package exception;

import java.io.IOException;

public class TicketNotFoundException extends RuntimeException {
    public TicketNotFoundException() {
        super();
    }

    public TicketNotFoundException(String message) {
        super(message);
    }
}
