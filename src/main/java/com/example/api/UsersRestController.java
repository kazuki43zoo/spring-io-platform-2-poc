package com.example.api;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

@RequestMapping("api/users")
@RestController
public class UsersRestController {

    @RequestMapping(path = "{id}", method = RequestMethod.GET)
    public UserResource getUser(@PathVariable String id) {
        UserResource resource = new UserResource();
        resource.setId(id);
        resource.setName("氏名");
        resource.setEmailAddress("spring@example.com");
        return resource;
    }

    @RequestMapping(path = "{id}/name", method = RequestMethod.GET)
    @JsonView(UserResource.NameSubResourceView.class)
    public UserResource getUserName(@PathVariable String id) {
        return getUser(id);
    }

    @RequestMapping(path = "{id}/email", method = RequestMethod.GET)
    @JsonView(UserResource.EmailSubResourceView.class)
    public UserResource getUserEmail(@PathVariable String id) {
        return getUser(id);
    }

    @RequestMapping(path = "{id}", method = RequestMethod.PUT)
    public UserResource updateUser(
            @Validated @RequestBody UserResource resource) {
        return resource;
    }

    @RequestMapping(path = "{id}/name", method = RequestMethod.PUT)
    @JsonView(UserResource.NameSubResourceView.class)
    public UserResource updateUserName(
            @Validated @RequestBody
            @JsonView(UserResource.NameSubResourceView.class) UserResource resource) {
        return resource;
    }

    @RequestMapping(path = "{id}/email", method = RequestMethod.PUT)
    @JsonView(UserResource.EmailSubResourceView.class)
    public UserResource updateUserEmail(
            @Validated @RequestBody
            @JsonView(UserResource.EmailSubResourceView.class) UserResource resource) {
        return resource;
    }


    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<UserResource> createBook(
            @Validated @RequestBody UserResource newResource,
            UriComponentsBuilder uriBuilder) {

        String id = UUID.randomUUID().toString();

        URI resourceUri = MvcUriComponentsBuilder.relativeTo(uriBuilder)
                .withMethodCall(on(UsersRestController.class).getUser(id))
                .build()
                .encode()
                .toUri();

        return ResponseEntity
                .created(resourceUri)
                .body(newResource);
    }

}
