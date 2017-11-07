package si.fri.prpo.zvestoba.interceptorji;

import si.fri.prpo.zvestoba.anotacije.BeleziKlice;
import si.fri.prpo.zvestoba.zrna.BelezenjeKlicevZrno;
import si.fri.prpo.zvestoba.zrna.UporabnikZrno;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.util.logging.Logger;

@Interceptor
@BeleziKlice
public class BelezenjeKlicevInterceptor {

    private Logger log = Logger.getLogger(UporabnikZrno.class.getName());
    private BelezenjeKlicevZrno belezenje;

    @AroundInvoke
    public Object zabeleziKlic(InvocationContext context) throws Exception {

        if(belezenje == null)
            belezenje = new BelezenjeKlicevZrno();
        belezenje.increaseCounter();

        return context.proceed();
    }
}