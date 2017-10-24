package si.fri.prpo;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class UporabnikDaoImpl implements BaseDao{

    private Connection con = null;
    private Logger log = Logger.getLogger(UporabnikDaoImpl.class.getName());

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
    public Entiteta vrni(String username) {
        PreparedStatement ps = null;

        try {

            if (con == null) {
                con = getConnection();
            }


            String sql = "SELECT * FROM uporabniki WHERE uporabniskoime = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, username);
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
        PreparedStatement ps = null;
        Uporabnik upo = (Uporabnik) ent;
        try {

            if (con == null) {
                con = getConnection();
            }


            String sql = "INSERT INTO uporabniki VALUES (? , ?, ?, ?)";
            ps = con.prepareStatement(sql);
            ps.setString(1, upo.getUporabniskoIme());
            ps.setString(2, upo.getIme());
            ps.setString(3, upo.getPriimek());
            ps.setString(4, upo.getEmail());
            ps.executeQuery();
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
    }

    @Override
    public void odstrani(String username) {
        PreparedStatement ps = null;

        try {

            if (con == null) {
                con = getConnection();
            }


            String sql = "DELETE FROM uporabniki WHERE uporabniskoime = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, username);
            ps.executeQuery();
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
    }

    @Override
    public void posodobi(Entiteta ent) {
        PreparedStatement ps = null;
        Uporabnik upo = (Uporabnik)ent;
        try {

            if (con == null) {
                con = getConnection();
            }


            String sql = "UPDATE uporabniki SET uporabniskoime=?, ime=?, priimek=?, email=? WHERE uporabniskoime=?";
            ps = con.prepareStatement(sql);
            ps.setString(1, upo.getUporabniskoIme());
            ps.setString(2, upo.getIme());
            ps.setString(3, upo.getPriimek());
            ps.setString(4, upo.getEmail());
            ps.setString(5, upo.getUporabniskoIme());
            ps.executeQuery();
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
    }

    @Override
    public List<Entiteta> vrniVse() {

        PreparedStatement ps = null;

        try {

            if (con == null) {
                con = getConnection();
            }


            String sql = "SELECT * FROM uporabniki";
            ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            List<Entiteta> uporabniki = new ArrayList<Entiteta>();

            while (rs.next()) {
                String ime = rs.getString("ime");
                String priimek = rs.getString("priimek");
                String uporabniskoIme = rs.getString("uporabniskoIme");
                String email = rs.getString("email");
                Uporabnik uporabnik = new Uporabnik();
                uporabnik.setEmail(email);
                uporabnik.setIme(ime);
                uporabnik.setPriimek(priimek);
                uporabnik.setUporabniskoIme(uporabniskoIme);
                uporabniki.add((Entiteta)uporabnik);
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
