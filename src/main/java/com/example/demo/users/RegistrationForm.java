package com.example.demo.users;

import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@FieldsValueMatch(field = "password", fieldMatch = "confirm", message = "The password must match the confirmation.")
public class RegistrationForm {

    @NotBlank(message = "Password is required")
    private String password;
    @NotBlank(message = "Password confirmation is required")
    private String confirm;
    @NotBlank(message = "Username is required")
    @Size(max = 100, message = "maximum length 100")
    private String username;
    @Size(max = 50, message = "maximum length 50")
    private String fullname;
    @Size(max = 50, message = "maximum length 50")
    private String street;
    @Size(max = 50, message = "maximum length 50")
    private String city;
    @Size(max = 2, message = "maximum length 2")
    private String state;
    @Size(max = 10, message = "maximum length 10")
    private String zip;
    private String phoneNumber;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "RegistrationForm{" +
                "password='******'" +
                ", confirm='******'" +
                ", username='" + username + '\'' +
                ", fullname='" + fullname + '\'' +
                ", street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zip='" + zip + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }

    public User toUser(PasswordEncoder passwordEncoder) {
        return new User(passwordEncoder.encode(password), username, fullname, street, city, state, zip, phoneNumber);
    }
}
