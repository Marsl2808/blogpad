package de.mwe.dev.blogpad.service.posts.control;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.lang.reflect.Field;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import de.mwe.dev.blogpad.service.posts.entity.Post;
import jakarta.inject.Inject;


@TestMethodOrder(OrderAnnotation.class)
public class PostStoreTest {

    static PostStore cut;
    static Post postRef;
    static String contentRoot = "D:/tmp/";

    @BeforeAll
    public static void init() {
        cut = new PostStore();
        postRef = new Post("testPost", "testContent");
        initFields();
    }

    private static void initFields() {
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
    public static void createPostTest() {
        cut.create(postRef, postRef.getTitle());
    }

    @Test
    @Order(2)
    public static void readPostTest() throws IOException{
        Post postAct = cut.read(contentRoot + postRef.getTitle());

        String postActString = cut.serialize(postAct);
        String postRefString = cut.serialize(postRef);
        System.out.println(postActString);
        assertEquals(postRefString, postActString);
    }
}
