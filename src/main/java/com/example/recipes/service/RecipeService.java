package com.example.recipes.service;


import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.example.recipes.model.Recipe;
import com.example.recipes.repos.RecipeRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;

    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public Optional<Recipe> findById(Long id) {
        return recipeRepository.findById(id);
    }

    public List<Recipe> findAllByName(String name) {
        return recipeRepository.findByNameIgnoreCaseContainingOrderByDateDesc(name);
    }

    public List<Recipe> findAllByCategory(String category) {
        return recipeRepository.findByCategoryIgnoreCaseOrderByDateDesc(category);
    }

    public void delete(Long id, String username) {
        Optional<Recipe> find = findById(id);
        if (find.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        if (!find.get().getUser().getUsername().equals(username)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        recipeRepository.deleteById(id);
    }

    public void update(Recipe recipe, String username) {
        Optional<Recipe> find = findById(recipe.getId());
        if (find.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        if (!find.get().getUser().getUsername().equals(username)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        recipe.setUser(find.get().getUser());
        save(recipe);
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }

    public Recipe save(Recipe recipe) {
        return recipeRepository.save(recipe);
    }
}
