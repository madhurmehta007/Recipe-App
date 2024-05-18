package com.example.recipiesearchapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipiesearchapp.BuildConfig
import com.example.recipiesearchapp.R
import com.example.recipiesearchapp.adapter.EquipmentsAdapter
import com.example.recipiesearchapp.adapter.IngredientsAdapter
import com.example.recipiesearchapp.databinding.FragmentRecipeDescriptionBottomSheetBinding
import com.example.recipiesearchapp.models.Equipment
import com.example.recipiesearchapp.models.Ingredient
import com.example.recipiesearchapp.models.RecipeDataBrief
import com.example.recipiesearchapp.models.SavedRecipeData
import com.example.recipiesearchapp.models.Step
import com.example.recipiesearchapp.ui.viewmodels.RecipeDescriptionViewModel
import com.example.recipiesearchapp.utils.GenericUtils
import com.example.recipiesearchapp.utils.GenericUtils.Companion.hide
import com.example.recipiesearchapp.utils.GenericUtils.Companion.removeDuplicates
import com.example.recipiesearchapp.utils.GenericUtils.Companion.show
import com.example.recipiesearchapp.utils.Snacker
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipeDescriptionBottomSheet(val recipe: RecipeDataBrief,val allRecipeData:List<SavedRecipeData>? = null) : BottomSheetDialogFragment() {
    private var _binding: FragmentRecipeDescriptionBottomSheetBinding? = null
    private val binding
        get() = _binding!!

    private var allIngredients: ArrayList<Ingredient>? = null
    private var allEquipments: ArrayList<Equipment>? = null

    private val recipeInformationViewModel: RecipeDescriptionViewModel by viewModels<RecipeDescriptionViewModel>()
    private lateinit var equipmentsAdapter: EquipmentsAdapter
    private lateinit var ingredientsAdapter: IngredientsAdapter

    private var savedRecipeData: SavedRecipeData? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeDescriptionBottomSheetBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClicks()
        attachObservers()
        setViews()
    }

    private fun setViews() {
        allRecipeData?.find { it.id == recipe.id }?.let {

            ingredientsAdapter =
                IngredientsAdapter(requireContext(), removeDuplicates(it.ingredients))

            binding.tvIngredientsText.visibility = View.VISIBLE
            val adapterIngredient = ingredientsAdapter

            binding.rvIngredients.setHasFixedSize(true)
            binding.rvIngredients.adapter = adapterIngredient
            binding.rvIngredients.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)


            equipmentsAdapter =
                EquipmentsAdapter(requireContext(), removeDuplicates(it.equipment))

            binding.tvEquipmentsText.visibility = View.VISIBLE
            var adapterEquipment = equipmentsAdapter

            binding.rvEquipments.setHasFixedSize(true)
            binding.rvEquipments.adapter = adapterEquipment
            binding.rvEquipments.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

            if (recipe.image.isEmpty()) {
                Picasso.get().load(R.drawable.ic_placeholder).into(binding.ivDishImage)
            } else {
                Picasso.get().load(it.image).into(binding.ivDishImage)
            }

            binding.apply {
                tvReadyTime.text =
                    getString(R.string.recipe_ready_time, it.readyInMinutes)
                tvServings.text = it.servings.toString()
                tvPrice.text = it.pricePerServing.toString()
                tvInstructions.text = it.instructions
                tvQuickSummary.text = it.summary
            }

        } ?: run {
            recipeInformationViewModel.getRecipeInformation(recipe.id.toString(), BuildConfig.API_KEY)
        }
    }

    private fun initClicks() {
        binding.apply {
            cvDropDown.setOnClickListener {
                if (tvNutrition.visibility == View.VISIBLE) {
                    tvNutrition.hide()
                    ivArrowDropDown.show()
                    ivArrowDropUp.hide()
                } else {
                    tvNutrition.show()
                    ivArrowDropDown.hide()
                    ivArrowDropUp.show()
                }
            }
            clNutrition.setOnClickListener {
                if (tvNutrition.visibility == View.VISIBLE) {
                    tvNutrition.hide()
                    ivArrowDropDown.show()
                    ivArrowDropUp.hide()
                } else {
                    tvNutrition.show()
                    ivArrowDropDown.hide()
                    ivArrowDropUp.show()
                }
            }
            cvBadNutritionDropDown.setOnClickListener {
                if (tvBadNutrition.visibility == View.VISIBLE) {
                    tvBadNutrition.hide()
                    ivBadNutritionDropDown.show()
                    ivBadNutritionDropUp.hide()
                } else {
                    tvBadNutrition.show()
                    ivBadNutritionDropDown.hide()
                    ivBadNutritionDropUp.show()
                }
            }
            clBadHealthNutrition.setOnClickListener {
                if (tvBadNutrition.visibility == View.VISIBLE) {
                    tvBadNutrition.hide()
                    ivBadNutritionDropDown.show()
                    ivBadNutritionDropUp.hide()
                } else {
                    tvBadNutrition.show()
                    ivBadNutritionDropDown.hide()
                    ivBadNutritionDropUp.show()
                }
            }
            cvGoodNutritionDropDown.setOnClickListener {
                if (tvGoodNutrition.visibility == View.VISIBLE) {
                    tvGoodNutrition.hide()
                    ivGoodNutritionDropDown.show()
                    ivGoodNutritionDropUp.hide()
                } else {
                    tvGoodNutrition.show()
                    ivGoodNutritionDropDown.hide()
                    ivGoodNutritionDropUp.show()
                }
            }
            clGoodHealthNutrition.setOnClickListener {
                if (tvGoodNutrition.visibility == View.VISIBLE) {
                    tvGoodNutrition.hide()
                    ivGoodNutritionDropDown.show()
                    ivGoodNutritionDropUp.hide()
                } else {
                    tvGoodNutrition.show()
                    ivGoodNutritionDropDown.hide()
                    ivGoodNutritionDropUp.show()
                }
            }

            cvFavourite.setOnClickListener {
                if (ivFavouriteOutline.visibility == View.VISIBLE) {
                    ivFavouriteOutline.hide()
                    ivFavouriteFilled.show()
                    savedRecipeData?.let { it1 -> recipeInformationViewModel.insertRecipe(it1) }
                    Snacker(binding.root, "Recipe added to favourites").success()
                } else {
                    ivFavouriteOutline.show()
                    ivFavouriteFilled.hide()
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

            it.body()?.let { recipeInfo ->
                binding.apply {
                    tvReadyTime.text =
                        getString(R.string.recipe_ready_time, recipeInfo.readyInMinutes)
                    tvServings.text = recipeInfo.servings.toString()
                    tvPrice.text = recipeInfo.pricePerServing.toString()
                    tvInstructions.text = recipeInfo.instructions
                    tvQuickSummary.text = recipeInfo.summary
                }
            }
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


            ingredientsAdapter =
                IngredientsAdapter(requireContext(), removeDuplicates(allIngredients!!))

            binding.tvIngredientsText.visibility = View.VISIBLE
            val adapterIngredient = ingredientsAdapter

            binding.rvIngredients.setHasFixedSize(true)
            binding.rvIngredients.adapter = adapterIngredient
            binding.rvIngredients.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

            equipmentsAdapter =
                EquipmentsAdapter(requireContext(), removeDuplicates(allEquipments!!))

            binding.tvEquipmentsText.visibility = View.VISIBLE
            val adapterEquipment = equipmentsAdapter

            binding.rvEquipments.setHasFixedSize(true)
            binding.rvEquipments.adapter = adapterEquipment
            binding.rvEquipments.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

            binding.cvFavourite.show()
        })
    }

    override fun onStart() {
        super.onStart()
        dialog?.let {
            val bottomSheet =
                it.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout
            val behavior = BottomSheetBehavior.from(bottomSheet)
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
            GenericUtils.setPeekHeight(1.0, binding.bottomSheet, requireActivity())
            binding.bottomSheet.requestLayout()
        }
    }


}