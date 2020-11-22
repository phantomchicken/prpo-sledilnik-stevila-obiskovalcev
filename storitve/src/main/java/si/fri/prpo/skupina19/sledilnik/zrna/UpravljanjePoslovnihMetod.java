package si.fri.prpo.skupina19.sledilnik.zrna;

import si.fri.prpo.skupina19.entitete.Prostor;
import si.fri.prpo.skupina19.entitete.Vrata;
import si.fri.prpo.skupina19.entitete.Zaposleni;
import si.fri.prpo.skupina19.sledilnik.dtos.ProstorDTO;
import si.fri.prpo.skupina19.sledilnik.dtos.VrataDTO;
import si.fri.prpo.skupina19.sledilnik.dtos.ZaposleniDTO;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Random;
import java.util.UUID;
import java.util.logging.Logger;

@ApplicationScoped
public class UpravljanjePoslovnihMetod {
    private Logger log = Logger.getLogger(UpravljanjePoslovnihMetod.class.getName());
    private String id;

    @Inject
    private ZaposleniZrno zaposleniZrno;
    @Inject
    private ProstorZrno prostorZrno;
    @Inject
    private VrataZrno vrataZrno;

    @PostConstruct
    private void initialization(){
        id = UUID.randomUUID().toString();
        log.info("Ustvarjeno UpravljanjePoslovnihMetod z ID-jem: " + id);
    }

    @PreDestroy
    private void destruction(){
        log.info("Uničeno UpravljanjePoslovnihMetod z ID-jem: " + id);
    }

    @PersistenceContext(unitName = "sledilnik-stevila-obiskovalcev-jpa")
    private EntityManager em;

    public void randomiziraj(){
        Random rand=new Random();
        zaposleniZrno.getZaposleniCriteriaAPI().forEach((z) -> {
            Integer vstopov = rand.nextInt((2 - 1) + 1) + 1;
            Integer izstopov = rand.nextInt((1 - 1) + 1) + 1;
            if (!spremeniSteviloOsebPoZaposlenim(getZaposleniDTOFromId(z.getId()),vstopov,izstopov))
                log.info("Na vratih " + z.getVrata().getId() + " stanje nespremenjeno");
        });
    }

    public boolean spremeniSteviloOsebPoZaposlenim(ZaposleniDTO zaposleniDTO, Integer vstopov, Integer izstopov){
        if (zaposleniDTO.getVrata()!=null) {
            Vrata v = zaposleniDTO.getVrata();
            if (v.getProstor()!=null) {
                Prostor p= zaposleniDTO.getVrata().getProstor();
                if(p.getTrenutnoOseb()!=null){
                    v.setStIzstopov(v.getStIzstopov()+izstopov);
                    v.setStVstopov(v.getStVstopov()+vstopov);
                    vrataZrno.updateVrata(v.getId(),v);
                    p.setTrenutnoOseb(p.getTrenutnoOseb()+vstopov-izstopov);
                    if (presezenaMeja(getProstorDTOFromId(p.getId())))
                        log.info("V prostoru " + p.getId() + " je presezena meja.");
                        prostorZrno.updateProstor(p.getId(), p);
                    return true;
                } else log.info("Ni prostora!");
            } else log.info("Ni zaposlenega!");
        } else log.info("Ni vrat!");
        return false;
    }

    public boolean presezenaMeja(ProstorDTO prostorDTO){
        if(prostorDTO.getTrenutnoOseb()!=null) {
            Integer g = prostorDTO.getTrenutnoOseb();
            if (g > getOmejitev(prostorDTO)) return true;
        }
        return false;
    }

    public Integer getOmejitev (ProstorDTO prostorDTO){
        if (prostorDTO.getProstorId()==null){
            log.info("Prostor s tem id-jem ne obstaja");
            return null;
        }
        Integer kv = prostorDTO.getKvadratura();
        Integer kvPoOsebi = prostorDTO.getKvadratovPoOsebi();
        if (kv == null || kvPoOsebi == null) return null;
        else return kv/kvPoOsebi;
    }

    public ProstorDTO getProstorDTOFromId(Integer id){
        Prostor p = prostorZrno.getProstor(id);
        if (p!=null){
            ProstorDTO prostorDTO = new ProstorDTO();
            prostorDTO.setImeProstora(p.getImeProstora());
            prostorDTO.setKvadratovPoOsebi(p.getKvadratPoOsebi());
            prostorDTO.setKvadratura(p.getKvadratura());
            prostorDTO.setStVrat(p.getStVrat());
            prostorDTO.setTrenutnoOseb(p.getTrenutnoOseb());
            prostorDTO.setProstorId(p.getId());
            prostorDTO.setSeznamVrat(p.getSeznamVrat());
            return prostorDTO;
        } else return null;
    }

    public ZaposleniDTO getZaposleniDTOFromId(Integer id){
        Zaposleni z = zaposleniZrno.getZaposleni(id);
        if (z!=null){
            ZaposleniDTO zaposleniDTO = new ZaposleniDTO();
            zaposleniDTO.setId(z.getId());
            zaposleniDTO.setIme(z.getIme());
            zaposleniDTO.setPriimek(z.getPriimek());
            zaposleniDTO.setVzdevek(z.getVzdevek());
            zaposleniDTO.setVrata(z.getVrata());
            return zaposleniDTO;
        } else return null;
    }

    public VrataDTO getVrataDTOFromId(Integer id){
        Vrata v = vrataZrno.getVrata(id);
        if (v!=null){
            VrataDTO vrataDTO = new VrataDTO();
            vrataDTO.setVrataId(v.getId());
            vrataDTO.setStVstopov(v.getStVstopov());
            vrataDTO.setStIzstopov(v.getStIzstopov());
            vrataDTO.setZaposleni(v.getZaposleni());
            vrataDTO.setProstor(v.getProstor());
            return vrataDTO;
        } else return null;
    }
}
