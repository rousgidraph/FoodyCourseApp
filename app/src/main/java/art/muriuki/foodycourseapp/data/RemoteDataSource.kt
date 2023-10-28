package art.muriuki.foodycourseapp.data

import art.muriuki.foodycourseapp.data.network.FoodRecipesApi
import art.muriuki.foodycourseapp.models.FoodRecipe
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val foodRecipesApi: FoodRecipesApi
) {

    suspend fun getRecipes(queries: Map<String, String>):Response< FoodRecipe>{
        return foodRecipesApi.getRecipes(queries)

    }

}