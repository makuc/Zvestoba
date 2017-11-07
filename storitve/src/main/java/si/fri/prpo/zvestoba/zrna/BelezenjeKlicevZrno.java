package si.fri.prpo.zvestoba.zrna;

public class BelezenjeKlicevZrno {
    private Integer counter;

    public BelezenjeKlicevZrno(){
        this.counter = 0;
    }
    public Integer getCounter() {
        return counter;
    }
    public void increaseCounter(){
        this.counter++;
        System.out.printf("Števec povišan: %d\n", this.counter);
    }
}
