package data.repository;

import data.entity.Showtime;
import data.entity.Showtime;
import jakarta.persistence.*;
import util.HibernateUtil;

import java.util.List;

public class ShowtimeRepository {
    private final EntityManagerFactory emf;

    public ShowtimeRepository() {
        this.emf = HibernateUtil.getEntityManagerFactory();
    }

    public Showtime getShowtime(int id) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("SELECT s FROM Showtime s WHERE s.id = :id", Showtime.class)
                     .setParameter("id", id)
                     .getSingleResult();
        } catch (NoResultException e) {
            throw new EntityNotFoundException("Showtime with id " + id + " not found");
        }
    }

    public List<Showtime> getAllShowtimes() {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("SELECT m FROM Showtime m", Showtime.class)
                    .getResultList();
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to get Showtimes", e);
        }
    }

    public List<Showtime> getShowtimesByMovieId(int movieId) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("SELECT s FROM Showtime s WHERE s.movie.id = :movieId", Showtime.class)
                     .setParameter("movieId", movieId)
                     .getResultList();
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to get Showtimes for movie id " + movieId, e);
        }
    }

    public Showtime add(Showtime Showtime) {
        EntityTransaction et = null;
        try (EntityManager em = emf.createEntityManager()) {
            et = em.getTransaction();
            et.begin();
            em.persist(Showtime);
            et.commit();
            return Showtime;
        } catch (RuntimeException e) {
            if (et != null && et.isActive()) {
                et.rollback();
            }
            throw new RuntimeException("Database error while adding Showtime", e);
        }
    }

    public void delete(int id) {
        EntityTransaction et = null;
        try (EntityManager em = emf.createEntityManager()) {
            et = em.getTransaction();
            et.begin();
            Showtime Showtime = em.find(Showtime.class, id);
            if (Showtime == null) {
                throw new EntityNotFoundException("Showtime with id " + id + " not found");
            }
            em.remove(Showtime);
            et.commit();
        } catch (RuntimeException e) {
            if (et != null && et.isActive()) {
                et.rollback();
            }
            throw new RuntimeException("Database error while deleting Showtime", e);
        }
    }

    public Showtime update(Showtime Showtime) {
        EntityTransaction et = null;
        try (EntityManager em = emf.createEntityManager()) {
            et = em.getTransaction();
            et.begin();
            em.merge(Showtime);
            et.commit();
            return Showtime;
        } catch (RuntimeException e) {
            if (et != null && et.isActive()) {
                et.rollback();
            }
            throw new RuntimeException("Database error while updating Showtime", e);
        }
    }
}
