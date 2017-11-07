package si.fri.prpo.zvestoba.entitete;

import java.io.Serializable;

public class ZbraneTockeId implements Serializable {
    private Integer storitev;
    private String uporabnik;

    public ZbraneTockeId() {}

    public ZbraneTockeId(Integer storitev, String uporabnik)
    {
        this.storitev = storitev;
        this.uporabnik = uporabnik;
    }

    public Integer getStoritevid() {
        return storitev;
    }

    public String getUporabnisko_ime() {
        return uporabnik;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((uporabnik == null) ? 0 : uporabnik.hashCode());
        result = prime * result + storitev;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ZbraneTockeId other = (ZbraneTockeId) obj;
        if (uporabnik == null) {
            if (other.uporabnik != null)
                return false;
        } else if (!uporabnik.equals(other.uporabnik))
            return false;
        if (storitev != other.storitev)
            return false;
        return true;
    }
}
