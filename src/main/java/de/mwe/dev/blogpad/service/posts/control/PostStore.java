package de.mwe.dev.blogpad.service.posts.control;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;

import de.mwe.dev.blogpad.service.posts.entity.Post;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;


@ApplicationScoped
public class PostStore {

    public void save(Post post, String filename, String contentRoot){
        String jsonPost = serialize(post);
        String fullQualifiedFilename = contentRoot + normalizeFilename(filename);
        writeToFs(fullQualifiedFilename, jsonPost);
    }

    public void writeToFs(String filename, String content) {
        try{
            Path path = Path.of(filename);
            Files.writeString(path, content);
        } catch(IOException | InvalidPathException e){
            throw new StorageException("cannot save post", e);
        }
    }

    String normalizeFilename(String filename){
        return filename.codePoints()
            .map(this::replaceWithDigitOrLetter)
            .collect(StringBuffer::new, StringBuffer::appendCodePoint, StringBuffer::append)
            .toString();
    }

    int replaceWithDigitOrLetter(int codePoint){
        if(Character.isLetterOrDigit(codePoint)){
            return codePoint;
        }else{
            return "-".codePoints().findFirst().orElseThrow();
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
        String stringifiedPost = "";
        try {
            Path path = Path.of(filename);
            stringifiedPost = Files.readString(path);
        } catch (IOException| InvalidPathException e) {
            throw new StorageException("cannot read post", e);
        }
        return stringifiedPost;
    }

    public Post deserialize(String stringifiedPost){
        Jsonb jsonb = JsonbBuilder.create();
        return jsonb.fromJson(stringifiedPost, Post.class);
    }
}
