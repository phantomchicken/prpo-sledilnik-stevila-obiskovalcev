package si.fri.prpo.skupina19.sledilnik.zrna;

import si.fri.prpo.skupina19.entitete.*;
import si.fri.prpo.skupina19.sledilnik.anotacije.BeleziKlice;
import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.*;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;
import java.util.logging.Logger;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;

@ApplicationScoped
public class ZaposleniZrno {
    private Logger log = Logger.getLogger(ZaposleniZrno.class.getName());
    private String idZ;

    @PostConstruct
    private void initialization(){
        idZ = UUID.randomUUID().toString();
        log.info("Ustvarjeno ZaposleniZrno z ID-jem: " + idZ);
    }

    @PreDestroy
    private void destruction(){
        log.info("Uničeno ZaposleniZrno z ID-jem: " + idZ);
    }

    @PersistenceContext(unitName = "sledilnik-stevila-obiskovalcev-jpa")
    private EntityManager em;

    //CRUD
    @BeleziKlice
    public Zaposleni getZaposleni (int zaposleniId) {
        Zaposleni zaposleni = em.find(Zaposleni.class, zaposleniId);
        return zaposleni;
    }

    @BeleziKlice
    @Transactional
    public Zaposleni createZaposleni (Zaposleni zaposleni) {
        //em.find(Zaposleni.class,zaposleni.getId());
        if (zaposleni != null) {
            em.persist(zaposleni);
        }

        return zaposleni;
    }

    @BeleziKlice
    @Transactional
    public Integer deleteZaposleni (int zaposleniId) {
        Zaposleni zaposleni = getZaposleni(zaposleniId);
        if (zaposleni != null) {
            em.remove(zaposleni);
        }
        return zaposleniId;
    }

    @BeleziKlice
    @Transactional
    public Zaposleni updateZaposleni (int zaposleniId, Zaposleni noviZ) {
        Zaposleni z = em.find(Zaposleni.class, zaposleniId);
        if (z!= null){
            z.setId(noviZ.getId());
            z.setVrata(noviZ.getVrata());
            z.setIme(noviZ.getIme());
            z.setPriimek(noviZ.getPriimek());
            z.setVzdevek(noviZ.getVzdevek());
            em.merge(noviZ);
        }
        return noviZ;
    }


    //vrne vse zaposlene
    @BeleziKlice
    public List<Zaposleni> getZaposleni() {
        TypedQuery<Object[]> query = em.createNamedQuery("Zaposleni.getAll", Object[].class);
        List<Object[]> results = query.getResultList();
        ArrayList<Zaposleni> resultsZ = new ArrayList<Zaposleni>();
        for (Object result : results) {
            resultsZ.add((Zaposleni)result);
        }
        return resultsZ;
    }
    @BeleziKlice
    public List<Zaposleni> getZaposleni(QueryParameters query) {
        List <Zaposleni> zaposleni = JPAUtils.queryEntities(em, Zaposleni.class, query);
        return zaposleni;
    }


    public Long getZaposleniCount(QueryParameters query) {
        return JPAUtils.queryEntitiesCount(em, Zaposleni.class, query);
    }

    //vrne vse zaposlene z CriteriaAPI
    @BeleziKlice
    public List<Zaposleni> getZaposleniCriteriaAPI () {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Zaposleni> query = criteriaBuilder.createQuery(Zaposleni.class);
        Root<Zaposleni> from = query.from(Zaposleni.class);
        query.select(from);
        return em.createQuery(query).getResultList();
    }

    //vrne prostor v katerem zaposleni dela
    @BeleziKlice
    public List<String> getDelovnoMesto() {

        // implementacija
        TypedQuery<Object> query = em.createNamedQuery("Zaposleni.getDelovnoMesto", Object.class);
        List<Object> results = query.getResultList();
        List<String> delovnoMesto = new ArrayList<String>();
        for (Object result : results) {
            delovnoMesto.add((String) result);
        }

        return delovnoMesto;
    }

    //vrnitev uporabnika z določenim vzdevekom , če obstaja
    @BeleziKlice
    public Zaposleni getZaposleniVzdevek (String vzdevek) {
        Zaposleni zaposleni = null;
        try {
            zaposleni = (Zaposleni)em.createNamedQuery("Zaposleni.getZaposleniVzdevek").setParameter("vzdevek", vzdevek).getSingleResult();
        }
        catch (NoResultException nre) { }

        return zaposleni;
    }


}