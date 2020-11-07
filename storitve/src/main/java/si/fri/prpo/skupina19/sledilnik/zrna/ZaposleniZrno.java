package si.fri.prpo.skupina19.sledilnik.zrna;

import si.fri.prpo.skupina19.entitete.Zaposleni;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.ArrayList;
import javax.persistence.criteria.*;

@ApplicationScoped
public class ZaposleniZrno {

    @PersistenceContext(unitName = "sledilnik-stevila-obiskovalcev-jpa")
    private EntityManager em;

    //vrne vse zaposlene
    public List<String> getZaposleni() {

        // implementacija
        TypedQuery<Object[]> query = em.createNamedQuery("Zaposleni.getImeInPriimek", Object[].class);
        List<Object[]> results = query.getResultList();
        List<String> imeInPriimek = new ArrayList<String>();
        for (Object[] result : results) {
            imeInPriimek.add(result[0] + " " + result[1]);
        }

        return imeInPriimek;
    }

    //vrne vse zaposlene z CriteriaAPI
    public List<Zaposleni> getZaposleniCriteriaAPI () {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Zaposleni> query = criteriaBuilder.createQuery(Zaposleni.class);
        Root<Zaposleni> from = query.from(Zaposleni.class);
        query.select(from);
        return em.createQuery(query).getResultList();
    }
}