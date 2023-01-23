package de.mwe.dev.blogpad.service.posts.control;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class TitleNormalizer {

    @Inject
    @ConfigProperty(name = "filename.separator", defaultValue = "-")
    String filenameSeperator;

    int codePointSeperator;

    @PostConstruct
    public void init(){
        this.codePointSeperator = this.filenameSeperator
                                    .codePoints()
                                    .findFirst()
                                    .orElseThrow();
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
            return this.codePointSeperator;
        }
    }
}
