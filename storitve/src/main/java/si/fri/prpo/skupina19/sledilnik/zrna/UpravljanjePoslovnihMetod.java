package si.fri.prpo.skupina19.sledilnik.zrna;

import si.fri.prpo.skupina19.entitete.Prostor;
import si.fri.prpo.skupina19.entitete.Vrata;
import si.fri.prpo.skupina19.sledilnik.dtos.ProstorDTO;

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

    public List<Vrata> spremeniSteviloOsebPoZaposlenim(){
        Random rand=new Random();

        List<Vrata>  spremenjenaVrata = new ArrayList<Vrata>();

        zaposleniZrno.getZaposleniCriteriaAPI().forEach((z) -> {
            if (z.getVrata()!=null) {
                Vrata vrataZaposlenega = z.getVrata();
                if (vrataZaposlenega.getProstor()!=null) {
                    Prostor p= z.getVrata().getProstor();
                    if(p.getTrenutnoOseb()!=null){
                        Integer g =p.getTrenutnoOseb();
                        if (g >0)
                            z.getVrata().setStIzstopov(z.getVrata().getStIzstopov()+rand.nextInt(g) + 1);
                        //  System.out.printf("g: %d\n", g);
                        z.getVrata().setStVstopov(z.getVrata().getStVstopov()+rand.nextInt(5) + 1);
                        spremenjenaVrata.add(vrataZaposlenega);

                    } else log.info("Ni prostora!");
                } else log.info("Ni zaposlenega!");
            } else log.info("Ni vrat!");

        });
        return spremenjenaVrata;
    }

    public boolean presezenaMeja(Prostor p){
        if(p.getTrenutnoOseb()!=null) {
            Integer g = p.getTrenutnoOseb();
            ProstorDTO pDTO = new ProstorDTO();
            pDTO.setProstorId(p.getId());
            pDTO.setTrenutnoOseb(p.getId());
            pDTO.setStVrat(p.getStVrat());
            pDTO.setKvadratura(p.getKvadratura());
            pDTO.setKvadratovPoOsebi(p.getKvadratPoOsebi());
            pDTO.setImeProstora(p.getImeProstora());
            if (g > getOmejitev(pDTO)) return true;
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

    public void updateSpremenjeneProstore(List<Vrata> spremenjena) {
        spremenjena.forEach((v) -> {
            Integer novo = 0;
            Prostor p = v.getProstor();
            novo = p.getTrenutnoOseb()+ v.getStVstopov() - v.getStIzstopov();
            if (novo<0) novo=0;

            if (presezenaMeja(p)) {
                log.info("V prostoru " + p.getId() + " je presezena meja");
            }
            else {
                p.setTrenutnoOseb(novo);
                prostorZrno.updateProstor(p.getId(), p);
            }
        });
    }
}
