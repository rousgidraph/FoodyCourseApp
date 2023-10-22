package art.muriuki.foodycourseapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import art.muriuki.foodycourseapp.databinding.FragmentRecipesBinding

class recipesFragment : Fragment() {

    private var _binding: FragmentRecipesBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentRecipesBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.recyclerView.showShimmer() // TODO: this was demo purposes
        return view

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}