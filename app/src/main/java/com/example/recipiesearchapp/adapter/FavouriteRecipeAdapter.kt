package com.example.recipiesearchapp.adapter

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recipiesearchapp.databinding.ItemAllRecipeBinding
import com.example.recipiesearchapp.models.RecipeDataBrief
import com.example.recipiesearchapp.ui.viewmodels.RecipeDescriptionViewModel
import com.example.recipiesearchapp.utils.Snacker
import com.squareup.picasso.Picasso

class FavouriteRecipeAdapter (val context: Context,
                              val favouriteRecipeList:MutableList<RecipeDataBrief>,
                              val onItemClick: (RecipeDataBrief) -> Unit):
RecyclerView.Adapter<FavouriteRecipeAdapter.FavouriteRecipeViewHolder>(){


    class FavouriteRecipeViewHolder(val binding: ItemAllRecipeBinding, context: Context):
        RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteRecipeViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemAllRecipeBinding.inflate(layoutInflater, parent, false)

        return FavouriteRecipeViewHolder(binding, parent.context)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: FavouriteRecipeViewHolder, position: Int) {
        val recipe = favouriteRecipeList[position]

        Picasso.get().load(recipe.image).into(holder.binding.ivDishImage)
        holder.binding.tvDishName.text = recipe.title

        holder.binding.cvRecipeCard.setOnClickListener {
            onItemClick.invoke(recipe)
        }

    }

    override fun getItemCount(): Int {
        return favouriteRecipeList.size
    }


}