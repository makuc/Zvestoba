package si.fri.prpo.ponudniki.beans;

import si.fri.prpo.ponudniki.entities.Ponudniki;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
public class PonudnikiZrno {

    List<Ponudniki> ponudniki;

    private Logger log = Logger.getLogger(PonudnikiZrno.class.getName());

    @PostConstruct
    public void init() {
        log.info("Ustvarjam entitete...");

        ponudniki = new LinkedList<Ponudniki>();

        Ponudniki ponudnik = new Ponudniki(0, "Ponudnik 1");
        ponudniki.add(ponudnik);

        ponudnik = new Ponudniki(1, "Ponudnik 2");
        ponudniki.add(ponudnik);

        ponudnik = new Ponudniki(2, "Ponudnik 3");
        ponudniki.add(ponudnik);

        log.info("Entitete ustvarjene!");
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
}
