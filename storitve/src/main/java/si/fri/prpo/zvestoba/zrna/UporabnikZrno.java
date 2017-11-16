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
import java.util.logging.Level;
import java.util.logging.Logger;


@ApplicationScoped
@BeleziKlice

public class UporabnikZrno {
    @PersistenceContext(unitName = "zvestoba-jpa")
    private EntityManager em;

    @Inject
    ZbraneTockeZrno tockeZrno;

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

    public Uporabnik getUporabnik(String uporabnisko_ime){

        if(uporabnisko_ime == null) {
            log.warning("Za iskanje uporabnika potrebno: uporabnisko_ime");
            return null;
        }

        log.fine("Vračam uporabnika " + uporabnisko_ime);

        return em.find(Uporabnik.class, uporabnisko_ime);
    }

    public void deleteUporabnik(String uporabnisko_ime){
        // Just a wrapper :P
        // No need to log it, it happens in the next step anyway
        Uporabnik uporabnik = getUporabnik(uporabnisko_ime);

        deleteUporabnik(uporabnik);
    }

    @Transactional
    public void deleteUporabnik(Uporabnik uporabnik){

        if(uporabnik.getUporabnisko_ime() == null){
            log.warning("Ne morem odstranit uporabnika brez: uporabnisko_ime");
        }

        log.fine("Odstranjujem uporabnika " + uporabnik.getUporabnisko_ime());

        tockeZrno.deleteStoritveUporabnika(uporabnik);

        em.getTransaction().begin();
        em.remove(uporabnik);
        em.getTransaction().commit();
    }

    @Transactional
    public Uporabnik updateUporabnik(Uporabnik uporabnik){

        if(uporabnik.getUporabnisko_ime() == null) {
            log.warning("Neveljaven uporabnik: manjka uporabnisko_ime");
            return null;
        }

        log.fine("Posodabljam uporabnika s uporabnisko_ime: " + uporabnik.getUporabnisko_ime());

        Uporabnik posodobi = getUporabnik(uporabnik.getUporabnisko_ime());

        if(uporabnik.getIme() != null)
            posodobi.setIme(uporabnik.getIme());
        if(uporabnik.getPriimek() != null)
            posodobi.setPriimek(uporabnik.getPriimek());
        if(uporabnik.getEmail() != null)
            posodobi.setEmail(uporabnik.getEmail());

        em.getTransaction().begin();
        posodobi = em.merge(posodobi);
        em.getTransaction().commit();

        return posodobi;
    }

    public Uporabnik createUporabnik(String username, String ime, String priimek, String email) {

        log.fine("Ustvarjam uporabnika s podanimi podatki");

        Uporabnik usr = new Uporabnik(
                username,
                ime,
                priimek,
                email
        );
        usr = storeUporabnik(usr);
        return usr;
    }

    @Transactional
    public Uporabnik storeUporabnik(Uporabnik uporabnik) {

        if(uporabnik.getUporabnisko_ime() == null) {
            log.warning("Ne morem ustvarit uporabnika brez: uporabnisko_ime");
            return null;
        }

        log.fine("Shranjujem uporabnika");

        em.getTransaction().begin();
        em.persist(uporabnik);
        em.getTransaction().commit();

        return getUporabnik(uporabnik.getUporabnisko_ime());
    }
}
