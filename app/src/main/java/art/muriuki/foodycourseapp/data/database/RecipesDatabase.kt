package art.muriuki.foodycourseapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import art.muriuki.foodycourseapp.data.database.Entities.RecipesEntity


@Database(entities = [RecipesEntity::class],
    version = 1,
    exportSchema = false)
@TypeConverters(RecipesTypeConverter::class)
abstract class  RecipesDatabase: RoomDatabase() {

    abstract fun recipesDao() : RecipesDao


}