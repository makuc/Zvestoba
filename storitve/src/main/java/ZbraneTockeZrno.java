import entitete.Storitev;
import entitete.Uporabnik;
import entitete.ZbraneTocke;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@ApplicationScoped

public class ZbraneTockeZrno {
    @PersistenceContext(unitName = "zvestoba-jpa")
    private EntityManager em;

    public List<ZbraneTocke> getZbraneTocke() {
        Query q = em.createNamedQuery("ZbraneTocke.getAll");
        return (List<ZbraneTocke>) (q.getResultList());

    }
    public List<ZbraneTocke> getStoritveUporabnika (Uporabnik user){
        Query q = em.createNamedQuery("ZbraneTocke.getStoritveUporabnika");
        q.setParameter(1, user);
        return (List<ZbraneTocke>) q.getResultList();
    }
    public List<ZbraneTocke> getUporabnikeStoritve(Storitev storitev){
        Query q = em.createNamedQuery("ZbraneTocke.getUporabnikeStoritve");
        q.setParameter(1, storitev);
        return (List<ZbraneTocke>) q.getResultList();
    }
    public ZbraneTocke getTockeStoritveUporabnika(Uporabnik user, Storitev storitev){
        Query q = em.createNamedQuery("ZbraneTocke.getTockeStoritveUporabnika");
        q.setParameter(1, storitev);
        q.setParameter(2, user);
        return (ZbraneTocke) q.getSingleResult();
    }
}
