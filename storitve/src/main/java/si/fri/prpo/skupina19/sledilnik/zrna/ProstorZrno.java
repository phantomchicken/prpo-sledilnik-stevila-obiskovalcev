package si.fri.prpo.skupina19.sledilnik.zrna;

import si.fri.prpo.skupina19.entitete.Prostor;
import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.ArrayList;
import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@ApplicationScoped
public class ProstorZrno {
    @PersistenceContext(unitName = "sledilnik-stevila-obiskovalcev-jpa")
    private EntityManager em;

    //implementacija
    public List<String> getProstori() {

        // implementacija
        TypedQuery<Object> query = em.createNamedQuery("Prostor.getIme", Object.class);
        List<Object> results = query.getResultList();
        List<String> imenaProstorov = new ArrayList<String>();
        for (Object result : results) {
            imenaProstorov.add((String) result);
        }

        return imenaProstorov;
    }

    // CriteriaAPI
    public List<Prostor> getProstoriCriteriaAPI () {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Prostor> query = criteriaBuilder.createQuery(Prostor.class);
        Root<Prostor> from = query.from(Prostor.class);
        query.select(from);
        return em.createQuery(query).getResultList();
    }

}
