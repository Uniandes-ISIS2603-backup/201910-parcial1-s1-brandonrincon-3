/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recipes.ejb;

import co.edu.uniandes.csw.recipes.entities.RecipeEntity;
import co.edu.uniandes.csw.recipes.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.recipes.persistence.RecipePersistence;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author CesarF
 */
@Stateless
public class RecipeLogic {
    @Inject
    private RecipePersistence persistence; // Variable para acceder a la persistencia de la aplicación. Es una inyección de dependencias.

    public RecipeEntity getRecipe(Long id) {
        return persistence.find(id);
    }

    //TODO crear el método createRecipe
    
    public RecipeEntity createRecipe(RecipeEntity in)throws BusinessLogicException{
        if(in.getName()==null)
            throw new BusinessLogicException("Nombre nulo");
        if(in.getName().length()>30)
            throw new BusinessLogicException("Nombre demasiado largo");
        if(in.getDescription()==null)
            throw new BusinessLogicException("Descripcion nula");
        if(in.getDescription().length()>150)
              throw new BusinessLogicException("Descripcion demasiado larga");
        if(persistence.findName(in.getName())!=null)
            throw new BusinessLogicException("Nombre de receta repetido");
        return  persistence.createRecipe(in);
    }


}
