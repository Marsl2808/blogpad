package de.mwe.dev.reactor.posts.boundary;


import org.eclipse.microprofile.rest.client.inject.RestClient;

import de.mwe.dev.reactor.posts.control.BackendClient;
import jakarta.inject.Inject;
import jakarta.json.JsonObject;
import jakarta.websocket.server.PathParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("posts")
public class PostsResource {
    
    @Inject
    @RestClient
    BackendClient client;

    @GET
    @Path("{title}")
    @Produces(MediaType.APPLICATION_JSON)
    public String findPost(@PathParam("title") String title){
        Response response =  this.client.findPost(title);
        return "<h1>hello</h1>" + title + " " + response.readEntity(JsonObject.class);
    }
}
