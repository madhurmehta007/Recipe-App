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
import com.example.recipiesearchapp.databinding.FragmentRecipeEquipmentBottomSheetBinding
import com.example.recipiesearchapp.databinding.FragmentSimilarRecipeBottomSheetBinding
import com.example.recipiesearchapp.models.Equipment
import com.example.recipiesearchapp.models.RecipeDataBrief
import com.example.recipiesearchapp.models.Step
import com.example.recipiesearchapp.ui.viewmodels.RecipeDescriptionViewModel
import com.example.recipiesearchapp.utils.GenericUtils
import com.example.recipiesearchapp.utils.GenericUtils.Companion.hide
import com.example.recipiesearchapp.utils.GenericUtils.Companion.replaceChildFragmentWithAnimation
import com.example.recipiesearchapp.utils.GenericUtils.Companion.show
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipeEquipmentBottomSheet(val recipe: RecipeDataBrief) : BottomSheetDialogFragment() {

    private val binding: FragmentRecipeEquipmentBottomSheetBinding by lazy {
        FragmentRecipeEquipmentBottomSheetBinding.inflate(layoutInflater)
    }

    private val recipeInformationViewModel: RecipeDescriptionViewModel by viewModels<RecipeDescriptionViewModel>()
    private lateinit var equipmentsAdapter: EquipmentsAdapter
    private var allEquipments: ArrayList<Equipment>? = null

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

            btnGetSimilarRecipe.setOnClickListener {
                fcvRecipeEquipment.show()
                replaceChildFragmentWithAnimation(SimilarRecipeBottomSheet(recipe), R.id.fcv_recipe_equipment)
            }

            ivEquipmentDropDown.setOnClickListener{
                fcvRecipeEquipment.hide()
            }

            tvFullRecipe.setOnClickListener {
                fcvRecipeEquipment.hide()
            }
        }
    }

    private fun attachObservers() {
        recipeInformationViewModel.recipeInformationResponse.observe(viewLifecycleOwner, Observer {

            val steps: MutableList<Step> =
                it.body()?.analyzedInstructions?.get(0)?.steps as MutableList<Step>

            if (allEquipments == null) {
                allEquipments = ArrayList()
            }

            for (i in steps) {
                for (j in i.equipment) {
                    allEquipments?.add(j)
                }
            }

            equipmentsAdapter = EquipmentsAdapter(
                requireContext(),
                GenericUtils.removeDuplicates(allEquipments!!)
            )

            val adapterEquipment = equipmentsAdapter

            binding.rvEquipments.setHasFixedSize(true)
            binding.rvEquipments.adapter = adapterEquipment
            binding.rvEquipments.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        })
    }

    override fun onStart() {
        super.onStart()
        dialog?.let {
            val bottomSheet =
                it.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout
            val behavior = BottomSheetBehavior.from(bottomSheet)
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
    }
}