package si.fri.prpo.zvestoba.interceptorji;

import si.fri.prpo.zvestoba.anotacije.BeleziKlice;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@Interceptor
@BeleziKlice
public class BelezenjeKlicevInterceptor {

    @AroundInvoke
    public Object zabeleziKlic(InvocationContext context) throws Exception {

        Object[] parameters = context.getParameters();

        return context.proceed();
    }
}