package org.example.services;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Path("myresource")
@Singleton
public class MyResource {

    @GET
    @Path("/cust")
    @Produces(MediaType.APPLICATION_JSON)
    public Date getFile(){
        return Calendar.getInstance().getTime();
    }

    @GET
    public Response getData(@Context Request request){
        Variant.VariantListBuilder vb = Variant.VariantListBuilder.newInstance();
        vb.mediaTypes(MediaType.APPLICATION_XML_TYPE,
                MediaType.APPLICATION_JSON_TYPE)
                .languages(new Locale("en"), new Locale("es"))
                .encodings("deflate", "gzip").add();

        List<Variant> variants = vb.build();
        // Pick the variant
        Variant v = request.selectVariant(variants);

        Response.ResponseBuilder builder = Response.ok(Calendar.getInstance().getTime());
        builder.type(v.getMediaType())
                .language(v.getLanguage())
                .header("Content-Encoding", v.getEncoding());

        return builder.build();
    }

}
