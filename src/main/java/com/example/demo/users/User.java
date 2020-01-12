package com.example.demo.users;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.Collection;
import java.util.List;

@Entity
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = {"username"})
)
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "password", length = 100, nullable = false)
    private final String password;
    @Column(name = "username", length = 100, nullable = false)
    private final String username;
    @Column(name = "fullname", length = 50)
    private final String fullname;
    @Column(name = "street", length = 50)
    private final String street;
    @Column(name = "city", length = 50)
    private final String city;
    @Column(name = "state", length = 2)
    private final String state;
    @Column(name = "zip", length = 10)
    private final String zip;
    @Column(name = "phonenumber", length = 20)
    private final String phoneNumber;

    private User() {
        this(null, null, null, null, null, null, null, null);
    }

    public User(String password, String username, String fullname, String street, String city, String state, String zip, String phoneNumber) {
        this.password = password;
        this.username = username;
        this.fullname = fullname;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.phoneNumber = phoneNumber;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        boolean accountNonExpired = true;
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        boolean accountNonLocked = true;
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        boolean credentialsNonExpired = true;
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        boolean enabled = true;
        return enabled;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getZip() {
        return zip;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", password='***" +
                ", username='" + username + '\'' +
                ", fullname='" + fullname + '\'' +
                ", street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zip='" + zip + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
