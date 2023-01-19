package de.mwe.dev.blogpad.service.posts.control;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    Post postRef;
    private String contentRoot = "D:/tmp/";

    @BeforeEach
    public void init() {
        this.cut = new PostStore();
        this.postRef = new Post("testPost", "testContent");
    }

    @Test
    @Order(1)
    public void serializePostTest() {
        this.cut.save(this.postRef, this.contentRoot +  this.postRef.getTitle());
    }

    @Test
    @Order(2)
    public void deserializePostTest() throws IOException{
        Post postAct = cut.read(this.contentRoot + postRef.getTitle());

        String postActString = this.cut.serialize(postAct);
        String postRefString = this.cut.serialize(this.postRef);
        System.out.println(postActString);
        assertEquals(postRefString, postActString);
    }
}
