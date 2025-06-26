package data.repository;

import data.entity.Ticket;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import util.HibernateUtil;

public class TicketRepository {
    private final EntityManagerFactory emf;

    public TicketRepository() {
        this.emf = HibernateUtil.getEntityManagerFactory();
    }

    public Ticket getTicket(int id) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("SELECT t FROM Ticket t WHERE t.id = :id", Ticket.class)
                     .setParameter("id", id)
                     .getSingleResult();
        } catch (NoResultException e) {
            throw new EntityNotFoundException("Ticket with id " + id + " not found");
        }
    }
}
