package si.fri.prpo.skupina19.sledilnik.zrna;

import si.fri.prpo.skupina19.entitete.Vrata;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.*;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class VrataZrno {
    @PersistenceContext(unitName = "sledilnik-stevila-obiskovalcev-jpa")
    private EntityManager em;

    //implementacija
    public List<String> getSt() {

        // implementacija
        TypedQuery<Object[]> query = em.createNamedQuery("Vrata.getSt", Object[].class);
        List<Object[]> results = query.getResultList();
        List<String> stevili = new ArrayList<String>();
        for (Object[] result : results) {
            stevili.add(result[0] + " " + result[1]);
        }

        return stevili;
    }

    // CriteriaAPI
    public List<Vrata> getStCriteriaAPI () {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Vrata> query = criteriaBuilder.createQuery(Vrata.class);
        Root<Vrata> from = query.from(Vrata.class);
        query.select(from);
        return em.createQuery(query).getResultList();
    }
}
