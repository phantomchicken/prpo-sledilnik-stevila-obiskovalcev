package si.fri.prpo.skupina19.sledilnik.zrna;

import si.fri.prpo.skupina19.entitete.Zaposleni;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@ApplicationScoped
public class ZaposleniZrno {

    @PersistenceContext(unitName = "sledilnik-stevila-obiskovalcev-jpa")
    private EntityManager em;

    public List<String> getZaposleni() {

        // implementacija
        List<String> ime = em.createNamedQuery("Zaposleni.getIme").getResultList();
        return ime;
    }
}