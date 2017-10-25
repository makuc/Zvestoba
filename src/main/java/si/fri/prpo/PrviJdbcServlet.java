package si.fri.prpo;

import com.kumuluz.ee.configuration.utils.ConfigurationUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/servlet")
public class PrviJdbcServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // implementacija
        PrintWriter writer = resp.getWriter();

        writer.append("Hello World\n");

        String serviceName = ConfigurationUtil.getInstance().get("kumuluzee.env.name").orElse("Ne dobim config datoteke");

        writer.append(serviceName + "\n");

        //SELECT ONE
        BaseDao dao = new UporabnikDaoImpl();
        Uporabnik uporabnik = (Uporabnik) dao.vrni("mmico");

        writer.append("SELECT ONE:\n");
        writer.append(uporabnik.getIme()+ "\n");

        // INSERT
        writer.append("INSERT:\n");
        Uporabnik upo = new Uporabnik();
        upo.setUporabniskoIme("evome");
        upo.setIme("Toni");
        upo.setPriimek("Fer");
        upo.setEmail("toni.fer@gmail.com");
        dao.vstavi((Entiteta)upo);
        uporabnik = (Uporabnik) dao.vrni("evome");
        writer.append(uporabnik.getIme() + "\n");

        //SELECT ALL
        writer.append("SELECT ALL:\n");
        List<Entiteta> list = dao.vrniVse();
        for (int i=0; i<list.size(); i++){
            writer.append(((Uporabnik)list.get(i)).getIme() + "\n");
        }

        //UPDATE
        writer.append("UPDATE:\n");
        upo.setIme("Toncek");
        dao.posodobi((Entiteta)upo);
        uporabnik = (Uporabnik) dao.vrni("evome");
        writer.append(uporabnik.getIme() + "\n");

        //DELETE
        writer.append("DELETE:\n");
        dao.odstrani("evome");
        list = dao.vrniVse();
        for (int i=0; i<list.size(); i++){
            writer.append(((Uporabnik)list.get(i)).getIme() + "\n");
        }
    }
}