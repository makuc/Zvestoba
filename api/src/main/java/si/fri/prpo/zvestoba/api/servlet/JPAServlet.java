package si.fri.prpo.zvestoba.api.servlet;

import si.fri.prpo.zvestoba.entitete.Storitev;
import si.fri.prpo.zvestoba.entitete.Uporabnik;
import si.fri.prpo.zvestoba.entitete.ZbraneTocke;
import si.fri.prpo.zvestoba.zrna.StoritevZrno;
import si.fri.prpo.zvestoba.zrna.UporabnikZrno;
import si.fri.prpo.zvestoba.zrna.ZbraneTockeZrno;

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

    @Inject
    private StoritevZrno storitveZrno;

    @Inject
    private ZbraneTockeZrno tockeZrno;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PrintWriter writer = resp.getWriter();

        // Izpisi vse uporabnike
        writer.append("SELECT u FROM uporabniki u\n");
        List<Uporabnik> uporabniki = uporabnikiZrno.getUporabniki();
        for(int i=0; i<uporabniki.size(); i++)
            writer.append(uporabniki.get(i).getIme() + "\n");

        // Ustvari novega uporabnika
        writer.append("\nKreiranje novega uporabnika:\n");
        Uporabnik newUser = new Uporabnik("testniupor", "Test","Testira","test.testira@testni.test");
        uporabnikiZrno.storeUporabnik(newUser);

        // Ponovno izpisi vse uporabnike
        uporabniki = uporabnikiZrno.getUporabniki();
        for(int i=0; i<uporabniki.size(); i++)
            writer.append(uporabniki.get(i).getIme() + "\n");

        // Izpisi ime novega uporabnika
        writer.append("\nSELECT u FROM uporabniki u WHERE u.uporabnisko_ime = ?1\n");
        writer.append(uporabnikiZrno.getUporabnik(newUser.getUporabnisko_ime()).getIme()+ "\n");

        // Spremeni ime novega uporabnika
        Uporabnik updateUser = new Uporabnik(newUser.getUporabnisko_ime(), "NovoIme","Testira","test.testira@testni.test");
        uporabnikiZrno.updateUporabnik(updateUser);
        writer.append("\nUPDATE uporabniki u SET u.ime = ?2 WHERE u.uporabnisko_ime = ?1\n");
        writer.append(uporabnikiZrno.getUporabnik(newUser.getUporabnisko_ime()).getIme()+ "\n");

        // Odstrani novega uporabnika iz baze
        writer.append("\nDELETE FROM uporabniki u WHERE u.uporabnisko_ime = ?1\n");
        uporabnikiZrno.deleteUporabnik(newUser.getUporabnisko_ime());
        uporabniki = uporabnikiZrno.getUporabniki();
        for(int i=0; i<uporabniki.size(); i++)
            writer.append(uporabniki.get(i).getIme() + "\n");

        //Izpis vseh storitev
        writer.append("\nSELECT o FROM storitve o\n");
        List<Storitev> storitve = storitveZrno.getStoritve();
        for(int i=0; i<storitve.size(); i++)
            writer.append(storitve.get(i).getNaziv() + "\n");

        // Izpis ene storitve
        writer.append("\nSELECT o FROM storitve o WHERE o.storitevId = ?1\n");
        Storitev sto = storitveZrno.getStoritev(1);
        writer.append(sto.getNaziv() + "\n");

        //Kreiranje storitve
        writer.append("\nDodajanje storitve\n");
        Storitev newSto = new Storitev("Dodajanje", "Dodajanje storitve", 3);
        storitveZrno.storeStoritev(newSto);
        storitve = storitveZrno.getStoritve();
        for(int i=0; i<storitve.size(); i++)
            writer.append(storitve.get(i).getNaziv() + "\n");

        // Sprememba naziva
        Storitev updateSto = new Storitev("Sprememba", "Dodajanje storitve", 3);
        storitveZrno.updateStoritev(newSto.getStoritevId(), updateSto);
        writer.append("\nUPDATE storitve s SET s.naziv = ?2 WHERE s.storitevId = ?1\n");
        writer.append(storitveZrno.getStoritev(newSto.getStoritevId()).getNaziv() + "\n");

        // Brisanje storitve
        writer.append("\nDELETE FROM storitve u WHERE u.storitevId = ?1\n");
        storitveZrno.deleteStoritev(newSto.getStoritevId());
        storitve = storitveZrno.getStoritve();
        for(int i=0; i<storitve.size(); i++)
            writer.append(storitve.get(i).getNaziv() + "\n");

        // Izpisovali bomo podatke za sledečega uporabnika
        Uporabnik upo = uporabnikiZrno.getUporabnik("petrakos");

        // Izpisovali bomo podatke za sledečo storitev
        sto = storitveZrno.getStoritev(1);

        // Izpisi tocke vseh uporabnikov za vse storitve
        writer.append("\nSELECT zt FROM zbrane_tocke zt\n");
        List<ZbraneTocke> tocke = tockeZrno.getZbraneTocke();
        for(int i=0; i<tocke.size(); i++)
            writer.append(tocke.get(i).toString() + "\n");


        // Izpisi storitve in tocke uporabnika
        writer.append("\nSELECT zt FROM zbrane_tocke zt WHERE zt.uporabnik = ?1\n");
        tocke = tockeZrno.getStoritveUporabnika(upo);
        for(int i=0; i<tocke.size(); i++)
            writer.append(tocke.get(i).toString() + "\n");


        // Izpisi uporabnike in tocke storitve
        writer.append("\nSELECT zt FROM zbrane_tocke zt WHERE zt.storitev = ?1\n");
        tocke = tockeZrno.getUporabnikeStoritve(sto);
        for(int i=0; i<tocke.size(); i++)
            writer.append(tocke.get(i).toString() + "\n");

        // Izpisi tocke storitve uporabnika
        writer.append("\nSELECT zt FROM zbrane_tocke zt WHERE zt.storitev = ?1 AND zt.uporabnik = ?2\n");
        writer.append(tockeZrno.getTockeStoritveUporabnika(upo, sto).toString() + "\n");

        // Dodaj uporabniku storitev
        Storitev dodStoritev = new Storitev("Nova testna storitev", "Uporablja se samo za testiranje", 3);
        storitveZrno.storeStoritev(dodStoritev);
        tockeZrno.dodajUporabnikuStoritev(upo, dodStoritev);
        writer.append("\nUporabniku: " + upo.getUporabnisko_ime() + ", dodana storitev: " + dodStoritev.getStoritevId() +"\n");
        writer.append(tockeZrno.getTockeStoritveUporabnika(upo, dodStoritev).toString() + "\n");

        // Povisaj uporabniku tocke storitve
        tockeZrno.povisajUporabnikuTockeStoritve(upo, dodStoritev);
        writer.append("\nUporabniku: " + upo.getUporabnisko_ime() + ", povisane tocke storitve: " + dodStoritev.getStoritevId() +"\n");
        writer.append(tockeZrno.getTockeStoritveUporabnika(upo, dodStoritev).toString() + "\n");
        // Povisaj uporabniku tocke storitve še enkrat, da se lepše vidi
        tockeZrno.povisajUporabnikuTockeStoritve(upo, dodStoritev);
        writer.append("Uporabniku: " + upo.getUporabnisko_ime() + " povisane tocke storitve: " + dodStoritev.getStoritevId() +"\n");
        writer.append(tockeZrno.getTockeStoritveUporabnika(upo, dodStoritev).toString() + "\n");
    }
}