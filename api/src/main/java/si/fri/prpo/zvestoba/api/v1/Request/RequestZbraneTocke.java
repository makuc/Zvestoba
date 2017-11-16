package si.fri.prpo.zvestoba.api.v1.Request;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RequestZbraneTocke {
    @XmlElement String uporabnisko_ime;
    @XmlElement Integer storitevId;

    public String getUporabnisko_ime() {
        return uporabnisko_ime;
    }

    public void setUporabnisko_ime(String uporabnisko_ime) {
        this.uporabnisko_ime = uporabnisko_ime;
    }

    public Integer getStoritevId() {
        return storitevId;
    }

    public void setStoritevId(Integer storitevId) {
        this.storitevId = storitevId;
    }
}