package de.mwe.dev.blogpad.service.posts.boundary;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;

import de.mwe.dev.blogpad.service.posts.control.PostStore;
import de.mwe.dev.blogpad.service.posts.entity.Post;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

@Path("posts")
public class PostsResource {

    @Inject
    @ConfigProperty(name = "content-root")
    private String contentRoot;

    @Inject
    PostStore postStore;

    @PUT
    @Timed(name = "getPropertiesTime", description = "Time needed to get the response")
    @Counted(absolute = true, description = "Number of times the endpoint is requested")
    @Consumes(MediaType.APPLICATION_JSON)
    public void save(Post post){
        postStore.save(post, post.getTitle(), contentRoot);
    }

    @GET
    @Timed(name = "getPropertiesTime", description = "Time needed to get the response")
    @Counted(absolute = true, description = "Number of times the endpoint is requested")
    @Produces(MediaType.APPLICATION_JSON)
    public Post findPost(@QueryParam("title") String title){
        return postStore.read(contentRoot + title);
    }
}
