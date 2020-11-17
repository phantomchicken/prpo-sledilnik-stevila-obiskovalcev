package si.fri.prpo.skupina19.sledilnik.zrna;

import si.fri.prpo.skupina19.entitete.*;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
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
    public Zaposleni getZaposleni (int zaposleniId) {
        Zaposleni zaposleni = em.find(Zaposleni.class, zaposleniId);
        return zaposleni;
    }

    @Transactional
    public Zaposleni createZaposleni (Zaposleni zaposleni) {
        if (zaposleni != null) {
            em.persist(zaposleni);
        }

        return zaposleni;
    }

    @Transactional
    public Integer deleteZaposleni (int zaposleniId) {
        Zaposleni zaposleni = getZaposleni(zaposleniId);
        if (zaposleni != null) {
            em.remove(zaposleni);
        }
        return zaposleniId;
    }

    //vrne vse zaposlene
    public List<String> getZaposleni() {
        TypedQuery<Object[]> query = em.createNamedQuery("Zaposleni.getAll", Object[].class);
        List<Object[]> results = query.getResultList();
        ArrayList<String> resultsString = new ArrayList<String>();
        for (Object result : results) {
            resultsString.add(result.toString());
        }
        return resultsString;
    }

    //vrne vse zaposlene z CriteriaAPI
    public List<Zaposleni> getZaposleniCriteriaAPI () {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Zaposleni> query = criteriaBuilder.createQuery(Zaposleni.class);
        Root<Zaposleni> from = query.from(Zaposleni.class);
        query.select(from);
        return em.createQuery(query).getResultList();
    }

    //vrne prostor v katerem zaposleni dela
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
    public Zaposleni getZaposleniVzdevek (String vzdevek) {
        Zaposleni zaposleni = null;
        try {
            zaposleni = (Zaposleni)em.createNamedQuery("Zaposleni.getZaposleniVzdevek").setParameter("vzdevek", vzdevek).getSingleResult();
        }
        catch (NoResultException nre) { }

        return zaposleni;
    }


}