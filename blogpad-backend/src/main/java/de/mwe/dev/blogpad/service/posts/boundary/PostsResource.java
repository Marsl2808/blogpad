package de.mwe.dev.blogpad.service.posts.boundary;

import java.net.URI;

import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mwe.dev.blogpad.service.posts.control.PostStore;
import de.mwe.dev.blogpad.service.posts.entity.Post;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
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

    private static final Logger LOG = LoggerFactory.getLogger(PostsResource.class);

    @Inject
    PostStore postStore;

    @POST
    @Timed(name = "getPropertiesTime", description = "Time needed to get the response")
    @Counted(absolute = true, description = "Number of times the endpoint is requested")
    @APIResponse( responseCode = "400", description = "Post with same title already exists. Use PUT for updates.")
    @APIResponse( responseCode = "201", description = "Post created.")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(@Context UriInfo uriInfo, Post post){
        Post savedPost = postStore.create(post, post.getTitle());
        URI uri = uriInfo.getAbsolutePathBuilder().path(savedPost.getFullQualifiedFilename()).build();
        LOG.info("Post " + savedPost.getTitle() + " created");
        return Response.created(uri).build();
    }

    @PUT
    @Timed(name = "getPropertiesTime", description = "Time needed to get the response")
    @Counted(absolute = true, description = "Number of times the endpoint is requested")
    @APIResponse( responseCode = "400", description = "Post not exists. Use POST to create.")
    @APIResponse( responseCode = "201", description = "Update successful.")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@Context UriInfo uriInfo, Post post){
        postStore.update(post, post.getTitle());
        LOG.info("Post " + post.getTitle() + " updated");
        return Response.ok().build();
    }

    @GET
    @Timed(name = "getPropertiesTime", description = "Time needed to get the response")
    @Counted(absolute = true, description = "Number of times the endpoint is requested")
    @Produces(MediaType.APPLICATION_JSON)
    public Post findPost(@QueryParam("title") String title){
        LOG.info("Post " + title + " requested");
        return postStore.read(title);
    }

    @GET
    @Path("test")
    public String myDebug(){
        return "Test successful";
    }

}
