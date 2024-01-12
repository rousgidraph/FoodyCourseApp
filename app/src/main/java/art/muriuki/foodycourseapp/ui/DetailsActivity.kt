package art.muriuki.foodycourseapp.ui

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.navArgs
import art.muriuki.foodycourseapp.R
import art.muriuki.foodycourseapp.adapters.PagerAdapter
import art.muriuki.foodycourseapp.data.database.entities.FavouritesEntity
import art.muriuki.foodycourseapp.databinding.ActivityDetailsBinding
import art.muriuki.foodycourseapp.ui.fragments.ingredients.IngredientsFragment
import art.muriuki.foodycourseapp.ui.fragments.instructions.InstructionFragment
import art.muriuki.foodycourseapp.ui.fragments.overview.OverviewFragement
import art.muriuki.foodycourseapp.util.Constants.Companion.RECIPE_RESULT_KEY
import art.muriuki.foodycourseapp.viewModels.MainViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding
    private val args by navArgs<DetailsActivityArgs>()
    private val mainViewModel: MainViewModel by viewModels()
    private var recipeSaved= false
    private var savedRecipeId = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        val root = binding.root
        setContentView(root)
        setSupportActionBar(binding.toolbar)
        binding.toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val fragments =  ArrayList<Fragment>()
        fragments.add(OverviewFragement())
        fragments.add(IngredientsFragment())
        fragments.add(InstructionFragment())

        val title = ArrayList<String>()
        title.add("Overview")
        title.add("Ingredients")
        title.add("Instructions")

        val resultBundle = Bundle()
        resultBundle.putParcelable(RECIPE_RESULT_KEY,args.Result)

        val adapter = PagerAdapter(resultBundle=resultBundle, fragments = fragments, title= title, fm= supportFragmentManager)
        binding.viewPager.adapter = adapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)

//        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.details_menu,menu)
        val menuItem = menu?.findItem(R.id.save_to_favourites_menu)
        changeMenuItemColor(menuItem!!,R.color.white)
        checkSavedRecipe(menuItem!!)
        return  true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId== android.R.id.home){
            finish()
        }else if (item.itemId== R.id.save_to_favourites_menu && !recipeSaved){
            saveToFavourites(item)
        }else if(item.itemId== R.id.save_to_favourites_menu && recipeSaved){
            removeFromFavourites(item)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun checkSavedRecipe(menuItem: MenuItem) {
        mainViewModel.readFavoriteRecipes.observe(this) { favouritesEntity ->
            try {
                for (savedRecipe in favouritesEntity) {
                    if (savedRecipe.result.recipeId == args.Result.recipeId) {
                        changeMenuItemColor(menuItem, R.color.yellow)
                        savedRecipeId = savedRecipe.id
                        recipeSaved = true
                    }
                }
            } catch (e: Exception) {
                Log.d("detailsActivity", "checkSavedRecipe: " + e.message.toString())
            }
        }

    }

    private fun saveToFavourites(item: MenuItem) {
        var favouritesEntity = FavouritesEntity(0,args.Result)
        mainViewModel.insertFavouriteRecipe(favouritesEntity)
        changeMenuItemColor(item, R.color.yellow)
        showSnackBar("RecipeSaved")
        recipeSaved = true
    }

    private fun removeFromFavourites(item: MenuItem){
        var favouritesEntity = FavouritesEntity(savedRecipeId,args.Result)
        mainViewModel.deleteFavouriteRecipe(favouritesEntity)
        changeMenuItemColor(item,R.color.white)
        showSnackBar("Removed From Favourites")
        recipeSaved = false
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(
            binding.detailsLayout,
            message,
            Snackbar.LENGTH_SHORT
        ).setAction("Okay"){}.show()
    }

    private fun changeMenuItemColor(item: MenuItem, color: Int) {
        item.icon?.setTint(ContextCompat.getColor(this, color))
    }
}