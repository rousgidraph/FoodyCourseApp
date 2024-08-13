package art.muriuki.foodycourseapp.ui.fragments.recipes.bottomSheet

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import art.muriuki.foodycourseapp.databinding.RecipesBottomSheetBinding
import art.muriuki.foodycourseapp.util.Constants.Companion.DEFAULT_DIET_TYPE
import art.muriuki.foodycourseapp.util.Constants.Companion.DEFAULT_MEAL_TYPE
import art.muriuki.foodycourseapp.viewModels.RecipesViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import java.util.Locale

class RecipesBottomSheet : BottomSheetDialogFragment() {
    private lateinit var recipesViewModel: RecipesViewModel

    private var mealTypeChip = DEFAULT_MEAL_TYPE
    private var mealTypeChipId = 0
    private var dietTypeChip = DEFAULT_DIET_TYPE
    private var dietTypeChipId = 0

    private var _binding: RecipesBottomSheetBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recipesViewModel = ViewModelProvider(requireActivity())[RecipesViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        val mview = inflater.inflate(R.layout.recipes_bottom_sheet, container, false)

        recipesViewModel.readMealAndDietType.asLiveData().observe(viewLifecycleOwner) { value ->
            mealTypeChip = value.selectedMealType
            dietTypeChip = value.selectedDietType
            updateChip(value.selectedMealTypeId, binding.mealTypeChipGroup)
            updateChip(value.selectedDietTypeId, binding.dietTypeChipGroup)
        }

        _binding = RecipesBottomSheetBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.mealTypeChipGroup.setOnCheckedChangeListener { group, selectedChipId ->
            val chip = group.findViewById<Chip>(selectedChipId)
            val selectedMealType = chip.text.toString().toLowerCase(Locale.ROOT)

            mealTypeChip = selectedMealType
            mealTypeChipId = selectedChipId

        }

        binding.dietTypeChipGroup.setOnCheckedChangeListener { group, selectedChipId ->
            val chip = group.findViewById<Chip>(selectedChipId)
            val selectedDietType = chip.text.toString().toLowerCase(Locale.ROOT)

            dietTypeChip = selectedDietType
            dietTypeChipId = selectedChipId

        }

        binding.applyBtn.setOnClickListener {
            recipesViewModel.saveMealAndDietType(
                mealTypeChip,
                mealTypeChipId,
                dietTypeChip,
                dietTypeChipId
            )
            var msg = String.format(
                "Bottom sheet result => meal Type %s | meal Type ID  %s | diet Type %s | diet Type ID %s ",
                mealTypeChip,
                mealTypeChipId,
                dietTypeChip,
                dietTypeChipId
            )
            Log.d("Bottom Sheet", msg)


            val action = RecipesBottomSheetDirections.actionRecipesBottomSheetToRecipesFragment(true)
            findNavController().navigate(action)
        }



        return view
    }


    private fun updateChip(chipId: Int, chipGroup: ChipGroup) {
        if (chipId != 0) {
            try {
//                Log.d("Bottom Sheet", )
                chipGroup.findViewById<Chip>(chipId).isChecked = true
            } catch (e: Exception) {
                Log.d("Bottom Sheet", e.message.toString())
            }
        }
    }

}