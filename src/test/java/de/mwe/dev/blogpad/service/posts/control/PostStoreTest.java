package de.mwe.dev.blogpad.service.posts.control;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import de.mwe.dev.blogpad.service.posts.entity.Post;

@TestMethodOrder(OrderAnnotation.class)
public class PostStoreTest {

    PostStore cut;
    String path = "target/firstPost";

    @BeforeEach
    public void init() {
        this.cut = new PostStore();
    }

    @Test
    @Order(1)
    public void serializePostTest() {
        try {
            // serialize
            Post postRef = new Post("hello", "world");
            String postRefString = this.cut.serialize(postRef);
            System.out.println(postRefString);
            assertNotNull(postRefString);

            // write to fs
            cut.writeToFs(path, postRefString);

            Post postAct = cut.readFromFs(path);
            String postActString = this.cut.serialize(postAct);
            System.out.println(postActString);
            assertEquals(postRefString, postActString);

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Test
    @Order(2)
    public void deserializePostTest() throws IOException{
        // Post postFromFs = cut.readFromFs(path);
        // System.out.println(this.contentRef);
        // System.out.println(postFromFs.getContent());
        // assertEquals(this.contentRef, postFromFs.getContent());
    }
}
