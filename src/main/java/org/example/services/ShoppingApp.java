package org.example.services;

import org.example.utilityMethod.MyExceptionMapper;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/services")
public class ShoppingApp extends Application {

    private Set<Object> singletons = new HashSet<Object>();
    private Set<Class<?>> empty = new HashSet<Class<?>>();

    public ShoppingApp(){
        singletons.add(new CustomerResource());
        singletons.add(new MyResource());
        singletons.add(new MyExceptionMapper());
    }
    @Override
    public Set<Class<?>> getClasses() {
        return empty;
    }

    @Override
    public Set<Object> getSingletons() {

        return singletons;
    }
}
