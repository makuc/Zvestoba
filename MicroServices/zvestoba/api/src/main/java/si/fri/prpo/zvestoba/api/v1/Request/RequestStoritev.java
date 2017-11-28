package si.fri.prpo.zvestoba.api.v1.Request;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RequestStoritev {
    @XmlElement Integer storitevId;
    @XmlElement String naziv;
    @XmlElement String opis;
    @XmlElement Integer stPridobljenihTock;

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
