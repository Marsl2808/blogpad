package de.mwe.dev.blogpad.service.posts.boundary;

import java.io.IOException;

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

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void save(Post post){
        String serializedPost = postStore.serialize(post);
        try {
            // TODO: add project root cfg property
            postStore.writeToFs("D:/tmp" + post.getTitle(), serializedPost);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
