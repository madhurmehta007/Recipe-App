package com.example.recipiesearchapp.ui.fragments

import SwipeToDeleteCallback
import android.app.AlertDialog
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipiesearchapp.adapter.FavouriteRecipeAdapter
import com.example.recipiesearchapp.databinding.FragmentFavouriteBinding
import com.example.recipiesearchapp.models.RecipeDataBrief
import com.example.recipiesearchapp.ui.viewmodels.FavouriteViewModel
import com.example.recipiesearchapp.ui.viewmodels.RecipeDescriptionViewModel
import com.example.recipiesearchapp.utils.Snacker
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouriteFragment : Fragment() {

    private val binding: FragmentFavouriteBinding by lazy {
        FragmentFavouriteBinding.inflate(layoutInflater)
    }
    private lateinit var favouriteRecipeAdapter: FavouriteRecipeAdapter
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
    }

    private fun initClicks() {

        val swipeToDeleteCallback = object : SwipeToDeleteCallback(context) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                // Get the position of the item
                val position = viewHolder.adapterPosition
                // Call the method to delete the item
                deleteItem(position)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(binding.rvSavedRecipes)

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

            favouriteRecipeAdapter = FavouriteRecipeAdapter(requireContext(), savedRecipeData,onItemClick = {
                val dialog = RecipeDescriptionBottomSheet(it)
                dialog.isCancelable = true
                dialog.show(parentFragmentManager,"RecipeDescriptionBottomSheet")
            })

            val adapter = favouriteRecipeAdapter

            binding.rvSavedRecipes.setHasFixedSize(true)
            binding.rvSavedRecipes.adapter = adapter
            binding.rvSavedRecipes.layoutManager = LinearLayoutManager(context)

        }
    }

    private fun deleteItem(position: Int) {
        val id = favouriteRecipeAdapter.favouriteRecipeList[position].id
        AlertDialog.Builder(context)
            .setTitle("Delete")
            .setMessage("Are you sure you want to delete?")
            .setPositiveButton("Yes") { dialog, _ ->
                recipeDescriptionViewModel.deleteRecipe(id)
                favouriteRecipeAdapter.notifyItemRemoved(position)
                Snacker(binding.root, "Recipe Deleted").error()
            }
            .setNegativeButton("No") { dialog, _ ->
                Snacker(binding.root, "Recipe Not Deleted").warning()
                favouriteRecipeAdapter.notifyItemRemoved(position)
            }
            .setCancelable(true)
            .show()

        true


    }

}