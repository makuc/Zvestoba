package si.fri.prpo;

import com.kumuluz.ee.configuration.utils.ConfigurationUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/servlet")
public class PrviJdbcServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // implementacija
        PrintWriter writer = resp.getWriter();

        writer.append("Hello World\n");

        String serviceName = ConfigurationUtil.getInstance().get("kumuluzee.env.name").orElse("Ne dobim config datoteke");

        writer.append(serviceName + "\n");

        BaseDao dao = new UporacnikDaoImpl();
        Uporabnik uporabnik = (Uporabnik) dao.vrni(1);

        writer.append(uporabnik.getIme());
    }
}