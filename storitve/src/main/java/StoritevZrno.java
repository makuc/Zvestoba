import entitete.Storitev;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@ApplicationScoped

public class StoritevZrno {
    @PersistenceContext(unitName = "zvestoba-jpa")
    private EntityManager em;

    public List<Storitev> getStoritve(){
        Query q = em.createNamedQuery("Storitev.getAll");
        return (List<Storitev>)(q.getResultList());
    }

    public Storitev getStoritev(int id){
        Query q = em.createNamedQuery("Storitev.getOne");
        q.setParameter(1, id);
        return (Storitev) q.getSingleResult();
    }

    public void deleteStoritev(int id){
        em.getTransaction().begin();
        Query q = em.createNamedQuery("Storitev.delete");
        q.setParameter(1, id);
        q.executeUpdate();
        em.getTransaction().commit();

    }
    public void updateStoritevNaziv(int id, String naziv){
        em.getTransaction().begin();
        Query q = em.createNamedQuery("Storitev.updateNaziv");
        q.setParameter(1, id);
        q.setParameter(2, naziv);
        q.executeUpdate();
        em.getTransaction().commit();
    }

    public void storeStoritev(Storitev sto) {
        em.getTransaction().begin();
        em.persist(sto);
        em.getTransaction().commit();
    }
}
