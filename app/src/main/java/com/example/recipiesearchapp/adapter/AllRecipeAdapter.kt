package com.example.recipiesearchapp.adapter

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recipiesearchapp.R
import com.example.recipiesearchapp.databinding.ItemAllRecipeBinding
import com.example.recipiesearchapp.models.RecipeDataBrief
import com.squareup.picasso.Picasso

class AllRecipeAdapter(
    val context: Context,
    private val allRecipeList: MutableList<RecipeDataBrief>,
    val onItemClick: (RecipeDataBrief) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_ITEM = 0
        private const val VIEW_TYPE_AD = 1
    }

    class AllRecipeViewHolder(val binding: ItemAllRecipeBinding, context: Context) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(recipe: RecipeDataBrief, onItemClick: (RecipeDataBrief) -> Unit) {
            Picasso.get().load(recipe.image).into(binding.ivDishImage)
            binding.tvDishName.text = recipe.title
            binding.cvRecipeCard.setOnClickListener {
                onItemClick(recipe)
            }
        }
    }

    class AdViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindAd() {
            // Implement ad binding logic here
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if ((position + 1) % 6 == 0) {
            VIEW_TYPE_AD
        } else {
            VIEW_TYPE_ITEM
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return if (viewType == VIEW_TYPE_ITEM) {
            val binding = ItemAllRecipeBinding.inflate(layoutInflater, parent, false)
            AllRecipeViewHolder(binding, parent.context)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_ad_layout, parent, false)
            AdViewHolder(view)
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is AllRecipeViewHolder) {
            val recipe = allRecipeList[getRealPosition(position)]
            holder.bind(recipe, onItemClick)
        } else if (holder is AdViewHolder) {
            holder.bindAd()
        }
    }

    override fun getItemCount(): Int {
        return allRecipeList.size + (allRecipeList.size / 5)
    }

    private fun getRealPosition(position: Int): Int {
        return position - (position / 6)
    }
}
