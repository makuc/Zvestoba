package si.fri.prpo.zvestoba.zrna;

import si.fri.prpo.zvestoba.anotacije.BeleziKlice;
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
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

@ApplicationScoped
@BeleziKlice
public class ZbraneTockeZrno {

    private Logger log = Logger.getLogger(UporabnikZrno.class.getName());

    @PostConstruct
    public void init() {
        // Zabeleži v logger
        log.log(Level.INFO, "Inicializacija zrna ZbraneTockeZrno");
    }


    @PersistenceContext(unitName = "zvestoba-jpa")
    private EntityManager em;

    public List<ZbraneTocke> getZbraneTocke() {
        Query q = em.createNamedQuery("ZbraneTocke.getAll");
        return (List<ZbraneTocke>) q.getResultList();

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
    @Transactional
    public void dodajUporabnikuStoritev(Uporabnik uporabnik, Storitev storitev){
        em.getTransaction().begin();
        ZbraneTocke dodaj = new ZbraneTocke(uporabnik, storitev);
        em.persist(dodaj);
        em.getTransaction().commit();
        log.log(Level.INFO,
                "Uporabniku: "+uporabnik.getUporabnisko_ime()+" dodana storitev: "+storitev.getStoritevId()
        );
    }
    @Transactional
    public void povisajUporabnikuTockeStoritve(Uporabnik uporabnik, Storitev storitev){
        if(uporabnik != null && storitev != null) {
            em.getTransaction().begin();
            ZbraneTockeId najdi = new ZbraneTockeId(storitev.getStoritevId(), uporabnik.getUporabnisko_ime());
            ZbraneTocke cur = em.find(ZbraneTocke.class, najdi);
            if(cur == null)
            {// Ta uporabnik nima Ne obstaja, ustvari ga
                dodajUporabnikuStoritev(uporabnik, storitev);
            }
            int tocke = cur.getSt_tock();
            tocke += storitev.getStPridobljenihTock();
            cur.setSt_tock(tocke);

            em.getTransaction().commit();

            log.log(Level.INFO, "Povisane tocke: "+uporabnik.getUporabnisko_ime()+":"+storitev.getStoritevId());
        } else
            log.log(Level.WARNING, "ZbraneTockeZrno.povisajUporabnikuTockeStoritve: Podan uporabnik ali storitev ne sme biti NULL!");
    }
}
