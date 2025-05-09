package com.br.dias.hubspot_integration.DTO;

import java.util.Objects;

public class RestHubContactDTO {
    private String accessToken;
    private String email;
    private String firstname;
    private String lastname;
    private String phone;

    public RestHubContactDTO() {
    }

    public RestHubContactDTO(String accessToken, String email, String firstname, String lastname, String phone) {
        this.accessToken = accessToken;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.phone = phone;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        RestHubContactDTO that = (RestHubContactDTO) o;
        return Objects.equals(accessToken, that.accessToken) && Objects.equals(email, that.email) && Objects.equals(firstname, that.firstname) && Objects.equals(lastname, that.lastname) && Objects.equals(phone, that.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accessToken, email, firstname, lastname, phone);
    }
}
