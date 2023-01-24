package de.mwe.dev.blogpad.service.posts.control;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import de.mwe.dev.blogpad.service.posts.entity.Post;
import jakarta.inject.Inject;


@TestMethodOrder(OrderAnnotation.class)
public class PostStoreTest {

    PostStore cut;
    Post postRef;
    // TODO: write to target folder
    String contentRoot = "D:/tmp/";

    @BeforeEach
    public void init() {
        cut = new PostStore();
        postRef = new Post("testPost", "testContent");
        initFields();
    }

    private void cleanDirectory() throws IOException{
        Path path = Path.of(contentRoot).resolve(postRef.getTitle());
        Files.deleteIfExists(path);
    }

    private void initFields() {
        // set injected values of poststore by reflection
        Class<?> clazz = cut.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for(Field field : fields) {
            ConfigProperty myCfgProp = field.getAnnotation(ConfigProperty.class);
            if(myCfgProp != null){
                field.setAccessible(true);
                try {
                    field.set(cut, contentRoot);
                    cut.init();
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    System.out.println("Reflection failed");
                    e.printStackTrace();
                }
            }else if(field.getAnnotation(Inject.class) != null){
                    try {
                        field.setAccessible(true);
                        field.set(cut, new TitleNormalizer());
                    } catch (IllegalArgumentException | IllegalAccessException e) {
                        System.out.println("Reflection failed");
                        e.printStackTrace();
                }
            }
        }
    }

    @Test
    @Order(1)
    public void createPostTest() throws IOException {
        cleanDirectory();
        cut.create(postRef, postRef.getTitle());
        assertTrue(cut.fileExists(contentRoot + postRef.getTitle()));
    }

    @Test
    @Order(2)
    public void updatePostTest() {
        cut.update(postRef, postRef.getTitle());
        assertTrue(cut.fileExists(contentRoot + postRef.getTitle()));
    }

    @Test
    @Order(3)
    public void readPostTest() throws IOException{
        Post postAct = cut.read(contentRoot + postRef.getTitle());
        postAct.setModifiedAt(postRef.getModifiedAt());

        String postActString = cut.serialize(postAct);
        String postRefString = cut.serialize(postRef);
        System.out.println(postActString);
        assertEquals(postRefString, postActString);
    }
}
