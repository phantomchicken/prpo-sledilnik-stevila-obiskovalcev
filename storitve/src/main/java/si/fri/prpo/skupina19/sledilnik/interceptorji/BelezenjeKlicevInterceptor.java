package si.fri.prpo.skupina19.sledilnik.interceptorji;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import si.fri.prpo.skupina19.sledilnik.anotacije.BeleziKlice;
import si.fri.prpo.skupina19.sledilnik.zrna.BelezenjeKlicevZrno;

@Interceptor
@BeleziKlice
public class BelezenjeKlicevInterceptor {
    @Inject
    private BelezenjeKlicevZrno belezenjeKlicevZrno;

    @AroundInvoke
    public Object intercept(InvocationContext context) throws Exception {
        belezenjeKlicevZrno.povecajStevecKlicev();
        return context.proceed();
    }
}
