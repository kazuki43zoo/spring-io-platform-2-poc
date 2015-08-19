package com.example.app.bodyadvice;


import com.fasterxml.jackson.annotation.JsonView;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class StreetAddress implements Serializable {

    interface View extends DefaultView {

    }

    interface Put {
    }

    private static final long serialVersionUID = 1L;

    @JsonView(DefaultView.class)
    @NotNull
    private String zipCode;

    @JsonView(DefaultView.class)
    @Size(max = 10)
    private String prefecture;

    @JsonView(DefaultView.class)
    @Size(max = 30)
    private String city;

    @JsonView(DefaultView.class)
    @Size(max = 50)
    private String address1;

    @JsonView(DefaultView.class)
    @Size(max = 50)
    private String address2;

    @JsonView(DefaultView.class)
    @Size(max = 50)
    private String address3;

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getPrefecture() {
        return prefecture;
    }

    public void setPrefecture(String prefecture) {
        this.prefecture = prefecture;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getAddress3() {
        return address3;
    }

    public void setAddress3(String address3) {
        this.address3 = address3;
    }


    public static class Builder {
        private String zipCode;
        private String prefecture;
        private String city;
        private String address1;
        private String address2;
        private String address3;

        private Builder() {
        }

        public static Builder aStreetAddress() {
            return new Builder();
        }

        public Builder withZipCode(String zipCode) {
            this.zipCode = zipCode;
            return this;
        }

        public Builder withPrefecture(String prefecture) {
            this.prefecture = prefecture;
            return this;
        }

        public Builder withCity(String city) {
            this.city = city;
            return this;
        }

        public Builder withAddress1(String address1) {
            this.address1 = address1;
            return this;
        }

        public Builder withAddress2(String address2) {
            this.address2 = address2;
            return this;
        }

        public Builder withAddress3(String address3) {
            this.address3 = address3;
            return this;
        }

        public Builder but() {
            return aStreetAddress().withZipCode(zipCode).withPrefecture(prefecture).withCity(city).withAddress1(address1).withAddress2(address2).withAddress3(address3);
        }

        public StreetAddress build() {
            StreetAddress streetAddress = new StreetAddress();
            streetAddress.setZipCode(zipCode);
            streetAddress.setPrefecture(prefecture);
            streetAddress.setCity(city);
            streetAddress.setAddress1(address1);
            streetAddress.setAddress2(address2);
            streetAddress.setAddress3(address3);
            return streetAddress;
        }
    }
}
