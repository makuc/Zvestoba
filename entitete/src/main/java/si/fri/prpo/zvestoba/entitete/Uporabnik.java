package si.fri.prpo.zvestoba.entitete;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;

@Entity(name = "uporabniki")
@NamedQueries(value =
        {
                @NamedQuery(name = "Uporabniki.getAll", query = "SELECT u FROM uporabniki u"),
                @NamedQuery(name = "Uporabniki.getOne", query = "SELECT u FROM uporabniki u WHERE u.uporabnisko_ime = ?1"),
                @NamedQuery(name = "Uporabniki.updateIme", query = "UPDATE uporabniki u SET u.ime = ?2 WHERE u.uporabnisko_ime = ?1"),
                @NamedQuery(name = "Uporabniki.delete", query = "DELETE FROM uporabniki u WHERE u.uporabnisko_ime = ?1")
        })
public class Uporabnik {
    @Id
    @XmlElement
    @XmlID
    private String uporabnisko_ime;
    private String ime;
    private String priimek;
    private String email;

    public Uporabnik(){}
    public Uporabnik(String uporabnisko_ime, String ime, String priimek, String email)
    {
        this.uporabnisko_ime = uporabnisko_ime;
        this.ime = ime;
        this.priimek = priimek;
        this.email = email;
    }
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