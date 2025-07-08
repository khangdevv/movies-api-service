package data.repository;

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
}
