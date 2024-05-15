package com.example.recipiesearchapp.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipiesearchapp.R
import com.example.recipiesearchapp.databinding.ItemIngredientsBinding
import com.example.recipiesearchapp.models.Ingredient
import com.squareup.picasso.Picasso

class IngredientsAdapter(
    val context: Context,
    val ingredients:ArrayList<Ingredient>
):RecyclerView.Adapter<IngredientsAdapter.IngredientsViewHolder>() {

    class IngredientsViewHolder(val binding: ItemIngredientsBinding, context: Context):
        RecyclerView.ViewHolder(binding.root){
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemIngredientsBinding.inflate(layoutInflater, parent, false)

        return IngredientsViewHolder(binding, parent.context)
    }


    override fun onBindViewHolder(holder: IngredientsViewHolder, position: Int) {
        val ingredient = ingredients[position]

        if (ingredient.image.isEmpty()){
            Glide.with(context).load(R.drawable.ic_placeholder).into(holder.binding.ivIngredientImage)
        }else{
            Glide.with(context).load(ingredient.image).into(holder.binding.ivIngredientImage)
        }

        holder.binding.tvIngredientName.text = ingredient.name

    }

    override fun getItemCount(): Int {
        return ingredients.size
    }
}