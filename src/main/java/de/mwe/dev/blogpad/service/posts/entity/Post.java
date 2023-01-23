package de.mwe.dev.blogpad.service.posts.entity;


public class Post {

    private String fullQualifiedFilename;
    private String title;
    private String content;

    public Post(String title, String content){
        this.title = title;
        this.content = content;
    }

    public Post(){
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setFullQualifiedFilename(String filename) {
        this.fullQualifiedFilename = filename;
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
