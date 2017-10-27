package entitete;
import javax.persistence.*;

@Entity(name = "zbranetocke")
@NamedQueries(value =
        {
                @NamedQuery(name = "Opomnik.getAll", query = "SELECT o FROM zbranetocke o")
        })
public class ZbraneTocke {

    @ManyToOne
    @JoinColumn(name = "uporabniskoIme")
    private String UporabniskoIme;

    @ManyToOne
    @JoinColumn(name = "storitevId")
    private Integer storitevId;

    private Integer stTock;
}