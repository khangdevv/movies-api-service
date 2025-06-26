package data.repository;

import data.entity.Movie;
import jakarta.persistence.*;
import util.HibernateUtil;

import java.util.List;

public class MovieRepository {
    private final EntityManagerFactory emf;

    public MovieRepository() {
        emf = HibernateUtil.getEntityManagerFactory();
    }

    public Movie getMovie(int id) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("SELECT m FROM Movie m WHERE m.id = :id", Movie.class)
                     .setParameter("id", id)
                     .getSingleResult();
        } catch (NoResultException e) {
            throw new EntityNotFoundException("Movie with id " + id + " not found");
        }
    }

    public List<Movie> getAllMovies() {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("SELECT m FROM Movie m", Movie.class)
                     .getResultList();
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to get movies", e);
        }
    }

    public Movie add(Movie movie) {
        EntityTransaction et = null;
        try (EntityManager em = emf.createEntityManager()) {
            et = em.getTransaction();
            et.begin();
            em.persist(movie);
            et.commit();
            return movie;
        } catch (RuntimeException e) {
            if (et != null && et.isActive()) {
                et.rollback();
            }
            throw new RuntimeException("Database error while adding movie", e);
        }
    }

    public void delete(int id) {
        EntityTransaction et = null;
        try (EntityManager em = emf.createEntityManager()) {
            et = em.getTransaction();
            et.begin();
            Movie movie = em.find(Movie.class, id);
            if (movie == null) {
                throw new EntityNotFoundException("Movie with id " + id + " not found");
            }
            em.remove(movie);
            et.commit();
        } catch (RuntimeException e) {
            if (et != null && et.isActive()) {
                et.rollback();
            }
            throw new RuntimeException("Database error while deleting movie", e);
        }
    }

    public Movie update(Movie movie) {
        EntityTransaction et = null;
        try (EntityManager em = emf.createEntityManager()) {
            et = em.getTransaction();
            et.begin();
            em.merge(movie);
            et.commit();
            return movie;
        } catch (RuntimeException e) {
            if (et != null && et.isActive()) {
                et.rollback();
            }
            throw new RuntimeException("Database error while updating movie", e);
        }
    }
}
