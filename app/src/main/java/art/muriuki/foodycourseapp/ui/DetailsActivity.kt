package art.muriuki.foodycourseapp.ui

import android.os.Bundle
import android.telecom.Call.Details
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.navArgs
import art.muriuki.foodycourseapp.R
import art.muriuki.foodycourseapp.adapters.PagerAdapter
import art.muriuki.foodycourseapp.data.database.Entities.FavouritesEntity
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
        return  true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId== android.R.id.home){
            finish()
        }else if (item.itemId== R.id.save_to_favourites_menu){
            saveToFavourites(item)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveToFavourites(item: MenuItem) {
        var favouritesEntity = FavouritesEntity(0,args.Result)
        mainViewModel.insertFavouriteRecipe(favouritesEntity)
        changeMenuItemColor(item, R.color.yellow)
        showSnackBar("RecipeSaved")
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