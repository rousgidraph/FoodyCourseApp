package art.muriuki.foodycourseapp.data.database.Entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import art.muriuki.foodycourseapp.models.Result
import art.muriuki.foodycourseapp.util.Constants.Companion.FAVOURITE_RECIPES_TABLE

@Entity(tableName = FAVOURITE_RECIPES_TABLE)
class FavouritesEntity (
    @PrimaryKey(autoGenerate = true)""
    var id: Int ,
    var result: Result
)