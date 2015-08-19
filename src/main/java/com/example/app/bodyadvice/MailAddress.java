package com.example.app.bodyadvice;


import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class MailAddress implements Serializable {

    interface View extends DefaultView {
    }

    interface Put {
    }

    private static final long serialVersionUID = 1L;

    @JsonView(DefaultView.class)
    @NotNull
    @Email
    @Size(max = 256)
    private String address;

    @JsonView(DefaultView.class)
    private boolean publication;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isPublication() {
        return publication;
    }

    public void setPublication(boolean publication) {
        this.publication = publication;
    }


    public static class Builder {
        private String address;
        private boolean publication;

        private Builder() {
        }

        public static Builder aMailAddress() {
            return new Builder();
        }

        public Builder withAddress(String address) {
            this.address = address;
            return this;
        }

        public Builder withPublication(boolean publication) {
            this.publication = publication;
            return this;
        }

        public Builder but() {
            return aMailAddress().withAddress(address).withPublication(publication);
        }

        public MailAddress build() {
            MailAddress mailAddress = new MailAddress();
            mailAddress.setAddress(address);
            mailAddress.setPublication(publication);
            return mailAddress;
        }
    }
}
