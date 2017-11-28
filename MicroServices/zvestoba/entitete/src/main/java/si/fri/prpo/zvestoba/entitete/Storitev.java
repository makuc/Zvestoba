package si.fri.prpo.zvestoba.entitete;
import si.fri.prpo.zvestoba.entitete.podatkovne.Ponudniki;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;

@Entity(name = "storitve")
@NamedQueries(value =
        {
                @NamedQuery(name = "Storitev.getAll", query = "SELECT o FROM storitve o"),
                @NamedQuery(name = "Storitev.getOne", query = "SELECT o FROM storitve o WHERE o.storitevId = ?1"),
                @NamedQuery(name = "Storitev.updateNaziv", query = "UPDATE storitve s SET s.naziv = ?2 WHERE s.storitevId = ?1"),
                @NamedQuery(name = "Storitev.delete", query = "DELETE FROM storitve u WHERE u.storitevId = ?1")
        })
public class Storitev {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer storitevId;
    private String naziv;

    private Integer ponudnikId;

    @Transient
    private Ponudniki ponudnikStoritve;

    private Integer stPridobljenihTock;
    private String opis;

    public Storitev(String naziv, String opis, Integer stPridobljenihTock) {
        this.naziv = naziv;
        this.opis = opis;
        this.stPridobljenihTock = stPridobljenihTock;
    }
    public Storitev(String naziv, Integer ponudnikId, String opis, Integer stPridobljenihTock) {
        this.naziv = naziv;
        this.ponudnikId = ponudnikId;
        this.opis = opis;
        this.stPridobljenihTock = stPridobljenihTock;
    }

    public Storitev() {
    }

    public Integer getStoritevId() {
        return storitevId;
    }
    public void setStoritevId(Integer storitevId) {
        this.storitevId = storitevId;
    }
    public String getNaziv() {
        return naziv;
    }
    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }
    public String getOpis() {
        return opis;
    }
    public void setOpis(String opis) {
        this.opis = opis;
    }
    public Integer getStPridobljenihTock() {
        return stPridobljenihTock;
    }
    public void setStPridobljenihTock(Integer stPridobljenihTock) {
        this.stPridobljenihTock = stPridobljenihTock;
    }
    public Integer getPonudnikId() {
        return ponudnikId;
    }
    public void setPonudnikId(Integer ponudnikId) {
        this.ponudnikId = ponudnikId;
    }

    public Ponudniki getPonudnikStoritve() {
        return ponudnikStoritve;
    }
    public void setPonudnikStoritve(Ponudniki ponudnikStoritve) {
        this.ponudnikStoritve = ponudnikStoritve;
    }
}