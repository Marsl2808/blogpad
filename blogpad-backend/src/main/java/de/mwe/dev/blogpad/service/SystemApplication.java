package de.mwe.dev.blogpad.service;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Info;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@ApplicationPath("/blogpad-backend")
@OpenAPIDefinition(info= @Info(title = "Blogpad Application", version = "1.0.0",
    description =  "... myDescription ..."))
public class SystemApplication extends Application {
}