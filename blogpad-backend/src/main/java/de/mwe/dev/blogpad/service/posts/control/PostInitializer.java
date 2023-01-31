package de.mwe.dev.blogpad.service.posts.control;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mwe.dev.blogpad.service.posts.entity.Post;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;

@ApplicationScoped
public class PostInitializer {

    static final String initialTitle = "initialTitle";

    private static final Logger LOG = LoggerFactory.getLogger(PostInitializer.class);

    @Inject
    PostStore store;

    public void installFirstPost(@Observes @Initialized(ApplicationScoped.class) Object doesntMatter){

        if(this.store.fileExists(initialTitle)){
            return;
        }
        Post post = this.createInitialPost();
        this.store.create(post, initialTitle);
        LOG.info("Default Post initialized on startup.");
    }

    private Post createInitialPost(){
        return new Post(initialTitle, "My Useless Test Content!!!");
    }
}
