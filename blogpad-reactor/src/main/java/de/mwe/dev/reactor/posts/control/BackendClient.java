package de.mwe.dev.reactor.posts.control;


import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("posts")
@RegisterRestClient(configKey = "content")
public interface BackendClient {

    @GET
    @Path("{title}")
    @Produces(MediaType.APPLICATION_JSON)
    Response findPost(@PathParam("title") String title);
}
