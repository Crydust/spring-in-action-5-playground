package com.example.demo.orders;

import com.example.demo.design.Taco;
import org.hibernate.validator.constraints.CreditCardNumber;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "Taco_Order")
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "placedat", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date placedAt;

    @OneToMany(
            targetEntity = Taco.class,
            mappedBy = "order",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<Taco> tacos = new HashSet<>();

    @Column(name = "deliveryname", length = 50, nullable = false)
    @NotBlank(message = "Name is required")
    @Size(max = 50, message = "maximum length 50")
    private String deliveryName;
    @Column(name = "deliverystreet", length = 50, nullable = false)
    @NotBlank(message = "Street is required")
    @Size(max = 50, message = "maximum length 50")
    private String deliveryStreet;
    @Column(name = "deliverycity", length = 50, nullable = false)
    @NotBlank(message = "City is required")
    @Size(max = 50, message = "maximum length 50")
    private String deliveryCity;
    @Column(name = "deliverystate", length = 2, nullable = false)
    @NotBlank(message = "State is required")
    @Size(max = 2, message = "maximum length 2")
    private String deliveryState;
    @Column(name = "deliveryzip", length = 10, nullable = false)
    @NotBlank(message = "Zip code is required")
    @Size(max = 10, message = "maximum length 10")
    private String deliveryZip;
    @Column(name = "ccnumber", length = 16, nullable = false)
    @CreditCardNumber(message = "Not a valid credit card number (eg. 4111111111111111)")
    private String ccNumber;
    @Column(name = "ccexpiration", length = 5, nullable = false)
    @Pattern(regexp = "^(0[1-9]|1[0-2])([\\\\/])([1-9][0-9])$",
            message = "Must be formatted MM/YY")
    private String ccExpiration;
    @Column(name = "cccvv", length = 3, nullable = false)
    @Digits(integer = 3, fraction = 0, message = "Invalid CVV")
    private String ccCVV;

    @PrePersist
    void prePersist() {
        this.placedAt = new Date();
        this.getTacos().forEach(it -> it.setOrder(this));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getPlacedAt() {
        return placedAt;
    }

    public void setPlacedAt(Date placedAt) {
        this.placedAt = placedAt;
    }

    public Set<Taco> getTacos() {
        return tacos;
    }

    public void setTacos(Set<Taco> tacos) {
        this.tacos = tacos;
    }

    public String getDeliveryName() {
        return deliveryName;
    }

    public void setDeliveryName(String deliveryName) {
        this.deliveryName = deliveryName;
    }

    public String getDeliveryStreet() {
        return deliveryStreet;
    }

    public void setDeliveryStreet(String deliveryStreet) {
        this.deliveryStreet = deliveryStreet;
    }

    public String getDeliveryCity() {
        return deliveryCity;
    }

    public void setDeliveryCity(String deliveryCity) {
        this.deliveryCity = deliveryCity;
    }

    public String getDeliveryState() {
        return deliveryState;
    }

    public void setDeliveryState(String deliveryState) {
        this.deliveryState = deliveryState;
    }

    public String getDeliveryZip() {
        return deliveryZip;
    }

    public void setDeliveryZip(String deliveryZip) {
        this.deliveryZip = deliveryZip;
    }

    public String getCcNumber() {
        return ccNumber;
    }

    public void setCcNumber(String ccNumber) {
        this.ccNumber = ccNumber;
    }

    public String getCcExpiration() {
        return ccExpiration;
    }

    public void setCcExpiration(String ccExpiration) {
        this.ccExpiration = ccExpiration;
    }

    public String getCcCVV() {
        return ccCVV;
    }

    public void setCcCVV(String ccCVV) {
        this.ccCVV = ccCVV;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", placedAt=" + placedAt +
                ", tacos=" + tacos +
                ", name='" + deliveryName + '\'' +
                ", street='" + deliveryStreet + '\'' +
                ", city='" + deliveryCity + '\'' +
                ", state='" + deliveryState + '\'' +
                ", zip='" + deliveryZip + '\'' +
                ", ccNumber='" + ccNumber + '\'' +
                ", ccExpiration='" + ccExpiration + '\'' +
                ", ccCVV='" + ccCVV + '\'' +
                '}';
    }

    public void addDesign(Taco taco) {
        this.tacos.add(taco);
    }
}
