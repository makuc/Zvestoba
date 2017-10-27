package entitete;
import javax.persistence.*;
@Entity(name = "storitve")
@NamedQueries(value =
        {
                @NamedQuery(name = "Storitev.getAll", query = "SELECT o FROM storitve o")
        })
public class Storitev {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer storitevId;
    private String naziv;
    private String opis;
    private Integer stPridobljenihTock;
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
}