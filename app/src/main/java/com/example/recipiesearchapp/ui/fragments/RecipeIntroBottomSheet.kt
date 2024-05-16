package com.example.recipiesearchapp.ui.fragments

import android.app.Dialog
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.recipiesearchapp.R
import com.example.recipiesearchapp.adapter.IngredientsAdapter
import com.example.recipiesearchapp.databinding.FragmentRecipeDescriptionBottomSheetBinding
import com.example.recipiesearchapp.databinding.FragmentRecipeIntroBottomSheetBinding
import com.example.recipiesearchapp.models.Result
import com.example.recipiesearchapp.ui.viewmodels.RecipeDescriptionViewModel
import com.example.recipiesearchapp.utils.Constants
import com.example.recipiesearchapp.utils.Constants.Companion.API_KEY
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipeIntroBottomSheet(val recipe: Result) : BottomSheetDialogFragment() {

    private var _binding: FragmentRecipeIntroBottomSheetBinding? = null
    private val binding
        get() = _binding!!
    val recipeInformationViewModel: RecipeDescriptionViewModel by viewModels<RecipeDescriptionViewModel>()
    private lateinit var ingredientsAdapter: IngredientsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecipeIntroBottomSheetBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClicks()
        attachObservers()
        recipeInformationViewModel.getRecipeInformation(recipe.id.toString(), API_KEY)
    }

    private fun initClicks(){
        binding.btnGetIngredients.setOnClickListener {
            val dialog = RecipeIngredientsBottomSheet()
            dialog.isCancelable = true
            dialog.show(parentFragmentManager,"RecipeIntroBottomSheet")
        }

    }

    private fun attachObservers(){
        recipeInformationViewModel.recipeInformationResponse.observe(viewLifecycleOwner, Observer {
            if(recipe.image.isEmpty()){
                Picasso.get().load(R.drawable.ic_placeholder).into(binding.ivDishImage)
            }else{
                Picasso.get().load(recipe.image).into(binding.ivDishImage)
            }

            binding.tvReadyTime.text = "${it.body()?.readyInMinutes.toString()} min"
            binding.tvServings.text = it.body()?.servings.toString()
            binding.tvPrice.text = it.body()?.pricePerServing.toString()


        })
    }


    override fun onStart() {
        super.onStart()
        val metrics = DisplayMetrics()
        requireActivity().windowManager?.defaultDisplay?.getMetrics(metrics)
        binding.bottomSheet.layoutParams.height = metrics.heightPixels
        binding.bottomSheet.requestLayout()
    }


}