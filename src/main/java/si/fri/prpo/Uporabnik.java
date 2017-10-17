package si.fri.prpo;

public class Uporabnik extends Entiteta {

    private String uporabniskoIme;
    private String ime;
    private String priimek;
    private String email;

    public String getUporabniskoIme(){
        return this.uporabniskoIme;
    }
    public void setUporabniskoIme(String uporabniskoIme){
        this.uporabniskoIme = uporabniskoIme;
    }

    public String getIme(){
        return this.ime;
    }
    public void setIme(String ime){
        this.ime = ime;
    }

    public String getPriimek(){
        return this.priimek;
    }
    public void setPriimek(String priimek){
        this.priimek = priimek;
    }

    public String getEmail() {
        return this.email;
    }
    public void setEmail(String email){
        this.email = email;
    }

}
