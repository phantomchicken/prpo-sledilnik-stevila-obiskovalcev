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

    @Inject
    UpravljanjeVrataZrno upravljanjeVrataZrno;

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

        ProstorDTO prostorDTO = new ProstorDTO();
        //prostorDTO.setProstorId(3);
        prostorDTO.setImeProstora("Bazen");
        prostorDTO.setKvadratovPoOsebi(10);
        prostorDTO.setKvadratura(500);
        prostorDTO.setStVrat(7);
        prostorDTO.setTrenutnoOseb(100);
        Prostor p = upravljanjeProstorovZrno.createProstor(prostorDTO);
        //izpis(writer,'p');
        //writer.append(upravljanjeProstorovZrno.getOmejitev(prostorDTO).toString());

        ZaposleniDTO zaposleniDTO = new ZaposleniDTO();
        //zaposleniDTO.setId(4);
        zaposleniDTO.setIme("Marko");
        zaposleniDTO.setPriimek("Ivanovski");
        //zaposleniDTO.setVrata(vrataDTO);
        Zaposleni z = upravljanjeZaposlenihZrno.createZaposleni(zaposleniDTO);
        //izpis(writer,'z');

        //zaposleniDTO.setId(4);
        //upravljanjeZaposlenihZrno.deleteZaposleni(zaposleniDTO);
        //izpis(writer,'z');

        VrataDTO vrataDTO = new VrataDTO();
        vrataDTO.setVrataId(4);
        vrataDTO.setStVstopov(23);
        vrataDTO.setStIzstopov(24);
        vrataDTO.setProstor(p);
        vrataDTO.setZaposleni(z);
        Vrata v = upravljanjeVrataZrno.createVrata(vrataDTO);

        writer.append("<h3>Zaposleni z DB</h3>");
        writer.append("<p>Zaposleni so:");
        izpisCriteria(writer,'z');

        writer.append("<h3>Vrata z DB </h3>");
        writer.append("<p>Stanja vrat so:");
        izpis(writer,'v');

        writer.append("<h3>Prostori z DB </h3>");
        writer.append("<p>Prostori so:");
        izpis(writer,'p');
    }
}