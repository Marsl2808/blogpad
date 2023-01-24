package de.mwe.dev.blogpad.service.posts.control;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import de.mwe.dev.blogpad.service.posts.entity.Post;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;


@ApplicationScoped
public class PostStore {

    @Inject
    @ConfigProperty(name = "content.root")
    private String contentRoot;

    Path storageDirectoryPath;

    @PostConstruct
    void init(){
        this.storageDirectoryPath = Path.of(this.contentRoot);
    }

    @Inject
    TitleNormalizer normalizer;

    public Post create(Post post, String filename){
        post.setCreatedAt();
        String normalizedFilename = this.normalizer.normalizeFilename(filename);
        if(fileExists(normalizedFilename)){
            throw new StorageException("file "+ normalizedFilename + " already exists. Use PUT for update.");
        }
        String jsonPost = serialize(post);
        post.setFullQualifiedFilename(normalizedFilename);
        writeToFs(normalizedFilename, jsonPost);
        return post;
    }

    public boolean fileExists(String filename){
        Path fqn = this.storageDirectoryPath.resolve(filename);
        return Files.exists(fqn);
    }

    public void update(Post post, String filename){
        String normalizedFilename = this.normalizer.normalizeFilename(filename);
        // if(!fileExists(normalizedFilename)){
        //     throw new StorageException("file "+ normalizedFilename + " not exists. Use POST to create.");
        // }
        post.setModifiedAt();
        String jsonPost = serialize(post);
        post.setFullQualifiedFilename(normalizedFilename);
        writeToFs(normalizedFilename, jsonPost);
    }

    public void writeToFs(String filename, String content) {
        try{
            Path path = this.storageDirectoryPath.resolve(filename);
            Files.writeString(path, content);
        } catch(IOException | InvalidPathException e){
            throw new StorageException("cannot save post", e);
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
            Path path = this.storageDirectoryPath.resolve(filename);
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
