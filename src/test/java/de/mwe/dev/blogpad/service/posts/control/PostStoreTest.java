package de.mwe.dev.blogpad.service.posts.control;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.lang.reflect.Field;

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
    private String contentRoot = "D:/tmp/";

    @BeforeEach
    public void init() {
        this.cut = new PostStore();
        this.postRef = new Post("testPost", "testContent");
        initFields();
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
                System.out.println("test");
            }else if(field.getAnnotation(Inject.class) != null){
                    try {
                        field.set(cut, new TitleNormalizer());
                    } catch (IllegalArgumentException | IllegalAccessException e) {
                        System.out.println("Reflection error");
                        e.printStackTrace();
                }
            }
        }
    }

    @Test
    @Order(1)
    public void serializePostTest() {
        this.cut.save(this.postRef, this.postRef.getTitle());
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
