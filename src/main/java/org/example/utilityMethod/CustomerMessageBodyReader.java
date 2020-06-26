package org.example.utilityMethod;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.domain.Customer;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

@Provider
@Consumes(MediaType.APPLICATION_JSON)
public class CustomerMessageBodyReader implements MessageBodyReader<Customer> {

    @Override
    public boolean isReadable(Class aClass, Type type, Annotation[] annotations, MediaType mediaType) {
        System.out.println("Inside Reader");
        return Customer.class.isAssignableFrom(aClass);
    }

    @Override
    public Customer readFrom(Class aClass, Type type, Annotation[] annotations, MediaType mediaType, MultivaluedMap multivaluedMap, InputStream inputStream) throws IOException, WebApplicationException {
        System.out.println("Inside Reader");
        ObjectMapper mapper = new ObjectMapper();
        Customer customer = mapper.readValue(inputStream, Customer.class);
        return customer;
    }
}
