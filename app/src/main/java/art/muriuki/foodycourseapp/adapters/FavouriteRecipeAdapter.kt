package art.muriuki.foodycourseapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import art.muriuki.foodycourseapp.data.database.entities.FavouritesEntity
import art.muriuki.foodycourseapp.databinding.FavouriteRecipeRowLayoutBinding
import art.muriuki.foodycourseapp.models.Result
import art.muriuki.foodycourseapp.ui.fragments.favourites.FavouriteRecipesFragmentDirections
import art.muriuki.foodycourseapp.util.RecipesDiffUtil

class FavouriteRecipeAdapter : RecyclerView.Adapter<FavouriteRecipeAdapter.MyViewHolder>() {

    private var favouritesRecipes = emptyList<FavouritesEntity>()

    class MyViewHolder(private val binding: FavouriteRecipeRowLayoutBinding):
        RecyclerView.ViewHolder(binding.root) {
            fun bind(favouritesEntity: FavouritesEntity) {
                binding.favouritesEntity = favouritesEntity
                binding.executePendingBindings()

            }

        /**
         * Single click listener
         * */
        fun setOnClick(result: Result,holder: MyViewHolder) {
            binding.favouriteRecipesRowLayout.setOnClickListener {
                val action =
                    FavouriteRecipesFragmentDirections.actionFavouriteRecipesFragmentToDetailsActivity(result)
                holder.itemView.findNavController().navigate(action)
            }
        }

        companion object{
            fun from(parent: ViewGroup): MyViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = FavouriteRecipeRowLayoutBinding.inflate(layoutInflater,parent,false)
                return MyViewHolder(binding)
            }
        }
        }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavouriteRecipeAdapter.MyViewHolder {
       return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       val selectedRecipe = favouritesRecipes[position]
        holder.bind(selectedRecipe)
        holder.setOnClick(selectedRecipe.result,holder)

    }

    override fun getItemCount(): Int {
       return favouritesRecipes.size
    }

    fun setData(newFavouritesEntity: List<FavouritesEntity>){
        val favouritesEntityRecipesDiffUtil = RecipesDiffUtil(favouritesRecipes,newFavouritesEntity)
        val diffUtilResult = DiffUtil.calculateDiff(favouritesEntityRecipesDiffUtil)
        favouritesRecipes = newFavouritesEntity
        diffUtilResult.dispatchUpdatesTo(this)
    }
}