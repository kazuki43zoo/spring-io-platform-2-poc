package com.example.app.uri;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class BookResource {

    @NotNull
    @Size(min = 1)
    public String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
