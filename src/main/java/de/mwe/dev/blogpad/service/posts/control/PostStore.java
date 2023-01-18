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

    public void writeToFs(String filename, String content) throws IOException{
        Path path = Path.of(filename);
        Files.writeString(path, content);
    }

    public Post readFromFs(String filename) throws IOException{
        Path path = Path.of(filename);
        return deserialize(Files.readString(path));
    }

    public Post deserialize(String stringifiedPost){
        Jsonb jsonb = JsonbBuilder.create();
        return jsonb.fromJson(stringifiedPost, Post.class);
    }

    public String serialize(Post post){
        Jsonb jsonb = JsonbBuilder.create();
        return jsonb.toJson(post);
    }
}
