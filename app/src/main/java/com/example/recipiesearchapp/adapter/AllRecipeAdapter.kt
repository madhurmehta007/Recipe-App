package com.example.recipiesearchapp.adapter

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recipiesearchapp.databinding.ItemAllRecipeBinding
import com.example.recipiesearchapp.models.RecipeDataBrief
import com.squareup.picasso.Picasso

class AllRecipeAdapter(
    val context: Context,
    val allRecipeList:MutableList<RecipeDataBrief>,
    val onItemClick: (RecipeDataBrief) -> Unit
):
RecyclerView.Adapter<AllRecipeAdapter.AllRecipeViewHolder>(){

//    var onItemClick: ((Result) -> Unit)? = null
    class AllRecipeViewHolder(val binding: ItemAllRecipeBinding, context: Context):
        RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllRecipeViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemAllRecipeBinding.inflate(layoutInflater, parent, false)

        return AllRecipeViewHolder(binding, parent.context)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: AllRecipeViewHolder, position: Int) {
        val recipe = allRecipeList[position]

        Picasso.get().load(recipe.image).into(holder.binding.ivDishImage)
        holder.binding.tvDishName.text = recipe.title

        holder.binding.cvRecipeCard.setOnClickListener {
            onItemClick.invoke(recipe)
        }

//        holder.itemView.setOnLongClickListener {
//            AlertDialog.Builder(context)
//                .setTitle("Delete")
//                .setMessage("Are you sure you want to delete?")
//                .setPositiveButton("Yes") { dialog, _ ->
//                    newsDBViewModel.deleteNews(item)
//                    Snacker(it, "News Deleted").error()
//                }
//                .setNegativeButton("No") { dialog, _ ->
//                    Snacker(it, "News Not Deleted").warning()
//                    newsDBViewModel.updateNews(item)
//                }
//                .setCancelable(true)
//                .show()
//
//            true
//        }
    }

    override fun getItemCount(): Int {
        return allRecipeList.size
    }


}