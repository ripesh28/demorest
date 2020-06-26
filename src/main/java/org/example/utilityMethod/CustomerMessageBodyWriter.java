package org.example.utilityMethod;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.domain.Customer;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

@Provider
@Produces(MediaType.APPLICATION_JSON)
public class CustomerMessageBodyWriter implements MessageBodyWriter<Customer> {
    @Override
    public boolean isWriteable(Class aClass, Type type, Annotation[] annotations, MediaType mediaType) {
        System.out.println("Inside Writer");
        return Customer.class.isAssignableFrom(aClass);
    }

    @Override
    public void writeTo(Customer customer, Class aClass, Type type, Annotation[] annotations, MediaType mediaType, MultivaluedMap multivaluedMap, OutputStream outputStream) throws IOException, WebApplicationException {
        System.out.println("Inside Writer");
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(outputStream, customer);
    }
}
