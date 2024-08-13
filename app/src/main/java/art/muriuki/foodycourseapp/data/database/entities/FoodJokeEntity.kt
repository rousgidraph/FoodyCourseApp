package art.muriuki.foodycourseapp.data.database.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import art.muriuki.foodycourseapp.models.FoodJoke
import art.muriuki.foodycourseapp.util.Constants


@Entity(tableName = Constants.FOOD_JOKE_TABLE)
class FoodJokeEntity (
    @Embedded
    var foodJoke: FoodJoke
){
    @PrimaryKey(autoGenerate = false)
    var id : Int =0


}