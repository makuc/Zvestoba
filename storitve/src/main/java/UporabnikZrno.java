import entitete.Uporabnik;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@ApplicationScoped

public class UporabnikZrno {
    @PersistenceContext(unitName = "zvestoba-jpa")
    private EntityManager em;

    public List<Uporabnik> getUporabniki() {
        Query q = em.createNamedQuery("Uporabniki.getAll");
        return (List<Uporabnik>)(q.getResultList());

    }

    public Uporabnik getUporabnik(String username){
        Query q = em.createNamedQuery("Uporabniki.getOne");
        q.setParameter(1, username);
        return (Uporabnik)q.getSingleResult();
    }

    public void deleteUporabnik(String username){
        em.getTransaction().begin();
        Query q = em.createNamedQuery("Uporabniki.delete");
        q.setParameter(1, username);
        q.executeUpdate();

    }
}
