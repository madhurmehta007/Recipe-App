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
import com.example.recipiesearchapp.models.Equipment
import com.example.recipiesearchapp.models.Ingredient
import com.example.recipiesearchapp.models.RecipeDataBrief
import com.example.recipiesearchapp.models.SavedRecipeData
import com.example.recipiesearchapp.models.Step
import com.example.recipiesearchapp.ui.viewmodels.RecipeDescriptionViewModel
import com.example.recipiesearchapp.utils.GenericUtils
import com.example.recipiesearchapp.utils.GenericUtils.Companion.hide
import com.example.recipiesearchapp.utils.GenericUtils.Companion.replaceChildFragmentWithAnimation
import com.example.recipiesearchapp.utils.GenericUtils.Companion.show
import com.example.recipiesearchapp.utils.Snacker
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipeIntroBottomSheet(val recipe: RecipeDataBrief) : BottomSheetDialogFragment() {

    private val binding: FragmentRecipeIntroBottomSheetBinding by lazy {
        FragmentRecipeIntroBottomSheetBinding.inflate(layoutInflater)
    }
    private var savedRecipeData: SavedRecipeData? = null

    private var allIngredients: ArrayList<Ingredient>? = null
    private var allEquipments: ArrayList<Equipment>? = null


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

    private fun initClicks() {
        binding.apply {
            btnGetIngredients.setOnClickListener {
                replaceChildFragmentWithAnimation(
                    RecipeIngredientsBottomSheet(recipe),
                    R.id.fcv_recipe_intro
                )
            }

            ivArrowLeft.setOnClickListener {
                dismiss()
            }

            ivFavouriteOutline.setOnClickListener {
                if (ivFavouriteOutline.visibility == View.VISIBLE) {
                    ivFavouriteOutline.hide()
                    ivFavoriteFilled.show()
                    savedRecipeData?.let { it1 -> recipeInformationViewModel.insertRecipe(it1) }
                    Snacker(binding.root, "Recipe added to favourites").success()
                } else {
                    ivFavouriteOutline.show()
                    ivFavouriteOutline.hide()
                }
            }
        }
    }


    private fun attachObservers() {
        recipeInformationViewModel.recipeInformationResponse.observe(viewLifecycleOwner, Observer {

            val steps: MutableList<Step> =
                it.body()?.analyzedInstructions?.get(0)?.steps as MutableList<Step>

            if (recipe.image.isEmpty()) {
                Picasso.get().load(R.drawable.ic_placeholder).into(binding.ivDishImage)
            } else {
                Picasso.get().load(recipe.image).into(binding.ivDishImage)
            }
            binding.tvRecipeName.text = it.body()?.title
            binding.tvReadyTime.text = getString(R.string.recipe_ready_time, it.body()?.readyInMinutes)
            binding.tvServings.text = it.body()?.servings.toString()
            binding.tvPrice.text = it.body()?.pricePerServing.toString()

            if (allIngredients == null) {
                allIngredients = ArrayList()
            }

            if (allEquipments == null) {
                allEquipments = ArrayList()
            }

            for (i in steps) {
                for (j in i.ingredients) {
                    allIngredients?.add(j)
                }
            }

            for (i in steps) {
                for (j in i.equipment) {
                    allEquipments?.add(j)
                }
            }


            it.body()?.let { recipeData ->
                savedRecipeData = SavedRecipeData(
                    id = recipeData.id,
                    image = recipeData.image,
                    readyInMinutes = recipeData.readyInMinutes,
                    instructions = recipeData.instructions,
                    servings = recipeData.servings,
                    summary = recipeData.summary,
                    title = recipeData.title,
                    equipment = allEquipments!!,
                    ingredients = allIngredients!!,
                    pricePerServing = recipeData.pricePerServing,
                )
            }

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