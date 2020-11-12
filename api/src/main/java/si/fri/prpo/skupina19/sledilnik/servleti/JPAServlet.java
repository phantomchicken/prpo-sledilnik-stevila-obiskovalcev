package si.fri.prpo.skupina19.sledilnik.servleti;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import si.fri.prpo.skupina19.entitete.*;
import si.fri.prpo.skupina19.sledilnik.dtos.ProstorDTO;
import si.fri.prpo.skupina19.sledilnik.dtos.ZaposleniDTO;
import si.fri.prpo.skupina19.sledilnik.dtos.VrataDTO;
import si.fri.prpo.skupina19.sledilnik.zrna.UpravljanjeProstorovZrno;
import si.fri.prpo.skupina19.sledilnik.zrna.*;

@WebServlet("/servlet")
public class JPAServlet extends HttpServlet {

    @Inject
    private ZaposleniZrno zaposleniZrno;

    @Inject
    private ProstorZrno prostorZrno;

    @Inject
    private VrataZrno vrataZrno;

    @Inject
    UpravljanjeProstorovZrno upravljanjeProstorovZrno;

    @Inject
    UpravljanjeZaposlenihZrno upravljanjeZaposlenihZrno;

    public void izpis(PrintWriter writer, char c){
        writer.append("<ol>");
        if (c=='z') zaposleniZrno.getZaposleni().stream().forEach( z -> writer.append("<li>" + z + "</li>"));
        if (c=='v') vrataZrno.getVsaVrata().stream().forEach( v -> writer.append("<li>" + v + "</li>"));
        if (c=='p') prostorZrno.getProstori().stream().forEach( p -> writer.append("<li>" + p + "</li>"));
        writer.append("</ol></p>");
    }

    public void izpisCriteria(PrintWriter writer, char c){
        writer.append("<ol>");
        if (c=='z') zaposleniZrno.getZaposleniCriteriaAPI().stream().forEach( z -> writer.append("<li>" + z + "</li>"));
        if (c=='v') vrataZrno.getStCriteriaAPI().stream().forEach( v -> writer.append("<li>" + v + "</li>"));
        if (c=='p') prostorZrno.getProstoriCriteriaAPI().stream().forEach( p -> writer.append("<li>" + p + "</li>"));
        writer.append("</ol></p>");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        // izpis zaposlenih na spletni strani
        resp.setContentType("text/html; charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");

        PrintWriter writer = resp.getWriter();

        writer.append("<h1>Enostavne GET operacije</h1>");

        //izpis imen in priimkov zaposlenih
        writer.append("<h3>Zaposleni z DB</h3>");
        writer.append("<p>Zaposleni so:");
        izpis(writer,'z');

        /*izpis vseh uporabnikov s CriteriaAPI
        writer.append("<h3>Zaposleni s CriteriaAPI</h3>");
        writer.append("<p>Zaposleni so:");
        izpisCriteria(writer,'z');*/

        //izpis stanja vrat
        writer.append("<h3>Vrata z DB </h3>");
        writer.append("<p>Stanja vrat so:");
        izpis(writer,'v');

        /*izpis stanja vrat s CriteriaAPI
        writer.append("<h3>Vrata s CriteriaAPI</h3>");
        writer.append("<p>Stanja vrat  so:");
        izpisCriteria(writer,'v');*/

        //izpis prostorov
        writer.append("<h3>Prostori z DB </h3>");
        writer.append("<p>Prostori so:");
        //writer.append(prostorZrno.getProstori().toString());
        izpis(writer,'p');

        /*izpis vseh prostorov s CriteriaAPI
        writer.append("<h3>Prostori s CriteriaAPI</h3>");
        writer.append("<p>Prostori so:");
        izpisCriteria(writer,'p');*/

        //POPRAVITI?
        //writer.append("<h1>Test</h1>");
        //zaposleniZrno.getDelovnoMesto().stream().forEach( z -> writer.append("<li>"+ z.toString() + "</li>"));

        ProstorDTO prostorDTO = new ProstorDTO();
        prostorDTO.setProstorId(3);
        prostorDTO.setImeProstora("Bazen");
        prostorDTO.setKvadratovPoOsebi(10);
        prostorDTO.setKvadratura(1000);
        prostorDTO.setStVrat(7);
        prostorDTO.setTrenutnoOseb(100);
        Prostor p = upravljanjeProstorovZrno.createProstor(prostorDTO);

        /*writer.append("<hr>");
        writer.append("<h1>Testiranje CRUD operacij</h1>");

        writer.append("<h3>Create prostor</h3><p>");
        Prostor prostor = new Prostor();
        prostor.setId(3);
        prostor.setImeProstora("Bazen");
        prostor.setKvadratovPoOsebi(10);
        prostor.setKvadratura(1000);
        prostor.setStVrat(7);
        prostor.setTrenutnoOseb(100);
        prostorZrno.createProstor(prostor);
        izpis(writer,'p');*/

        ZaposleniDTO zaposleniDTO = new ZaposleniDTO();
        zaposleniDTO.setId(4);
        zaposleniDTO.setIme("Marko");
        zaposleniDTO.setPriimek("Ivanovski");
        //zaposleniDTO.setVrata();
        Zaposleni z = upravljanjeZaposlenihZrno.createZaposleni(zaposleniDTO);

        /*writer.append("<h3>Create zaposleni</h3><p>");
        Zaposleni zaposleni = new Zaposleni();
        zaposleni.setIme("Patrick");
        zaposleni.setPriimek("Ewing");
        zaposleni.setId(4);
        zaposleniZrno.createZaposleni(zaposleni);
        izpis(writer,'z');

        writer.append("<h3>Update prostor</h3><p>");
        prostor.setImeProstora("BazenNEW");
        prostorZrno.updateProstor(3,prostor);
        izpis(writer,'p');

        writer.append("<h3>Update zaposleni</h3><p>");
        zaposleni.setIme("Patrick Aloysius");
        zaposleniZrno.updateZaposleni(4,zaposleni);
        izpis(writer,'z');

        writer.append("<h3>Delete prostor</h3><p>");
        prostorZrno.deleteProstor(3);
        izpis(writer,'p');

        writer.append("<h3>Delete zaposleni</h3><p>");
        zaposleniZrno.deleteZaposleni(4);
        izpis(writer,'z');*/
    }
}