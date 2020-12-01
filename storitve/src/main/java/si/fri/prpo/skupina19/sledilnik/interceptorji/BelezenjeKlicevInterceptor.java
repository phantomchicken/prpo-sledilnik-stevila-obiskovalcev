package si.fri.prpo.skupina19.sledilnik.interceptorji;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import si.fri.prpo.skupina19.sledilnik.anotacije.BeleziKlice;
import si.fri.prpo.skupina19.sledilnik.zrna.BelezenjeKlicevZrno;

import java.util.HashMap;
import java.util.Map;

@Interceptor
@BeleziKlice
public class BelezenjeKlicevInterceptor {
    @Inject
    private BelezenjeKlicevZrno belezenjeKlicevZrno;
    private Map<String, Integer> stevci = new HashMap<>();

    @AroundInvoke
    public Object intercept(InvocationContext context) throws Exception {
        String metoda = context.getMethod().getName();
        if (stevci.containsKey(metoda)){
            Integer stevecMetode = stevci.get(context.getMethod().getName());
            stevecMetode++;
            stevci.put(metoda, stevecMetode);
            belezenjeKlicevZrno.povecajStevecKlicev(metoda, stevecMetode);
        } else {
            stevci.put(metoda,(Integer) 1);
            belezenjeKlicevZrno.povecajStevecKlicev(metoda, (Integer) 1);
        }
        return context.proceed();
    }
}
