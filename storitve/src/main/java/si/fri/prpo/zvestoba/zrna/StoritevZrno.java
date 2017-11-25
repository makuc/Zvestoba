package si.fri.prpo.zvestoba.zrna;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import org.omg.CosNaming.NamingContextPackage.NotFound;
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
import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@ApplicationScoped
@BeleziKlice

public class StoritevZrno {
    @PersistenceContext(unitName = "zvestoba-jpa")
    private EntityManager em;

    @Inject
    ZbraneTockeZrno tockeZrno;

    private Logger log = Logger.getLogger(StoritevZrno.class.getName());

    @PostConstruct
    public void init() {
        // Zabeleži v logger
        log.log(Level.INFO, "Inicializacija zrna StoritevZrno");
    }

    public List<Storitev> getStoritve(QueryParameters queryParameters){
        log.fine("Vračam vse storitve");

        List<Storitev> zbraneTocke = JPAUtils.queryEntities(em, Storitev.class, queryParameters);

        return zbraneTocke;
    }

    public Storitev getStoritev(Integer storitevId){

        if(storitevId == null){
            log.warning("Za iskanje storitve potreben: storitevId");
            return null;
        }

        log.log(Level.FINE, "Vračam storitev s storitevId: " + storitevId);

        Storitev storitev = em.find(Storitev.class, storitevId);

        if(storitev == null)
            throw new NotFoundException();

        return storitev;
    }

    @Transactional
    public void deleteStoritev(Integer storitevId){

        if(storitevId == null) {
            log.warning("Ne morem odstraniti storitve brez: storitevId");
        }

        log.fine("Odstranjujem storitev s storitevId: " + storitevId);

        Storitev storitev = getStoritev(storitevId);

        tockeZrno.deleteUporabnikeStoritve(storitev);

        em.getTransaction().begin();
        em.remove(storitev);
        em.getTransaction().commit();
    }

    public Storitev createStoritev(Integer storitevId, String naziv, String opis, Integer stPridobljenihTock){
        log.fine("Ustvarjam novo storitev");

        Storitev storitev = new Storitev(naziv, opis, stPridobljenihTock);

        return storeStoritev(storitev);
    }

    @Transactional
    public Storitev updateStoritev(Integer storitevId, Storitev storitev){

        log.log(Level.FINE, "Posodabljam storitev z id-jem " + storitevId);

        Storitev posodobi = getStoritev(storitevId);

        if(storitev.getNaziv() != null)
            posodobi.setNaziv(storitev.getNaziv());
        if(storitev.getOpis() != null)
            posodobi.setOpis(storitev.getOpis());
        if(storitev.getStPridobljenihTock() != null)
            posodobi.setStPridobljenihTock(storitev.getStPridobljenihTock());

        em.getTransaction().begin();
        posodobi = em.merge(posodobi);
        em.getTransaction().commit();

        return posodobi;
    }

    @Transactional
    public Storitev storeStoritev(Storitev storitev) {

        log.fine("Shranjujem storitev");

        em.getTransaction().begin();
        em.persist(storitev);
        em.getTransaction().commit();

        return getStoritev(storitev.getStoritevId());
    }
}
