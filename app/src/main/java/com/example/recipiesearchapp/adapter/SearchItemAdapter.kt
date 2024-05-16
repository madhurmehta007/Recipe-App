package com.example.recipiesearchapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recipiesearchapp.databinding.ItemSearchBinding
import com.example.recipiesearchapp.models.Result
import com.squareup.picasso.Picasso

class SearchItemAdapter(
    val context: Context,
    val searchItem:MutableList<Result>
):
    RecyclerView.Adapter<SearchItemAdapter.SearchItemViewHolder>(){

    var onItemClick: ((Result) -> Unit)? = null
    class SearchItemViewHolder(val binding: ItemSearchBinding, context: Context):
        RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemSearchBinding.inflate(layoutInflater, parent, false)

        return SearchItemViewHolder(binding, parent.context)
    }

    override fun onBindViewHolder(holder: SearchItemViewHolder, position: Int) {
        val search = searchItem[position]

        Picasso.get().load(search.image).into(holder.binding.ivSearchItem)
        holder.binding.tvSearchedText.text = search.title

        holder.binding.clSearchItem.setOnClickListener {
            onItemClick?.invoke(search)
        }

    }

    override fun getItemCount(): Int {
        return searchItem.size
    }


}