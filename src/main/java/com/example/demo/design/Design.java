package com.example.demo.design;

import java.util.List;
import java.util.Objects;

public class Design {
    private String name;
    private List<String> ingredients;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
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
