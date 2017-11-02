import entitete.Uporabnik;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/servlet")
public class JPAServlet extends HttpServlet {

    @Inject
    private UporabnikZrno uporabnikiZrno;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {



        PrintWriter writer = resp.getWriter();

        writer.append("SELECT u FROM uporabniki u\n");
        List<Uporabnik> uporabniki = uporabnikiZrno.getUporabniki();
        for(int i=0; i<uporabniki.size(); i++)
        writer.append(uporabniki.get(i).getIme() + "\n");

        writer.append("\nSELECT u FROM uporabniki u WHERE u.uporabnisko_ime = ?1\n");
        writer.append(uporabnikiZrno.getUporabnik("petrakos").getIme()+ "\n");

        writer.append("\nDELETE FROM uporabniki u WHERE u.uporabnisko_ime = ?1\n");
        uporabnikiZrno.deleteUporabnik("petrakos");
        uporabniki = uporabnikiZrno.getUporabniki();
        for(int i=0; i<uporabniki.size(); i++)
            writer.append(uporabniki.get(i).getIme() + "\n");








    }
}