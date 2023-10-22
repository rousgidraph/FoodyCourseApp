package art.muriuki.foodycourseapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import art.muriuki.foodycourseapp.databinding.FragmentFavouriteRecipesBinding


class FavouriteRecipesFragment : Fragment() {

    private var _binding: FragmentFavouriteRecipesBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFavouriteRecipesBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}