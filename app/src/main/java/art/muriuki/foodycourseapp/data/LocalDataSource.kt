package art.muriuki.foodycourseapp.data

import art.muriuki.foodycourseapp.data.database.RecipesDao
import art.muriuki.foodycourseapp.data.database.RecipesEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val recipesDao : RecipesDao
) {

     fun readDatabase(): Flow<List<RecipesEntity>> {
        return recipesDao.readRecipes()
    }
    suspend fun insertRecipes(recipesEntity: RecipesEntity){
        recipesDao.insertRecipes(recipesEntity)
    }

}