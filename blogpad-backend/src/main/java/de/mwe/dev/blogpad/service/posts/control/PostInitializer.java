package de.mwe.dev.blogpad.service.posts.control;

import de.mwe.dev.blogpad.service.posts.entity.Post;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;

@ApplicationScoped
public class PostInitializer {

    static final String initialTitle = "initialTitle";

    @Inject
    PostStore store;

    public void installFirstPost(@Observes @Initialized(ApplicationScoped.class) Object doesntMatter){

        if(this.store.fileExists(initialTitle)){
            return;
        }
        Post post = this.createInitialPost();
        this.store.create(post, initialTitle);
        System.out.println("Default Post initialized on startup.");
    }

    private Post createInitialPost(){
        return new Post(initialTitle, "My Useless Test Content!!!");
    }
}
