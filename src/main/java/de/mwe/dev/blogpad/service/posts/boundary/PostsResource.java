package de.mwe.dev.blogpad.service.posts.boundary;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import de.mwe.dev.blogpad.service.posts.control.PostStore;
import de.mwe.dev.blogpad.service.posts.entity.Post;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;

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
}
