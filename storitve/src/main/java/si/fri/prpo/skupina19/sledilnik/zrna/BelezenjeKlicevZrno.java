package si.fri.prpo.skupina19.sledilnik.zrna;

import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class BelezenjeKlicevZrno {
    private Logger log = Logger.getLogger(BelezenjeKlicevZrno.class.getName());

    public void povecajStevecKlicev(String metoda, Integer trStevec) {
        log.info("Stevec za metodo " + metoda +" je " + trStevec);
    }
}
