package art.muriuki.foodycourseapp.ui.fragments.favourites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import art.muriuki.foodycourseapp.R
import art.muriuki.foodycourseapp.adapters.FavouriteRecipeAdapter
import art.muriuki.foodycourseapp.databinding.FragmentFavouriteRecipesBinding
import art.muriuki.foodycourseapp.viewModels.MainViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouriteRecipesFragment : Fragment() {

    private val mainViewModel : MainViewModel by viewModels()

    private val mAdapter: FavouriteRecipeAdapter by lazy { FavouriteRecipeAdapter(requireActivity(),mainViewModel) }

    private var _binding: FragmentFavouriteRecipesBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFavouriteRecipesBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.lifecycleOwner = this
        binding.mainViewModel = mainViewModel
        binding.mAdapter = mAdapter

        setHasOptionsMenu(true)
        setUpRecyclerView(binding.favouriteRecipesRecyclerView)
//        mainViewModel.readFavoriteRecipes.observe(viewLifecycleOwner){
//            mAdapter.setData(it)
//        }

        return view
    }

    private fun setUpRecyclerView(recyclerView: RecyclerView){
        recyclerView.adapter = mAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())


    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        mAdapter.clearContextualActionMode()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.deleteall_favourite_recipes_menu){
            mainViewModel.deleteAllFavouriteRecipe()
            showSnackBar("All recipes removed")
        }
        return super.onOptionsItemSelected(item)
    }
    private fun showSnackBar(message: String){
        Snackbar.make(
            binding.root,
            message,
            Snackbar.LENGTH_SHORT
        ).setAction("Okay"){}.show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.favourites_recipes_menu,menu)
    }
}