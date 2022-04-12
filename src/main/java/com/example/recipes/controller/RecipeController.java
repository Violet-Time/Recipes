package com.example.recipes.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import com.example.recipes.service.RecipeService;
import com.example.recipes.service.UserService;
import com.example.recipes.model.Recipe;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/recipe")
public class RecipeController {

    private final RecipeService recipeService;
    private final UserService userService;

    public RecipeController(RecipeService recipeService, UserService userService) {
        this.recipeService = recipeService;
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Recipe> getRecipe(@PathVariable Long id) {
        return ResponseEntity.of(recipeService.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Recipe> deleteRecipe(@PathVariable Long id, @AuthenticationPrincipal UserDetails details) {
        recipeService.delete(id, details.getUsername());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{id}")
    public void updateRecipe(@Valid @RequestBody Recipe recipe, @PathVariable Long id,
                             @AuthenticationPrincipal UserDetails details) {
        recipe.setId(id);
        recipeService.update(recipe, details.getUsername());
    }

    @PostMapping("/new")
    public Map<String, Long> setRecipe(@Valid @RequestBody Recipe recipe, @AuthenticationPrincipal UserDetails details) {
        recipe.setUser(userService.findByEmail(details.getUsername()).get());
        recipe = recipeService.save(recipe);
        return Map.of("id", recipe.getId());
    }

    @GetMapping("search")
    public List<Recipe> search(@RequestParam(name = "category", required = false) String category,
                                         @RequestParam(name = "name", required = false) String name) {

        if (category != null && !category.isEmpty()) {
            if (name != null)
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            return recipeService.findAllByCategory(category);
        }

        if (name != null && !name.isEmpty()) {
            return recipeService.findAllByName(name);
        }

        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }
}
