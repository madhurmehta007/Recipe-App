package com.example.recipiesearchapp.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recipiesearchapp.databinding.ItemAllRecipeBinding
import com.example.recipiesearchapp.models.SimilarRecipeItem

class SimilarRecipeAdapter(val context: Context,
                           val similarRecipeList:MutableList<SimilarRecipeItem>,
):
    RecyclerView.Adapter<SimilarRecipeAdapter.SimilarRecipeViewHolder>(){

    class SimilarRecipeViewHolder(val binding: ItemAllRecipeBinding, context: Context):
        RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimilarRecipeViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemAllRecipeBinding.inflate(layoutInflater, parent, false)

        return SimilarRecipeViewHolder(binding, parent.context)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: SimilarRecipeViewHolder, position: Int) {
        val similarRecipe = similarRecipeList[position]

//        Picasso.get().load(similarRecipe.image).into(holder.binding.ivDishImage)
        holder.binding.tvDishName.text = similarRecipe.title
        holder.binding.tvReadyTime.text = "Ready in ${similarRecipe.readyInMinutes} min"

    }

    override fun getItemCount(): Int {
        return similarRecipeList.size
    }


}