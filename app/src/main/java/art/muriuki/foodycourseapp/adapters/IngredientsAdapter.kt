package art.muriuki.foodycourseapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import art.muriuki.foodycourseapp.R
import art.muriuki.foodycourseapp.databinding.IngredientsRowLayoutBinding
import art.muriuki.foodycourseapp.models.ExtendedIngredient
import art.muriuki.foodycourseapp.util.Constants.Companion.BASE_IMAGE_URL
import art.muriuki.foodycourseapp.util.RecipesDiffUtil
import coil.load
import java.util.Locale

class IngredientsAdapter : RecyclerView.Adapter<IngredientsAdapter.MyViewHolder>(){

    private var ingredientsList = emptyList<ExtendedIngredient>()
//    class MyViewHolder(itemView: IngredientsRowLayoutBinding) : RecyclerView.ViewHolder(itemView.root)
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
//        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.ingredients_row_layout,parent,false))

        val binding = IngredientsRowLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding.root)

    }

    override fun getItemCount(): Int {
        return ingredientsList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val binding = IngredientsRowLayoutBinding.bind(holder.itemView)
        binding.ingredientsImageView.load(BASE_IMAGE_URL + ingredientsList[position].image){
            crossfade(600)
            error(R.drawable.ic_error_placeholder)
        }
        binding.ingredientName.text= ingredientsList[position].name.capitalize(Locale.ROOT)
        binding.ingredientAmount.text = ingredientsList[position].amount.toString()
        binding.ingredientUnit.text = ingredientsList[position].unit
        binding.ingredientConsistency.text = ingredientsList[position].consistency
        binding.ingredientOriginal.text = ingredientsList[position].original

//        holder.itemView.ingredient_imageView.load(BASE_IMAGE_URL + ingredientsList[position].image)
    }

    fun setData(newIngredient : List<ExtendedIngredient>){
        val ingredientRecipesDiffUtil = RecipesDiffUtil(ingredientsList,newIngredient)
        var diffUtilResult  = DiffUtil.calculateDiff(ingredientRecipesDiffUtil)
        ingredientsList = newIngredient
        diffUtilResult.dispatchUpdatesTo(this)
    }
}