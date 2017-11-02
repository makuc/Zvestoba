package entitete;
import javax.persistence.*;

@Entity(name = "uporabniki")
@NamedQueries(value =
        {
                @NamedQuery(name = "Uporabniki.getAll", query = "SELECT u FROM uporabniki u")
        })
public class Uporabnik {
    @Id
    private String uporabnisko_ime;
    private String ime;
    private String priimek;
    private String email;
    public String getUporabnisko_ime() {
        return uporabnisko_ime;
    }
    public void setUporabnisko_ime(String uporabnisko_ime) {
        this.uporabnisko_ime = uporabnisko_ime;
    }
    public String getIme() {
        return ime;
    }
    public void setIme(String ime) {
        this.ime = ime;
    }
    public String getPriimek() {
        return priimek;
    }
    public void setPriimek(String priimek) {
        this.priimek = priimek;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}