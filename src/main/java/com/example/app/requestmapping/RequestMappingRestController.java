package com.example.app.requestmapping;

import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.lang.annotation.*;
import java.util.Locale;

@RequestMappingRestController.MessageRequestMapping
@RestController
public class RequestMappingRestController {

    @Inject
    MessageSource messageSource;

    @Get
    public ResponseEntity<String> get(@PathVariable("messageId") String messageId, @RequestParam(name = "args", required = false) Object[] args, Locale locale) {
        String message = messageSource.getMessage(messageId, args, messageId, locale);
        return ResponseEntity
                .ok(message);
    }

    @RequestMapping(path = "requestmapping/message/{messageId}")
    @Target({ElementType.METHOD, ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    public @interface MessageRequestMapping {

    }

    @RequestMapping(method = RequestMethod.GET)
    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    public @interface Get {

    }

}
