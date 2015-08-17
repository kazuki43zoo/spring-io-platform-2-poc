package com.example.app.rest;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RequestMapping("rest")
@RestController
public class StandardRestController {

    @RequestMapping(method = RequestMethod.POST)
    public String post() {
        return "post with by " + UUID.randomUUID().toString();
    }

    @RequestMapping(path = "{id}", method = {RequestMethod.GET, RequestMethod.HEAD})
    public String get(@PathVariable("id") String id) {
        return "get by " + id;
    }

    @RequestMapping(path = "{id}", method = RequestMethod.PUT)
    public String put(@PathVariable("id") String id) {
        return "put by " + id;
    }

    @RequestMapping(path = "{id}", method = RequestMethod.PATCH)
    public String patch(@PathVariable("id") String id) {
        return "patch by " + id;
    }

    @RequestMapping(path = "{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable("id") String id) {
        return "delete by " + id;
    }

}
