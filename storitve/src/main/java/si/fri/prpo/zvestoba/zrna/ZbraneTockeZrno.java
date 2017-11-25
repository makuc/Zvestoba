package si.fri.prpo.zvestoba.zrna;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
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
import javax.ws.rs.core.Response;
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

    @Inject
    UporabnikZrno uporabnikiZrno;

    @Inject
    StoritevZrno storitveZrno;

    private Logger log = Logger.getLogger(UporabnikZrno.class.getName());

    @PostConstruct
    public void init() {
        // Zabeleži v logger
        log.info("Inicializacija zrna ZbraneTockeZrno");
    }


    @PersistenceContext(unitName = "zvestoba-jpa")
    private EntityManager em;

    public List<ZbraneTocke> getZbraneTocke(QueryParameters queryParameters) {
        log.info("Začetek obdelave zahtevka: " + uuidZrno.getId().toString());

        log.fine("Vracam vse vnose ZbraneTocke");

        //Query q = em.createNamedQuery("ZbraneTocke.getAll");

        List<ZbraneTocke> zbraneTocke = JPAUtils.queryEntities(em, ZbraneTocke.class, queryParameters);

        log.info("Konec obdelave zahtevka: " + uuidZrno.getId().toString());

        return zbraneTocke;
    }

    public List<ZbraneTocke> getStoritveUporabnika (Uporabnik uporabnik){

        if(uporabnik.getUporabnisko_ime() == null){
            log.warning("Za iskanje storitev uporabnika, uporabnik ne sme biti NULL");
            return null;
        }

        log.fine("Vracam storitve uporabnika: " + uporabnik.getUporabnisko_ime());

        Query q = em.createNamedQuery("ZbraneTocke.getStoritveUporabnika");
        q.setParameter(1, uporabnik);

        return (List<ZbraneTocke>) q.getResultList();
    }

    public List<ZbraneTocke> getUporabnikeStoritve(Storitev storitev){

        if(storitev.getStoritevId() == null){
            log.warning("Za iskanje uporabnikov storitve, storitev ne sme biti NULL");
            return null;
        }

        log.fine("Vracam vse uporabnike storitve (id): " + storitev.getStoritevId());

        Query q = em.createNamedQuery("ZbraneTocke.getUporabnikeStoritve");
        q.setParameter(1, storitev);
        return (List<ZbraneTocke>) q.getResultList();
    }

    public ZbraneTocke getTockeStoritveUporabnika(Uporabnik uporabnik, Storitev storitev){

        if(uporabnik.getUporabnisko_ime() == null || storitev.getStoritevId() == null){
            log.warning("Za iskanje tock storitve uporabnika, uporabnik in/ali storitev ne smeta biti NULL");
            return null;
        }

        log.fine("Vracam tocke uporabnika: " + uporabnik.getUporabnisko_ime() +", storitve (id): " +storitev.getStoritevId());

        ZbraneTockeId zbraneTockeId = new ZbraneTockeId(storitev.getStoritevId(), uporabnik.getUporabnisko_ime());

        return em.find(ZbraneTocke.class, zbraneTockeId);
    }

    public ZbraneTocke dodajUporabnikuStoritev(String uporabnisko_ime, Integer storitevId){
        // Just a wrapper :P
        // No need to log anything here

        Uporabnik uporabnik = uporabnikiZrno.getUporabnik(uporabnisko_ime);
        if(uporabnik == null)
            return null;

        Storitev storitev = storitveZrno.getStoritev(storitevId);
        if(storitev == null) {
            return null;
        }

        return dodajUporabnikuStoritev(uporabnik, storitev);
    }

    @Transactional
    public ZbraneTocke dodajUporabnikuStoritev(Uporabnik uporabnik, Storitev storitev){

        if(uporabnik == null || storitev == null){
            log.warning("Za dodajanje storitve uporabniku, uporabnik in/ali storitev ne smeta biti NULL");
            return null;
        }

        if(getTockeStoritveUporabnika(uporabnik, storitev) != null) {
            log.warning("Uporabnik s uporabnisko_ime: " + uporabnik.getUporabnisko_ime() +
                    ", že uporablja storitev s storitevId: " + storitev.getStoritevId()
            );
            return null;
        }

        log.fine("Uporabniku: " + uporabnik.getUporabnisko_ime() +
                " dodana storitev (id): " + storitev.getStoritevId()
        );

        ZbraneTocke dodaj = new ZbraneTocke(uporabnik, storitev);

        em.getTransaction().begin();
        em.persist(dodaj);
        em.getTransaction().commit();

        return getTockeStoritveUporabnika(uporabnik, storitev);
    }

    @Transactional
    public ZbraneTocke povisajUporabnikuTockeStoritve(Uporabnik uporabnik, Storitev storitev){
        if(uporabnik == null || storitev == null) {
            log.warning("Za visanje tock uporabnika, uporabnik in/ali storitev ne smeta biti NULL!");
            return null;
        }

        log.fine("Povisujem tocke uporabniku: " + uporabnik.getUporabnisko_ime() +
                ", za storitev (id): " + storitev.getStoritevId()
        );

        ZbraneTocke posodobi = getTockeStoritveUporabnika(uporabnik, storitev);

        if(posodobi == null)
        {// Ta uporabnik še ne uporablja izbrane storitve, dodaj mu jo
            dodajUporabnikuStoritev(uporabnik, storitev);
        }

        Integer tocke = posodobi.getSt_tock();
        tocke += storitev.getStPridobljenihTock();
        posodobi.setSt_tock(tocke);

        em.getTransaction().begin();
        em.getTransaction().commit();

        return posodobi;
    }
    @Transactional
    public void deleteZbraneTocke(ZbraneTocke zbraneTocke){
        log.fine("Odstranjujem  zbraneTocke s uporabnisko_ime: "+
                zbraneTocke.getUporabnik().getUporabnisko_ime() + "; in storitevId: " +
                zbraneTocke.getStoritev().getStoritevId()
        );

        em.getTransaction().begin();
        em.remove(zbraneTocke);
        em.getTransaction().begin();
    }

    public void deleteStoritveUporabnika(Uporabnik uporabnik){
        log.fine("Brišem storitve uporabnika s uporabnisko_ime: " + uporabnik.getUporabnisko_ime());

        List<ZbraneTocke> tockeList = getStoritveUporabnika(uporabnik);
        for(ZbraneTocke tocke : tockeList){
            deleteZbraneTocke(tocke);
        }
    }

    public void deleteUporabnikeStoritve(Storitev storitev){
        log.fine("Brišem uporabnike storitve s storitevId: " + storitev.getStoritevId());

        List<ZbraneTocke> tockeList = getUporabnikeStoritve(storitev);
        for(ZbraneTocke tocke : tockeList){
            deleteZbraneTocke(tocke);
        }
    }
}
