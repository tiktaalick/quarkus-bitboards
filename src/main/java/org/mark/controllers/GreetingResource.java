package org.mark.controllers;

import io.quarkus.logging.Log;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.mark.bitboards.BitboardFactory;

@Path("/hello")
public class GreetingResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        Log.info("Standard chess");
        BitboardFactory.createInitialBitboardsWhitePlayer();
        return "Hello from Quarkus REST";
    }
}
