package com.example.demo.design;

import com.example.demo.users.User;
import com.example.demo.users.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
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

    @MockBean
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        when(ingredientRepository.findById("DUMMY"))
                .thenReturn(Optional.of(new Ingredient("DUMMY", "dummy", Ingredient.Type.CHEESE)));
        when(userRepository.findByUsername("testuser"))
                .thenReturn(Optional.of(new User("testuser", "testpass", "Test User", "123 Street", "Someville", "CO", "12345", "123-123-1234")));
    }

    @Test
    @WithMockUser(username = "testuser", password = "testpass", roles = "USER")
    void processDesignShouldGoBackToDesignWhenNameTooShort() throws Exception {
        mockMvc.perform(post("/design")
                .with(csrf())
                .param("name", "bad")
                .param("ingredients", "DUMMY"))
                .andExpect(model().hasErrors())
                .andExpect(view().name("design"));
    }

    @Test
    @WithMockUser(username = "testuser", password = "testpass", roles = "USER")
    void processDesignShouldGoBackToDesignIngredientsEmpty() throws Exception {
        mockMvc.perform(post("/design")
                .with(csrf())
                .param("name", "correct"))
                .andExpect(model().hasErrors())
                .andExpect(view().name("design"));
    }

    @Test
    @WithMockUser(username = "testuser", password = "testpass", authorities = "ROLE_USER")
    void processDesignShouldRedirectToOrdersWhenTacoIsValid() throws Exception {
        mockMvc.perform(post("/design")
                .with(csrf())
                .param("name", "correct")
                .param("ingredients", "DUMMY"))
                .andExpect(model().hasNoErrors())
                .andExpect(redirectedUrl("/orders/current"));
    }
}