package si.fri.prpo.zvestoba.entitete;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlIDREF;


@Entity(name = "zbrane_tocke")
@IdClass(ZbraneTockeId.class)
@NamedQueries(value =
        {
                @NamedQuery(name = "ZbraneTocke.getAll", query = "SELECT zt FROM zbrane_tocke zt"),
                @NamedQuery(name = "ZbraneTocke.getStoritveUporabnika",
                        query = "SELECT zt FROM zbrane_tocke zt WHERE zt.uporabnik = ?1"),
                @NamedQuery(name = "ZbraneTocke.getUporabnikeStoritve",
                        query = "SELECT zt FROM zbrane_tocke zt WHERE zt.storitev = ?1"),
                @NamedQuery(name = "ZbraneTocke.getTockeStoritveUporabnika",
                        query = "SELECT zt FROM zbrane_tocke zt WHERE zt.storitev = ?1 AND zt.uporabnik = ?2")
        })
public class ZbraneTocke {

    @XmlIDREF
    @ManyToOne
    @Id
    @JoinColumn(name = "uporabnisko_ime", referencedColumnName = "uporabnisko_ime")
    private Uporabnik uporabnik;

    @ManyToOne
    @Id
    @JoinColumn(name = "storitevid", referencedColumnName = "storitevid")
    private Storitev storitev;

    private Integer st_tock;

    public ZbraneTocke(){
        this.st_tock = 0;
    }
    public ZbraneTocke(Uporabnik uporabnik, Storitev storitev){
        this.uporabnik = uporabnik;
        this.storitev = storitev;
        this.st_tock = 0;
    }

    public Uporabnik getUporabnik() {
        return uporabnik;
    }

    public void setUporabnik(Uporabnik uporabnik) {
        this.uporabnik = uporabnik;
    }

    public Storitev getStoritev() {
        return storitev;
    }

    public void setStoritev(Storitev storitev) {
        this.storitev = storitev;
    }

    public Integer getSt_tock() {
        return st_tock;
    }

    public void setSt_tock(Integer st_tock) {
        this.st_tock = st_tock;
    }

    public String toString(){
        return "Uporabnisko ime: " + uporabnik.getUporabnisko_ime() + "; Storitev: " + storitev.getStoritevId() + "; Tock: " + st_tock;
    }
}