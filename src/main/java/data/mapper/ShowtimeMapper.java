package data.mapper;

import data.entity.Showtime;
import data.response.ShowtimeResponse;

import java.util.ArrayList;
import java.util.List;

public class ShowtimeMapper {
    public static ShowtimeResponse toShowtimeResponse(Showtime showtime) {
        ShowtimeResponse response = new ShowtimeResponse();
        response.setId(showtime.getId());
        response.setTicketPrice(showtime.getTicketPrice());
        response.setStartTime(showtime.getStartTime());
        if (showtime.getMovie() != null) {
            response.setMovieId(showtime.getMovie().getId());
            response.setMovieName(showtime.getMovie().getTitle());
        }
        if (showtime.getRoom() != null) {
            response.setRoomId(showtime.getRoom().getId());
            response.setRoomName(showtime.getRoom().getRoomName());
        }
        return response;
    }

    public static List<ShowtimeResponse> toShowtimeResponses(List<Showtime> showtimes) {
        List<ShowtimeResponse> responses = new ArrayList<>();
        for (Showtime showtime : showtimes) {
            responses.add(toShowtimeResponse(showtime));
        }
        return responses;
    }
}
