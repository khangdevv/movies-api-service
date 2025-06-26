package data.repository;

import data.entity.Room;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import util.HibernateUtil;

public class RoomRepository {
    private final EntityManagerFactory emf;

    public RoomRepository() {
        this.emf = HibernateUtil.getEntityManagerFactory();
    }

    public Room getRoom(int id) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("SELECT r FROM Room r WHERE r.id = :id", Room.class)
                     .setParameter("id", id)
                     .getSingleResult();
        } catch (NoResultException e) {
            throw new EntityNotFoundException("Room with id " + id + " not found");
        }
    }
}
