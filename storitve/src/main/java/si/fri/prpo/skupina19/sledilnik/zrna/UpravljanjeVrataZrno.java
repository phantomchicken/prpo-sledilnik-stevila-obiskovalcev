package si.fri.prpo.skupina19.sledilnik.zrna;

import si.fri.prpo.skupina19.entitete.Prostor;
import si.fri.prpo.skupina19.entitete.Vrata;
import si.fri.prpo.skupina19.entitete.Zaposleni;
import si.fri.prpo.skupina19.sledilnik.dtos.VrataDTO;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@ApplicationScoped
public class UpravljanjeVrataZrno {
    private Logger log = Logger.getLogger(UpravljanjeVrataZrno.class.getName());
    private String idZ;

    @Inject
    private VrataZrno vrataZrno;
    @Inject
    private ProstorZrno prostorZrno;
    @Inject
    private ZaposleniZrno zaposleniZrno;

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

        if (vrataDTO.getVrataId() != null){
            log.info("Vrata s tem id-jem ze obstajajo");
            return null;
        }
        Vrata novaVrata = new Vrata();
        novaVrata.setId(vrataDTO.getVrataId());
        novaVrata.setStVstopov(vrataDTO.getStVstopov());
        novaVrata.setStIzstopov(vrataDTO.getStIzstopov());
        novaVrata.setProstor(vrataDTO.getProstor());
        novaVrata.setZaposleni(vrataDTO.getZaposleni());

        novaVrata = vrataZrno.createVrata(novaVrata);
        novaVrata.getZaposleni().setVrata(novaVrata);

        List<Vrata> seznamVrat = novaVrata.getProstor().getSeznamVrat();
        seznamVrat.add(novaVrata);
        novaVrata.getProstor().setSeznamVrat(seznamVrat);

        Prostor noviProstor = novaVrata.getProstor();
        prostorZrno.updateProstor(noviProstor.getId(),noviProstor);

        Zaposleni noviZaposleni = novaVrata.getZaposleni();
        zaposleniZrno.updateZaposleni(noviZaposleni.getId(),noviZaposleni);

        return novaVrata;
    }

    public Integer deleteVrata (VrataDTO vrataDTO){
        if (vrataDTO.getVrataId()==null) {
            log.info("Zaposleni s tem ID-jem ne obstaja");
            return null;
        }
        return vrataZrno.deleteVrata(vrataDTO.getVrataId());
    }

}
