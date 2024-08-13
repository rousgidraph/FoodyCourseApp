package art.muriuki.foodycourseapp.ui.fragments.instructions

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import art.muriuki.foodycourseapp.R
import art.muriuki.foodycourseapp.databinding.FragmentInstructionBinding
import art.muriuki.foodycourseapp.models.Result
import art.muriuki.foodycourseapp.util.Constants


class InstructionFragment : Fragment() {

    var _binding: FragmentInstructionBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInstructionBinding.inflate(inflater,container,false)

        val args = arguments
        val myBundle: Result? = args?.getParcelable(Constants.RECIPE_RESULT_KEY)
        binding.instructionsWebView.webViewClient = object : WebViewClient(){}
        val websiteUrl: String = myBundle!!.sourceUrl
        binding.instructionsWebView.loadUrl(websiteUrl)
        return binding.root
    }


}