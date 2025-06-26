package service;

import data.entity.Movie;
import data.mapper.MovieMapper;
import data.repository.MovieRepository;
import data.request.AddMovieRequest;
import data.request.UpdateMovieRequest;
import data.response.MovieResponse;
import exception.MovieNotFoundException;
import jakarta.persistence.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class MoviesService {
    private final MovieRepository moviesRepository;
    public MoviesService(MovieRepository moviesRepository) {
        this.moviesRepository = moviesRepository;
    }

    public MovieResponse getMovieById(int id) {
        if (id <= 0)
            throw new IllegalArgumentException("ID must be positive");

        try {
            Movie movie = moviesRepository.getMovie(id);
            return MovieMapper.toMovieResponse(movie);
        } catch (Exception e) {
            throw new MovieNotFoundException(e.getMessage());
        }
    }

    public List<MovieResponse> getAllMovies() {
        List<Movie> movies = moviesRepository.getAllMovies();
        List<MovieResponse> movieResponses = new ArrayList<>();
        for (Movie movie : movies) {
            movieResponses.add(MovieMapper.toMovieResponse(movie));
        }
        return movieResponses;
    }

    public MovieResponse addMovie(AddMovieRequest addMovieRequest) {
        Movie movie = MovieMapper.toMovie(addMovieRequest);
        moviesRepository.add(movie);
        return MovieMapper.toMovieResponse(movie);
    }

    public MovieResponse updateMovie(UpdateMovieRequest updateMovieRequest) {
        if (updateMovieRequest.getId() <= 0)
            throw new IllegalArgumentException("ID must be positive");

        Movie movie = MovieMapper.toMovie(updateMovieRequest);
        try {
            Movie movie1 = moviesRepository.getMovie(updateMovieRequest.getId());
        }
        catch (EntityNotFoundException e) {
            throw new MovieNotFoundException(e.getMessage());
        }
        moviesRepository.update(movie);
        return MovieMapper.toMovieResponse(movie);
    }

    public MovieResponse deleteMovie(int id) {
        if (id <= 0)
            throw new IllegalArgumentException("ID must be positive");

        try {
            Movie movie = moviesRepository.getMovie(id);
            moviesRepository.delete(movie.getId());
            return MovieMapper.toMovieResponse(movie);
        } catch (EntityNotFoundException e) {
            throw new MovieNotFoundException(e.getMessage());
        }
    }
}
