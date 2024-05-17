package com.example.recipiesearchapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipiesearchapp.adapter.EquipmentsAdapter
import com.example.recipiesearchapp.databinding.FragmentRecipeEquipmentBottomSheetBinding
import com.example.recipiesearchapp.models.Equipment
import com.example.recipiesearchapp.models.RecipeDataBrief
import com.example.recipiesearchapp.models.Step
import com.example.recipiesearchapp.ui.viewmodels.RecipeDescriptionViewModel
import com.example.recipiesearchapp.utils.Constants
import com.example.recipiesearchapp.utils.GenericUtils
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipeEquipmentBottomSheet(val recipe: RecipeDataBrief) : BottomSheetDialogFragment() {
    private var _binding: FragmentRecipeEquipmentBottomSheetBinding? = null
    private val binding
        get() = _binding!!

    val recipeInformationViewModel: RecipeDescriptionViewModel by viewModels<RecipeDescriptionViewModel>()
    private lateinit var equipmentsAdapter: EquipmentsAdapter
    private var allEquipments: ArrayList<Equipment>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecipeEquipmentBottomSheetBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initClicks()
        attachObservers()
        recipeInformationViewModel.getRecipeInformation(recipe.id.toString(), Constants.API_KEY)
    }

    private fun initClicks(){
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

        binding.btnGetSimilarRecipe.setOnClickListener {
            val dialog = SimilarRecipeBottomSheet(recipe)
            dialog.isCancelable = true
            dialog.show(parentFragmentManager,"SimilarRecipeBottomSheet")
        }
    }

    private fun attachObservers(){
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

            equipmentsAdapter = EquipmentsAdapter(requireContext(),
                GenericUtils.removeDuplicates(allEquipments!!)
            )

            var adapterEquipment = equipmentsAdapter

            adapterEquipment.notifyDataSetChanged()
            binding.rvEquipments.setHasFixedSize(true)
            binding.rvEquipments.adapter = adapterEquipment
            binding.rvEquipments.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapterEquipment.notifyDataSetChanged()
        })
    }

    override fun onStart() {
        super.onStart()
        dialog?.let {
            val bottomSheet =
                it.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout
            val behavior = BottomSheetBehavior.from(bottomSheet)
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
            GenericUtils.setPeekHeight(0.79, binding.bottomSheet, requireActivity())
            binding.bottomSheet.requestLayout()
        }
    }
}