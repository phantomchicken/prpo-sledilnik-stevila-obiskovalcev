package si.fri.prpo.skupina19.sledilnik.servleti;

import java.io.IOException;
import java.io.PrintWriter;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import si.fri.prpo.skupina19.entitete.*;
import si.fri.prpo.skupina19.sledilnik.zrna.*;

@WebServlet("/servlet")
public class JPAServlet extends HttpServlet {

    @Inject
    private ZaposleniZrno zaposleniZrno;

    @Inject
    private ProstorZrno prostorZrno;

    @Inject
    private VrataZrno vrataZrno;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        // izpis zaposlenih na spletni strani
        resp.setContentType("text/html; charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");

        PrintWriter writer = resp.getWriter();

        
        //izpis imen in priimkov zaposlenih
        writer.append("<h1>Zaposleni z DB</h1>");
        writer.append("<p>Zaposleni imajo imena: <ol>");
        zaposleniZrno.getZaposleni().stream().forEach( z -> writer.append("<li>" + z + "</li>"));
        writer.append("</ol></p>");

        //izpis vseh uporabnikov s CriteriaAPI
        writer.append("<h1>Zaposleni s CriteriaAPI</h1>");
        writer.append("<p>Zaposleni imajo imena: <ol>");
        zaposleniZrno.getZaposleniCriteriaAPI().stream().forEach( z -> writer.append("<li>"+ z.toString() + "</li>"));
        writer.append("</ol></p>");

        //izpis stanja vrat
        writer.append("<h1>Vrata z DB </h1>");
        writer.append("<p>Stanja vrat so: <ol>");
        vrataZrno.getSt().stream().forEach( v -> writer.append("<li>" + v + "</li>"));
        writer.append("</ol></p>");

        //izpis stanja vrat s CriteriaAPI
        writer.append("<h1>Vrata s CriteriaAPI</h1>");
        writer.append("<p>Stanja vrat  so: <ol>");
        vrataZrno.getStCriteriaAPI().stream().forEach( v -> writer.append("<li>"+ v + "</li>"));
        writer.append("</ol></p>");

        //izpis prostorov
        writer.append("<h1>Prostori z DB </h1>");
        writer.append("<p>Prostori so: <ol>");
        prostorZrno.getProstori().stream().forEach( p -> writer.append("<li>" + p + "</li>"));
        writer.append("</ol></p>");

        //izpis vseh prostorov s CriteriaAPI
        writer.append("<h1>Prostori s CriteriaAPI</h1>");
        writer.append("<p>Prostori so: <ol>");
        prostorZrno.getProstoriCriteriaAPI().stream().forEach( p -> writer.append("<li>"+ p.toString() + "</li>"));
        writer.append("</ol></p>");


        //writer.append("<h1>Test</h1>");
        //zaposleniZrno.getDelovnoMesto().stream().forEach( z -> writer.append("<li>"+ z.toString() + "</li>"));

    }
}