package org.example.services;

import org.example.domain.Customer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.annotation.PostConstruct;
import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Path("/customer")
@Singleton
public class CustomerResource {
    private Map<Integer, Customer> customerDB;
    private AtomicInteger idCounter ;

    public CustomerResource() {
        customerDB = new ConcurrentHashMap<Integer, Customer>();
        idCounter = new AtomicInteger();
    }

    /*@POST
    @Consumes("application/xml")
    public Response createCustomer(InputStream inputStream){
        Customer customer=readCustomer(inputStream);
        customer.setId(idCounter.getAndIncrement());
        customerDB.put(customer.getId(),customer);
        System.out.println("Created Customer :"+customer.getId());
        return Response.created(URI.create("/services/customer/"
                + customer.getId())).build();
    }

    @GET
    @Path("{id}")
    @Produces("application/xml")
    public StreamingOutput getCustomer(@PathParam("id") int id){
        final Customer customer=customerDB.get(id);
        if (customer == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return new StreamingOutput() {
            @Override
            public void write(OutputStream outputStream) throws IOException, WebApplicationException {
                writeCustomer(outputStream,customer);
            }
        };
    }*/


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createCustomer(Customer customer){
        System.out.println("Inside createMethod");
        customer.setId(idCounter.getAndIncrement());
        customerDB.put(customer.getId(),customer);
        System.out.println("Created Customer :"+customer.getId());

        //Create custom Response
        String message="Created Customer :"+customer.getId();
        String path="/services/customer/"+customer.getId();
        URI uri=URI.create(path);
        Response.ResponseBuilder responseBuilder=Response.ok(message,MediaType.TEXT_PLAIN);
        responseBuilder.language("en-us")
                .encoding("gzip")
                .status(201,"Created")
                .location(uri);
        return responseBuilder.build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Customer getCustomer(@PathParam("id") int id){
        final Customer customer=customerDB.get(id);
        if (customer == null) {
            throw new WebApplicationException();
        }
        Response.ResponseBuilder builder=Response.ok(customer,MediaType.APPLICATION_JSON);
        builder.build();
        return customer;
    }


    //Utility Methods
    private void writeCustomer(OutputStream outputStream, Customer customer) throws IOException{

        PrintStream writer = new PrintStream(outputStream);
        writer.println("<customer id=\"" + customer.getId() + "\">");
        writer.println("   <first-name>" + customer.getFirstName()
                + "</first-name>");
        writer.println("   <last-name>" + customer.getLastName()
                + "</last-name>");
        writer.println("   <street>" + customer.getStreet() + "</street>");
        writer.println("   <city>" + customer.getCity() + "</city>");
        writer.println("   <state>" + customer.getState() + "</state>");
        writer.println("   <zip>" + customer.getZip() + "</zip>");
        writer.println("   <country>" + customer.getCountry() + "</country>");
        writer.println("</customer>");
    }

    private Customer readCustomer(InputStream inputStream) {
        try {
            DocumentBuilder builder =
                    DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(inputStream);
            Element root = doc.getDocumentElement();

            Customer cust = new Customer();
            if (root.getAttribute("id") != null
                    && !root.getAttribute("id").trim().equals("")) {
                cust.setId(Integer.valueOf(root.getAttribute("id")));
            }
            NodeList nodes = root.getChildNodes();
            for (int i = 0; i < nodes.getLength(); i++) {
                Element element = (Element) nodes.item(i);
                if (element.getTagName().equals("first-name")) {
                    cust.setFirstName(element.getTextContent());
                }
                else if (element.getTagName().equals("last-name")) {
                    cust.setLastName(element.getTextContent());
                }
                else if (element.getTagName().equals("street")) {
                    cust.setStreet(element.getTextContent());
                }
                else if (element.getTagName().equals("city")) {
                    cust.setCity(element.getTextContent());
                }
                else if (element.getTagName().equals("state")) {
                    cust.setState(element.getTextContent());
                }
                else if (element.getTagName().equals("zip")) {
                    cust.setZip(element.getTextContent());
                }
                else if (element.getTagName().equals("country")) {
                    cust.setCountry(element.getTextContent());
                }
            }
            return cust;
        }
        catch (Exception e) {
            throw new WebApplicationException(e,
                    Response.Status.BAD_REQUEST);
        }
    }
}
