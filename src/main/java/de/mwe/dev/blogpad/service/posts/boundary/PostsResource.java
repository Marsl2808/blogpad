package de.mwe.dev.blogpad.service.posts.boundary;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import de.mwe.dev.blogpad.service.posts.control.PostStore;
import de.mwe.dev.blogpad.service.posts.entity.Post;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("posts")
public class PostsResource {

    @Inject
    PostStore postStore;

    @Inject
    @ConfigProperty(name = "content-root")
    private String contentRoot;// = "D:/tmp/";

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void save(Post post){
        postStore.save(post, contentRoot + post.getTitle());
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Post findPost(@QueryParam("title") String title){
        return postStore.read(contentRoot + title);
    }
}
