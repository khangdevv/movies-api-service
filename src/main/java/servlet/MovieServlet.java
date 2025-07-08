package servlet;

import data.repository.MovieRepository;
import data.request.AddMovieRequest;
import data.request.UpdateMovieRequest;
import data.response.ErrorType;
import data.response.MovieResponse;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import service.MoviesService;
import util.HttpResponseUtil;
import util.JsonUtil;

import java.io.IOException;
import java.util.List;

@WebServlet("/movies")
public class MovieServlet extends HttpServlet {

    private MoviesService moviesService;

    @Override
    public void init() throws ServletException {
        moviesService = new MoviesService(new MovieRepository());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        String id = req.getParameter("id");

        if (id != null) {
            int movieId = Integer.parseInt(id);
            MovieResponse movieResponse = moviesService.getMovieById(movieId);
            if (movieResponse != null) {
                HttpResponseUtil.sendSuccess(
                        resp,
                        HttpServletResponse.SC_OK,
                        "Movie retrieved successfully",
                        movieResponse
                );
            } else {
                HttpResponseUtil.sendError(
                        resp,
                        HttpServletResponse.SC_NOT_FOUND, ErrorType.INVALID_INPUT,
                        "Movie not found"
                );
            }
        } else {
            List<MovieResponse> movieResponses = moviesService.getAllMovies();
            String message = movieResponses.isEmpty()
                    ? "There are no movies in the database"
                    : "There are " + movieResponses.size() + " movies in the database";
            HttpResponseUtil.sendSuccess(resp, HttpServletResponse.SC_OK, message, movieResponses);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        AddMovieRequest addMovieRequest = JsonUtil.fromJson(req.getInputStream(), AddMovieRequest.class);
        MovieResponse movieResponse = moviesService.addMovie(addMovieRequest);
        HttpResponseUtil .sendSuccess(
                resp,
                HttpServletResponse.SC_CREATED,
                "Movie added successfully",
                movieResponse
        );
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        UpdateMovieRequest updateRequest = JsonUtil.fromJson(req.getInputStream(), UpdateMovieRequest.class);
        MovieResponse updatedMovie = moviesService.updateMovie(updateRequest);
        if (updatedMovie != null) {
            HttpResponseUtil.sendSuccess(resp, HttpServletResponse.SC_OK, "Movie updated successfully", updatedMovie);
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("id");
        if (id != null) {
            int movieId = Integer.parseInt(id);
            MovieResponse movieResponse = moviesService.deleteMovie(movieId);
            if (movieResponse != null) {
                HttpResponseUtil.sendSuccess(
                        resp,
                        HttpServletResponse.SC_OK,
                        "Movie deleted successfully",
                        movieResponse
                );
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Movie not found");
            }
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid endpoint");
        }
    }
}