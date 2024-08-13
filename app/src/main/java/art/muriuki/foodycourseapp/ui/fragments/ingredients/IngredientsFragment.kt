package art.muriuki.foodycourseapp.ui.fragments.ingredients

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import art.muriuki.foodycourseapp.R
import art.muriuki.foodycourseapp.adapters.IngredientsAdapter
import art.muriuki.foodycourseapp.databinding.FragmentIngredientsBinding
import art.muriuki.foodycourseapp.models.Result
import art.muriuki.foodycourseapp.util.Constants.Companion.RECIPE_RESULT_KEY


class IngredientsFragment : Fragment() {

    private val mAdapter : IngredientsAdapter by lazy {
        IngredientsAdapter()
    }
    private var _binding: FragmentIngredientsBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentIngredientsBinding.inflate(inflater, container, false)

        val args = arguments
        val myBundle: Result? = args?.getParcelable(RECIPE_RESULT_KEY)
        setUpRecyclerView(binding)
        myBundle?.extendedIngredients?.let { mAdapter.setData(it) }

        return binding.root
    }

    fun setUpRecyclerView(view: FragmentIngredientsBinding){
        view.ingredientsRecyclerView.adapter = mAdapter
        view.ingredientsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

}