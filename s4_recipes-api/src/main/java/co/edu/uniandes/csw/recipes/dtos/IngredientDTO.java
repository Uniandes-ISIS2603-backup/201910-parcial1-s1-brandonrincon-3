/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recipes.dtos;

import co.edu.uniandes.csw.recipes.entities.IngredientEntity;
import java.io.Serializable;

/**
 *
 * @author estudiante
 */
public class IngredientDTO  implements Serializable{
    private String name;
    private Integer calories;
    private Long id;
    
   public IngredientDTO(){}
   
   public IngredientDTO(IngredientEntity ingredient) {
    this.id = ingredient.getId();
    this.name = ingredient.getName();
    this.calories=ingredient.getCalories();
}
   
   public IngredientEntity toEntity() {
    IngredientEntity entity = new IngredientEntity();
    entity.setId(this.id);
    entity.setName(this.name);
    entity.setCalories(this.calories);
    return entity;
}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCalories() {
        return calories;
    }

    public void setCalories(Integer calories) {
        this.calories = calories;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
   
   
}
