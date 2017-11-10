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
import java.util.logging.Level;
import java.util.logging.Logger;


@ApplicationScoped
@BeleziKlice

public class UporabnikZrno {
    @PersistenceContext(unitName = "zvestoba-jpa")
    private EntityManager em;

    private Logger log = Logger.getLogger(UporabnikZrno.class.getName());

    @PostConstruct
    public void init() {
        // Zabeleži v logger
        log.log(Level.INFO, "Inicializacija zrna UporabnikZrno");
    }

    public List<Uporabnik> getUporabniki() {
        log.log(Level.FINE, "Vračam vse uporabnike");
        Query q = em.createNamedQuery("Uporabniki.getAll");
        return (List<Uporabnik>)(q.getResultList());

    }

    public Uporabnik getUporabnik(String username){
        log.log(Level.FINE, "Vračam uporabnika " + username);
        Query q = em.createNamedQuery("Uporabniki.getOne");
        q.setParameter(1, username);
        Uporabnik usr = (Uporabnik)q.getSingleResult();
        return usr;
    }

    @Transactional
    public void deleteUporabnik(String username){
        log.log(Level.FINE, "Odstranjujem uporabnika " + username);
        em.getTransaction().begin();
        Uporabnik upo = em.find(Uporabnik.class, username);
        Query q = em.createNamedQuery("Storitev.getAll");
        List<Storitev> storitve = (List<Storitev>)(q.getResultList());
        for(int i=0; i<storitve.size(); i++){
            ZbraneTockeId najdi = new ZbraneTockeId(storitve.get(i).getStoritevId(), username);
            ZbraneTocke zt = em.find(ZbraneTocke.class, najdi);
            if(zt != null) {
                em.remove(zt);
            }
        }
        em.remove(upo);
        em.flush();
        em.getTransaction().commit();

    }

    @Transactional
    public void updateUporabnik(String username, Uporabnik upo){
        log.log(Level.FINE, "Posodabljam uporabnika " + username);
        em.getTransaction().begin();
        Uporabnik uporabnik = em.find(Uporabnik.class,username);
        uporabnik.setIme(upo.getIme());
        uporabnik.setPriimek(upo.getPriimek());
        uporabnik.setEmail(upo.getEmail());
        em.merge(uporabnik);
        em.getTransaction().commit();
    }

    @Transactional
    public void createUporabnik(String username, String ime, String priimek, String email) {
        log.log(Level.FINE, "Ustvarjam uporabnika");
        Uporabnik usr = new Uporabnik(
                username,
                ime,
                priimek,
                email
        );
        em.getTransaction().begin();
        em.persist(usr);
        em.getTransaction().commit();
    }

    @Transactional
    public void storeUporabnik(Uporabnik usr) {
        log.log(Level.FINE, "Ustvarjam uporabnika");
        em.getTransaction().begin();
        em.persist(usr);
        em.getTransaction().commit();
    }
}
