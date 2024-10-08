package art.muriuki.foodycourseapp.viewModels

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import art.muriuki.foodycourseapp.data.Repository
import art.muriuki.foodycourseapp.data.database.entities.FavouritesEntity
import art.muriuki.foodycourseapp.data.database.entities.FoodJokeEntity
import art.muriuki.foodycourseapp.data.database.entities.RecipesEntity
import art.muriuki.foodycourseapp.models.FoodJoke
import art.muriuki.foodycourseapp.models.FoodRecipe
import art.muriuki.foodycourseapp.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val repository: Repository
) : ViewModel() {

    /** Room Database */

    var readRecipes: LiveData<List<RecipesEntity>> = repository.local.readRecipes().asLiveData()

    var readFavoriteRecipes: LiveData<List<FavouritesEntity>> = repository.local.readFavouritesRecipes().asLiveData()
    var readFoodjoke : LiveData<List<FoodJokeEntity>> = repository.local.readFoodJoke().asLiveData()


    private fun insertRecipes(recipesEntity: RecipesEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertRecipe(recipesEntity)
        }

     fun insertFavouriteRecipe(favouritesEntity: FavouritesEntity)=
            viewModelScope.launch(Dispatchers.IO) {
                repository.local.insertFavouriteRecipe(favouritesEntity)
            }


    fun insertFoodJoke(foodJokeEntity: FoodJokeEntity){
        viewModelScope.launch (Dispatchers.IO){
            repository.local.insertFoodJoke(foodJokeEntity) }
    }
    fun deleteFavouriteRecipe(favouritesEntity: FavouritesEntity)=
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.deleteFavouriteRecipe(favouritesEntity)
        }

   fun deleteAllFavouriteRecipe()=
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.deleteAllFavouriteRecipes()
        }

    /** RETROFIT */
    var recipesResponse: MutableLiveData<NetworkResult<FoodRecipe>> = MutableLiveData()
    var searchedRecipesResponse : MutableLiveData<NetworkResult<FoodRecipe>> = MutableLiveData()
    var foodJokeResponse : MutableLiveData<NetworkResult<FoodJoke>> = MutableLiveData()
    fun getRecipes(queries: Map<String, String>) = viewModelScope.launch {
        getRecipesSafeCall(queries)
    }

    fun getFoodJoke(apiKey: String ) = viewModelScope.launch{
        getFoodJokeSafeCall(apiKey)
    }

    fun searchRecipes(searchQuery: Map<String, String>) = viewModelScope.launch {
        searchRecipesSafeCall(searchQuery)
    }



    private suspend fun getRecipesSafeCall(queries: Map<String, String>) {
        recipesResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = repository.remote.getRecipes(queries)
                recipesResponse.value = handleFoodrecipesResponse(response)

                val foodRecipe = recipesResponse.value!!.data
                if (foodRecipe != null) {
                    offlineCacheRecipes(foodRecipe)
                }
            } catch (e: Exception) {
                recipesResponse.value = NetworkResult.Error("Recipes Not found")
            }
        } else {
            recipesResponse.value = NetworkResult.Error("No internet Connection")
        }
    }

    private suspend fun searchRecipesSafeCall(searchQuery: Map<String, String>) {
        searchedRecipesResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = repository.remote.searchRecipes(searchQuery)
                searchedRecipesResponse.value = handleFoodrecipesResponse(response)

            } catch (e: Exception) {
                searchedRecipesResponse.value = NetworkResult.Error("Recipes Not found")
            }
        } else {
            searchedRecipesResponse.value = NetworkResult.Error("No internet Connection")
        }
    }


    private suspend fun getFoodJokeSafeCall(apiKey: String) {
        foodJokeResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = repository.remote.getFoodJoke(apiKey)
                foodJokeResponse.value = handleFoodJokeResponse(response)
                val foodjoke = foodJokeResponse.value!!.data
                if(foodjoke != null){
                    offlineCacheFoodJoke(foodjoke)
                }
            } catch (e: Exception) {
                foodJokeResponse.value = NetworkResult.Error("FoodJoke Not found")
            }
        } else {
            foodJokeResponse.value = NetworkResult.Error("No internet Connection")
        }
    }

    private fun offlineCacheRecipes(foodRecipe: FoodRecipe) {
        val recipesEntity = RecipesEntity(foodRecipe)
        insertRecipes(recipesEntity)
    }

    private fun offlineCacheFoodJoke(foodJoke: FoodJoke) {
        val foodjokeEntity = FoodJokeEntity(foodJoke)
        insertFoodJoke(foodjokeEntity)
    }

    private fun handleFoodrecipesResponse(response: Response<FoodRecipe>): NetworkResult<FoodRecipe> {
        when {
            response.message().toString().contains("timeout") -> {
                return NetworkResult.Error("Timeout")
            }

            response.code() == 402 -> {
                return NetworkResult.Error("API key Limited")
            }

            response.body()!!.results.isNullOrEmpty() -> {
                return NetworkResult.Error("Recipes not found.")
            }

            response.isSuccessful -> {
                val foodRecipe = response.body()
                return NetworkResult.Success(foodRecipe!!)
            }

            else -> {
                return NetworkResult.Error(response.message())
            }
        }
    }


    private fun handleFoodJokeResponse(response: Response<FoodJoke>): NetworkResult<FoodJoke> {
        when {
            response.message().toString().contains("timeout") -> {
                return NetworkResult.Error("Timeout")
            }
            response.code() == 402 -> {
                return NetworkResult.Error("API key Limited")
            }
            response.isSuccessful -> {
                val foodJoke = response.body()
                return NetworkResult.Success(foodJoke!!)
            }
            else -> {
                return NetworkResult.Error(response.message())
            }
        }
    }

    private fun hasInternetConnection(): Boolean {

        val connectivityManager = context.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager

        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false

        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }


    }
}