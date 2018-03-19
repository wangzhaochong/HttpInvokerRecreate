package httpClient.component;

import org.springframework.beans.factory.FactoryBean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * Created by Hayden on 2018/3/18.
 */
public class httpInvokerFactory implements FactoryBean {

    private Class type;


    public Object getObject() throws Exception {
        InvocationHandler handler = new httpApiInvocationHandler(new HttpApiClient());
        return  Proxy.newProxyInstance(handler.getClass().getClassLoader(),
                new Class[]{type},
                handler);
    }

    public Class getObjectType() {
        return type;
    }

    public boolean isSingleton() {
        return false;
    }

    public Class getType() {
        return type;
    }

    public void setType(Class type) {
        this.type = type;
    }
}
