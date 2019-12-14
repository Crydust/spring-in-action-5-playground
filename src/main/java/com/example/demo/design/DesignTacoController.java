package com.example.demo.design;

import com.example.demo.Ingredient;
import com.example.demo.Ingredient.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @GetMapping("")
    public String showDesignForm(Model model) {

        model.addAttribute(
                "wrap",
                INGREDIENTS.stream()
                        .filter(it -> it.getType() == Type.WRAP)
                        .collect(toList())
        );
        model.addAttribute(
                "protein",
                INGREDIENTS.stream()
                        .filter(it -> it.getType() == Type.PROTEIN)
                        .collect(toList())
        );
        model.addAttribute(
                "veggies",
                INGREDIENTS.stream()
                        .filter(it -> it.getType() == Type.VEGGIES)
                        .collect(toList())
        );
        model.addAttribute(
                "cheese",
                INGREDIENTS.stream()
                        .filter(it -> it.getType() == Type.CHEESE)
                        .collect(toList())
        );
        model.addAttribute(
                "sauce",
                INGREDIENTS.stream()
                        .filter(it -> it.getType() == Type.SAUCE)
                        .collect(toList())
        );

        model.addAttribute("design", new Taco());

        return "design";
    }

    @PostMapping("")
    public String processDesign(Design design) {
        LOGGER.info("DesignTacoController.processDesign");
        LOGGER.info("design = [" + design + "]");
        return "redirect:/orders/current";
    }
}
