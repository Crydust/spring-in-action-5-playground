package com.example.demo.design;

import com.example.demo.design.Ingredient.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;

@Controller
@RequestMapping("/design")
public class DesignTacoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DesignTacoController.class);

    private final IngredientRepository ingredientRepository;
    private final TacoRepository tacoRepository;

    @Autowired
    public DesignTacoController(IngredientRepository ingredientRepository, TacoRepository tacoRepository) {
        this.ingredientRepository = ingredientRepository;
        this.tacoRepository = tacoRepository;
    }

    private static List<Ingredient> ingredientsFilteredByType(Iterable<Ingredient> ingredients, Type type) {
        return StreamSupport.stream(ingredients.spliterator(), false)
                .filter(it -> it.getType() == type)
                .collect(toList());
    }

    @ModelAttribute
    public void addIngredientsToModel(Model model) {
        final Iterable<Ingredient> ingredients = ingredientRepository.findAll();
        model.addAttribute("wrap", ingredientsFilteredByType(ingredients, Ingredient.Type.WRAP));
        model.addAttribute("protein", ingredientsFilteredByType(ingredients, Ingredient.Type.PROTEIN));
        model.addAttribute("veggies", ingredientsFilteredByType(ingredients, Ingredient.Type.VEGGIES));
        model.addAttribute("cheese", ingredientsFilteredByType(ingredients, Ingredient.Type.CHEESE));
        model.addAttribute("sauce", ingredientsFilteredByType(ingredients, Ingredient.Type.SAUCE));
    }

    @ModelAttribute("taco")
    public Taco taco() {
        return new Taco();
    }

    @GetMapping("")
    public String designForm() {
        return "design";
    }

    @PostMapping("")
    public String processDesign(
            @Valid @ModelAttribute("taco") Taco taco,
             Errors errors) {
        if (errors.hasErrors()) {
            LOGGER.error("Error processing design: {}", taco);
            return "design";
        }

        LOGGER.info("Processing design: {}", taco);
        final Taco saved = tacoRepository.save(taco);
        //order.addDesign(saved);

        return "redirect:/orders/current";
    }
}
