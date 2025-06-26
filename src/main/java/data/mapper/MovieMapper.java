package data.mapper;

import data.entity.Movie;
import data.entity.Showtime;
import data.repository.ShowtimeRepository;
import data.request.AddMovieRequest;
import data.request.UpdateMovieRequest;
import data.response.MovieResponse;

import java.util.List;

public class MovieMapper {
    public static MovieResponse toMovieResponse(Movie movie) {
        MovieResponse movieResponse = new MovieResponse();
        movieResponse.setId(movie.getId());
        movieResponse.setTitle(movie.getTitle());
        movieResponse.setDescription(movie.getDescription());
        movieResponse.setDurationMinutes(movie.getDurationMinutes());
        movieResponse.setGenre(movie.getGenre());
        ShowtimeRepository showtimeRepository = new ShowtimeRepository();
        List<Showtime> showtimes = showtimeRepository.getShowtimesByMovieId(movie.getId());
        if (showtimes != null && !showtimes.isEmpty()) {
            movieResponse.setShowtimes(showtimes);
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
