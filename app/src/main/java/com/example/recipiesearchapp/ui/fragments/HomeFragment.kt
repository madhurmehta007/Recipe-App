package com.example.recipiesearchapp.ui.fragments

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipiesearchapp.R
import com.example.recipiesearchapp.adapter.AllRecipeAdapter
import com.example.recipiesearchapp.adapter.PopularRecipeAdapter
import com.example.recipiesearchapp.databinding.FragmentHomeBinding
import com.example.recipiesearchapp.models.Recipe
import com.example.recipiesearchapp.models.Result
import com.example.recipiesearchapp.ui.viewmodels.HomeViewModel
import com.example.recipiesearchapp.utils.Constants.Companion.API_KEY
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding
        get() = _binding!!
     val homeViewModel: HomeViewModel by viewModels<HomeViewModel>()
    private lateinit var popularRecipeAdapter: PopularRecipeAdapter
    private lateinit var allRecipeAdapter: AllRecipeAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClicks()
        attachObservers()
        homeViewModel.getPopularRecipeData(API_KEY,"10")
        homeViewModel.getAllRecipeData(API_KEY)
    }

    private fun initClicks() {
        binding.apply {

            cvRecipeSearch.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
            }


        }
    }

    private fun attachObservers(){
        homeViewModel.popularRecipeDataResponse.observe(viewLifecycleOwner, Observer {

            val popularRecipeData:MutableList<Recipe> = it.body()?.recipes as MutableList<Recipe>

            if (popularRecipeData.isNotEmpty()){
                popularRecipeAdapter = PopularRecipeAdapter(requireContext(),popularRecipeData)
                binding.tvPopularRecipes.visibility = View.VISIBLE
                var adapter = popularRecipeAdapter

                adapter.notifyDataSetChanged()
                binding.rvPopularRecipes.setHasFixedSize(true)
                binding.rvPopularRecipes.adapter = adapter
                binding.rvPopularRecipes.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter.notifyDataSetChanged()
            }

        })

        homeViewModel.allRecipeDataResponse.observe(viewLifecycleOwner, Observer {

            val allRecipeData:MutableList<Result> = it.body()?.results as MutableList<Result>

            if (allRecipeData.isNotEmpty()){
                allRecipeAdapter = AllRecipeAdapter(requireContext(),allRecipeData)
                binding.tvAllRecipes.visibility = View.VISIBLE
                var adapter = allRecipeAdapter

                adapter.notifyDataSetChanged()
                binding.rvAllRecipes.setHasFixedSize(true)
                binding.rvAllRecipes.adapter = adapter
                binding.rvAllRecipes.layoutManager = LinearLayoutManager(context)
                adapter.notifyDataSetChanged()

                allRecipeAdapter.onItemClick = {
                    val dialog = RecipeDescriptionBottomSheet(it)
                    dialog.isCancelable = true
                    dialog.show(parentFragmentManager,"RecipeDescriptionBottomSheet")

                }

            }
        })
    }
}