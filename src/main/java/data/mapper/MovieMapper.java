package data.mapper;

import data.entity.Movie;
import data.request.AddMovieRequest;
import data.request.UpdateMovieRequest;
import data.response.MovieResponse;
import data.response.ShowtimeResponse;

import java.util.List;

public class MovieMapper {
    public static MovieResponse toMovieResponse(Movie movie) {
        MovieResponse movieResponse = new MovieResponse();
        movieResponse.setId(movie.getId());
        movieResponse.setTitle(movie.getTitle());
        movieResponse.setDescription(movie.getDescription());
        movieResponse.setDurationMinutes(movie.getDurationMinutes());
        movieResponse.setGenre(movie.getGenre());
        if (movie.getShowtimes() != null) {
            List<ShowtimeResponse> showtimes = movie.getShowtimes().stream().map(showtime -> {
                ShowtimeResponse showtimeResponse = new ShowtimeResponse();
                showtimeResponse.setId(showtime.getId());
                showtimeResponse.setStartTime(showtime.getStartTime());
                showtimeResponse.setTicketPrice(showtime.getTicketPrice());
                showtimeResponse.setMovieName(showtime.getMovie().getTitle());
                showtimeResponse.setMovieId(showtime.getMovie().getId());
                showtimeResponse.setRoomId(showtime.getRoom().getId());
                showtimeResponse.setRoomName(showtime.getRoom().getRoomName());
                return showtimeResponse;
            }).toList();
            if (!showtimes.isEmpty()) {
                movieResponse.setShowtimes(showtimes);
            }
        }
        return movieResponse;
    }

    public static Movie toMovie(AddMovieRequest addMovieRequest) {
        Movie movie = new Movie();
        movie.setTitle(addMovieRequest.getTitle());
        movie.setDescription(addMovieRequest.getDescription());
        movie.setDurationMinutes(addMovieRequest.getDurationMinutes());
        movie.setGenre(addMovieRequest.getGenre());
        return movie;
    }

    public static Movie toMovie(UpdateMovieRequest updateMovieRequest) {
        Movie movie = new Movie();
        movie.setId(updateMovieRequest.getId());
        movie.setTitle(updateMovieRequest.getTitle());
        movie.setDescription(updateMovieRequest.getDescription());
        movie.setDurationMinutes(updateMovieRequest.getDurationMinutes());
        movie.setGenre(updateMovieRequest.getGenre());
        return movie;
    }
}
