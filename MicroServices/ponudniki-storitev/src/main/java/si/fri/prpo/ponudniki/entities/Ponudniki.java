package si.fri.prpo.ponudniki.entities;

public class Ponudniki {
    private Integer ponudnikId;
    private String ime_ponudnika;

    public Ponudniki(Integer id, String ime_ponudnika) {
        this.ime_ponudnika = ime_ponudnika;
        this.ponudnikId = id;
    }

    public Ponudniki() {
    }

    public Integer getPonudnikId() {
        return ponudnikId;
    }

    public void setPonudnikId(Integer ponudnikId) {
        this.ponudnikId = ponudnikId;
    }

    public String getIme_ponudnika() {
        return ime_ponudnika;
    }

    public void setIme_ponudnika(String ime_ponudnika) {
        this.ime_ponudnika = ime_ponudnika;
    }
}