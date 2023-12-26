package art.muriuki.foodycourseapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import art.muriuki.foodycourseapp.data.database.Entities.RecipesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipes(recipesEntity: RecipesEntity)


    @Query("SELECT * FROM recipes_table ORDER BY id ASC")
     fun readRecipes():Flow<List<RecipesEntity>>

}