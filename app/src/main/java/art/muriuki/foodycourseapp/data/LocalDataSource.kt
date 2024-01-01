package art.muriuki.foodycourseapp.data

import art.muriuki.foodycourseapp.data.database.Entities.FavouritesEntity
import art.muriuki.foodycourseapp.data.database.RecipesDao
import art.muriuki.foodycourseapp.data.database.Entities.RecipesEntity
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
    suspend fun insertRecipe(recipesEntity: RecipesEntity){
        recipesDao.insertRecipes(recipesEntity)
    }

    suspend fun insertFavouriteRecipe(favouritesEntity: FavouritesEntity){
        recipesDao.insertFavoriteRecipe(favouritesEntity)
    }

    suspend fun deleteFavouriteRecipe(favouritesEntity: FavouritesEntity){
        recipesDao.deleteFavouriteRecipe(favouritesEntity)
    }

    suspend fun  deleteAllFavouriteRecipes(){
        recipesDao.deleteAllFavouriteRecipes()
    }
}