package com.example.app.uri;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

@RequestMapping("api/books")
@RestController
public class BooksRestController {

    @RequestMapping(path = "{isbn}", method = RequestMethod.POST)
    public BookResource getBook(@PathVariable String isbn) {
        return null;
    }
//
//    @RequestMapping(method = RequestMethod.POST)
//    public ResponseEntity<Void> createBook(
//            @Validated @RequestBody BookResource newResource,
//            UriComponentsBuilder uriBuilder) {
//
//        String isbn = UUID.randomUUID().toString();
//
//        URI resourceUri = uriBuilder
//                .path("api/books/{isbn}")
//                .build()
//                .expand(isbn)
//                .encode()
//                .toUri();
//
//        return ResponseEntity
//                .created(resourceUri)
//                .build();
//    }


    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> createBook(
            @Validated @RequestBody BookResource newResource,
            UriComponentsBuilder uriBuilder) {

        String isbn = UUID.randomUUID().toString();

        URI resourceUri = MvcUriComponentsBuilder.relativeTo(uriBuilder)
                .withMethodCall(on(BooksRestController.class).getBook(isbn))
                .build()
                .encode()
                .toUri();

        return ResponseEntity
                .created(resourceUri)
                .build();
    }
}
