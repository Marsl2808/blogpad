package de.mwe.dev.blogpad.service.posts.entity;

import java.time.LocalDateTime;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class Post {

    @Schema(readOnly = true)
    public String fullQualifiedFilename;

    @Schema(required = true)
    public String title;

    @Schema(required = true)
    public String content;

    @Schema(readOnly = true)
    public LocalDateTime createdAt;
    @Schema(readOnly = true)
    public LocalDateTime modifiedAt;

    public Post(String title, String content){
        this.title = title;
        this.content = content;
    }

    public Post(){
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(LocalDateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public void setCreatedAt(){
        this.createdAt = LocalDateTime.now();
    }

    public void setModifiedAt(){
        this.modifiedAt = LocalDateTime.now();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setFullQualifiedFilename(String fqn) {
        this.fullQualifiedFilename = fqn;
    }

    public String getFullQualifiedFilename() {
        return fullQualifiedFilename;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
