package si.fri.prpo.zvestoba.zrna;

import java.util.logging.Level;
import java.util.logging.Logger;

public class BelezenjeKlicevZrno {
    private Integer counter;

    private Logger log = Logger.getLogger(UporabnikZrno.class.getName());

    public BelezenjeKlicevZrno(){
        this.counter = 0;
    }
    public Integer getCounter() {
        return counter;
    }
    public void increaseCounter(){

        this.counter++;
        System.out.printf("Št. klicov metod: %d\n", this.counter);
    }
}
