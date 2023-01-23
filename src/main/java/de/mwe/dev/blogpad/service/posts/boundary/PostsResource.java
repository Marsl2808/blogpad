package de.mwe.dev.blogpad.service.posts.boundary;

import java.net.URI;

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
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

@Path("posts")
public class PostsResource {

    @Inject
    PostStore postStore;

    @PUT
    @Timed(name = "getPropertiesTime", description = "Time needed to get the response")
    @Counted(absolute = true, description = "Number of times the endpoint is requested")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response save(@Context UriInfo uriInfo, Post post){
        Post savedPost = postStore.save(post, post.getTitle());
        URI uri = uriInfo.getAbsolutePathBuilder().path(savedPost.getFullQualifiedFilename()).build();//URI.create(savedPost.getFullQualifiedFilename());
        return Response.created(uri).build();
    }

    @GET
    @Timed(name = "getPropertiesTime", description = "Time needed to get the response")
    @Counted(absolute = true, description = "Number of times the endpoint is requested")
    @Produces(MediaType.APPLICATION_JSON)
    public Post findPost(@QueryParam("title") String title){
        return postStore.read(title);
    }
}
