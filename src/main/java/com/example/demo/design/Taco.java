package com.example.demo.design;

import java.util.List;
import java.util.Objects;

public class Taco {
    private final String name;
    private final List<String> ingredients;

    public Taco(String name, List<String> ingredients) {
        this.name = name;
        this.ingredients = ingredients;
    }

    public Taco() {
        this("", List.of());
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
        Taco taco = (Taco) o;
        return Objects.equals(name, taco.name) &&
                Objects.equals(ingredients, taco.ingredients);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, ingredients);
    }
}
