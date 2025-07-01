package servlet;

import data.repository.MovieRepository;
import data.request.UpdateMovieRequest;
import data.response.MovieResponse;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import service.MoviesService;
import util.HttpResponseUtil;
import util.JsonUtil;

import java.io.IOException;

@WebServlet("/movies")
public class ProductServlet extends HttpServlet {
    private MoviesService moviesService;

    // private final ProductDAO dao = new ProductDAO();
    // private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void init() throws ServletException {
        moviesService = new MoviesService(new MovieRepository());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        String id = req.getParameter("id");

        if (id != null) {
            int id = Integer.parseInt(id);
            Product product = dao.getById(id);
            if (product != null) {
                mapper.writeValue(resp.getOutputStream(), product);
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter().write("{\"error\"😕"Not found\"}");
            }
        } else {
            List<Product> products = dao.getAll();
            mapper.writeValue(resp.getOutputStream(), products);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Product p = mapper.readValue(req.getInputStream(), Product.class);
        dao.create(p);
        resp.setStatus(HttpServletResponse.SC_CREATED);
        mapper.writeValue(resp.getOutputStream(), p);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        UpdateMovieRequest updateRequest = JsonUtil.fromJson(req.getInputStream(), UpdateMovieRequest.class);
        MovieResponse updatedMovie = moviesService.updateMovie(updateRequest);
        if (updatedMovie != null) {
            HttpResponseUtil.sendSuccess(resp, HttpServletResponse.SC_OK, "Movie updated successfully", updatedMovie);
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String idParam = req.getParameter("id");
        if (idParam != null) {
            int id = Integer.parseInt(idParam);
            dao.delete(id);
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\"😕"Missing id\"}");
        }
    }
}