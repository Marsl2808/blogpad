package de.mwe.dev.blogpad.service.posts.entity;


public class Post {

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

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
