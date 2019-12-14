package com.example.demo.design;

import com.example.demo.Ingredient;
import com.example.demo.Ingredient.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Controller
@RequestMapping("/design")
public class DesignTacoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DesignTacoController.class);

    private static final List<Ingredient> INGREDIENTS = List.of(
            new Ingredient("FLTO", "Flour Tortilla", Type.WRAP),
            new Ingredient("COTO", "Corn Tortilla", Type.WRAP),
            new Ingredient("GRBF", "Ground Beef", Type.PROTEIN),
            new Ingredient("CARN", "Carnitas", Type.PROTEIN),
            new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES),
            new Ingredient("LETC", "Lettuce", Type.VEGGIES),
            new Ingredient("CHED", "Cheddar", Type.CHEESE),
            new Ingredient("JACK", "Monterrey Jack", Type.CHEESE),
            new Ingredient("SLSA", "Salsa", Type.SAUCE),
            new Ingredient("SRCR", "Sour Cream", Type.SAUCE)
    );

    private static List<Ingredient> ingredientsFilteredByType(List<Ingredient> ingredients, Type type) {
        return ingredients.stream()
                .filter(it -> it.getType() == type)
                .collect(toList());
    }

    @ModelAttribute
    public void addIngredientsToModel(Model model) {
        model.addAttribute("wrap", ingredientsFilteredByType(INGREDIENTS, Ingredient.Type.WRAP));
        model.addAttribute("protein", ingredientsFilteredByType(INGREDIENTS, Ingredient.Type.PROTEIN));
        model.addAttribute("veggies", ingredientsFilteredByType(INGREDIENTS, Ingredient.Type.VEGGIES));
        model.addAttribute("cheese", ingredientsFilteredByType(INGREDIENTS, Ingredient.Type.CHEESE));
        model.addAttribute("sauce", ingredientsFilteredByType(INGREDIENTS, Ingredient.Type.SAUCE));
    }

    @GetMapping("")
    public String design(Model model) {
        model.addAttribute("design", new Taco());

        return "design";
    }

    @PostMapping("")
    public String processDesign(@Valid @ModelAttribute("design") Taco design, Errors errors) {
        if (errors.hasErrors()) {
            LOGGER.error("Error processing design: {}", design);
            return "design";
        }

        LOGGER.info("Processing design: {}", design);

        return "redirect:/orders/current";
    }
}
