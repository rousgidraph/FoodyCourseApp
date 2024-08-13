package art.muriuki.foodycourseapp.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import art.muriuki.foodycourseapp.data.database.entities.FavouritesEntity
import art.muriuki.foodycourseapp.data.database.entities.FoodJokeEntity
import art.muriuki.foodycourseapp.data.database.entities.RecipesEntity
import art.muriuki.foodycourseapp.models.FoodJoke
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipes(recipesEntity: RecipesEntity)


    @Query("SELECT * FROM recipes_table ORDER BY id ASC")
     fun readRecipes():Flow<List<RecipesEntity>>

    @Query("SELECT * FROM favourite_recipes_table ORDER BY id ASC")
    fun readFavouriteRecipes():Flow<List<FavouritesEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     suspend fun insertFavoriteRecipe(favouritesEntity: FavouritesEntity)

     @Delete
     suspend fun deleteFavouriteRecipe(favouritesEntity: FavouritesEntity)

     @Query("DELETE FROM favourite_recipes_table")
     suspend fun deleteAllFavouriteRecipes()

     @Insert(onConflict = OnConflictStrategy.REPLACE)
     suspend fun insertFoodJoke(foodJoke: FoodJokeEntity)

     @Query("SELECT * FROM food_joke_table order by id ASC")
     fun readFoodJoke():Flow<List<FoodJokeEntity>>

}