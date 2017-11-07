package si.fri.prpo.zvestoba.zrna;

import si.fri.prpo.zvestoba.entitete.Storitev;
import si.fri.prpo.zvestoba.entitete.Uporabnik;
import si.fri.prpo.zvestoba.entitete.ZbraneTocke;
import si.fri.prpo.zvestoba.entitete.ZbraneTockeId;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
public class ZbraneTockeZrno {

    private Logger log = Logger.getLogger(UporabnikZrno.class.getName());

    @PostConstruct
    private void init() {
        // Zabeleži v logger
    }

    @PersistenceContext(unitName = "zvestoba-jpa")
    private EntityManager em;

    public List<ZbraneTocke> getZbraneTocke() {
        em.getTransaction().begin();
        Query q = em.createNamedQuery("ZbraneTocke.getAll");
        return (List<ZbraneTocke>) (q.getResultList());

    }
    public List<ZbraneTocke> getStoritveUporabnika (Uporabnik user){
        em.getTransaction().begin();
        Query q = em.createNamedQuery("ZbraneTocke.getStoritveUporabnika");
        q.setParameter(1, user);
        return (List<ZbraneTocke>) q.getResultList();
    }
    public List<ZbraneTocke> getUporabnikeStoritve(Storitev storitev){
        em.getTransaction().begin();
        Query q = em.createNamedQuery("ZbraneTocke.getUporabnikeStoritve");
        q.setParameter(1, storitev);
        return (List<ZbraneTocke>) q.getResultList();
    }
    public ZbraneTocke getTockeStoritveUporabnika(Uporabnik user, Storitev storitev){
        em.getTransaction().begin();
        Query q = em.createNamedQuery("ZbraneTocke.getTockeStoritveUporabnika");
        q.setParameter(1, storitev);
        q.setParameter(2, user);
        return (ZbraneTocke) q.getSingleResult();
    }
    @Transactional
    public void dodajUporabnikuTockeStoritve(Uporabnik uporabnik, Storitev storitev){
        em.getTransaction().begin();
        ZbraneTocke dodaj = new ZbraneTocke(uporabnik, storitev);
        em.persist(dodaj);
        em.getTransaction().commit();
    }
    @Transactional
    public void povisajUporabnikuTockeStoritve(Uporabnik uporabnik, Storitev storitev){
        em.getTransaction().begin();

        ZbraneTockeId najdi = new ZbraneTockeId(storitev.getStoritevId(), uporabnik.getUporabnisko_ime());
        ZbraneTocke cur = em.find(ZbraneTocke.class, najdi);
        int tocke = cur.getSt_tock();
        tocke += storitev.getStPridobljenihTock();
        cur.setSt_tock(tocke);

        em.getTransaction().commit();
    }
}
