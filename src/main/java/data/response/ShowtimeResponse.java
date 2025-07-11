package data.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
public class ShowtimeResponse {
    private Integer id;
    private Instant startTime;
    private BigDecimal ticketPrice;
    private Integer roomId;
    private String roomName;
    private Integer movieId;
    private String movieName;
}
