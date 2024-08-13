package art.muriuki.foodycourseapp.data

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.preferences.Preferences
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.emptyPreferences
import androidx.datastore.preferences.preferencesKey
import art.muriuki.foodycourseapp.util.Constants
import art.muriuki.foodycourseapp.util.Constants.Companion.PREFERENCES_BACK_ONLINE
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject


@ActivityRetainedScoped
class DataStoreRepository @Inject constructor(@ApplicationContext private val context: Context) {

    private object PreferenceKeys {

        val selectedMealType = preferencesKey<String>(Constants.PREFERENCES_MEAL_TYPE)
        val selectedMealTypeID = preferencesKey<Int>(Constants.PREFERENCES_MEAL_TYPE_ID)
        val selectedDietType = preferencesKey<String>(Constants.PREFERENCES_DIET_TYPE)
        val selectedDietTypeId = preferencesKey<Int>(Constants.PREFERENCES_DIET_TYPE_ID)
        val backOnLine = preferencesKey<Boolean>(PREFERENCES_BACK_ONLINE)

    }


    private val dataStore: DataStore<Preferences> = context.createDataStore(
        name = Constants.PREFERENCES_NAME
    )

    suspend fun saveMealAndDietType(
        mealType: String,
        mealTypeId: Int,
        dietType: String,
        dietTypeId: Int
    ) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.selectedMealType] = mealType
            preferences[PreferenceKeys.selectedMealTypeID] = mealTypeId
            preferences[PreferenceKeys.selectedDietType] = dietType
            preferences[PreferenceKeys.selectedDietTypeId] = dietTypeId
        }
    }

    suspend fun saveBackOnline(backOnline: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.backOnLine] = backOnline
        }
    }

    val readMealAndDietType: Flow<MealAndDietType> = dataStore.data.catch { exception ->
        if (exception is IOException) {
            emit(emptyPreferences())
        } else {
            throw exception
        }

    }
        .map { preferences ->
            val selectedMealType =
                preferences[PreferenceKeys.selectedMealType] ?: Constants.DEFAULT_MEAL_TYPE
            val selectedMealTypeId = preferences[PreferenceKeys.selectedMealTypeID] ?: 0
            val selectedDietType =
                preferences[PreferenceKeys.selectedDietType] ?: Constants.DEFAULT_DIET_TYPE
            val selectedDietTypeId = preferences[PreferenceKeys.selectedDietTypeId] ?: 0
            MealAndDietType(
                selectedMealType,
                selectedMealTypeId,
                selectedDietType,
                selectedDietTypeId
            )
        }

    val readBackOnline: Flow<Boolean> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            val backOnline = preferences[PreferenceKeys.backOnLine] ?: false
            backOnline
        }

    data class MealAndDietType(
        val selectedMealType: String,
        val selectedMealTypeId: Int,
        val selectedDietType: String,
        val selectedDietTypeId: Int,
    )


}