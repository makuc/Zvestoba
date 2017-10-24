package si.fri.prpo;

import java.sql.Connection;
import java.util.List;

public interface BaseDao {
    Connection getConnection();

    Entiteta vrni(String username);

    void vstavi(Entiteta ent);

    void odstrani(String username);

    void posodobi(Entiteta ent);

    List<Entiteta> vrniVse();
}
