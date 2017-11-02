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
}
