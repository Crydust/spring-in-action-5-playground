package com.example.demo.design;

import com.example.demo.design.Ingredient.Type;
import com.example.demo.orders.Order;
import com.example.demo.users.UserRepository;
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
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;

@Controller
@RequestMapping("/design")
@SessionAttributes("order")
public class DesignTacoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DesignTacoController.class);

    private final IngredientRepository ingredientRepository;
    private final TacoRepository tacoRepository;
    private final UserRepository userRepository;

    @Autowired
    public DesignTacoController(IngredientRepository ingredientRepository, TacoRepository tacoRepository, UserRepository userRepository) {
        this.ingredientRepository = ingredientRepository;
        this.tacoRepository = tacoRepository;
        this.userRepository = userRepository;
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

    @ModelAttribute("order")
    public Order order() {
        return new Order();
    }

    @GetMapping("")
    public String designForm(
            Model model,
            Principal principal
    ) {
        // TODO https://stackoverflow.com/questions/52180555/mock-authenticationprincipal-argument
        //  https://fourminded.blogspot.com/2016/03/mocking-authenticationprincipal-in.html
        //  Doing this for test convenience instead of
        //  @AuthenticationPrincipal User user
        model.addAttribute("user", userRepository.findByUsername(principal.getName()).orElseThrow());
        return "design";
    }

    @PostMapping("")
    public String processDesign(
            @Valid @ModelAttribute(name = "taco") Taco taco,
            Errors errors,
            @ModelAttribute(name = "order", binding = false) Order order,
            Model model,
            Principal principal
    ) {
        if (errors.hasErrors()) {
            LOGGER.error("Error processing design: {}", taco);
            // TODO https://stackoverflow.com/questions/52180555/mock-authenticationprincipal-argument
            //  https://fourminded.blogspot.com/2016/03/mocking-authenticationprincipal-in.html
            //  Doing this for test convenience instead of
            //  @AuthenticationPrincipal User user
            model.addAttribute("user", userRepository.findByUsername(principal.getName()).orElseThrow());
            return "design";
        }

        LOGGER.info("Processing design: {}", taco);
        final Taco saved = tacoRepository.save(taco);
        order.addDesign(saved);

        return "redirect:/orders/current";
    }

}
