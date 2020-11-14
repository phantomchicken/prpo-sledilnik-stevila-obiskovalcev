package si.fri.prpo.skupina19.sledilnik.zrna;

import si.fri.prpo.skupina19.entitete.Prostor;
import si.fri.prpo.skupina19.entitete.Vrata;
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

    public Zaposleni createZaposleni (ZaposleniDTO zaposleniDTO) {
        if (zaposleniDTO.getZaposleniId()!=null) {
            log.info("Zaposleni s tem ID-jem ze obstaja");
            return null;
        }
        Zaposleni noviZaposleni = new Zaposleni();
        noviZaposleni.setIme(zaposleniDTO.getIme());
        noviZaposleni.setPriimek(zaposleniDTO.getPriimek());
        noviZaposleni.setVrata(zaposleniDTO.getVrata());
        return zaposleniZrno.createZaposleni(noviZaposleni);
    }

    /*public Zaposleni updateZaposleni (ZaposleniDTO zaposleniDTO) {
        if (zaposleniDTO.getZaposleniId()==null) {
            log.info("Zaposleni s tem ID-jem ne obstaja");
            return null;
        }
        Zaposleni noviZaposleni = new Zaposleni();
        noviZaposleni.setIme(zaposleniDTO.getIme());
        noviZaposleni.setPriimek(zaposleniDTO.getPriimek());
        noviZaposleni.setVrata(zaposleniDTO.getVrata());
        return zaposleniZrno.updateZaposleni(noviZaposleni);
    }*/

    public Integer deleteZaposleni (ZaposleniDTO zaposleniDTO){
        if (zaposleniDTO.getZaposleniId()==null) {
            log.info("Zaposleni s tem ID-jem ne obstaja");
            return null;
        }
        return zaposleniZrno.deleteZaposleni(zaposleniDTO.getZaposleniId());
    }

    public Integer povecajStevilo (ZaposleniDTO zaposleniDTO){
        if (zaposleniDTO.getZaposleniId()==null) {
            log.info("Zaposleni s tem ID-jem ne obstaja");
            return null;
        }
        Vrata vrataZaposlenega = zaposleniDTO.getVrata();
        if (vrataZaposlenega == null) return null;
        Prostor prostorVrat = vrataZaposlenega.getProstor();
        if (prostorVrat == null) return null;
        Integer trenutno = prostorVrat.getTrenutnoOseb();
        Integer novo = trenutno++;
        prostorVrat.setTrenutnoOseb(novo);
        return novo;
    }

    public Prostor getProstorZaposlenega (ZaposleniDTO zaposleniDTO){
        if (zaposleniDTO.getZaposleniId()==null) {
            log.info("Zaposleni s tem ID-jem ne obstaja");
            return null;
        }
        Vrata vrataZaposlenega = zaposleniDTO.getVrata();
        if (vrataZaposlenega == null) return null;
        return vrataZaposlenega.getProstor();
    }
}
