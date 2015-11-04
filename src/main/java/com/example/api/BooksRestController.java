package com.example.api;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.UUID;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

@RequestMapping("api/books")
@RestController
public class BooksRestController {

    @RequestMapping(path = "{isbn}", method = RequestMethod.GET)
    public BookResource getBook(@PathVariable String isbn) {
        BookResource resource = new BookResource();
        resource.setIbsn("xxx-x-xxxx-xxxx-x");
        resource.setName("書籍名");
        resource.setAuthors(Arrays.asList("著者A"));
        resource.setPublishedDate(LocalDate.of(2016, 4, 1));
        BookResource.Publisher publisher = new BookResource.Publisher();
        publisher.setName("翔泳社");
        publisher.setTel("03-xxxx-xxxx");
        resource.setPublisher(publisher);
        return resource;
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
    public ResponseEntity<BookResource> createBook(
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
                .body(newResource);
    }
}
