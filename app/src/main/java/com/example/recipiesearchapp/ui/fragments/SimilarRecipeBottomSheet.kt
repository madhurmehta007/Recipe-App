package com.example.recipiesearchapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipiesearchapp.adapter.SimilarRecipeAdapter
import com.example.recipiesearchapp.databinding.FragmentSimilarRecipeBottomSheetBinding
import com.example.recipiesearchapp.models.RecipeDataBrief
import com.example.recipiesearchapp.models.SimilarRecipeItem
import com.example.recipiesearchapp.ui.viewmodels.SimilarRecipeViewModel
import com.example.recipiesearchapp.utils.Constants.Companion.API_KEY
import com.example.recipiesearchapp.utils.GenericUtils
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SimilarRecipeBottomSheet(val recipe: RecipeDataBrief) : BottomSheetDialogFragment() {

    private var _binding: FragmentSimilarRecipeBottomSheetBinding? = null
    private val binding
        get() = _binding!!
    val similarRecipeViewModel:SimilarRecipeViewModel by viewModels<SimilarRecipeViewModel>()
    private lateinit var similarRecipeAdapter: SimilarRecipeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSimilarRecipeBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initClicks()
        attachObservers()
        similarRecipeViewModel.getSimilarRecipeData(recipe.id.toString(),API_KEY)
    }

    private fun initClicks(){

    }

    private fun attachObservers(){
        similarRecipeViewModel.similarRecipeDataResponse.observe(viewLifecycleOwner, Observer {
            val similarRecipeData:ArrayList<SimilarRecipeItem>? = it.body()

            if (similarRecipeData?.isNotEmpty() == true){
                similarRecipeAdapter = SimilarRecipeAdapter(requireContext(), similarRecipeData)
                var adapter = similarRecipeAdapter

                adapter.notifyDataSetChanged()
                binding.rvSimilarRecipes.setHasFixedSize(true)
                binding.rvSimilarRecipes.adapter = adapter
                binding.rvSimilarRecipes.layoutManager = LinearLayoutManager(context)
                adapter.notifyDataSetChanged()

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
            GenericUtils.setPeekHeight(0.75, binding.bottomSheet, requireActivity())
            binding.bottomSheet.requestLayout()
        }
    }

}