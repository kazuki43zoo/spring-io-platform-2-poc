package com.example.app.bodyadvice;

import com.fasterxml.jackson.annotation.JsonView;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class PersonName implements Serializable {

    interface View extends DefaultView {
    }
    interface Put {
    }

    private static final long serialVersionUID = 1L;

    @JsonView(DefaultView.class)
    @NotNull
    @Size(max = 50)
    private String first;

    @JsonView(DefaultView.class)
    @NotNull
    @Size(max = 50)
    private String last;

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public static class Builder {
        private String first;
        private String last;

        private Builder() {
        }

        public static Builder aPersonName() {
            return new Builder();
        }

        public Builder withFirst(String first) {
            this.first = first;
            return this;
        }

        public Builder withLast(String last) {
            this.last = last;
            return this;
        }

        public Builder but() {
            return aPersonName().withFirst(first).withLast(last);
        }

        public PersonName build() {
            PersonName personName = new PersonName();
            personName.setFirst(first);
            personName.setLast(last);
            return personName;
        }
    }
}
