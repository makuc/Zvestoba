package si.fri.prpo.zvestoba.zrna;

import com.kumuluz.ee.configuration.utils.ConfigurationUtil;
import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import si.fri.prpo.zvestoba.anotacije.BeleziKlice;
import si.fri.prpo.zvestoba.entitete.Storitev;
import si.fri.prpo.zvestoba.entitete.podatkovne.Ponudniki;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
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

    private Client httpClient;
    private String baseURL;

    @PostConstruct
    public void init() {
        // Zabeleži v logger
        log.log(Level.INFO, "Inicializacija zrna StoritevZrno");

        httpClient = ClientBuilder.newClient();
        //baseURL = "http://localhost:8081/v1";
        baseURL = ConfigurationUtil.getInstance().get("rest-properties.external-services.ponudniki-storitev.base-url").get();
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

        storitev.setPonudnikStoritve(pridobiPonudnikaStoritve(storitev.getPonudnikId()));

        return storitev;
    }

    private List<Ponudniki> pridobiPonudnikeStoritev() {
        try {
            return httpClient
                    .target(baseURL + "/ponudniki")
                    .request().get(new GenericType<List<Ponudniki>>() {});
        } catch (WebApplicationException | ProcessingException e) {
            log.severe(e.getMessage());
            throw new InternalServerErrorException(e);
        }
    }
    private Ponudniki pridobiPonudnikaStoritve(Integer ponudnikId) {
        try {
            return httpClient
                    .target(baseURL + "/ponudniki/" + ponudnikId)
                    .request().get(new GenericType<Ponudniki>() {});
        } catch (WebApplicationException | ProcessingException e) {
            log.severe(e.getMessage());
            throw new InternalServerErrorException(e);
        }
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

    public Storitev createStoritev(Integer storitevId, String naziv, Integer ponudnikId, String opis, Integer stPridobljenihTock){
        log.fine("Ustvarjam novo storitev");

        Storitev storitev = new Storitev(naziv, ponudnikId, opis, stPridobljenihTock);

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
