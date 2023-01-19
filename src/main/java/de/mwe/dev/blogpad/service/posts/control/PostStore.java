package de.mwe.dev.blogpad.service.posts.control;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import de.mwe.dev.blogpad.service.posts.entity.Post;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;


@ApplicationScoped
public class PostStore {

    // TODO
    //@Inject
    //@ConfigProperty(name = "content-root")
    //private String contentRoot = "D:/tmp/";

    public void save(Post post, String filename){
        String jsonPost = serialize(post);
        writeToFs(filename, jsonPost);
    }

    public void writeToFs(String filename, String content) {
        Path path = Path.of(filename);
        try{
            Files.writeString(path, content);
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    public String serialize(Post post){
        Jsonb jsonb = JsonbBuilder.create();
        return jsonb.toJson(post);
    }

    public Post read(String filename){
        String stringifiedPost = readFromFs(filename);
        return deserialize(stringifiedPost);
    }

    public String readFromFs(String filename){
        Path path = Path.of(filename);
        String stringifiedPost = "";
        try {
            stringifiedPost = Files.readString(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringifiedPost;
    }

    public Post deserialize(String stringifiedPost){
        Jsonb jsonb = JsonbBuilder.create();
        return jsonb.fromJson(stringifiedPost, Post.class);
    }
}
