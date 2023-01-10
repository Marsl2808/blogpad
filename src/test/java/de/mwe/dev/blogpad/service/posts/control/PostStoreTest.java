package de.mwe.dev.blogpad.service.posts.control;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Filter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.mwe.dev.blogpad.service.posts.entity.Post;

public class PostStoreTest {

    PostStore cut;

    @BeforeEach
    public void init() {
        this.cut = new PostStore();
    }

    @Test
    public void serializePost() {

        // try {
        //     String postString = this.cut.serialize(new Post("hello", "world"));
        //     System.out.println(postString);
        //     System.out.println("Hello Test");
        //     assertNotNull(postString);
        // } catch (Exception e) {
        //     System.out.println(e);
        // }
    }

    @Test
    public void writeToFsTest() throws IOException{
        String path = "target/firstPost";
        String content = "hello, duke";
        cut.writeToFs(path, content);
        String contentAct = cut.read(path);
        assertEquals(content, contentAct);
    }
}
