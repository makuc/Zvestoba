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

public class StoritevZrno {
    @PersistenceContext(unitName = "zvestoba-jpa")
    private EntityManager em;

    private Logger log = Logger.getLogger(StoritevZrno.class.getName());

    @PostConstruct
    public void init() {
        // Zabeleži v logger
        log.log(Level.INFO, "Inicializacija zrna StoritevZrno");
    }

    public List<Storitev> getStoritve(){
        log.log(Level.FINE, "Vračam vse storitve");
        Query q = em.createNamedQuery("Storitev.getAll");
        return (List<Storitev>)(q.getResultList());
    }

    public Storitev getStoritev(int id){
        log.log(Level.FINE, "Vračam storitev z id-jem " + id);
        Query q = em.createNamedQuery("Storitev.getOne");
        q.setParameter(1, id);
        Storitev sto = (Storitev) q.getSingleResult();
        em.refresh(sto);
        return sto;
    }

    @Transactional
    public void deleteStoritev(int id){
        log.log(Level.FINE, "Odstranjujem storitev z id-jem " + id);
        em.getTransaction().begin();
        Storitev sto = em.find(Storitev.class, id);
        Query q = em.createNamedQuery("Uporabniki.getAll");
        List<Uporabnik> uporabniki = (List<Uporabnik>)(q.getResultList());
        for(int i=0; i<uporabniki.size(); i++){
            ZbraneTockeId najdi = new ZbraneTockeId(id, uporabniki.get(i).getUporabnisko_ime());
            ZbraneTocke zt = em.find(ZbraneTocke.class, najdi);
            if(zt != null) {
                em.remove(zt);
            }
        }
        em.remove(sto);
        em.flush();
        em.getTransaction().commit();
    }

    @Transactional
    public void updateStoritev(int id, Storitev sto){
        log.log(Level.FINE, "Posodabljam storitev z id-jem " + id);
        em.getTransaction().begin();
        Storitev storitev = em.find(Storitev.class, id);
        storitev.setNaziv(sto.getNaziv());
        storitev.setOpis(sto.getOpis());
        storitev.setStPridobljenihTock(sto.getStPridobljenihTock());
        em.merge(storitev);
        em.getTransaction().commit();
    }

    @Transactional
    public void storeStoritev(Storitev sto) {
        log.log(Level.FINE, "Dodajam storitev");
        em.getTransaction().begin();
        em.persist(sto);
        em.getTransaction().commit();
    }
}
