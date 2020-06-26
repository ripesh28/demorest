package org.example.client;

import org.example.domain.Customer;
import org.example.utilityMethod.CustomerMessageBodyReader;
import org.example.utilityMethod.CustomerMessageBodyWriter;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;

public class RestAPIClient {
    public static void main(String[] args) {
        Customer customer=new Customer();
        customer.setFirstName("ABC");
        customer.setLastName("xyz");
        customer.setCity("noida");
        Client client= ClientBuilder
                            .newBuilder()
                            .property("connection.timeout",100)
                            .register(CustomerMessageBodyWriter.class)
                            .register(CustomerMessageBodyReader.class)
                            .build();

        /*Response response=client.target("http://localhost:8080/services/customer")
                .request()
                .accept(MediaType.APPLICATION_JSON)
                .acceptLanguage("en-us")
                .acceptEncoding("gzip")
                .post(Entity.json(customer));
        if (response.getStatus() != 201) throw new RuntimeException("Failed to create");
        System.out.println(response.readEntity(String.class));
        String location = response.getLocation().toString();
        System.out.println("Location: " + location);
        response.close();*/

        //System.out.println("** GET Created Customer **");

        Response response1=client.target("http://localhost:8080/services/myresource")
                                    .request()
                                    .accept(MediaType.APPLICATION_JSON)
                                    .acceptLanguage("es")
                                    .acceptEncoding("deflate")
                                    .get();
        System.out.println(response1.readEntity(Date.class));
        response1.close();
    }
}


/*String body="<customer>" +
                "<first-name>Laksh12</first-name>" +
                "<last-name>Yadav</last-name>" +
                "<street>JGP</street>" +
                "<city>JSP</city>" +
                "<state>UK</state>" +
                "<zip>12345</zip>" +
                "<country>IN</country>" +
                "</customer>";*/