package si.fri.prpo;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

public class UporacnikDaoImpl implements BaseDao{

    private Connection con = null;
    private Logger log = Logger.getLogger(UporacnikDaoImpl.class.getName());

    @Override
    public Connection getConnection() {
        InitialContext initCtx = null;
        Connection con = null;
        try {
            initCtx = new InitialContext();
            DataSource ds = (DataSource) initCtx.lookup("jdbc/SimpleJdbcDS");
            con = ds.getConnection();
        } catch (NamingException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }

    @Override
    public Entiteta vrni(int id) {
        PreparedStatement ps = null;

        try {

            if (con == null) {
                con = getConnection();
            }

            String sql = "SELECT * FROM uporabnik WHERE id = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return getUporabnikFromRS(rs);
            } else {
                log.info("Uporabnik ne obstaja");
            }

        } catch (SQLException e) {
            log.severe(e.toString());
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    log.severe(e.toString());
                }
            }
        }
        return null;
    }

    @Override
    public void vstavi(Entiteta ent) {

    }

    @Override
    public void odstrani(int id) {

    }

    @Override
    public void posodobi(Entiteta ent) {

    }

    @Override
    public List<Entiteta> vrniVse() {
        return null;
    }

    private Uporabnik getUporabnikFromRS(ResultSet rs) throws SQLException {
        String ime = rs.getString("ime");
        String priimek = rs.getString("priimek");
        String uporabniskoIme = rs.getString("uporabniskoIme");
        String email = rs.getString("email");
        Uporabnik uporabnik = new Uporabnik();
        uporabnik.setEmail(email);
        uporabnik.setIme(ime);
        uporabnik.setPriimek(priimek);
        uporabnik.setUporabniskoIme(uporabniskoIme);
        return uporabnik;

    }
}
