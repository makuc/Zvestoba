package si.fri.prpo.zvestoba.zrna;

import si.fri.prpo.zvestoba.anotacije.BeleziKlice;
import si.fri.prpo.zvestoba.entitete.Storitev;
import si.fri.prpo.zvestoba.entitete.Uporabnik;
import si.fri.prpo.zvestoba.entitete.ZbraneTocke;
import si.fri.prpo.zvestoba.entitete.ZbraneTockeId;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
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

    @Inject
    UUIDGeneratorZrno uuidZrno;

    private Logger log = Logger.getLogger(UporabnikZrno.class.getName());

    @PostConstruct
    public void init() {
        // Zabeleži v logger
        log.log(Level.INFO, "Inicializacija zrna ZbraneTockeZrno");
    }


    @PersistenceContext(unitName = "zvestoba-jpa")
    private EntityManager em;

    public List<ZbraneTocke> getZbraneTocke() {
        log.info("Začetek obdelave zahtevka: " + uuidZrno.getId().toString());
        log.log(Level.FINE, "Vracam vse vnose ZbraneTocke");

        Query q = em.createNamedQuery("ZbraneTocke.getAll");

        log.info("Konec obdelave zahtevka: " + uuidZrno.getId().toString());

        return (List<ZbraneTocke>) q.getResultList();
    }
    public List<ZbraneTocke> getStoritveUporabnika (Uporabnik user){
        log.log(Level.FINE, "Vracam storitve uporabnika: " + user.getUporabnisko_ime());

        Query q = em.createNamedQuery("ZbraneTocke.getStoritveUporabnika");
        q.setParameter(1, user);
        return (List<ZbraneTocke>) q.getResultList();
    }
    public List<ZbraneTocke> getUporabnikeStoritve(Storitev storitev){
        log.log(Level.FINE, "Vracam vse uporabnike storitve (id): " + storitev.getStoritevId());

        Query q = em.createNamedQuery("ZbraneTocke.getUporabnikeStoritve");
        q.setParameter(1, storitev);
        return (List<ZbraneTocke>) q.getResultList();
    }
    public ZbraneTocke getTockeStoritveUporabnika(Uporabnik user, Storitev storitev){
        log.log(Level.FINE, "Vracam tocke uporabnika: " + user.getUporabnisko_ime() +", storitve (id): " +storitev.getStoritevId());

        Query q = em.createNamedQuery("ZbraneTocke.getTockeStoritveUporabnika");
        q.setParameter(1, storitev);
        q.setParameter(2, user);
        return (ZbraneTocke) q.getSingleResult();
    }
    @Transactional
    public void dodajUporabnikuStoritev(Uporabnik uporabnik, Storitev storitev){
        log.log(Level.FINE, "Uporabniku: "+uporabnik.getUporabnisko_ime()+" dodana storitev (id): "+storitev.getStoritevId());

        em.getTransaction().begin();
        ZbraneTocke dodaj = new ZbraneTocke(uporabnik, storitev);
        em.persist(dodaj);
        em.getTransaction().commit();
    }
    @Transactional
    public void povisajUporabnikuTockeStoritve(Uporabnik uporabnik, Storitev storitev){
        if(uporabnik != null && storitev != null) {
            log.log(Level.FINE, "Povisujem tocke uporabniku: "+uporabnik.getUporabnisko_ime()+", za storitev (id): "+storitev.getStoritevId());

            em.getTransaction().begin();
            ZbraneTockeId najdi = new ZbraneTockeId(storitev.getStoritevId(), uporabnik.getUporabnisko_ime());
            ZbraneTocke cur = em.find(ZbraneTocke.class, najdi);
            if(cur == null)
            {// Ta uporabnik še ne uporablja izbrane storitve, dodaj mu jo
                dodajUporabnikuStoritev(uporabnik, storitev);
            }
            int tocke = cur.getSt_tock();
            tocke += storitev.getStPridobljenihTock();
            cur.setSt_tock(tocke);

            em.getTransaction().commit();
        } else
            log.log(Level.WARNING, "ZbraneTockeZrno.povisajUporabnikuTockeStoritve: Podan uporabnik ali storitev ne sme biti NULL!");
    }
}
