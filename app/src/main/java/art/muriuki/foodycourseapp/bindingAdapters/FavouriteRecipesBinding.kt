package art.muriuki.foodycourseapp.bindingAdapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import art.muriuki.foodycourseapp.adapters.FavouriteRecipeAdapter
import art.muriuki.foodycourseapp.data.database.entities.FavouritesEntity

class FavouriteRecipesBinding {
    companion object{

        @BindingAdapter("viewVisibility","setData", requireAll = false)
        @JvmStatic
        fun setDataAndViewVisibility(
            view: View,
            favouritesEntity: LiveData<List<FavouritesEntity>>?,
            mAdapter: FavouriteRecipeAdapter?){
            val data = favouritesEntity?.value
            if(data.isNullOrEmpty()){
                when(view){
                    is ImageView ->{
                        view.visibility = View.VISIBLE
                    }
                    is TextView -> {
                        view.visibility = View.VISIBLE
                    }
                    is RecyclerView ->{
                        view.visibility = View.INVISIBLE
                    }
                }
            }else{
                when(view){
                    is ImageView ->{
                        view.visibility = View.INVISIBLE
                    }
                    is TextView -> {
                        view.visibility = View.INVISIBLE
                    }
                    is RecyclerView ->{
                        view.visibility = View.VISIBLE
                        mAdapter?.setData(data)
                    }
                }
            }

        }
    }
}