package art.muriuki.foodycourseapp.ui

import android.os.Bundle
import android.telecom.Call.Details
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.navArgs
import art.muriuki.foodycourseapp.R
import art.muriuki.foodycourseapp.adapters.PagerAdapter
import art.muriuki.foodycourseapp.databinding.ActivityDetailsBinding
import art.muriuki.foodycourseapp.ui.fragments.ingredients.IngredientsFragment
import art.muriuki.foodycourseapp.ui.fragments.instructions.InstructionFragment
import art.muriuki.foodycourseapp.ui.fragments.overview.OverviewFragement
import art.muriuki.foodycourseapp.util.Constants.Companion.RECIPE_RESULT_KEY


class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding
    private val args by navArgs<DetailsActivityArgs>()

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
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId== android.R.id.home){
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}