package si.fri.prpo.skupina19.sledilnik.servleti;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
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
        /// System.out.println("Zaposleni vrata: "+z.getVrata());
        ////    zaposleniDTO.setId(z.getId());
        //izpis(writer,'z');

        //zaposleniDTO.setId(4);
        //upravljanjeZaposlenihZrno.deleteZaposleni(zaposleniDTO);
        //izpis(writer,'z');


        //zaposleniDTO.setId(v.getId());
        //System.out.println("nova vrata: "+v);
        //zaposleniDTO.setVrata(v);

        writer.append("<h3>Prostori z DB </h3>");
        writer.append("<p>Prostori so:");
        izpis(writer,'p');

        writer.append("<h3>Vrata z DB </h3>");
        writer.append("<p>Stanja vrat so:");
        izpis(writer,'v');

        writer.append("<h3>Zaposleni z DB</h3>");
        writer.append("<p>Zaposleni so:");
        izpisCriteria(writer,'z');




        writer.append("<h3>Po povecanju</h3>");

        ProstorDTO prostorDTO = new ProstorDTO();
        prostorDTO.setImeProstora("Bazen");
        prostorDTO.setKvadratovPoOsebi(10);
        prostorDTO.setKvadratura(500);
        prostorDTO.setStVrat(7);
        prostorDTO.setTrenutnoOseb(100);
        Prostor p = upravljanjeProstorovZrno.createProstor(prostorDTO);
        Vrata v = null;

        if(p != null && p.getId() != null) {
            writer.append("<p>Ustvarjen nov prostor in vrata</p>");

            VrataDTO vrataDTO = new VrataDTO();
            vrataDTO.setStVstopov(23);
            vrataDTO.setStIzstopov(24);
            vrataDTO.setProstor(p);
            v = upravljanjeVrataZrno.createVrata(vrataDTO);

            writer.append("<h3>Prostori z DB </h3>");
            writer.append("<p>Prostori so:");
            izpis(writer,'p');

            writer.append("<h3>Vrata z DB </h3>");
            writer.append("<p>Stanja vrat so:");
            izpis(writer,'v');


        }
        else {
            writer.append("<p>Novega prostora z obstojecim imenom ni mogoce dodati</p>");
        }


        ZaposleniDTO zaposleniDTO = new ZaposleniDTO();
        zaposleniDTO.setVzdevek("markos");
        zaposleniDTO.setIme("Marko");
        zaposleniDTO.setPriimek("Ivanovski");
        zaposleniDTO.setVrata(v);

        Zaposleni z = upravljanjeZaposlenihZrno.createZaposleni(zaposleniDTO);

        if(z != null && z.getId() != null) {
            writer.append("<p>Dodat novi zaposleni</p>");

            writer.append("<h3>Zaposleni z DB</h3>");
            writer.append("<p>Zaposleni so:");
            izpisCriteria(writer,'z');

        }
        else {
            writer.append("<p>Novega zaposlenega z obstojecim vzdevkom ni mogoce dodati</p>");
        }



        //prostorZrno.deleteProstor(3);
        //izpis(writer,'p');

        // Integer novo = upravljanjeZaposlenihZrno.povecajStevilo(zaposleniDTO);
        //  prostorDTO.setTrenutnoOseb(novo);
        List<Vrata> spremenjeni = upravljanjeZaposlenihZrno.spremeniSteviloOsebPoZaposlenim();
        upravljanjeVrataZrno.updateSpremenjeneProstore(spremenjeni);

        writer.append("<p>Spremenjeno stevilo oseb v prostorih</p>");
        writer.append("<h3>Prostori z DB </h3>");
        writer.append("<p>Prostori so:");
        izpis(writer,'p');

        writer.append("<hr>");
        Vrata v1 = new Vrata();
        v1.setId(1);
        v1.setProstor(null);
        v1.setStIzstopov(0);
        v1.setStVstopov(0);
        v1.setZaposleni(null);
        vrataZrno.updateVrata(1,v1);
        izpis(writer,'v');

        vrataZrno.deleteVrata(4);
        izpis(writer,'v');
        izpis(writer,'z');
        izpis(writer,'p');

    }
}