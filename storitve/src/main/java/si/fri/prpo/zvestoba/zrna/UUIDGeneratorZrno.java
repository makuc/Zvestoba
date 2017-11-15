package si.fri.prpo.zvestoba.zrna;

import javax.enterprise.context.RequestScoped;
import java.util.UUID;

@RequestScoped
public class UUIDGeneratorZrno {

    private UUID id;

    public UUID getId() {

        if(id == null)
            id = UUID.randomUUID();

        return id;
    }
}
