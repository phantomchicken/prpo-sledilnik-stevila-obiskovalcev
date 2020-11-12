package si.fri.prpo.skupina19.sledilnik.zrna;

import si.fri.prpo.skupina19.sledilnik.dtos.*;
import si.fri.prpo.skupina19.entitete.*;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.UUID;
import java.util.logging.Logger;

@ApplicationScoped
public class UpravljanjeProstorovZrno {

    private Logger log = Logger.getLogger(UpravljanjeProstorovZrno.class.getName());
    private String idP;

    @Inject
    private ProstorZrno prostorZrno;

    @PostConstruct
    private void initialization(){
        idP = UUID.randomUUID().toString();
        log.info("Ustvarjeno UpravljanjeProstorovZrno z ID-jem: " + idP);
    }

    @PreDestroy
    private void destruction(){
        log.info("Uničeno UpravljanjeProstorovZrno z ID-jem: " + idP);
    }

    @PersistenceContext(unitName = "sledilnik-stevila-obiskovalcev-jpa")
    private EntityManager em;

    public Prostor createProstor (ProstorDTO prostorDTO) {
        Prostor prostor = prostorZrno.getProstor(prostorDTO.getProstorId());
        if (prostor != null) {
            Prostor noviProstor = new Prostor();
            noviProstor.setImeProstora(prostorDTO.getImeProstora());
            noviProstor.setKvadratovPoOsebi(prostorDTO.getKvadratovPoOsebi());
            noviProstor.setKvadratura(prostorDTO.getKvadratovPoOsebi());
            noviProstor.setStVrat(prostorDTO.getStVrat());
            noviProstor.setTrenutnoOseb(prostorDTO.getTrenutnoOseb());
            return prostorZrno.createProstor(noviProstor);
        } else {
            log.info("Ne morem ustvariti novega prostora!");
            return null;
        }
    }
}
