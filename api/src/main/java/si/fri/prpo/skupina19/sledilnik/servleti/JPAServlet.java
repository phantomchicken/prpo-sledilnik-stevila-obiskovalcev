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
import java.util.List;

@WebServlet("/servlet")
public class JPAServlet extends HttpServlet {

    @Inject
    private ZaposleniZrno zaposleniZrno;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //List<String> imePriimek = zaposleniZrno.getZaposleni();

        // izpis zaposlenih na spletni strani
        resp.setContentType("text/html; charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");

        PrintWriter writer = resp.getWriter();

        
        //izpis imen in priimkov zaposlenih
        writer.append("<h1>Poizvedba po podatkovni bazi</h1>");
        writer.append("<p>Zaposleni imajo imena: <ol>");
        zaposleniZrno.getZaposleni().stream().forEach( u -> writer.append("<li>" + u.toString() + "</li>"));
        writer.append("</ol></p>");

        //izpis vseh uporabnikov s CriteriaAPI
        writer.append("<h1>Poizvedba po podatkovni bazi s CriteriaAPI</h1>");
        writer.append("<p>Zaposleni imajo imena: <ol>");
        zaposleniZrno.getZaposleniCriteriaAPI().stream().forEach( u -> writer.append("<li>"+ u.toString() + "</li>"));
        writer.append("</ol></p>");
    }
}