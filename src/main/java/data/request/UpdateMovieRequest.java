package data.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateMovieRequest {
    private Integer id;
    private String title;
    private String genre;
    private Integer durationMinutes;
    private String description;
}
