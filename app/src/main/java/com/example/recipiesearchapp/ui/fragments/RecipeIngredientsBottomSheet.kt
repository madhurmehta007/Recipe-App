package com.example.recipiesearchapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.recipiesearchapp.adapter.IngredientsAdapter
import com.example.recipiesearchapp.databinding.FragmentRecipeIngredientsBottomSheetBinding
import com.example.recipiesearchapp.models.Ingredient
import com.example.recipiesearchapp.models.RecipeDataBrief
import com.example.recipiesearchapp.models.Step
import com.example.recipiesearchapp.ui.viewmodels.RecipeDescriptionViewModel
import com.example.recipiesearchapp.utils.Constants
import com.example.recipiesearchapp.utils.GenericUtils.Companion.removeDuplicates
import com.example.recipiesearchapp.utils.GenericUtils.Companion.setPeekHeight
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipeIngredientsBottomSheet(val recipe: RecipeDataBrief) : BottomSheetDialogFragment() {
    private var _binding: FragmentRecipeIngredientsBottomSheetBinding? = null
    private val binding
        get() = _binding!!
    private lateinit var ingredientsAdapter: IngredientsAdapter
    val recipeInformationViewModel: RecipeDescriptionViewModel by viewModels<RecipeDescriptionViewModel>()
    private var allIngredients: ArrayList<Ingredient>? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecipeIngredientsBottomSheetBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClicks()
        attachObservers()
        recipeInformationViewModel.getRecipeInformation(recipe.id.toString(), Constants.API_KEY)
    }

    private fun initClicks() {
        binding.btnGetFullRecipe.setOnClickListener {
            val dialog = RecipeEquipmentBottomSheet(recipe)
            dialog.isCancelable = true
            dialog.show(parentFragmentManager,"RecipeEquipmentBottomSheet")
        }

    }

    private fun attachObservers() {
        recipeInformationViewModel.recipeInformationResponse.observe(viewLifecycleOwner, Observer {
            val steps: MutableList<Step> =
                it.body()?.analyzedInstructions?.get(0)?.steps as MutableList<Step>

            if (allIngredients == null) {
                allIngredients = ArrayList()
            }

            for (i in steps) {
                for (j in i.ingredients) {
                    allIngredients?.add(j)
                }
            }

            ingredientsAdapter =
                IngredientsAdapter(requireContext(), removeDuplicates(allIngredients!!))

            var adapter = ingredientsAdapter

            adapter.notifyDataSetChanged()
            binding.rvIngredients.setHasFixedSize(true)
            binding.rvIngredients.adapter = adapter
            binding.rvIngredients.layoutManager = GridLayoutManager(context, 3)
            adapter.notifyDataSetChanged()

        })
    }


    override fun onStart() {
        super.onStart()
        dialog?.let {
            val bottomSheet =
                it.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout
            val behavior = BottomSheetBehavior.from(bottomSheet)
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
           setPeekHeight(0.84,binding.bottomSheet,requireActivity())
            binding.bottomSheet.requestLayout()
        }
    }

}