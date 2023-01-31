package de.mwe.dev.blogpad.service.posts.control;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
//import org.eclipse.microprofile.metrics.MetricRegistry;
//import org.eclipse.microprofile.metrics.MetricUnits;
//import org.eclipse.microprofile.metrics.MetricRegistry.Type;
//import org.eclipse.microprofile.metrics.annotation.Gauge;
//import org.eclipse.microprofile.metrics.annotation.RegistryType;
import org.slf4j.LoggerFactory;

import de.mwe.dev.blogpad.service.posts.entity.Post;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;


@ApplicationScoped
public class PostStore {

    private static final Logger LOG = LoggerFactory.getLogger(PostInitializer.class);

    @Inject
    @ConfigProperty(name = "content.root")
    private String contentRoot;

    // @Inject
    // @RegistryType(type=Type.APPLICATION)
    // MetricRegistry registry;

    Path storageDirectoryPath;

    @PostConstruct
    void init(){
        this.storageDirectoryPath = Path.of(this.contentRoot);
    }

    @Inject
    TitleNormalizer normalizer;

    // @Gauge(name = "post-storage", unit = MetricUnits.MEGABYTES)
    // public long getPostStorageInMB(){
    //     try{
    //         return Files.getFileStore(this.storageDirectoryPath).getUsableSpace() / 1024 / 1024;
    //     }catch(IOException e){
    //         throw new StorageException("Cannot fetch size information from " + this.storageDirectoryPath, e);
    //     }
    // }

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

    public Post read(String filename){
        String normalizedFilename = this.normalizer.normalizeFilename(filename);
        if(!fileExists(normalizedFilename)){
            increaseNotExistingPostCounter();
            return null;
        }
        String stringifiedPost = readFromFs(normalizedFilename);
        return deserialize(stringifiedPost);
    }

    public void update(Post post, String filename){
        String normalizedFilename = this.normalizer.normalizeFilename(filename);
        if(!fileExists(normalizedFilename)){
            throw new StorageException("file "+ normalizedFilename + " not exists. Use POST to create.");
        }
        post.setModifiedAt();
        String jsonPost = serialize(post);
        post.setFullQualifiedFilename(normalizedFilename);
        writeToFs(normalizedFilename, jsonPost);
    }

    private void increaseNotExistingPostCounter() {
        //        this.registry.counter("fetch_post_with_ne_title").inc();
            }

    public boolean fileExists(String filename){
        Path fqn = this.storageDirectoryPath.resolve(filename);
        return Files.exists(fqn);
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
