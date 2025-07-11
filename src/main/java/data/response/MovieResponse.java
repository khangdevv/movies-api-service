package data.response;

import data.entity.Showtime;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MovieResponse {
    private Integer id;
    private String title;
    private String genre;
    private Integer durationMinutes;
    private String description;
    private List<ShowtimeResponse> showtimes;
}
