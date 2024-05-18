package com.example.recipiesearchapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.recipiesearchapp.BuildConfig
import com.example.recipiesearchapp.R
import com.example.recipiesearchapp.databinding.FragmentRecipeIntroBottomSheetBinding
import com.example.recipiesearchapp.models.RecipeDataBrief
import com.example.recipiesearchapp.ui.viewmodels.RecipeDescriptionViewModel
import com.example.recipiesearchapp.utils.GenericUtils
import com.example.recipiesearchapp.utils.GenericUtils.Companion.replaceChildFragmentWithAnimation
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipeIntroBottomSheet(val recipe: RecipeDataBrief) : BottomSheetDialogFragment() {

    private val binding: FragmentRecipeIntroBottomSheetBinding by lazy {
        FragmentRecipeIntroBottomSheetBinding.inflate(layoutInflater)
    }

    private val recipeInformationViewModel: RecipeDescriptionViewModel by viewModels<RecipeDescriptionViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClicks()
        attachObservers()
        recipeInformationViewModel.getRecipeInformation(recipe.id.toString(), BuildConfig.API_KEY)
    }

    private fun initClicks(){
        binding.apply {
            btnGetIngredients.setOnClickListener {
                replaceChildFragmentWithAnimation(RecipeIngredientsBottomSheet(recipe), R.id.fcv_recipe_intro)
            }

        ivArrowLeft.setOnClickListener{
            dismiss()
        }
        }
    }


    private fun attachObservers(){
        recipeInformationViewModel.recipeInformationResponse.observe(viewLifecycleOwner, Observer {

            if(recipe.image.isEmpty()){
                Picasso.get().load(R.drawable.ic_placeholder).into(binding.ivDishImage)
            }else{
                Picasso.get().load(recipe.image).into(binding.ivDishImage)
            }
            binding.tvRecipeName.text = it.body()?.title
            binding.tvReadyTime.text = "${it.body()?.readyInMinutes.toString()} min"
            binding.tvServings.text = it.body()?.servings.toString()
            binding.tvPrice.text = it.body()?.pricePerServing.toString()

        })
    }


    override fun onStart() {
        super.onStart()
        dialog?.let {
            val bottomSheet =
                it.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout
            val behavior = BottomSheetBehavior.from(bottomSheet)
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
            GenericUtils.setPeekHeight(0.95, binding.bottomSheet, requireActivity())
            binding.bottomSheet.requestLayout()
        }
    }

}