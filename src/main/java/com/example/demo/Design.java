package com.example.demo;

import java.util.List;
import java.util.Objects;

public class Design {
    private final String name;
    private final List<String> ingredients;

    public Design(String name, List<String> ingredients) {
        this.name = name;
        this.ingredients = ingredients;
    }

    public String getName() {
        return name;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Design design = (Design) o;
        return Objects.equals(name, design.name) &&
                Objects.equals(ingredients, design.ingredients);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, ingredients);
    }

    @Override
    public String toString() {
        return "Design{" +
                "name='" + name + '\'' +
                ", ingredients=" + ingredients +
                '}';
    }
}
