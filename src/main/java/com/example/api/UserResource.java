package com.example.api;

import com.fasterxml.jackson.annotation.JsonView;

import java.io.Serializable;

public class UserResource implements Serializable {


    interface NameSubResourceView {
    }

    interface EmailSubResourceView {
    }

    private static final long serialVersionUID = 1L;

    @JsonView({NameSubResourceView.class, EmailSubResourceView.class})
    private String id;
    @JsonView(NameSubResourceView.class)
    private String name;
    @JsonView(EmailSubResourceView.class)
    private String emailAddress;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

}
