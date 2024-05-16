package com.example.recipiesearchapp.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipiesearchapp.R
import com.example.recipiesearchapp.adapter.EquipmentsAdapter
import com.example.recipiesearchapp.adapter.IngredientsAdapter
import com.example.recipiesearchapp.databinding.FragmentRecipeDescriptionBottomSheetBinding
import com.example.recipiesearchapp.models.Equipment
import com.example.recipiesearchapp.models.Ingredient
import com.example.recipiesearchapp.models.Result
import com.example.recipiesearchapp.models.Step
import com.example.recipiesearchapp.ui.viewmodels.RecipeDescriptionViewModel
import com.example.recipiesearchapp.utils.Constants.Companion.API_KEY
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipeDescriptionBottomSheet(val recipe: Result) : BottomSheetDialogFragment() {
    private var _binding: FragmentRecipeDescriptionBottomSheetBinding? = null
    private val binding
        get() = _binding!!

    private var allIngredients: ArrayList<Ingredient>? = null
    private var allEquipments: ArrayList<Equipment>? = null

    val recipeInformationViewModel: RecipeDescriptionViewModel by viewModels<RecipeDescriptionViewModel>()
    private lateinit var equipmentsAdapter: EquipmentsAdapter
    private lateinit var ingredientsAdapter: IngredientsAdapter

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
        recipeInformationViewModel.getRecipeInformation(recipe.id.toString(), API_KEY)
    }

    private fun initClicks() {
        binding.apply {
            cvDropDown.setOnClickListener {
                if (tvNutrition.visibility == View.VISIBLE) {
                    tvNutrition.visibility = View.GONE
                    ivArrowDropDown.visibility = View.VISIBLE
                    ivArrowDropUp.visibility = View.GONE
                } else {
                    tvNutrition.visibility = View.VISIBLE
                    ivArrowDropDown.visibility = View.GONE
                    ivArrowDropUp.visibility = View.VISIBLE
                }
            }
            clNutrition.setOnClickListener {
                if (tvNutrition.visibility == View.VISIBLE) {
                    tvNutrition.visibility = View.GONE
                    ivArrowDropDown.visibility = View.VISIBLE
                    ivArrowDropUp.visibility = View.GONE
                } else {
                    tvNutrition.visibility = View.VISIBLE
                    ivArrowDropDown.visibility = View.GONE
                    ivArrowDropUp.visibility = View.VISIBLE
                }
            }
            cvBadNutritionDropDown.setOnClickListener {
                if (tvBadNutrition.visibility == View.VISIBLE) {
                    tvBadNutrition.visibility = View.GONE
                    ivBadNutritionDropDown.visibility = View.VISIBLE
                    ivBadNutritionDropUp.visibility = View.GONE
                } else {
                    tvBadNutrition.visibility = View.VISIBLE
                    ivBadNutritionDropDown.visibility = View.GONE
                    ivBadNutritionDropUp.visibility = View.VISIBLE
                }
            }
            clBadHealthNutrition.setOnClickListener {
                if (tvBadNutrition.visibility == View.VISIBLE) {
                    tvBadNutrition.visibility = View.GONE
                    ivBadNutritionDropDown.visibility = View.VISIBLE
                    ivBadNutritionDropUp.visibility = View.GONE
                } else {
                    tvBadNutrition.visibility = View.VISIBLE
                    ivBadNutritionDropDown.visibility = View.GONE
                    ivBadNutritionDropUp.visibility = View.VISIBLE
                }
            }
            cvGoodNutritionDropDown.setOnClickListener {
                if (tvGoodNutrition.visibility == View.VISIBLE) {
                    tvGoodNutrition.visibility = View.GONE
                    ivGoodNutritionDropDown.visibility = View.VISIBLE
                    ivGoodNutritionDropUp.visibility = View.GONE
                } else {
                    tvGoodNutrition.visibility = View.VISIBLE
                    ivGoodNutritionDropDown.visibility = View.GONE
                    ivGoodNutritionDropUp.visibility = View.VISIBLE
                }
            }
            clGoodHealthNutrition.setOnClickListener {
                if (tvGoodNutrition.visibility == View.VISIBLE) {
                    tvGoodNutrition.visibility = View.GONE
                    ivGoodNutritionDropDown.visibility = View.VISIBLE
                    ivGoodNutritionDropUp.visibility = View.GONE
                } else {
                    tvGoodNutrition.visibility = View.VISIBLE
                    ivGoodNutritionDropDown.visibility = View.GONE
                    ivGoodNutritionDropUp.visibility = View.VISIBLE
                }
            }

        }
    }


    private fun attachObservers() {
        recipeInformationViewModel.recipeInformationResponse.observe(viewLifecycleOwner, Observer {

            val steps: MutableList<Step> =
                it.body()?.analyzedInstructions?.get(0)?.steps as MutableList<Step>


            if(recipe.image.isEmpty()){
                Picasso.get().load(R.drawable.ic_placeholder).into(binding.ivDishImage)
            }else{
                Picasso.get().load(recipe.image).into(binding.ivDishImage)
            }

            binding.tvReadyTime.text = "${it.body()?.readyInMinutes.toString()} min"
            binding.tvServings.text = it.body()?.servings.toString()
            binding.tvPrice.text = it.body()?.pricePerServing.toString()
            binding.tvInstructions.text = it.body()?.instructions
            binding.tvQuickSummary.text = it.body()?.summary

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

            ingredientsAdapter = IngredientsAdapter(requireContext(), removeDuplicates(allIngredients!!))

            binding.tvIngredientsText.visibility = View.VISIBLE
            var adapterIngredient = ingredientsAdapter

            adapterIngredient.notifyDataSetChanged()
            binding.rvIngredients.setHasFixedSize(true)
            binding.rvIngredients.adapter = adapterIngredient
            binding.rvIngredients.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapterIngredient.notifyDataSetChanged()


            equipmentsAdapter = EquipmentsAdapter(requireContext(), removeDuplicates(allEquipments!!))

            binding.tvEquipmentsText.visibility = View.VISIBLE
            var adapterEquipment = equipmentsAdapter

            adapterEquipment.notifyDataSetChanged()
            binding.rvEquipments.setHasFixedSize(true)
            binding.rvEquipments.adapter = adapterEquipment
            binding.rvEquipments.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapterEquipment.notifyDataSetChanged()

        })
    }
    fun <T> removeDuplicates(list: ArrayList<T>): ArrayList<T> {
        val distinctItems = ArrayList<T>()
        distinctItems.addAll(list.distinct())
        return distinctItems
    }


}