package si.fri.prpo.skupina19.sledilnik.zrna;

import si.fri.prpo.skupina19.entitete.Prostor;
import si.fri.prpo.skupina19.entitete.Zaposleni;
import si.fri.prpo.skupina19.sledilnik.dtos.ProstorDTO;
import si.fri.prpo.skupina19.sledilnik.dtos.ZaposleniDTO;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.UUID;
import java.util.logging.Logger;

@ApplicationScoped
public class UpravljanjeZaposlenihZrno {
    private Logger log = Logger.getLogger(UpravljanjeProstorovZrno.class.getName());
    private String idZ;

    @Inject
    private ZaposleniZrno zaposleniZrno;

    @PostConstruct
    private void initialization(){
        idZ = UUID.randomUUID().toString();
        log.info("Ustvarjeno UpravljanjeZaposlenihZrno z ID-jem: " + idZ);
    }

    @PreDestroy
    private void destruction(){
        log.info("Uničeno UpravljanjeZaposlenihZrno z ID-jem: " + idZ);
    }

    @PersistenceContext(unitName = "sledilnik-stevila-obiskovalcev-jpa")
    private EntityManager em;

    public Zaposleni createZaposleni (ZaposleniDTO zaposleniDTO) {
        Zaposleni zaposleni = zaposleniZrno.getZaposleni(zaposleniDTO.getZaposleniId());
        if (zaposleni != null) {
            Zaposleni noviZaposleni = new Zaposleni();
            noviZaposleni.setId(zaposleniDTO.getZaposleniId());
            noviZaposleni.setIme(zaposleniDTO.getIme());
            noviZaposleni.setPriimek(zaposleniDTO.getPriimek());
            noviZaposleni.setVrata(zaposleniDTO.getVrata());
            return zaposleniZrno.createZaposleni(noviZaposleni);
        } else {
            log.info("Ne morem ustvariti novega zaposlenega!");
            return null;
        }
    }

}
