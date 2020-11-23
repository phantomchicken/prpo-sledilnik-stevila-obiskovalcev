package si.fri.prpo.skupina19.sledilnik.zrna;

import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class BelezenjeKlicevZrno {
    private Logger log = Logger.getLogger(BelezenjeKlicevZrno.class.getName());
    private static Integer stKlicev = 0;

    public void povecajStevecKlicev() {
        stKlicev ++;
        log.info("Stevilo klicev je " + stKlicev);
    }
}
