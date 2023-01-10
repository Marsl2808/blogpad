package de.mwe.dev.blogpad.service.posts.control;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import de.mwe.dev.blogpad.service.posts.entity.Post;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
public class PostStore {

    public void writeToFs(String filename, String content) throws IOException{
        Path path = Path.of(filename);
        Files.writeString(path, content);
    }

    public String read(String filename) throws IOException{
        Path path = Path.of(filename);
        return Files.readString(path);
    }

    public String serialize(Post post){
        Jsonb jsonb = JsonbBuilder.create();
        return jsonb.toJson(post);
    }
}
