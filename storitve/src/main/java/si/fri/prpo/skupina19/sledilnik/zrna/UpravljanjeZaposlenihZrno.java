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

   /* public Zaposleni updateZaposleni (ZaposleniDTO zaposleniDTO) {
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

    public List<Vrata> spremeniSteviloOsebPoZaposlenim(){
        Random rand=new Random();

        List<Vrata>  spremenjenaVrata = new ArrayList<Vrata>();

        zaposleniZrno.getZaposleniCriteriaAPI().forEach((z) -> {
            Vrata vrataZaposlenega = z.getVrata();
            Prostor p= z.getVrata().getProstor();
            Integer g =p.getTrenutnoOseb();
            //ne moze vise da izadje nego sto ih je unutra
            if (g >0)
                z.getVrata().setStIzstopov(z.getVrata().getStIzstopov()+rand.nextInt(g) + 1);
            //  System.out.printf("g: %d\n", g);

            z.getVrata().setStVstopov(z.getVrata().getStVstopov()+rand.nextInt(5) + 1);
            spremenjenaVrata.add(vrataZaposlenega);
        });
        return spremenjenaVrata;
    }

    public Integer povecajStevilo (ZaposleniDTO zaposleniDTO){

        if (zaposleniDTO.getZaposleniId() == null) {
            log.info("Zaposleni s tem ID-jem ne obstaja");
            return -1;
        }
        System.out.printf("zaps: %d\n", zaposleniDTO.getZaposleniId());
        Vrata vrataZaposlenega = zaposleniDTO.getVrata();
        System.out.printf("vrata: %d\n", zaposleniDTO.getVrata().getId());
        //System.out.printf("vrataZaposlenega: %d\n", vrataZaposlenega.getId());
        //if (vrataZaposlenega == null)  {
        //    log.info("vrataZaposlenega s tem ID-jem ne obstaja");
        //    return -1;
        //}
        Prostor prostorVrat = vrataZaposlenega.getProstor();
        if (prostorVrat == null)  {
            log.info("prostorVrat s tem ID-jem ne obstaja");
            return -1;
        }

        Integer trenutno = prostorVrat.getTrenutnoOseb();
        Integer novo = trenutno + 1;
        System.out.println(novo);
        prostorVrat.setTrenutnoOseb(novo);
        return novo;
    }

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
