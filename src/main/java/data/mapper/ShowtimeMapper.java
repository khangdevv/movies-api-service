package data.mapper;

import data.entity.Showtime;
import data.request.AddShowtimeRequest;
import data.request.UpdateShowtimeRequest;
import data.response.ShowtimeResponse;

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

    public static Showtime toShowtime(AddShowtimeRequest request) {
        Showtime showtime = new Showtime();
        showtime.setTicketPrice(request.getTicketPrice());
        showtime.setStartTime(request.getStartTime());
        return showtime;
    }

    public static Showtime toShowtime(UpdateShowtimeRequest request) {
        Showtime showtime = new Showtime();
        showtime.setId(request.getId());
        showtime.setTicketPrice(request.getTicketPrice());
        showtime.setStartTime(request.getStartTime());
        return showtime;
    }
}
