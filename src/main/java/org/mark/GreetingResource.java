package org.mark;

import io.quarkus.logging.Log;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/hello")
public class GreetingResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        Log.info("Standard chess");
        BoardGeneration.initiateStandardChess();
        Log.info("Chess 960");
        BoardGeneration.initiateChess960();
        return "Hello from Quarkus REST";
    }
}
