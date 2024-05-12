package com.example.recipiesearchapp.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recipiesearchapp.databinding.ItemPopularRecipeBinding
import com.example.recipiesearchapp.models.Recipe
import com.squareup.picasso.Picasso

class PopularRecipeAdapter(
    val context: Context,
    val recipeList:MutableList<Recipe>
    ):
        RecyclerView.Adapter<PopularRecipeAdapter.RecipeViewHolder>(){

    class RecipeViewHolder(val binding:ItemPopularRecipeBinding,context: Context):
        RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemPopularRecipeBinding.inflate(layoutInflater, parent, false)

        return RecipeViewHolder(binding, parent.context)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
       val recipe = recipeList[position]

        Picasso.get().load(recipe.image).into(holder.binding.ivDishImage)
        holder.binding.tvDishName.text = recipe.title
        holder.binding.tvReadyTime.text = "Ready in ${recipe.readyInMinutes} min"
    }

    override fun getItemCount(): Int {
        return recipeList.size
    }


}