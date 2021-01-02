package si.fri.prpo.skupina19.sledilnik.zrna;

import si.fri.prpo.skupina19.entitete.*;
import si.fri.prpo.skupina19.sledilnik.anotacije.BeleziKlice;
import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import javax.annotation.*;
import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.ArrayList;
import java.util.logging.Logger;
import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.UUID;

@ApplicationScoped
public class ProstorZrno {
    private Logger log = Logger.getLogger(ProstorZrno.class.getName());
    private String idP;

    @PostConstruct
    private void initialization(){
        idP = UUID.randomUUID().toString();
        log.info("Ustvarjeno ProstorZrno z ID-jem: " + idP);
    }

    @PreDestroy
    private void destruction(){
        log.info("Uničeno ProstorZrno z ID-jem: " + idP);
    }

    @PersistenceContext(unitName = "sledilnik-stevila-obiskovalcev-jpa")
    private EntityManager em;

    //CRUD
    @BeleziKlice
    public Prostor getProstor (int prostorId) {
        Prostor prostor = em.find(Prostor.class, prostorId);
        return prostor;
    }

    @BeleziKlice
    @Transactional
    public Prostor createProstor (Prostor prostor) {
        if (prostor != null) {
            em.persist(prostor);
        }
        return prostor;
    }

    @BeleziKlice
    @Transactional
    public Integer deleteProstor (int prostorId) {
        Prostor prostor = getProstor(prostorId);
        if (prostor != null) {
            em.remove(prostor);
        }
        return prostorId;
    }

    @BeleziKlice
    @Transactional
    public Prostor updateProstor (int prostorId, Prostor noviP) {
        Prostor p = em.find(Prostor.class, prostorId);
        if (p!= null){
            // ali modularno? , Integer kvadratura, Integer stVrat, Integer trenutnoOseb, Integer kvadratovPoOsebi, String imeProstora
            p.setId(noviP.getId());
            p.setImeProstora(noviP.getImeProstora());
            p.setKvadratovPoOsebi(noviP.getKvadratovPoOsebi());
            p.setKvadratura(noviP.getKvadratura());
            p.setStVrat(noviP.getStVrat());
            p.setTrenutnoOseb(noviP.getTrenutnoOseb());
            p.setSeznamVrat(noviP.getSeznamVrat());
            em.merge(noviP);
        }
        return noviP;
    }


    @BeleziKlice
    public List<Prostor> getProstori() {

        Query query = em.createNamedQuery("Prostor.getAll");
        List <Object> results = query.getResultList();
        ArrayList <Prostor> resultsPr = new ArrayList<Prostor>();
        for (Object result : results) {
            resultsPr.add((Prostor) result);
        }
        return resultsPr;
    }

    @BeleziKlice
    public List<Prostor> getProstori(QueryParameters query) {
        List <Prostor> prostori = JPAUtils.queryEntities(em, Prostor.class, query);
        return prostori;
    }

    public Long getProstoriCount(QueryParameters query) {
        return JPAUtils.queryEntitiesCount(em, Prostor.class, query);
    }


    // CriteriaAPI
    @BeleziKlice
    public List<Prostor> getProstoriCriteriaAPI () {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Prostor> query = criteriaBuilder.createQuery(Prostor.class);
        Root<Prostor> from = query.from(Prostor.class);
        query.select(from);
        return em.createQuery(query).getResultList();
    }

    //vrnitev prostor z določenim imenom , če obstaja
    @BeleziKlice
    public Prostor getProstorZImenom (String ime) {
        Prostor prostor = null;
        try {
            prostor = (Prostor)em.createNamedQuery("Prostor.getProstorZImenom").setParameter("ime", ime).getSingleResult();
        }
        catch (NoResultException nre) { }
        //System.out.println(prostor);
        return prostor;
    }

}
