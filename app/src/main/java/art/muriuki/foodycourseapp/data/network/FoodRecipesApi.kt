package art.muriuki.foodycourseapp.data.network

import art.muriuki.foodycourseapp.models.FoodJoke
import art.muriuki.foodycourseapp.models.FoodRecipe
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface FoodRecipesApi {

    @GET("/recipes/complexSearch")
    suspend fun getRecipes(
        @QueryMap queries: Map<String,String>
    ): Response<FoodRecipe>

    @GET("/recipes/complexSearch")
    suspend fun searchRecipe(
        @QueryMap searchQuery: Map<String,String>
    ): Response<FoodRecipe>


    @GET("/food/jokes/random")
    suspend fun getFoodJoke(
       @Query("apiKey") apiKey: String
    ): Response<FoodJoke>
}