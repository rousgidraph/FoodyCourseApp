package art.muriuki.foodycourseapp.data

import art.muriuki.foodycourseapp.data.database.entities.FavouritesEntity
import art.muriuki.foodycourseapp.data.database.RecipesDao
import art.muriuki.foodycourseapp.data.database.entities.FoodJokeEntity
import art.muriuki.foodycourseapp.data.database.entities.RecipesEntity
import art.muriuki.foodycourseapp.models.FoodJoke
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val recipesDao : RecipesDao
) {

     fun readRecipes(): Flow<List<RecipesEntity>> {
        return recipesDao.readRecipes()
    }

    fun readFavouritesRecipes(): Flow<List<FavouritesEntity>>{
        return recipesDao.readFavouriteRecipes()
    }

     fun readFoodJoke(): Flow<List<FoodJokeEntity>>{
        return recipesDao.readFoodJoke()
    }

    suspend fun insertRecipe(recipesEntity: RecipesEntity){
        recipesDao.insertRecipes(recipesEntity)
    }

    suspend fun insertFavouriteRecipe(favouritesEntity: FavouritesEntity){
        recipesDao.insertFavoriteRecipe(favouritesEntity)
    }

    suspend fun insertFoodJoke(foodJoke: FoodJokeEntity){
        recipesDao.insertFoodJoke(foodJoke)
    }
    suspend fun deleteFavouriteRecipe(favouritesEntity: FavouritesEntity){
        recipesDao.deleteFavouriteRecipe(favouritesEntity)
    }

    suspend fun  deleteAllFavouriteRecipes(){
        recipesDao.deleteAllFavouriteRecipes()
    }

}