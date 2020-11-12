package si.fri.prpo.skupina19.sledilnik.zrna;

import si.fri.prpo.skupina19.entitete.Prostor;
import si.fri.prpo.skupina19.entitete.Zaposleni;
import si.fri.prpo.skupina19.entitete.Vrata;
import si.fri.prpo.skupina19.sledilnik.dtos.ProstorDTO;
import si.fri.prpo.skupina19.sledilnik.dtos.ZaposleniDTO;
import si.fri.prpo.skupina19.sledilnik.dtos.VrataDTO;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.UUID;
import java.util.logging.Logger;

@ApplicationScoped
public class UpravljanjeVrataZrno {
    private Logger log = Logger.getLogger(UpravljanjeVrataZrno.class.getName());
    private String idZ;

    @Inject
    private VrataZrno varataZrno;

    @PostConstruct
    private void initialization(){
        idZ = UUID.randomUUID().toString();
        log.info("Ustvarjeno UpravljanjeVrataZrno z ID-jem: " + idZ);
    }

    @PreDestroy
    private void destruction(){
        log.info("Uničeno UpravljanjeVrataZrno z ID-jem: " + idZ);
    }

    @PersistenceContext(unitName = "sledilnik-stevila-obiskovalcev-jpa")
    private EntityManager em;

    public Vrata createVrata (VrataDTO vrataDTO) {
        Vrata vrata = varataZrno.getVrata(vrataDTO.getVrataId());

        if (vrata != null) {
            log.info("Vrata s tem id-jem ze obstajajo");
            return null;
        }

        Vrata novaVrata = new Vrata();
        novaVrata.setId(vrataDTO.getVrataId());
        novaVrata.setStVstopov(vrataDTO.getStVstopov());
        novaVrata.setStIzstopov(vrataDTO.getStIzstopov());
        novaVrata.setProstor(vrataDTO.getProstor());
        novaVrata.setZaposleni(vrataDTO.getZaposleni());
        return varataZrno.createVrata(novaVrata);
    }

}
