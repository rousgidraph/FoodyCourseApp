package art.muriuki.foodycourseapp.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import art.muriuki.foodycourseapp.models.FoodRecipe
import art.muriuki.foodycourseapp.util.Constants.Companion.RECIPES_TABLE


@Entity(tableName = RECIPES_TABLE)
class RecipesEntity (var foodRecipe: FoodRecipe){
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}