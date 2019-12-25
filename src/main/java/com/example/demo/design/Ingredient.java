package com.example.demo.design;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class Ingredient implements Serializable {
    @Id
    @Column(length = 4)
    private final String id;
    @Column(length = 25, nullable = false)
    private final String name;
    @Column(length = 10, nullable = false)
    @Enumerated(EnumType.STRING)
    private final Type type;

    private Ingredient() {
        this(null, null, null);
    }

    public Ingredient(String id, String name, Type type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ingredient that = (Ingredient) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public enum Type {
        WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE;
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", type=" + type +
                '}';
    }
}
