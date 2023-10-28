package art.muriuki.foodycourseapp

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import art.muriuki.foodycourseapp.data.Repository
import art.muriuki.foodycourseapp.models.FoodRecipe
import art.muriuki.foodycourseapp.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val repository: Repository
) : ViewModel() {

    var recipesResponse : MutableLiveData<NetworkResult<FoodRecipe>> = MutableLiveData()


    fun getRecipes(queries: Map<String, String>) = viewModelScope.launch {
        getRecipesSafeCall(queries)
    }

    private suspend fun getRecipesSafeCall(queries: Map<String, String>) {
        recipesResponse.value = NetworkResult.Loading()
        if(hasInternetConnection()){
            try{
                val response = repository.remote.getRecipes(queries)
                recipesResponse.value = handleFoodrecipesResponse(response)
            }catch(e: Exception){
                recipesResponse.value = NetworkResult.Error("Recipes Not found")
            }
        }else{
            recipesResponse.value = NetworkResult.Error("No internet Connection")
        }
    }

    private fun handleFoodrecipesResponse(response: Response<FoodRecipe>): NetworkResult<FoodRecipe> {
        when{
            response.message().toString().contains("timeout") ->{
                return NetworkResult.Error("Timeout")
            }

            response.code() == 402 -> {
                return NetworkResult.Error("API key Limited")
            }
            response.body()!!.results.isNullOrEmpty() -> {
                return  NetworkResult.Error("Recipes not found.")
            }

            response.isSuccessful -> {
                val foodRecipe = response.body()
                return NetworkResult.Success(foodRecipe!!)
            }else -> {
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