package art.muriuki.foodycourseapp.data

import art.muriuki.foodycourseapp.data.network.FoodRecipesApi
import art.muriuki.foodycourseapp.models.FoodJoke
import art.muriuki.foodycourseapp.models.FoodRecipe
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val foodRecipesApi: FoodRecipesApi
) {

    suspend fun getRecipes(queries: Map<String, String>):Response< FoodRecipe>{
        return foodRecipesApi.getRecipes(queries)

    }


    suspend fun searchRecipes(searchQuery: Map<String, String>):Response< FoodRecipe>{
        return foodRecipesApi.searchRecipe(searchQuery)
    }

    suspend fun getFoodJoke(apiKey: String):Response<FoodJoke>{
        return foodRecipesApi.getFoodJoke(apiKey)
    }
}