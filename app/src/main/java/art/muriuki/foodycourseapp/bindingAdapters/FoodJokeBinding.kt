package art.muriuki.foodycourseapp.bindingAdapters

import android.media.Image
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import art.muriuki.foodycourseapp.data.database.entities.FoodJokeEntity
import art.muriuki.foodycourseapp.models.FoodJoke
import art.muriuki.foodycourseapp.util.NetworkResult
import com.google.android.material.card.MaterialCardView

class FoodJokeBinding {
    companion object {

        @BindingAdapter("readApiResponse3", "readDatabase3", requireAll = false)
        @JvmStatic
        fun setCardAndProgressVisibility(view: View, apiResponse: NetworkResult<FoodJoke>?, database: List<FoodJokeEntity>?
        ) {
            Log.d("Binding adapter", "api response : "+apiResponse+" database : "+database)
            when (apiResponse) {


                is NetworkResult.Error -> {
                    when (view) {

                        is ProgressBar -> {
                            view.visibility = View.INVISIBLE
                        }

                        is MaterialCardView -> {
                            view.visibility = View.VISIBLE
                            if (database != null) {
                                if (database.isEmpty()) {
                                    view.visibility = View.INVISIBLE
                                }
                            }
                        }
                    }
                }

                is NetworkResult.Success -> {
                    when(view){
                        is ProgressBar -> {

                            view.visibility = View.INVISIBLE
                        }
                        is MaterialCardView -> {

                            view.visibility = View.VISIBLE
                        }
                    }
                }

                is NetworkResult.Loading -> {
                    when (view) {
                        is ProgressBar -> {

                            view.visibility = View.VISIBLE
                        }

                        is MaterialCardView -> {

                            view.visibility = View.INVISIBLE
                        }
                        is ImageView -> {

                            view.visibility = View.INVISIBLE
                        }
                    }
                }
                else -> {

                }
            }
        }

        @BindingAdapter("readApiResponse4", "readDatabase4", requireAll = false)
        @JvmStatic
        fun setErrorViewsVisibility(
            view: View,
            apiResponse: NetworkResult<FoodJoke>?,
            database: List<FoodJokeEntity>?
        ){
            when (apiResponse) {
                is NetworkResult.Error -> {
                    when (view) {
                        is ImageView -> {
                            view.visibility = View.VISIBLE
                            if (database != null) {
                                if (database.isNotEmpty()) {
                                    view.visibility = View.INVISIBLE
                                }
                            }
                        }
                        is TextView -> {
                            view.visibility = View.VISIBLE
                            view.text = apiResponse.message
                            if (database != null) {
                                if (database.isNotEmpty()) {
                                    view.visibility = View.INVISIBLE
                                }
                            }
                        }
                    }
                }
                is NetworkResult.Loading -> {
                    view.visibility = View.INVISIBLE
                }
                is NetworkResult.Success -> {
                    view.visibility = View.INVISIBLE
                }
                null -> {

                }
            }
        }
    }
}