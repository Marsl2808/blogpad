package de.mwe.dev.blogpad.service;

import java.io.IOException;

import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;

import de.mwe.dev.blogpad.service.posts.control.PostStore;
import de.mwe.dev.blogpad.service.posts.entity.Post;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@RequestScoped
@Path("/resource")
public class SystemResource {

    PostStore postStore = new PostStore();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Timed(name = "getPropertiesTime",
           description = "Time needed to get the response")
    @Counted(absolute = true, description
             = "Number of times the endpoint is requested")
    public Response getProperties() {
        String jsonMsg = postStore.serialize(new Post("hello", "duke"));
        
        // String filename="firstPost";
        // String absPath = Paths.get(filename).toAbsolutePath().toString();
        // System.out.println(absPath);
        try {
            postStore.writeToFs("D:/DEV/java/openliberty/blogpad/target/firstPost", jsonMsg);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Response.ok(jsonMsg).build();
    }
}
