package art.muriuki.foodycourseapp.ui.fragments.recipes

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import art.muriuki.foodycourseapp.viewModels.MainViewModel
import art.muriuki.foodycourseapp.adapters.RecipesAdapter
import art.muriuki.foodycourseapp.databinding.FragmentRecipesBinding
import art.muriuki.foodycourseapp.util.Constants
import art.muriuki.foodycourseapp.util.Constants.Companion.API_KEY
import art.muriuki.foodycourseapp.util.NetworkResult
import art.muriuki.foodycourseapp.util.observeOnce
import kotlinx.coroutines.launch


class RecipesFragment : Fragment() {


    lateinit var mainViewModel: MainViewModel
    private val mAdapter by lazy { RecipesAdapter() }
    private var _binding: FragmentRecipesBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentRecipesBinding.inflate(inflater, container, false)
        val view = binding.root

        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        setUpRecyclerView()
        readDatabase()
        return view

    }

    private fun setUpRecyclerView(){
        binding.recyclerView.adapter = mAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        showShimmerEffect()
    }

    private fun readDatabase() {
        lifecycleScope.launch {
            mainViewModel.readRecipes.observeOnce( viewLifecycleOwner) { database ->
                if (database.isNotEmpty()) {
                    Log.d("RecipesFragment", "readDatabase called ")
                    mAdapter.setData(database[0].foodRecipe)
                    hideShimmerEffect()
                } else {
                    requestApiData()
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun requestApiData(){
        Log.d("RecipesFragment", "requestApiData called ")
        mainViewModel.getRecipes(applyQueries())
        mainViewModel.recipesResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    hideShimmerEffect()
                    response.data?.let {
                        mAdapter.setData(it)
                    }
                }

                is NetworkResult.Error -> {
                    hideShimmerEffect()
                    loadDataFromCache()
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()

                }

                is NetworkResult.Loading ->{
                    showShimmerEffect()
                }
            }
        }
    }


    private fun applyQueries(): HashMap<String, String>{
        val queries : HashMap<String, String> = HashMap()

        queries[Constants.QUERY_API_KEY]=API_KEY
        queries[Constants.QUERY_NUMBER]="50"
        queries[Constants.QUERY_TYPE]="snack"
        queries[Constants.QUERY_DIET]="vegan"
        queries[Constants.QUERY_ADD_RECIPE_INFORMATION]="true"
        queries[Constants.QUERY_FILL_INGREDIENTS]="true"

        return queries
    }

    private fun showShimmerEffect() {
        binding.recyclerView.showShimmer()
    }

    private fun loadDataFromCache(){
        lifecycleScope.launch {
            mainViewModel.readRecipes.observe(viewLifecycleOwner) { database ->
                if (database.isNotEmpty()) {
                    mAdapter.setData(database[0].foodRecipe)
                }
            }
        }
    }

    private fun hideShimmerEffect() {
        binding.recyclerView.hideShimmer()
    }


}