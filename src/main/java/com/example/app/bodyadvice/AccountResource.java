package com.example.app.bodyadvice;

import com.fasterxml.jackson.annotation.JsonView;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;

public class AccountResource implements Serializable {

    interface View extends DefaultView, PersonName.View, StreetAddress.View, MailAddress.View {
    }

    interface Post {
    }

    interface Put extends PersonName.Put, StreetAddress.Put, MailAddress.Put {
    }

    private static final long serialVersionUID = 1L;

    @JsonView(DefaultView.class)
    @Null(groups = Post.class)
    @NotNull(groups = Put.class)
    private String id;

    @JsonView({AccountResource.View.class, PersonName.View.class})
    @NotNull(groups = {Post.class, PersonName.Put.class})
    @Valid
    private PersonName personName;

    @JsonView({AccountResource.View.class, StreetAddress.View.class})
    @NotNull(groups = StreetAddress.Put.class)
    @Valid
    private StreetAddress streetAddress;

    @JsonView({AccountResource.View.class, MailAddress.View.class})
    @NotNull(groups = {Post.class, MailAddress.Put.class})
    @Valid
    private MailAddress mailAddress;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PersonName getPersonName() {
        return personName;
    }

    public void setPersonName(PersonName personName) {
        this.personName = personName;
    }

    public StreetAddress getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(StreetAddress streetAddress) {
        this.streetAddress = streetAddress;
    }

    public MailAddress getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(MailAddress mailAddress) {
        this.mailAddress = mailAddress;
    }


    public static class Builder {
        private String id;
        private PersonName personName;
        private StreetAddress streetAddress;
        private MailAddress mailAddress;

        private Builder() {
        }

        public static Builder anAccountResource() {
            return new Builder();
        }

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withPersonName(PersonName personName) {
            this.personName = personName;
            return this;
        }

        public Builder withStreetAddress(StreetAddress streetAddress) {
            this.streetAddress = streetAddress;
            return this;
        }

        public Builder withMailAddress(MailAddress mailAddress) {
            this.mailAddress = mailAddress;
            return this;
        }

        public Builder but() {
            return anAccountResource().withId(id).withPersonName(personName).withStreetAddress(streetAddress).withMailAddress(mailAddress);
        }

        public AccountResource build() {
            AccountResource accountResource = new AccountResource();
            accountResource.setId(id);
            accountResource.setPersonName(personName);
            accountResource.setStreetAddress(streetAddress);
            accountResource.setMailAddress(mailAddress);
            return accountResource;
        }
    }
}
