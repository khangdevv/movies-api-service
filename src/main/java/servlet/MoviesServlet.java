package servlet;

import data.entity.Movie;
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

@WebServlet(urlPatterns = {"/movies","/add","/delete","/update","/find-all","/find"})
public class MoviesServlet extends HttpServlet {
    public MoviesService moviesService;

    @Override
    public void init() throws ServletException {
        moviesService = new MoviesService(new MovieRepository());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();
        switch (path) {
            case "/find":
                findMovie(request, response);
                break;
            case "/find-all":
                findAllMovies(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Invalid endpoint");
        }
    }

    private void findAllMovies(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<MovieResponse> movieResponses = moviesService.getAllMovies();
        String message;
        if (movieResponses.isEmpty()) {
            message = "There are no movies in the database";
        } else {
            message = "There are " + movieResponses.size() + " movies in the database";
            HttpResponseUtil.sendSuccess(response, HttpServletResponse.SC_OK, message, movieResponses);
        }
    }

    private void findMovie(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getParameter("id");
        if (id == null || id.isEmpty()) {
            HttpResponseUtil.sendError(
                    response,
                    HttpServletResponse.SC_BAD_REQUEST, ErrorType.INVALID_INPUT,
                    "Movie id is required"
            );
            return;
        }
        try {
            int movieId = Integer.parseInt(id);
            MovieResponse movieResponse = moviesService.getMovieById(movieId);
            HttpResponseUtil.sendSuccess(
                    response,
                    HttpServletResponse.SC_OK,
                    "Movie retrieved successfully",
                    movieResponse
            );
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Movie id is not a number");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();
        if ("/add".equals(path)) {
            AddMovieRequest addMovieRequest = JsonUtil.fromJson(request.getInputStream(), AddMovieRequest.class);
            MovieResponse movieResponse = moviesService.addMovie(addMovieRequest);
            HttpResponseUtil .sendSuccess(
                    response,
                    HttpServletResponse.SC_CREATED,
                    "Movie added successfully",
                    movieResponse
            );
        }
        else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Invalid endpoint");
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();
        if ("/update".equals(path)) {
            UpdateMovieRequest updateMovieRequest = JsonUtil.fromJson(request.getInputStream(), UpdateMovieRequest.class);
            MovieResponse movieResponse = moviesService.updateMovie(updateMovieRequest);
            HttpResponseUtil.sendSuccess(
                    response,
                    HttpServletResponse.SC_OK,
                    "Movie updated successfully",
                    movieResponse
            );
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Invalid endpoint");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();
        if ("/delete".equals(path)) {
            String id = request.getParameter("id");
            if (id == null || id.isEmpty()) {
                HttpResponseUtil.sendError(
                        response,
                        HttpServletResponse.SC_BAD_REQUEST, ErrorType.INVALID_INPUT,
                        "Movie id is required"
                );
                return;
            }
            try {
                int movieId = Integer.parseInt(id);
                MovieResponse movieResponse = moviesService.deleteMovie(movieId);
                HttpResponseUtil.sendSuccess(
                        response,
                        HttpServletResponse.SC_OK,
                        "Movie deleted successfully",
                        movieResponse
                );
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Movie id is not a number");
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Invalid endpoint");
        }
    }
}