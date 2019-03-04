package co.edu.uniandes.csw.recipes.test.logic;

import co.edu.uniandes.csw.recipes.ejb.RecipeLogic;
import co.edu.uniandes.csw.recipes.entities.IngredientEntity;
import co.edu.uniandes.csw.recipes.entities.RecipeEntity;
import co.edu.uniandes.csw.recipes.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.recipes.persistence.RecipePersistence;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author bs.rincon
 */
@RunWith(Arquillian.class)
public class RecipeLogicTest {
    
     private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private RecipeLogic recipeLogic;
    
  
    

    @PersistenceContext
    private EntityManager em;
    
    @Inject
    private UserTransaction utx;
    
     private List<RecipeEntity> data = new ArrayList<RecipeEntity>();
    
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(RecipeEntity.class.getPackage())
                .addPackage(RecipeLogic.class.getPackage())
                .addPackage(RecipePersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    /**
     * Configuración inicial de la prueba.
     */
    @Before
    public void configTest() {
        try {
            utx.begin();
            clearData();
            insertData();
            utx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                utx.rollback();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * Limpia las tablas que están implicadas en la prueba.
     */
    private void clearData() {
        em.createQuery("delete from BookEntity").executeUpdate();
        em.createQuery("delete from EditorialEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            RecipeEntity books = factory.manufacturePojo(RecipeEntity.class);
            em.persist(books);
            data.add(books);
        }
    }
    
    /**
     * Prueba para crear un recipe
     *
     * @throws co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException
     */
    @Test
    public void createRecipeTest() throws BusinessLogicException {
         RecipeEntity newEntity  = factory.manufacturePojo(RecipeEntity.class);
         newEntity.getIngredientes().add(new IngredientEntity());
         RecipeEntity persistence=recipeLogic.createRecipe(newEntity);
         Assert.assertEquals(persistence.getId(), newEntity.getId());
        Assert.assertEquals(persistence.getDescription(), newEntity.getDescription());
        Assert.assertEquals(persistence.getName(), newEntity.getName());
    }
    
     @Test(expected = BusinessLogicException.class)
    public void createRecipeFailTest() throws BusinessLogicException {
         RecipeEntity newEntity  = factory.manufacturePojo(RecipeEntity.class);
         newEntity.setName("receta que es muy larga y que va a fallar siempre");
         newEntity.setDescription("ssl");
         RecipeEntity persistence=recipeLogic.createRecipe(newEntity);
    }
    
    @Test(expected = BusinessLogicException.class)
    public void createRecipeFailDescriptionTest() throws BusinessLogicException {
         RecipeEntity newEntity  = factory.manufacturePojo(RecipeEntity.class);
         newEntity.setDescription("    (5%) Crear el método createRecipe en la clase RecipePersistence el cual permita almacenar la receta en la base de datos\n" +
"\n" +
"    (5%) Completar el método de prueba createRecipeTest para que valide si se crea correctamente una receta.\n" +
"\n" +
"    (10%) Crear el método createRecipe en la clase RecipeLogic el cual valida las siguientes reglas de negocio.\n" +
"\n" +
"    El nombre de la receta es válido, no es vacio ni nulo y tampoco supera los 30 caracteres\n" +
"    No deben haber dos recetas con el mismo nombre\n" +
"    La descripción es válida, no es vacia ni nula y tampoco supera los 150 caracteres\n" +
"\n" +
"    (10%) Cree la clase de pruebas RecipeLogicTest que contiene el método de prueba createRecipeTest para que valide si se crea correctamente una receta.");
         RecipeEntity persistence=recipeLogic.createRecipe(newEntity);
    }
}
