package art.muriuki.foodycourseapp.adapters

import android.view.ActionMode
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import art.muriuki.foodycourseapp.R
import art.muriuki.foodycourseapp.data.database.entities.FavouritesEntity
import art.muriuki.foodycourseapp.databinding.FavouriteRecipeRowLayoutBinding
import art.muriuki.foodycourseapp.ui.fragments.favourites.FavouriteRecipesFragmentDirections
import art.muriuki.foodycourseapp.util.RecipesDiffUtil
import art.muriuki.foodycourseapp.viewModels.MainViewModel
import com.google.android.material.snackbar.Snackbar

class FavouriteRecipeAdapter(
    private val requireActivity: FragmentActivity,
    private val mainViewModel : MainViewModel
) : RecyclerView.Adapter<FavouriteRecipeAdapter.MyViewHolder>(), ActionMode.Callback {

    private var multiselection = false
    private var selectedRecipes = arrayListOf<FavouritesEntity>()
    private var favouritesRecipes = emptyList<FavouritesEntity>()
    private var myViewHolders = arrayListOf<MyViewHolder>()
    private lateinit var mActionMode: ActionMode
    private lateinit var rootView: View

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
        myViewHolders.add(holder)
        rootView=holder.itemView.rootView
        val currentRecipe = favouritesRecipes[position]
        holder.bind(currentRecipe)

        // on click listener
        holder.binding.favouriteRecipesRowLayout.setOnClickListener {
            if (multiselection) {
                applySelection(holder, currentRecipe)
            } else {
                val action =
                    FavouriteRecipesFragmentDirections.actionFavouriteRecipesFragmentToDetailsActivity(
                        currentRecipe.result
                    )
                holder.itemView.findNavController().navigate(action)
            }

        }

        // long click listener
        holder.binding.favouriteRecipesRowLayout.setOnLongClickListener {
            if (!multiselection) {
                multiselection = true
                requireActivity.startActionMode(this)
                applySelection(holder, currentRecipe)
                true
            } else {
                multiselection = false
                false
            }

        }


    }

    override fun getItemCount(): Int {
        return favouritesRecipes.size
    }


    private fun applySelection(holder: MyViewHolder, currentRecipe: FavouritesEntity) {
        if (selectedRecipes.contains(currentRecipe)) {
            selectedRecipes.remove(currentRecipe)
            changeRecipeStyle(holder, R.color.cardBackgroundColor, R.color.strokeColor)
            applyActionModeTitle()
        } else {
            selectedRecipes.add(currentRecipe)
            changeRecipeStyle(holder, R.color.cardBackgroundLightColor, R.color.colorPrimary)
            applyActionModeTitle()
        }
    }

    private fun changeRecipeStyle(holder: MyViewHolder, backgroundColor: Int, strokeColor: Int) {
        holder.binding.favouriteRecipesRowLayout.setBackgroundColor(
            ContextCompat.getColor(requireActivity, backgroundColor)
        )
        holder.binding.favouriteRowCardView.strokeColor =
            ContextCompat.getColor(requireActivity, strokeColor)
    }


    private fun applyActionModeTitle(){
        when(selectedRecipes.size){
            0 -> {
                mActionMode.finish()
            }
            1 -> {
                mActionMode.title="${selectedRecipes.size} item selected"
            }
            else -> {
                mActionMode.title="${selectedRecipes.size} items selected"
            }
        }
    }
    override fun onCreateActionMode(actionMode: ActionMode, menu: Menu?): Boolean {
        actionMode.menuInflater.inflate(R.menu.favourites_contexual_menu, menu)
        mActionMode = actionMode!!
        applyStatusBarColor(R.color.contexualStatusBarColor)
        return true
    }

    override fun onPrepareActionMode(actionMode: ActionMode?, menu: Menu?): Boolean {
        return true
    }

    override fun onActionItemClicked(actionMode: ActionMode?, menu: MenuItem?): Boolean {
        if(menu?.itemId == R.id.delete_favourite_recipe_menu){
            selectedRecipes.forEach{
                mainViewModel.deleteFavouriteRecipe(it)
            }
            showSnackBar("${selectedRecipes.size} Recipe/s removed")
            multiselection = false
            selectedRecipes.clear()
            actionMode?.finish()
        }
        return true
    }

    override fun onDestroyActionMode(actionMode: ActionMode?) {
        myViewHolders.forEach { holder ->
            changeRecipeStyle(holder, R.color.cardBackgroundColor, R.color.strokeColor)
        }
        multiselection = false
        selectedRecipes.clear()
        applyStatusBarColor(R.color.statusBarColor)
    }

    private fun applyStatusBarColor(color: Int) {
        requireActivity.window.statusBarColor = ContextCompat.getColor(requireActivity, color)
    }

    fun setData(newFavouritesEntity: List<FavouritesEntity>) {
        val favouritesEntityRecipesDiffUtil =
            RecipesDiffUtil(favouritesRecipes, newFavouritesEntity)
        val diffUtilResult = DiffUtil.calculateDiff(favouritesEntityRecipesDiffUtil)
        favouritesRecipes = newFavouritesEntity
        diffUtilResult.dispatchUpdatesTo(this)
    }


    private fun showSnackBar(message: String){
        Snackbar.make(
            rootView,
            message,
            Snackbar.LENGTH_SHORT
        ).setAction("Okay"){}.show()
    }

    fun clearContextualActionMode(){
        if(this::mActionMode.isInitialized){
            mActionMode.finish()
        }
    }
}