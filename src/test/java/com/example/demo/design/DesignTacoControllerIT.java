package com.example.demo.design;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(controllers = DesignTacoController.class)
class DesignTacoControllerIT {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IngredientRepository ingredientRepository;

    @MockBean
    private TacoRepository tacoRepository;

    @BeforeEach
    void setUp() {
        when(ingredientRepository.findOne("DUMMY"))
                .thenReturn(new Ingredient("DUMMY", "dummy", Ingredient.Type.CHEESE));
    }

    @Test
    void processDesignShouldGoBackToDesignWhenNameTooShort() throws Exception {
        mockMvc.perform(post("/design")
                .param("name", "bad")
                .param("ingredients", "DUMMY"))
                .andExpect(model().hasErrors())
                .andExpect(view().name("design"));
    }

    @Test
    void processDesignShouldGoBackToDesignIngredientsEmpty() throws Exception {
        mockMvc.perform(post("/design")
                .param("name", "correct"))
                .andExpect(model().hasErrors())
                .andExpect(view().name("design"));
    }

    @Test
    void processDesignShouldRedirectToOrdersWhenTacoIsValid() throws Exception {
        mockMvc.perform(post("/design")
                .param("name", "correct")
                .param("ingredients", "DUMMY"))
                .andExpect(model().hasNoErrors())
                .andExpect(redirectedUrl("/orders/current"));
    }
}