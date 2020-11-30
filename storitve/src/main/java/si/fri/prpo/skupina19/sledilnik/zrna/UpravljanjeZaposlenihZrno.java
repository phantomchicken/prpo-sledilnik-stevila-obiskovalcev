package si.fri.prpo.skupina19.sledilnik.zrna;

import si.fri.prpo.skupina19.entitete.Prostor;
import si.fri.prpo.skupina19.entitete.Vrata;
import si.fri.prpo.skupina19.entitete.Zaposleni;
import si.fri.prpo.skupina19.sledilnik.anotacije.BeleziKlice;
import si.fri.prpo.skupina19.sledilnik.dtos.ProstorDTO;
import si.fri.prpo.skupina19.sledilnik.dtos.ZaposleniDTO;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.logging.Logger;

@ApplicationScoped
public class UpravljanjeZaposlenihZrno {
    private Logger log = Logger.getLogger(UpravljanjeZaposlenihZrno.class.getName());
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

    @BeleziKlice
    public Zaposleni createZaposleni (ZaposleniDTO zaposleniDTO) {
        if (zaposleniDTO.getZaposleniId()!=null) {
            log.info("Zaposleni s tem ID-jem ze obstaja");
            return null;
        }

        Zaposleni uporabljenVzdevek = zaposleniZrno.getZaposleniVzdevek(zaposleniDTO.getVzdevek());
        //ce obstaja ze zaposleni s tem vzdevkom ne naredi novega zaposlenega
        if (uporabljenVzdevek != null) {
            log.info("Zaposleni s tem vzdevkom že obstaja");
            return null;
        }

        Zaposleni noviZaposleni = new Zaposleni();
        noviZaposleni.setVzdevek(zaposleniDTO.getVzdevek());
        noviZaposleni.setIme(zaposleniDTO.getIme());
        noviZaposleni.setPriimek(zaposleniDTO.getPriimek());
        noviZaposleni.setVrata(zaposleniDTO.getVrata());

        Zaposleni  nz  = zaposleniZrno.createZaposleni(noviZaposleni);
        return nz;
    }

    @BeleziKlice
    public Integer deleteZaposleni (ZaposleniDTO zaposleniDTO){
        if (zaposleniDTO.getZaposleniId()==null) {
            log.info("Zaposleni s tem ID-jem ne obstaja");
            return null;
        }
        return zaposleniZrno.deleteZaposleni(zaposleniDTO.getZaposleniId());
    }

    @BeleziKlice
    public Prostor getProstorZaposlenega (ZaposleniDTO zaposleniDTO){
        if (zaposleniDTO.getZaposleniId()==null) {
            log.info("Zaposleni s tem ID-jem ne obstaja");
            return null;
        }
        log.info(zaposleniDTO.getIme());
        log.info(zaposleniDTO.getZaposleniId().toString());
        log.info(zaposleniDTO.getVrata().toString());
        Vrata vrataZaposlenega = zaposleniDTO.getVrata();
        if (vrataZaposlenega == null) return null;
        log.info(vrataZaposlenega.toString());
        return vrataZaposlenega.getProstor();
    }
}
