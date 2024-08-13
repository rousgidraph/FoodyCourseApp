package art.muriuki.foodycourseapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import art.muriuki.foodycourseapp.data.database.entities.FavouritesEntity
import art.muriuki.foodycourseapp.data.database.entities.FoodJokeEntity
import art.muriuki.foodycourseapp.data.database.entities.RecipesEntity


@Database(entities = [RecipesEntity::class, FavouritesEntity::class, FoodJokeEntity::class],
    version = 1,
    exportSchema = false)
@TypeConverters(RecipesTypeConverter::class)
abstract class  RecipesDatabase: RoomDatabase() {

    abstract fun recipesDao() : RecipesDao


}