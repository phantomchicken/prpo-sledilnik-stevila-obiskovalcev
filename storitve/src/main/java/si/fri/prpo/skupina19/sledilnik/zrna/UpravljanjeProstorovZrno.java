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
        if (prostorDTO.getProstorId()!=null){
            log.info("Prostor s tem id-jem ze obstaja");
            return null;
        }
        Prostor noviProstor = new Prostor();
        noviProstor.setImeProstora(prostorDTO.getImeProstora());
        noviProstor.setKvadratovPoOsebi(prostorDTO.getKvadratovPoOsebi());
        noviProstor.setKvadratura(prostorDTO.getKvadratura());
        noviProstor.setStVrat(prostorDTO.getStVrat());
        noviProstor.setTrenutnoOseb(prostorDTO.getTrenutnoOseb());
        return prostorZrno.createProstor(noviProstor);
    }

    public Integer getOmejitev (ProstorDTO prostorDTO){
        log.info(prostorDTO.toString());
        log.info(prostorDTO.getKvadratura().toString());
        log.info(prostorDTO.getProstorId().toString());

        if (prostorDTO.getProstorId()==null){
            log.info("Prostor s tem id-jem ne obstaja");
            return null;
        }
        Integer kv = prostorDTO.getKvadratura();
        Integer kvPoOsebi = prostorDTO.getKvadratovPoOsebi();

        System.out.println("kv je "+kv);
        System.out.println("kvPoOSebi je " + kvPoOsebi);
        if (kv == null || kvPoOsebi == null) return null;
        else return kv/kvPoOsebi;
    }
}
