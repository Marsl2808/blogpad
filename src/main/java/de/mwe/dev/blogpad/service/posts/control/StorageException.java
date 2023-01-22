package de.mwe.dev.blogpad.service.posts.control;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

public class StorageException extends WebApplicationException {

    public StorageException(String message, Throwable cause){
        super(Response.status(400).header("message", message).header("cause", cause.getMessage()).build());
    }
    
}
