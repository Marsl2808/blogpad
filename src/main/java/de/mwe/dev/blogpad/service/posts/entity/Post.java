package de.mwe.dev.blogpad.service.posts.entity;

public class Post {
    
    public String title;
    public String content;

    public Post(String title, String content){
        this.title = title;
        this.content = content;
    }

    public Post(){
    }
}
