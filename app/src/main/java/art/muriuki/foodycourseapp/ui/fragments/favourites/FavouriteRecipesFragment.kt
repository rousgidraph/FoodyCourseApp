package art.muriuki.foodycourseapp.ui.fragments.favourites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import art.muriuki.foodycourseapp.adapters.FavouriteRecipeAdapter
import art.muriuki.foodycourseapp.databinding.FragmentFavouriteRecipesBinding
import art.muriuki.foodycourseapp.viewModels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouriteRecipesFragment : Fragment() {

    private val mAdapter: FavouriteRecipeAdapter by lazy { FavouriteRecipeAdapter() }

    private val mainViewModel : MainViewModel by viewModels()

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
    }
}