package art.muriuki.foodycourseapp.ui.fragments.overview

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import art.muriuki.foodycourseapp.R
import art.muriuki.foodycourseapp.databinding.FragmentOverviewFragementBinding
import art.muriuki.foodycourseapp.models.Result
import art.muriuki.foodycourseapp.util.Constants.Companion.RECIPE_RESULT_KEY
import coil.load
import org.jsoup.Jsoup


class OverviewFragement : Fragment() {

    private var _binding: FragmentOverviewFragementBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOverviewFragementBinding.inflate(inflater, container, false)
        val view = binding.root
        val args = arguments
        val myBundle: Result? = args?.getParcelable(RECIPE_RESULT_KEY)

        binding.mainimageView.load(myBundle?.image)
        binding.titleTextView.text = myBundle?.title
        binding.likesTextView.text = myBundle?.aggregateLikes.toString()
        binding.timeTextView.text = myBundle?.readyInMinutes.toString()

        myBundle?.summary.let {
            val summary = Jsoup.parse(it).text()
            binding.summaryTextView.text = summary
        }

        if (myBundle?.vegetarian == true) {
            binding.vegeterianImageView.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.green
                )
            )
            binding.vegeterianTextView.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.green
                )
            )
        }
        if (myBundle?.cheap == true) {
            binding.cheapImageView.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.green
                )
            )
            binding.cheapTextView.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.green
                )
            )
        }
        if (myBundle?.dairyFree == true) {
            binding.dairyFreeImageview.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.green
                )
            )
            binding.dairyFreeTextView.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.green
                )
            )
        }
        if (myBundle?.glutenFree == true) {
            binding.glutenFreeImageView.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.green
                )
            )
            binding.glutenFreeTextview.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.green
                )
            )
        }
        if (myBundle?.veryHealthy == true) {
            binding.healtyImageView.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.green
                )
            )
            binding.healthTextView.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.green
                )
            )
        }
        if (myBundle?.vegan == true) {
            binding.veganImageView.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.green
                )
            )
            binding.veganTextView.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.green
                )
            )
        }




        return view
    }


}