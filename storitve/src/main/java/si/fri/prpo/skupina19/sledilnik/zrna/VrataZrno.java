package si.fri.prpo.skupina19.sledilnik.zrna;

import si.fri.prpo.skupina19.entitete.Prostor;
import si.fri.prpo.skupina19.entitete.Vrata;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.persistence.*;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@ApplicationScoped
public class VrataZrno {
    private Logger log = Logger.getLogger(VrataZrno.class.getName());
    private String idV;

    @PostConstruct
    private void initialization(){
        idV = UUID.randomUUID().toString();
        log.info("Ustvarjeno VrataZrno z ID-jem: " + idV);
    }

    @PreDestroy
    private void destruction(){
        log.info("Uničeno VrataZrno z ID-jem: " + idV);
    }


    @PersistenceContext(unitName = "sledilnik-stevila-obiskovalcev-jpa")
    private EntityManager em;

    //CRUD
    public Vrata getVrata (int vrataId) {
        Vrata vrata = em.find(Vrata.class, vrataId);
        return vrata;
    }

    @Transactional
    public Vrata createVrata (Vrata vrata) {
        if (vrata != null) {
            em.persist(vrata);
        }
        return vrata;
    }

    @Transactional
    public Integer deleteVrata (int vrataId) {
        Vrata vrata = getVrata(vrataId);
        if (vrata != null) {
            em.remove(vrata);
        }
        return vrataId;
    }

    @Transactional
    public Vrata updateVrata (int vrataId, Vrata novaV) {
        Vrata v = em.find(Vrata.class, vrataId);
        if (v!= null){
            // ali modularno? Integer prostor, Integer zaposleni, Integer stIzstopov, Integer stVstopov
            v.setId(novaV.getId());
            v.setProstor(novaV.getProstor());
            v.setStIzstopov(novaV.getStIzstopov());
            v.setStVstopov(novaV.getStVstopov());
            v.setZaposleni(novaV.getZaposleni());
            em.merge(novaV);
        }
        return novaV;
    }

    public List<String> getVsaVrata() {
        Query query = em.createNamedQuery("Vrata.getAll");
        List <Object> results = query.getResultList();
        ArrayList <String> resultsString = new ArrayList<String>();
        for (Object result : results) {
            resultsString.add(result.toString());
        }
        return resultsString;
    }

    public List<String> getSt() {
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
