package art.muriuki.foodycourseapp.viewModels

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import art.muriuki.foodycourseapp.data.DataStoreRepository
import art.muriuki.foodycourseapp.util.Constants
import art.muriuki.foodycourseapp.util.Constants.Companion.DEFAULT_DIET_TYPE
import art.muriuki.foodycourseapp.util.Constants.Companion.DEFAULT_MEAL_TYPE
import art.muriuki.foodycourseapp.util.Constants.Companion.DEFAULT_RECIPES_NUMBER
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RecipesViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    private var mealType = DEFAULT_MEAL_TYPE
    private var dietType = DEFAULT_DIET_TYPE
    var networkStatus = false
    var backOnLine = false
    val readMealAndDietType = dataStoreRepository.readMealAndDietType
    val readBackOnline = dataStoreRepository.readBackOnline.asLiveData()

    fun saveMealAndDietType( mealType: String, mealTypeId: Int, dietType: String, dietTypeId: Int)=
        viewModelScope.launch(Dispatchers.IO) {
        dataStoreRepository.saveMealAndDietType(mealType, mealTypeId, dietType, dietTypeId)
    }

    fun saveBackonline(backOnline : Boolean) =
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveBackOnline(backOnline)
        }

    fun applyQueries(): HashMap<String, String>{
        viewModelScope.launch {
            readMealAndDietType.collect{value ->
                mealType = value.selectedMealType
                dietType = value.selectedDietType
            }
        }
        val queries : HashMap<String, String> = HashMap()
        queries[Constants.QUERY_API_KEY]= Constants.API_KEY
        queries[Constants.QUERY_NUMBER]=DEFAULT_RECIPES_NUMBER
        queries[Constants.QUERY_TYPE]=mealType
        queries[Constants.QUERY_DIET]=dietType
        queries[Constants.QUERY_ADD_RECIPE_INFORMATION]="true"
        queries[Constants.QUERY_FILL_INGREDIENTS]="true"
        return queries
    }

    fun applySearchQueries(searchQuery : String): HashMap<String, String>{
        val queries : HashMap<String, String> = HashMap()
        queries[Constants.QUERY_SEARCH]=searchQuery
        queries[Constants.QUERY_NUMBER]=DEFAULT_RECIPES_NUMBER
        queries[Constants.QUERY_API_KEY]= Constants.API_KEY
        queries[Constants.QUERY_ADD_RECIPE_INFORMATION]="true"
        queries[Constants.QUERY_FILL_INGREDIENTS]="true"
        return  queries
    }

    fun showNetworkStatus(){
        if(!networkStatus){
            Toast.makeText(context,"No Internet Connection",Toast.LENGTH_LONG).show()
            saveBackonline(true)
        }else if(networkStatus){
            if(backOnLine){
            Toast.makeText(context,"We are back online",Toast.LENGTH_LONG).show()
            saveBackonline(false)
            }
        }
    }
}
