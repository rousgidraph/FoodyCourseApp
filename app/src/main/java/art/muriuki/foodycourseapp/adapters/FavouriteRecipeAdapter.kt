package art.muriuki.foodycourseapp.adapters

import android.content.Context
import android.view.ActionMode
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import art.muriuki.foodycourseapp.R
import art.muriuki.foodycourseapp.data.database.entities.FavouritesEntity
import art.muriuki.foodycourseapp.databinding.FavouriteRecipeRowLayoutBinding
import art.muriuki.foodycourseapp.models.Result
import art.muriuki.foodycourseapp.ui.fragments.favourites.FavouriteRecipesFragmentDirections
import art.muriuki.foodycourseapp.util.RecipesDiffUtil

class FavouriteRecipeAdapter(
    private val requireActivity: FragmentActivity
) : RecyclerView.Adapter<FavouriteRecipeAdapter.MyViewHolder>(), ActionMode.Callback {

    private var multiselection = false
    private var selectedRecipes = arrayListOf<FavouritesEntity>()
    private var favouritesRecipes = emptyList<FavouritesEntity>()

    class MyViewHolder(val binding: FavouriteRecipeRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(favouritesEntity: FavouritesEntity) {

            binding.favouritesEntity = favouritesEntity
            binding.executePendingBindings()

        }


        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = FavouriteRecipeRowLayoutBinding.inflate(layoutInflater, parent, false)
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

        // on click listener
        holder.binding.favouriteRecipesRowLayout.setOnClickListener {
            val action =
                FavouriteRecipesFragmentDirections.actionFavouriteRecipesFragmentToDetailsActivity(
                    selectedRecipe.result
                )
            holder.itemView.findNavController().navigate(action)
        }

        // long click listener
        holder.binding.favouriteRecipesRowLayout.setOnLongClickListener {
            requireActivity.startActionMode(this)
            true
        }


    }

    override fun getItemCount(): Int {
        return favouritesRecipes.size
    }


    private fun applySelection(holder : MyViewHolder, currentRecipe: FavouritesEntity){
        if(selectedRecipes.contains(currentRecipe)){
            selectedRecipes.remove(currentRecipe)
        }else{
            selectedRecipes.add(currentRecipe)
        }
    }

    private fun changeRecipeStyle(holder: MyViewHolder, backgroundColor: Int , strokeColor: Int){
        holder.binding.favouriteRecipesRowLayout
    }


    override fun onCreateActionMode(actionMode: ActionMode, menu: Menu?): Boolean {
        actionMode.menuInflater.inflate(R.menu.favourites_contexual_menu, menu)
        applyStatusBarColor(R.color.contexualStatusBarColor)
        return true
    }

    override fun onPrepareActionMode(actionMode: ActionMode?, menu: Menu?): Boolean {
       return true
    }

    override fun onActionItemClicked(actionMode: ActionMode?, item: MenuItem?): Boolean {
        return true
    }

    override fun onDestroyActionMode(actionMode: ActionMode?) {
        applyStatusBarColor(R.color.statusBarColor)
    }

    private fun applyStatusBarColor(color:Int){
        requireActivity.window.statusBarColor = ContextCompat.getColor(requireActivity,color)
    }
    fun setData(newFavouritesEntity: List<FavouritesEntity>) {
        val favouritesEntityRecipesDiffUtil =
            RecipesDiffUtil(favouritesRecipes, newFavouritesEntity)
        val diffUtilResult = DiffUtil.calculateDiff(favouritesEntityRecipesDiffUtil)
        favouritesRecipes = newFavouritesEntity
        diffUtilResult.dispatchUpdatesTo(this)
    }
}