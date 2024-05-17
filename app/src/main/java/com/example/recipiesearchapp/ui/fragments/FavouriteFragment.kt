package com.example.recipiesearchapp.ui.fragments

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipiesearchapp.adapter.AllRecipeAdapter
import com.example.recipiesearchapp.database.RecipeDatabase
import com.example.recipiesearchapp.databinding.FragmentFavouriteBinding
import com.example.recipiesearchapp.models.RecipeDataBrief
import com.example.recipiesearchapp.ui.viewmodels.FavouriteViewModel
import com.example.recipiesearchapp.ui.viewmodels.RecipeDescriptionViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouriteFragment : Fragment() {

    private val binding: FragmentFavouriteBinding by lazy {
        FragmentFavouriteBinding.inflate(layoutInflater)
    }
    private lateinit var allRecipeAdapter: AllRecipeAdapter
    private val viewModel: FavouriteViewModel by viewModels()
    private val recipeDescriptionViewModel: RecipeDescriptionViewModel by viewModels<RecipeDescriptionViewModel>()


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
        initRecyclerView()
    }

    private fun initClicks() {

    }

    private fun attachObservers() {

        recipeDescriptionViewModel.allrecipeLists.observe(viewLifecycleOwner) { savedRecipeDataList ->
            var savedRecipeData: MutableList<RecipeDataBrief> = mutableListOf()

            for (data in savedRecipeDataList) {

                savedRecipeData.add(
                    RecipeDataBrief(
                        id = data.id,
                        image = data.image,
                        imageType = "",
                        title = data.title
                    )
                )
            }

            allRecipeAdapter =
                savedRecipeData?.let { AllRecipeAdapter(requireContext(), it, onItemClick = {}) }!!

            val adapter = allRecipeAdapter

            binding.rvSavedRecipes.setHasFixedSize(true)
            binding.rvSavedRecipes.adapter = adapter
            binding.rvSavedRecipes.layoutManager = LinearLayoutManager(context)

        }
    }

    private fun initRecyclerView() {


    }

}