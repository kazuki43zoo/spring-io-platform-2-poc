package com.example.app.uri;

import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.inject.Inject;
import java.net.URI;
import java.util.Locale;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;
import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.relativeTo;

@RequestMapping("uri")
@RestController
public class UriRestController {

    @Inject
    MessageSource messageSource;

    @RequestMapping(path = "message/{messageId1}/{messageId2}", method = RequestMethod.GET)
    public String get1(@PathVariable("messageId1") String messageId1, @PathVariable("messageId2") String messageId2, Locale locale) {
        return new StringBuilder()
                .append(messageSource.getMessage(messageId1, null, messageId1, locale))
                .append(messageSource.getMessage(messageId2, null, messageId2, locale)).toString();
    }

    @RequestMapping(path = "message/{messageId1}%2F{messageId2}", method = RequestMethod.GET)
    public String get2(@PathVariable("messageId1") String messageId1, @PathVariable("messageId2") String messageId2, Locale locale) {
        return new StringBuilder()
                .append(messageSource.getMessage(messageId1, null, messageId1, locale))
                .append(messageSource.getMessage(messageId2, null, messageId2, locale)).toString();
    }

    @RequestMapping(path = "messages", method = RequestMethod.POST)
    public ResponseEntity<Void> createMessage(UriComponentsBuilder baseUri, Locale locale) {
        URI resourceUri = relativeTo(baseUri).withMethodCall(on(UriRestController.class).getMessage("msg999", locale)).build().toUri();
        return ResponseEntity
                .created(resourceUri)
                .build();
    }

    @RequestMapping(path = "messages/{messageId}", method = RequestMethod.GET)
    public ResponseEntity<String> getMessage(@PathVariable("messageId") String messageId, Locale locale) {
        return ResponseEntity.ok(messageSource.getMessage(messageId, null, messageId, locale));
    }

}
