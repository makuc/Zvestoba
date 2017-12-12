package si.fri.prpo.ponudniki.beans;

import si.fri.prpo.ponudniki.entities.Ponudniki;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
public class PonudnikiZrno {

    List<Ponudniki> ponudniki;

    private Logger log = Logger.getLogger(PonudnikiZrno.class.getName());

    @Inject
    QueueManager queueManager;

    @PostConstruct
    public void init() {
        log.info("Ustvarjam entitete...");

        ponudniki = new LinkedList<Ponudniki>();

        Ponudniki ponudnik;

        ponudnik = addPonudnik("Ponudnik 1");

        ponudnik = addPonudnik("Ponudnik 2");

        ponudnik = addPonudnik("Ponudnik 3");

        log.info("Osnovne entitete ustvarjene!");
    }

    public List<Ponudniki> getPonudniki() {
        log.info("Pridobivam seznam vseh ponudnikov");
        return ponudniki;
    }

    public Ponudniki getPonudnik(Integer ponudnikId) {
        log.info("Pridobivam ponudnika z ID: " + ponudnikId);
        for(Ponudniki ponudnik : ponudniki) {
            if(ponudnik.getPonudnikId() == ponudnikId)
                return ponudnik;
        }
        return null;
    }

    public Ponudniki addPonudnik(String ime_ponudnika) {
        Ponudniki ponudnik = new Ponudniki(ponudniki.size(), ime_ponudnika);
        while(getPonudnik(ponudnik.getPonudnikId()) != null) {
            ponudnik.setPonudnikId(ponudnik.getPonudnikId() +1);
        }
        return addPonudnik(ponudnik);
    }
    public Ponudniki addPonudnik(Ponudniki ponudnik) {
        log.info("Dodajam ponudnika: " + ponudnik.getIme_ponudnika());

        if(getPonudnik(ponudnik.getPonudnikId()) != null)
            return null;

        ponudniki.add(ponudnik);
        queueManager.posljiObvestiloODodanemPonudniku(ponudnik.getPonudnikId());
        return ponudnik;
    }
}
